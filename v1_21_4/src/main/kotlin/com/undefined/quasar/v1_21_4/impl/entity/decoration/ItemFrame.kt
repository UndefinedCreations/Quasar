package com.undefined.quasar.v1_21_4.impl.entity.decoration

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.decoration.ItemFrame
import com.undefined.quasar.util.ItemStackDeserializer
import com.undefined.quasar.util.serializer
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

open class ItemFrame(entityType: EntityType = EntityType.ITEM_FRAME) : HangingEntity(entityType), ItemFrame {

    private var DATA_ITEM: EntityDataAccessor<net.minecraft.world.item.ItemStack>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.decoration.ItemFrame::class.java,
            FieldMappings.Entity.Decoration.ItemFrame.DATA_ITEM
        )

    private var DATA_ROTATION: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.decoration.ItemFrame::class.java,
            FieldMappings.Entity.Decoration.ItemFrame.DATA_ROTATION
        )

    override fun setItem(itemStack: ItemStack) = setEntityDataAccessor(DATA_ITEM, CraftItemStack.asNMSCopy(itemStack))

    override fun getItem(): ItemStack = getEntityDataValue(DATA_ITEM)?.let { CraftItemStack.asBukkitCopy(it) } ?: ItemStack(Material.AIR)

    override fun setRotation(rotation: Int) = setEntityDataAccessor(DATA_ROTATION, rotation)

    override fun getRotation(): Int = getEntityDataValue(DATA_ROTATION) ?: 0

    override fun getEntityData(): JsonObject {
        val hangingJson = super.getEntityData()
        val itemFrameJson = JsonObject()
        itemFrameJson.addProperty("item", getItem().serializer())
        itemFrameJson.addProperty("rotation", getRotation())
        hangingJson.add("itemFrame", itemFrameJson)
        return hangingJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<HangingEntity>.setEntityData(jsonObject)
        val itemFrameJson = jsonObject["itemFrame"].asJsonObject
        setRotation(itemFrameJson["rotation"].asInt)
        setItem(ItemStackDeserializer.deserializer(itemFrameJson["item"].asString))
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setItem(ItemStack(Material.AIR))
        setRotation(0)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.decoration.ItemFrame(net.minecraft.world.entity.EntityType.ITEM_FRAME, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setItem(ItemStack(Material.entries.random()))
                getTestMessage(this@ItemFrame::class, "Set item", getItem().type.name.lowercase())
            },
            {
                setItem(ItemStack(Material.AIR))
                getTestMessage(this@ItemFrame::class, "Set item", getItem().type.name.lowercase())
            },
            {
                setRotation(Random.nextInt(5))
                getTestMessage(this@ItemFrame::class, "Set rotation", getRotation())
            },
            {
                setRotation(0)
                getTestMessage(this@ItemFrame::class, "Set rotation", getRotation())
            }
        )) }
}