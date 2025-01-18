package com.undefined.quasar.v1_21_4.impl.entity.vehicle.minecart

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.vehicle.minecart.MinecartHopper
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class MinecartHopper : Minecart(EntityType.HOPPER_MINECART), MinecartHopper {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.vehicle.MinecartHopper(net.minecraft.world.entity.EntityType.HOPPER_MINECART, level)
}