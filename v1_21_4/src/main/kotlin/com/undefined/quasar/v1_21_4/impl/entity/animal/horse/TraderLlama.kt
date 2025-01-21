package com.undefined.quasar.v1_21_4.impl.entity.animal.horse

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.horse.TraderLlama
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class TraderLlama : Llama(EntityType.TRADER_LLAMA), TraderLlama {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.horse.TraderLlama(net.minecraft.world.entity.EntityType.TRADER_LLAMA, level)
}