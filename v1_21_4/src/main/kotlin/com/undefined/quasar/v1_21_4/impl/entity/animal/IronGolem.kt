package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.IronGolem
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class IronGolem : LivingEntity(EntityType.IRON_GOLEM), IronGolem {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.IronGolem(net.minecraft.world.entity.EntityType.IRON_GOLEM, level)
}