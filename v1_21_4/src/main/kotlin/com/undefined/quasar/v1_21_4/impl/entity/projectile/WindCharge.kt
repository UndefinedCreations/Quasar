package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.projectile.WindCharge
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import net.minecraft.world.level.Level


class WindCharge : Entity(EntityType.WIND_CHARGE), WindCharge {
    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.projectile.windcharge.WindCharge(net.minecraft.world.entity.EntityType.WIND_CHARGE, level)
}