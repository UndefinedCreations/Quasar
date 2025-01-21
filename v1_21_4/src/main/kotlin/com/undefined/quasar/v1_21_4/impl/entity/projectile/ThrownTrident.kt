package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.abstracts.Projectile
import com.undefined.quasar.interfaces.entities.entity.projectile.ThrownTrident
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import net.minecraft.world.level.Level

class ThrownTrident : Entity(EntityType.TRIDENT), ThrownTrident {
    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.projectile.ThrownTrident(net.minecraft.world.entity.EntityType.TRIDENT, level)
}