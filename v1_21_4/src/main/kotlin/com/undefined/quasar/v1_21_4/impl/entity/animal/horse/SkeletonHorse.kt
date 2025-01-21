package com.undefined.quasar.v1_21_4.impl.entity.animal.horse

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.horse.SkeletonHorse
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.AbstractHorse
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class SkeletonHorse : AbstractHorse(EntityType.SKELETON_HORSE), SkeletonHorse {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.horse.SkeletonHorse(net.minecraft.world.entity.EntityType.SKELETON_HORSE, level)
}