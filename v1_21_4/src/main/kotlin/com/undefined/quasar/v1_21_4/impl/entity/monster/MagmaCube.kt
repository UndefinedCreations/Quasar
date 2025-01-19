package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.MagmaCube
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class MagmaCube : Slime(EntityType.MAGMA_CUBE), MagmaCube {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.MagmaCube(net.minecraft.world.entity.EntityType.MAGMA_CUBE, level)
}