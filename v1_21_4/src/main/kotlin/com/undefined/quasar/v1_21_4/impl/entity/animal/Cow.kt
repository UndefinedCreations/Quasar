package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Cow
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import kotlin.math.E

open class Cow(entityType: EntityType = EntityType.COW) : Animal(entityType), Cow {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Cow(net.minecraft.world.entity.EntityType.COW, level)
}