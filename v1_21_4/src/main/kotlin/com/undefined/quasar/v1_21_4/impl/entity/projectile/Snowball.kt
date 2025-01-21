package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.projectile.Snowball
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.ThrowableItemProjectile
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Snowball : ThrowableItemProjectile(EntityType.SNOWBALL), Snowball {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.projectile.Snowball(net.minecraft.world.entity.EntityType.SNOWBALL, level)
}