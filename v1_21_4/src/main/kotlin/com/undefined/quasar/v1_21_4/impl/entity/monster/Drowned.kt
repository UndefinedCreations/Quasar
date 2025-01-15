package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Drowned
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Drowned : Zombie(EntityType.DROWNED), Drowned {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Drowned(net.minecraft.world.entity.EntityType.DROWNED, level)
}