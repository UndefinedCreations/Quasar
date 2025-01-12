package com.undefined.quasar.interfaces.entities.entity.projectile

import com.undefined.quasar.interfaces.Projectile
import org.bukkit.Color

interface Arrow : Projectile {

    fun getEffectColor(): Color?
    fun setEffectColor(color: Color?)

}