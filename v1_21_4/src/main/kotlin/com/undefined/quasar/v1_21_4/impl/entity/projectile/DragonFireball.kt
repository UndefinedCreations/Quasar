package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.undefined.quasar.enums.EntityType

import com.undefined.quasar.interfaces.entities.entity.projectile.DragonFireball
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import net.minecraft.world.level.Level

class DragonFireball : Entity(EntityType.DRAGON_FIREBALL), DragonFireball {
    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.projectile.DragonFireball(net.minecraft.world.entity.EntityType.DRAGON_FIREBALL, level)
}