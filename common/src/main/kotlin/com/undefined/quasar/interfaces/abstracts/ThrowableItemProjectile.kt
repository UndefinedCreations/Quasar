package com.undefined.quasar.interfaces.abstracts

import org.bukkit.inventory.ItemStack

interface ThrowableItemProjectile : Projectile {

    fun setItem(item: ItemStack)
    fun getItem(): ItemStack?

}