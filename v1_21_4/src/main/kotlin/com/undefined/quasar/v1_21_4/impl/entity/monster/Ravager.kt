package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Ravager
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Raider
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Ravager : Raider(EntityType.RAVAGER), Ravager {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Ravager(net.minecraft.world.entity.EntityType.RAVAGER, level)
}