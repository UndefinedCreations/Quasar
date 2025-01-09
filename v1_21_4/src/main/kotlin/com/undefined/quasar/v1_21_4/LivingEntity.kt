package com.undefined.quasar.v1_21_4

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.LivingEntity
import com.undefined.quasar.util.repeat
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket
import net.minecraft.network.protocol.game.ClientboundHurtAnimationPacket
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player
import kotlin.math.floor

abstract class LivingEntity(entityType: EntityType): LivingEntity, Entity(entityType) {

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

    private fun toRotationValue(yaw: Float): Byte = floor(yaw * 256.0f / 360.0f).toInt().toByte()
    private fun toDeltaValue(value: Double, newValue: Double) = (((newValue - value) * 32 * 128).toInt().toShort())
    override fun setRotation(yaw: Float, pitch: Float) {
        val entity = entity ?: return
        sendPackets(ClientboundMoveEntityPacket.Rot(entity.id, toRotationValue(yaw), toRotationValue(pitch), true))
    }
    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
    }

    override fun runTest(logger: Player, delayTime: Int, testStage: (Exception?) -> Unit, done: (Unit) -> Unit): Int {
        super.runTest(logger, delayTime, { e ->
            trycatch({
                if (e != null) return@trycatch
                var time = 0
                repeat(4, delayTime) {
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