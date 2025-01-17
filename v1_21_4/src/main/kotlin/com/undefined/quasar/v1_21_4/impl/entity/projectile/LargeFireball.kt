package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.projectile.LargeFireball
import net.minecraft.world.level.Level

class LargeFireball : Fireball(EntityType.FIREBALL), LargeFireball {
    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.projectile.LargeFireball(net.minecraft.world.entity.EntityType.FIREBALL, level)
}