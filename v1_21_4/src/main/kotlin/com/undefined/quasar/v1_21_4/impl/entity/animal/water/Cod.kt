package com.undefined.quasar.v1_21_4.impl.entity.animal.water

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.water.Cod
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import net.minecraft.world.level.Level

class Cod : Entity(EntityType.COD), Cod {
    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.animal.Cod(net.minecraft.world.entity.EntityType.COD, level)
}