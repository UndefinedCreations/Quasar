package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.projectile.ShulkerBullet
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import net.minecraft.world.level.Level

class ShulkerBullet : Entity(EntityType.SHULKER_BULLET), ShulkerBullet {
    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.projectile.ShulkerBullet(net.minecraft.world.entity.EntityType.SHULKER_BULLET, level)
}