package com.undefined.quasar.v1_21_4.impl.entity.animal.horse

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.horse.Donkey
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.AbstractChestHorse
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Donkey : AbstractChestHorse(EntityType.DONKEY), Donkey {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.horse.Donkey(net.minecraft.world.entity.EntityType.DONKEY, level)
}