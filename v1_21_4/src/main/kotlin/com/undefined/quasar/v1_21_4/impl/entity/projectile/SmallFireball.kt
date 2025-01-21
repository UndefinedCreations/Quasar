package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.projectile.SmallFireball
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class SmallFireball : Fireball(EntityType.SMALL_FIREBALL), SmallFireball {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.projectile.SmallFireball(net.minecraft.world.entity.EntityType.SMALL_FIREBALL, level)
}