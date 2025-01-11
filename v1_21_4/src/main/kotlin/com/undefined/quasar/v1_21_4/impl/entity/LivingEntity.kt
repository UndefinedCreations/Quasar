package com.undefined.quasar.v1_21_4.impl.entity

import com.google.gson.JsonObject
import com.mojang.datafixers.util.Pair
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.LivingEntity
import com.undefined.quasar.util.repeat
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket
import net.minecraft.network.protocol.game.ClientboundHurtAnimationPacket
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket
import net.minecraft.world.entity.EquipmentSlot
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.math.floor

abstract class LivingEntity(entityType: EntityType): LivingEntity, Entity(entityType) {

    private val items: HashMap<Int, ItemStack> = hashMapOf()

    override fun moveTo(location: Location) {
        val entity = entity ?: return

        if (getLocation().distance(location) > 8) return

        val deltaX = toDeltaValue(getLocation().x, location.x)
        val deltaY = toDeltaValue(getLocation().y, location.y)
        val deltaZ = toDeltaValue(getLocation().z, location.z)
        val isOnGround = location.clone().subtract(0.0, 1.0, 0.0).block.type.isSolid

        val headRotationYaw = toRotationValue(location.yaw)
        val headRotationPitch = toRotationValue(location.pitch)

        sendPackets(ClientboundMoveEntityPacket.PosRot(
            entity.id,
            deltaX,
            deltaY,
            deltaZ,
            headRotationYaw,
            headRotationPitch,
            isOnGround
        ), ClientboundRotateHeadPacket(
                entity,
                headRotationYaw
        )
        )
        setLocation(location)
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

    private fun toRotationValue(yaw: Float): Byte = floor(yaw * 256.0f / 360.0f).toInt().toByte()
    private fun toDeltaValue(value: Double, newValue: Double) = (((newValue - value) * 32 * 128).toInt().toShort())
    override fun setRotation(yaw: Float, pitch: Float) {
        val entity = entity ?: return
        sendPackets(ClientboundMoveEntityPacket.Rot(entity.id, toRotationValue(yaw), toRotationValue(pitch), true))
    }

    override fun getEntityData(): JsonObject {
        val entityJson = super.getEntityData()
        val livingEntityJson = JsonObject()

        return entityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
    }

    override fun runTest(logger: Player, delayTime: Int, testStage: (Exception?) -> Unit, done: (Unit) -> Unit): Int {
        super.runTest(logger, delayTime, { e ->
            trycatch({
                if (e != null) return@trycatch
                var time = 0
                repeat(6, delayTime) {
                    when(time) {
                        0 -> {
                            val location = getLocation().clone().add(0.0, 5.0, 0.0)
                            moveTo(location)
                            logger.sendMessage("${ChatColor.GRAY} LivingEntity | Move to {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${Math.round(location.x)}, ${Math.round(location.y)}, ${Math.round(location.z)}${ChatColor.GRAY}]")
                        }
                        1 -> {
                            val location = getLocation().clone().subtract(0.0, 5.0, 0.0)
                            moveTo(location)
                            logger.sendMessage("${ChatColor.GRAY} LivingEntity | Move to {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${Math.round(location.x)}, ${Math.round(location.y)}, ${Math.round(location.z)}${ChatColor.GRAY}]")
                        }
                        2 -> {
                            damageAnimation()
                            logger.sendMessage("${ChatColor.GRAY} LivingEntity | Damage animation {${ChatColor.GREEN}Success!${ChatColor.GRAY}}")
                        }
                        3 -> {
                            val item = Material.entries.random()
                            val slot = LivingEntity.EquipmentSlot.entries.random()
                            setItem(slot, item)
                            logger.sendMessage("${ChatColor.GRAY} LivingEntity | Set item {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${slot.name.lowercase()}, ${item.name.lowercase()}${ChatColor.GRAY}]")
                        }
                        4 -> {
                            clearItems()
                            logger.sendMessage("${ChatColor.GRAY} LivingEntity | Clear items {${ChatColor.GREEN}Success!${ChatColor.GRAY}}")
                        }
                        5 -> {
                            testStage(null)
                        }
                    }
                    time++
                }
            }, testStage)
        }, done)

        return 2
    }
}