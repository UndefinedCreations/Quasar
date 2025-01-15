package com.undefined.quasar.v1_21_4.impl.entity.vehicle.minecart

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.vehicle.minecart.MinecartChest
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class MinecartChest : Minecart(EntityType.CHEST_MINECART), MinecartChest {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.vehicle.MinecartChest(net.minecraft.world.entity.EntityType.CHEST_MINECART, level)
}