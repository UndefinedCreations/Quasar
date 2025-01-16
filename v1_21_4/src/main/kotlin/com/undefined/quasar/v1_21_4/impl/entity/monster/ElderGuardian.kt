package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.ElderGuardian
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class ElderGuardian : Guardian(EntityType.ELDER_GUARDIAN), ElderGuardian {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.ElderGuardian(net.minecraft.world.entity.EntityType.ELDER_GUARDIAN, level)
}