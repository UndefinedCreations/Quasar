package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.monster.WitherSkeleton
import net.minecraft.world.level.Level

class WitherSkeleton : LivingEntity(EntityType.WITHER_SKELETON), com.undefined.quasar.interfaces.entities.entity.monster.WitherSkeleton {
    override fun getEntityClass(level: Level): Entity =
        WitherSkeleton(net.minecraft.world.entity.EntityType.WITHER_SKELETON, level)
}