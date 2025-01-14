package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import net.minecraft.world.level.Level

class BreezeWindCharge : Entity(EntityType.BREEZE_WIND_CHARGE),
    com.undefined.quasar.interfaces.entities.entity.projectile.BreezeWindCharge {
    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.projectile.windcharge.BreezeWindCharge(
            net.minecraft.world.entity.EntityType.BREEZE_WIND_CHARGE,
            level
        )
}