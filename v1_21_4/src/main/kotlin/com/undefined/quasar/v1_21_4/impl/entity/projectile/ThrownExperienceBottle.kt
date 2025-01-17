package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.projectile.ThrownExperienceBottle
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.ThrowableItemProjectile
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class ThrownExperienceBottle : ThrowableItemProjectile(EntityType.EXPERIENCE_BOTTLE), ThrownExperienceBottle {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.projectile.ThrownExperienceBottle(net.minecraft.world.entity.EntityType.EXPERIENCE_BOTTLE, level)
}