package com.undefined.quasar.v1_21_4.impl.entity.decoration

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.decoration.GlowItemFrame
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class GlowItemFrame : ItemFrame(EntityType.GLOW_ITEM_FRAME), GlowItemFrame {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.decoration.GlowItemFrame(net.minecraft.world.entity.EntityType.GLOW_ITEM_FRAME, level)
}