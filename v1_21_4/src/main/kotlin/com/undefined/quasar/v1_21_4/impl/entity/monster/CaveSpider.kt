package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.CaveSpider
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class CaveSpider : Spider(EntityType.CAVE_SPIDER), CaveSpider {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.CaveSpider(net.minecraft.world.entity.EntityType.CAVE_SPIDER, level)
}