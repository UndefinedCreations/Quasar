package com.undefined.quasar.v1_21_4.impl.entity.animal.horse

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.horse.ZombieHorse
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.AbstractHorse
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class ZombieHorse : AbstractHorse(EntityType.ZOMBIE_HORSE), ZombieHorse {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.horse.ZombieHorse(net.minecraft.world.entity.EntityType.ZOMBIE_HORSE, level)
}