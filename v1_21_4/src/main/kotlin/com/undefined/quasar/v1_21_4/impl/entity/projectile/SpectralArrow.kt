package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.projectile.SpectralArrow
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.Color

class SpectralArrow : Arrow(EntityType.SPECTRAL_ARROW), SpectralArrow {

    override fun setEffectColor(color: Color?) {}

    override fun getEffectColor(): Color? { return null }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.projectile.SpectralArrow(net.minecraft.world.entity.EntityType.SPECTRAL_ARROW, level)
}