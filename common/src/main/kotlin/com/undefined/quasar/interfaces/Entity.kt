package com.undefined.quasar.interfaces

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.undefined.quasar.enums.EntityType
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

interface Entity {

    val entityType: EntityType

    fun addViewer(player: Player)

    fun removeViewer(player: Player)

    fun hasViewer(player: Player): Boolean

    fun spawn(location: Location)

    fun kill()

    fun setCustomName(name: String?)

    fun getCustomName(): String?

    fun hasCustomName(): Boolean

    fun isAlive(): Boolean

    fun teleport(location: Location)

    fun teleport(entity: Entity) = teleport(entity.location)

    fun getLocation(): Location

    fun setVisualFire(fire: Boolean)

    fun isVisualFire(): Boolean

    fun setVisualFreezing(freezing: Boolean)

    fun isFreezing(): Boolean

    fun setVisible()

    fun isViable(): Boolean

    fun setCollidable(collibable: Boolean)

    fun isCollidable(): Boolean

    fun setRotation(yaw: Float, pitch: Float)

    fun addPassenger(entity: Entity)

    fun removePassenger(entity: Entity)

    fun clearPassenger()

    fun setGlowing(glow: Boolean)

    fun setGlowingColor(chatColor: ChatColor)

    fun isGlowing(): Boolean

    fun setGravity(gravity: Boolean)

    fun hasGravity(): Boolean

    fun setEntityData(string: String) = setEntityData(JsonParser.parseString(string).asJsonObject)

    fun setEntityData(jsonObject: JsonObject)

    fun getEntityData(): JsonObject

    fun updateEntity()

    fun runTest(logger: Player, delayTime: Int = 10, entityTests: (Exception?) -> Unit = {}, specificTests: (Exception?) -> Unit = {})

}