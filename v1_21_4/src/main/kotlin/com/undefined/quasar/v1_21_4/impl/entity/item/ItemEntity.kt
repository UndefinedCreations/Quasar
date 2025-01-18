package com.undefined.quasar.v1_21_4.impl.entity.item

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.item.ItemEntity
import com.undefined.quasar.util.ItemStackDeserializer
import com.undefined.quasar.util.serializer
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.level.Level
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

class ItemEntity : Entity(EntityType.ITEM), ItemEntity {

    private var DATA_ITEM: EntityDataAccessor<net.minecraft.world.item.ItemStack>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.item.ItemEntity::class.java,
            FieldMappings.Entity.ItemEntity.DATA_ITEM
        )

    override fun setItem(itemStack: ItemStack) = setEntityDataAccessor(DATA_ITEM, CraftItemStack.asNMSCopy(itemStack))

    override fun getItem(): ItemStack = getEntityDataValue(DATA_ITEM)?.let { CraftItemStack.asBukkitCopy(it) } ?: ItemStack(Material.STONE)

    override fun getEntityData(): JsonObject {
        val entityJson = super.getEntityData()
        val itemJson = JsonObject()
        itemJson.addProperty("item", getItem().serializer())
        entityJson.add("item", itemJson)
        return entityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val itemJson = jsonObject["item"].asJsonObject
        setItem(ItemStackDeserializer.deserializer(itemJson["item"].asString))
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setItem(ItemStack(Material.STONE))
    }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.item.ItemEntity(net.minecraft.world.entity.EntityType.ITEM, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setItem(ItemStack(Material.entries.random()))
                getTestMessage(this@ItemEntity::class, "Set item", getItem().type.name.lowercase())
            }
        )) }
}