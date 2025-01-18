package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.projectile.EyeOfEnder
import com.undefined.quasar.util.ItemStackDeserializer
import com.undefined.quasar.util.serializer
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.level.Level
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

class EyeOfEnder : Entity(EntityType.EYE_OF_ENDER), EyeOfEnder {

    private var DATA_ITEM_STACK: EntityDataAccessor<net.minecraft.world.item.ItemStack>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.projectile.EyeOfEnder::class.java,
            FieldMappings.Entity.Projectile.EyeOfEnder.DATA_ITEM_STACK
        )

    override fun setItem(item: ItemStack) = setEntityDataAccessor(DATA_ITEM_STACK, CraftItemStack.asNMSCopy(item))

    override fun getItem(): ItemStack = getEntityDataValue(DATA_ITEM_STACK)?.let { CraftItemStack.asBukkitCopy(it) } ?: ItemStack(Material.ENDER_EYE)

    override fun getEntityData(): JsonObject {
        val entityJson = super.getEntityData()
        val eyeOfEnderJson = JsonObject()
        eyeOfEnderJson.addProperty("item", getItem().serializer())
        entityJson.add("eyeOfEnder", eyeOfEnderJson)
        return entityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val eyeOfEnderJson = jsonObject["eyeOfEnder"].asJsonObject
        setItem(ItemStackDeserializer.deserializer(eyeOfEnderJson["item"].asString))
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setItem(ItemStack(Material.ENDER_EYE))
    }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.projectile.EyeOfEnder(net.minecraft.world.entity.EntityType.EYE_OF_ENDER, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setItem(ItemStack(Material.entries.random()))
                getTestMessage(this@EyeOfEnder::class, "Set item", getItem().type.name.lowercase())
            }
        )) }
}