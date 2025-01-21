package com.undefined.quasar.v1_21_4.impl.entity.npc

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.npc.WanderingTrader
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class WanderingTrader : LivingEntity(EntityType.WANDERING_TRADER), WanderingTrader {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.npc.WanderingTrader(net.minecraft.world.entity.EntityType.WANDERING_TRADER, level)
}