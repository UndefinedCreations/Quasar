package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Giant
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Giant : LivingEntity(EntityType.GIANT), Giant {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Giant(net.minecraft.world.entity.EntityType.GIANT, level)
}