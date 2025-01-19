package com.undefined.quasar.v1_21_4.impl.entity.animal.water

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.water.Squid
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

open class Squid(entityType: EntityType = EntityType.SQUID) : com.undefined.quasar.v1_21_4.impl.entity.Entity(entityType), Squid {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Squid(net.minecraft.world.entity.EntityType.SQUID, level)
}