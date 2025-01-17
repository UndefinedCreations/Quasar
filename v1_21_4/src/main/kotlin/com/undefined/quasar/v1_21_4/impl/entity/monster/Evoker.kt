package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Evoker
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.SpellcasterIllager
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Evoker : SpellcasterIllager(EntityType.EVOKER), Evoker {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Evoker(net.minecraft.world.entity.EntityType.EVOKER, level)
}