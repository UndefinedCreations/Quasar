package com.undefined.quasar.interfaces

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.undefined.quasar.enums.EntityType
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*

interface Entity {
    val entityType: EntityType

    fun getUUID(): UUID
    fun addViewer(player: Player)
    fun removeViewer(player: Player)
    fun hasViewer(player: Player): Boolean
    fun spawn(location: Location)
    fun kill()
    fun respawn() {
        kill()
        spawn(getLocation())
    }
    fun setCustomName(name: String?)
    fun getCustomName(): String?
    fun hasCustomName(): Boolean { return getCustomName() != null }
    fun isCustomNameVisible(): Boolean
    fun setCustomNameVisibility(visible: Boolean)
    fun isAlive(): Boolean
    fun teleport(location: Location)
    fun teleport(entity: org.bukkit.entity.Entity) = teleport(entity.location)
    fun moveTo(location: Location)
    fun moveTo(entity: org.bukkit.entity.Entity) { moveTo(entity.location) }
    fun moveOrTeleport(location: Location) = if (getLocation().distance(location) > 8) teleport(location) else moveTo(location)
    fun moveOrTeleport(entity: org.bukkit.entity.Entity) { moveOrTeleport(entity.location) }
    fun getLocation(): Location
    fun setVisualFire(fire: Boolean)
    fun isVisualFire(): Boolean
    fun setVisualFreezing(freezing: Boolean)
    fun isFreezing(): Boolean
    fun setVisible(visible: Boolean)
    fun isVisible(): Boolean
    fun setCollidable(collidable: Boolean)
    fun isCollidable(): Boolean
    fun setRotation(yaw: Float, pitch: Float)
    fun addPassenger(passenger: Entity)
    fun removePassenger(passenger: Entity)
    fun getPassengers(): List<Entity>
    fun clearPassenger()
    fun setGlowing(glow: Boolean)
    fun setGlowingColor(chatColor: ChatColor)
    fun getGlowingColor(): ChatColor
    fun isGlowing(): Boolean
    fun setGravity(gravity: Boolean)
    fun hasGravity(): Boolean
    fun isSilent(): Boolean
    fun setSilent(silent: Boolean)
    fun setSleeping()
    fun isSleeping(): Boolean
    fun setStanding()
    fun isStanding(): Boolean
    fun setEntityData(string: String) = setEntityData(JsonParser.parseString(string).asJsonObject)
    fun setEntityData(jsonObject: JsonObject)
    fun getEntityData(): JsonObject
    fun updateEntity()
    fun runTest(
        logger: Player,
        delayTime: Int = 10,
        exception: (Exception) -> Unit = {},
        done: () -> Unit = {},
        sendMessage: Boolean = true
    )
    fun getTests(): MutableList<() -> String>
}