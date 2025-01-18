package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Illusioner
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.SpellcasterIllager
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Illusioner : SpellcasterIllager(EntityType.ILLUSIONER), Illusioner {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Illusioner(net.minecraft.world.entity.EntityType.ILLUSIONER, level)
}