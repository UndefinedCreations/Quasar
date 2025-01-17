package com.undefined.quasar.v1_21_4.impl.entity

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.ExperienceOrb
import net.minecraft.world.level.Level

class ExperienceOrb : Entity(EntityType.EXPERIENCE_ORB), ExperienceOrb {
    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.ExperienceOrb(net.minecraft.world.entity.EntityType.EXPERIENCE_ORB, level)
}