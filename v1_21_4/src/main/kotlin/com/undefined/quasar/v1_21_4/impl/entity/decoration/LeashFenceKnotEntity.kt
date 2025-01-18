package com.undefined.quasar.v1_21_4.impl.entity.decoration

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.decoration.LeashFenceKnotEntity
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class LeashFenceKnotEntity : BlockAttachedEntity(EntityType.LEASH_KNOT), LeashFenceKnotEntity {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.decoration.LeashFenceKnotEntity(net.minecraft.world.entity.EntityType.LEASH_KNOT, level)
}