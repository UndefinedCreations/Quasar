package com.undefined.quasar.v1_21_4.impl.entity

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.LightningBolt
import net.minecraft.world.level.Level

class LightningBolt : Entity(EntityType.LIGHTNING_BOLT), LightningBolt {
    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.LightningBolt(net.minecraft.world.entity.EntityType.LIGHTNING_BOLT, level)
}