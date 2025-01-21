package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.ZombifiedPiglin
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class ZombifiedPiglin : Zombie(EntityType.ZOMBIFIED_PIGLIN), ZombifiedPiglin {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.ZombifiedPiglin(net.minecraft.world.entity.EntityType.ZOMBIFIED_PIGLIN, level)
}