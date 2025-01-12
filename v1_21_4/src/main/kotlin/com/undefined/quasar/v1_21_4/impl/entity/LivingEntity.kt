package com.undefined.quasar.v1_21_4.impl.entity

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.mojang.datafixers.util.Pair
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.LivingEntity
import com.undefined.quasar.util.ItemStackDeserializer
import com.undefined.quasar.util.serializer
import net.minecraft.network.protocol.game.*
import net.minecraft.world.entity.EquipmentSlot
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack
import kotlin.math.floor

abstract class LivingEntity(entityType: EntityType): LivingEntity, Entity(entityType) {

    private val items: HashMap<Int, ItemStack> = hashMapOf()

    override fun deathAnimation() {
        val entity = entity ?: return
        sendPackets(
            ClientboundEntityEventPacket(entity, 3),
            ClientboundEntityEventPacket(entity, 60)
        )
    }
    override fun damageAnimation() {
        val entity = entity ?: return
        sendPackets(
            ClientboundHurtAnimationPacket(entity.id, 0f)
        )
    }
    override fun getItem(slot: Int): ItemStack? = items[slot]
    override fun setItem(slot: Int, itemStack: ItemStack?) {
        val entity = entity ?: return
        val notNullItemStack = itemStack ?: ItemStack(Material.AIR)
        val nmsItemStack = CraftItemStack.asNMSCopy(notNullItemStack)
        val equipmentSlot = LivingEntity.EquipmentSlot.entries.filter { it.slot == slot }.getOrNull(0) ?: return

        if (notNullItemStack.type == Material.AIR) items.remove(slot) else items[slot] = notNullItemStack

        sendPackets(ClientboundSetEquipmentPacket(
            entity.id,
            mutableListOf(
                Pair(
                    EquipmentSlot.valueOf(equipmentSlot.name),
                    nmsItemStack
                )
            )
        ))
        sendEntityMetaData()
    }
    override fun setRotation(yaw: Float, pitch: Float) {
        val entity = entity ?: return
        sendPackets(ClientboundMoveEntityPacket.Rot(entity.id, toRotationValue(yaw), toRotationValue(pitch), true))
    }
    override fun getEntityData(): JsonObject {
        val entityJson = super.getEntityData()
        val livingEntityJson = JsonObject()
        val itemArray = JsonArray()
        items.forEach { (slot, item) ->
            itemArray.add("$slot@${item.serializer()}")
        }
        livingEntityJson.add("items", itemArray)
        entityJson.add("livingEntity", livingEntityJson)
        return entityJson
    }
    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val livingEntityJson = jsonObject["livingEntity"].asJsonObject
        val itemArray = livingEntityJson["items"].asJsonArray
        itemArray.forEach {
            val split = it.asString.split("@")
            val slot = split[0].toInt()
            val item = ItemStackDeserializer.deserializer(split[1])
            items[slot] = item
        }
    }
    override fun updateEntity() {
        items.forEach { setItem(it.key, it.value) }
    }
    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                damageAnimation()
                getTestMessage(this@LivingEntity::class, "Damage animation")
            },
            {
                val item = Material.entries.random()
                val slot = LivingEntity.EquipmentSlot.entries.random()
                setItem(slot, item)
                getTestMessage(this@LivingEntity::class, "Set item", slot.name.lowercase(), item.name.lowercase())
            },
            {
                clearItems()
                getTestMessage(this@LivingEntity::class, "Clear items")
            }
        )) }
}