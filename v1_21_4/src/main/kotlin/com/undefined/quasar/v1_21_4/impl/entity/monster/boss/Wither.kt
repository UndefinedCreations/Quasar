package com.undefined.quasar.v1_21_4.impl.entity.monster.boss

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.boss.Wither
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.boss.wither.WitherBoss
import net.minecraft.world.level.Level

class Wither : LivingEntity(EntityType.WITHER), Wither {
    override fun getEntityClass(level: Level): Entity =
        WitherBoss(net.minecraft.world.entity.EntityType.WITHER, level)
}