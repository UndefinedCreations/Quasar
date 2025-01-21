package com.undefined.quasar.v1_21_4.impl.entity.animal.water

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.water.Tadpole
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Tadpole : LivingEntity(EntityType.TADPOLE), Tadpole {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.frog.Tadpole(net.minecraft.world.entity.EntityType.TADPOLE, level)
}