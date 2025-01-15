package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Chicken
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Chicken : Animal(EntityType.CHICKEN), Chicken {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Chicken(net.minecraft.world.entity.EntityType.CHICKEN, level)
}