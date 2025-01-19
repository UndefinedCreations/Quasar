package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.PiglinBrute
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.AbstractPiglin
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class PiglinBrute : AbstractPiglin(EntityType.PIGLIN_BRUTE), PiglinBrute {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.piglin.PiglinBrute(net.minecraft.world.entity.EntityType.PIGLIN_BRUTE, level)
}