package com.undefined.quasar.interfaces.entities.entity.projectile

import com.undefined.quasar.interfaces.abstracts.Projectile
import org.bukkit.inventory.ItemStack

interface Fireball : Projectile {

    fun setItem(item: ItemStack?)
    fun getItem(): ItemStack?

}