package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Vindicator
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Raider
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Vindicator : Raider(EntityType.VINDICATOR), Vindicator{
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Vindicator(net.minecraft.world.entity.EntityType.VINDICATOR, level)
}