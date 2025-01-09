package com.undefined.quasar.interfaces

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.vehicle.Minecart
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player
import kotlin.random.Random

interface Entity {

    val entityType: EntityType

    fun addViewer(player: Player)
    fun removeViewer(player: Player)
    fun hasViewer(player: Player): Boolean
    fun spawn(location: Location)
    fun kill()
    fun setCustomName(name: String?)
    fun getCustomName(): String?
    fun hasCustomName(): Boolean { return getCustomName() != null }
    fun isCustomNameVisible(): Boolean
    fun setCustomNameVisibility(visible: Boolean)
    fun isAlive(): Boolean
    fun teleport(location: Location)
    fun teleport(entity: org.bukkit.entity.Entity) = teleport(entity.location)
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
    fun setEntityData(string: String) = setEntityData(JsonParser.parseString(string).asJsonObject)
    fun setEntityData(jsonObject: JsonObject)
    fun getEntityData(): JsonObject
    fun updateEntity()
    fun runTest(
        logger: Player,
        delayTime: Int = 10,
        testStage: (Exception?) -> Unit = {},
        done: (Unit) -> Unit = {}
    ): Int
}