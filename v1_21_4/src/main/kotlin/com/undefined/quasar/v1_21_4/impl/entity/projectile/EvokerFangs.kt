package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.projectile.EvokerFangs
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import net.minecraft.world.level.Level

class EvokerFangs : Entity(EntityType.EVOKER_FANGS), EvokerFangs {
    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.projectile.EvokerFangs(net.minecraft.world.entity.EntityType.EVOKER_FANGS, level)
}