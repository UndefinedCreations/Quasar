package com.undefined.quasar.v1_21_4.impl.entity

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.OminousItemSpawner
import com.undefined.quasar.util.ItemStackDeserializer
import com.undefined.quasar.util.serializer
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.level.Level
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

class OminousItemSpawner : Entity(EntityType.OMINOUS_ITEM_SPAWNER), OminousItemSpawner {

    private var DATA_ITEM: EntityDataAccessor<net.minecraft.world.item.ItemStack>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.OminousItemSpawner::class.java,
            FieldMappings.Entity.OminousItemSpawner.DATA_ITEM
        )

    override fun setItem(item: ItemStack) = setEntityDataAccessor(DATA_ITEM, CraftItemStack.asNMSCopy(item))

    override fun getItem(): ItemStack = getEntityDataValue(DATA_ITEM)?.let { CraftItemStack.asBukkitCopy(it) } ?: ItemStack(Material.AIR)

    override fun getEntityData(): JsonObject {
        val entityJson = super.getEntityData()
        val itemSpawner = JsonObject()
        itemSpawner.addProperty("item", getItem().serializer())
        entityJson.add("ominousItemSpawner", itemSpawner)
        return entityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val itemSpawnJson = jsonObject["ominousItemSpawner"].asJsonObject
        setItem(ItemStackDeserializer.deserializer(itemSpawnJson["item"].asString))
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setItem(ItemStack(Material.AIR))
    }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.OminousItemSpawner(net.minecraft.world.entity.EntityType.OMINOUS_ITEM_SPAWNER, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setItem(ItemStack(Material.entries.random()))
                getTestMessage(this@OminousItemSpawner::class, "Set item", getItem().type.name.lowercase())
            },
            {
                setItem(ItemStack(Material.AIR))
                getTestMessage(this@OminousItemSpawner::class, "Set item", getItem().type.name.lowercase())
            }
        )) }
}