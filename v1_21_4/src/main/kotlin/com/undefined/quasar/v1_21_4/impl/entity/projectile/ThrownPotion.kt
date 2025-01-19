package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.projectile.ThrownPotion
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.ThrowableItemProjectile
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class ThrownPotion : ThrowableItemProjectile(EntityType.POTION), ThrownPotion {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.projectile.ThrownPotion(net.minecraft.world.entity.EntityType.POTION, level)
}