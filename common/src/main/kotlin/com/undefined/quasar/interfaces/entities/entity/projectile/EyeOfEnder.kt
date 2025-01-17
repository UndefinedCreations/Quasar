package com.undefined.quasar.interfaces.entities.entity.projectile

import com.undefined.quasar.interfaces.Entity
import org.bukkit.inventory.ItemStack

interface EyeOfEnder : Entity {

    fun setItem(item: ItemStack)
    fun getItem(): ItemStack

}