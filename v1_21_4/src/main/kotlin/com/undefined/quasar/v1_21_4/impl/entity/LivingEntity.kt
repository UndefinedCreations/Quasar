package com.undefined.quasar.v1_21_4.impl.entity

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.mojang.datafixers.util.Pair
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.LivingEntity
import com.undefined.quasar.util.ItemStackDeserializer
import com.undefined.quasar.util.serializer
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.protocol.game.*
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.ai.attributes.Attributes
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

abstract class LivingEntity(entityType: EntityType): LivingEntity, Entity(entityType) {

    private val items: HashMap<Int, ItemStack> = hashMapOf()

    private var usingMainHand = false
    private var usingOffhand = false

    private var DATA_LIVING_ENTITY_FLAGS: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.LivingEntity::class.java,
            FieldMappings.Entity.LivingEntity.DATA_LIVING_ENTITY_FLAGS
        )

    override fun setHeadRotation(yaw: Float) {
        val serverPlayer = entity ?: return
        sendPackets(ClientboundRotateHeadPacket(serverPlayer, toRotationValue(yaw)))
    }

    override fun isUsingItem(offhand: Boolean): Boolean = if (offhand) usingOffhand else usingMainHand

    override fun stopUsingItem() {
        entity ?: return
        setLivingEntityFlag(1, false)
        sendEntityMetaData()
        usingMainHand = false
        usingOffhand = false
    }

    override fun useItem(offhand: Boolean) {
        entity ?: return
        setLivingEntityFlag(1, true)
        setLivingEntityFlag(2, offhand)
        sendEntityMetaData()
        if (offhand) usingOffhand = true else usingMainHand = true
    }

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

    override fun setScale(double: Double) {
        val entity = entity as? net.minecraft.world.entity.LivingEntity ?: return
        entity.getAttribute(Attributes.SCALE)?.baseValue = double
        sendPackets(ClientboundUpdateAttributesPacket(entity.id, entity.attributes.attributesToUpdate))
    }

    override fun getScale(): Double {
        val entity = entity as? net.minecraft.world.entity.LivingEntity ?: return 1.0
        return entity.getAttribute(Attributes.SCALE)?.baseValue ?: 1.0
    }

    private fun setLivingEntityFlag(i: Int, flag: Boolean) {
        val entity = entity ?: return
        var j = (entity.entityData.get(DATA_LIVING_ENTITY_FLAGS) as Byte).toInt()
        j = if (flag) {
            j or i
        } else {
            j and i.inv()
        }
        entity.entityData.set(DATA_LIVING_ENTITY_FLAGS, j.toByte())
    }

    override fun getEntityData(): JsonObject {
        val entityJson = super.getEntityData()
        val livingEntityJson = JsonObject()
        val itemArray = JsonArray()
        items.forEach { (slot, item) ->
            itemArray.add("$slot@${item.serializer()}")
        }
        livingEntityJson.add("items", itemArray)
        livingEntityJson.addProperty("scale", getScale())
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
        setScale(livingEntityJson["scale"].asDouble)
    }

    override fun setDefaultValues() {
        items.forEach { setItem(it.key, it.value) }
        setScale(1.0)
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
            },
            {
                setScale(Random.nextDouble(1.0, 2.0))
                getTestMessage(this@LivingEntity::class, "Set scale", getScale())
            },
            {
                setScale(1.0)
                getTestMessage(this@LivingEntity::class, "Set scale", getScale())
            }
        )) }
}