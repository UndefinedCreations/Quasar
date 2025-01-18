package com.undefined.quasar.interfaces.entities.entity.item

import com.undefined.quasar.interfaces.Entity
import org.bukkit.inventory.ItemStack

interface ItemEntity : Entity {

    fun setItem(itemStack: ItemStack)
    fun getItem(): ItemStack

}