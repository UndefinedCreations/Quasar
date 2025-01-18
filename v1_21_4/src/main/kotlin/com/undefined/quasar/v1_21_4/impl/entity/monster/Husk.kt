package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Husk
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Husk : LivingEntity(EntityType.HUSK), Husk {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Husk(net.minecraft.world.entity.EntityType.HUSK, level)
}