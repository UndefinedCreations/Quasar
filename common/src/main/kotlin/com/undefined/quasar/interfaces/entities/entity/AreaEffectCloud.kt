package com.undefined.quasar.interfaces.entities.entity

import com.undefined.quasar.interfaces.Entity
import org.bukkit.Color
import org.bukkit.Location

interface AreaEffectCloud : Entity {

    fun setRadius(radius: Double) = setRadius(radius.toFloat())
    fun setRadius(float: Float)
    fun getRadius(): Float

    fun setParticleColor(color: Color)
    fun getParticleColor(): Color?

    fun isInCloud(location: Location): Boolean
    fun isInCloud(entity: org.bukkit.entity.Entity): Boolean = isInCloud(entity.location)

}