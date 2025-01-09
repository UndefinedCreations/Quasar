package com.undefined.quasar.interfaces

import org.bukkit.Location

interface LivingEntity : Entity {

    fun moveTo(location: Location)
    fun moveTo(entity: org.bukkit.entity.Entity) { moveTo(entity.location) }

    fun moveOrTeleport(location: Location) =
        if (getLocation().distance(location) > 8) teleport(location) else moveTo(location)
    fun moveOrTeleport(entity: org.bukkit.entity.Entity) { moveOrTeleport(entity.location) }

    fun deathAnimation()
    fun damageAnimation()

}