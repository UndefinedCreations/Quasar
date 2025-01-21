package com.undefined.quasar.v1_21_4.impl.entity.vehicle.minecart

import com.undefined.quasar.enums.EntityType
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.vehicle.MinecartTNT
import net.minecraft.world.level.Level

class MinecartTNT : Minecart(EntityType.TNT_MINECART),
    com.undefined.quasar.interfaces.entities.entity.vehicle.minecart.MinecartTNT {
    override fun getEntityClass(level: Level): Entity =
        MinecartTNT(net.minecraft.world.entity.EntityType.TNT_MINECART, level)
}