package com.unedfined.quasar.v1_21_4

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.Entity
import org.bukkit.ChatColor
import org.bukkit.Location

abstract class Entity(
    override val entityType: EntityType
) : AbstractEntity(entityType), Entity {

    private var customName: String? = null

    override fun setCustomName(name: String?) {
        TODO("Not yet implemented")
    }

    override fun getCustomName(): String? {
        TODO("Not yet implemented")
    }

    override fun hasCustomName(): Boolean {
        TODO("Not yet implemented")
    }

    override fun teleport(location: Location) {
        TODO("Not yet implemented")
    }

    override fun getLocation(): Location {
        TODO("Not yet implemented")
    }

    override fun setVisualFire(fire: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isVisualFire(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setVisualFreezing(freezing: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isFreezing(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setVisible() {
        TODO("Not yet implemented")
    }

    override fun isViable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setCollidable(collibable: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isCollidable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setRotation(yaw: Float, pitch: Float) {
        TODO("Not yet implemented")
    }

    override fun addPassenger(entity: org.bukkit.entity.Entity) {
        TODO("Not yet implemented")
    }

    override fun removePassenger(entity: org.bukkit.entity.Entity) {
        TODO("Not yet implemented")
    }

    override fun clearPassenger() {
        TODO("Not yet implemented")
    }

    override fun setGlowing(glow: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setGlowingColor(chatColor: ChatColor) {
        TODO("Not yet implemented")
    }

    override fun isGlowing(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setGravity(gravity: Boolean) {
        TODO("Not yet implemented")
    }

    override fun hasGravity(): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateEntity() {

    }
}