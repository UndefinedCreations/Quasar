package com.undefined.quasar.v1_21_4.impl.entity.vehicle.minecart

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.vehicle.minecart.MinecartSpawner
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class MinecartSpawner : Minecart(EntityType.SPAWNER_MINECART), MinecartSpawner {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.vehicle.MinecartSpawner(net.minecraft.world.entity.EntityType.SPAWNER_MINECART, level)
}