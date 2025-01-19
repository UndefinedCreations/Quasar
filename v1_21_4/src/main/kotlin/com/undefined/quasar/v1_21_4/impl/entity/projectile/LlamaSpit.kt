package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.projectile.LlamaSpit
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import net.minecraft.world.level.Level

class LlamaSpit : Entity(EntityType.LLAMA_SPIT), LlamaSpit {
    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.projectile.LlamaSpit(net.minecraft.world.entity.EntityType.LLAMA_SPIT, level)
}