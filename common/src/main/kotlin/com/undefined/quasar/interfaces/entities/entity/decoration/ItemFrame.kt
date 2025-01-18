package com.undefined.quasar.interfaces.entities.entity.decoration

import org.bukkit.inventory.ItemStack

interface ItemFrame : HangingEntity {

    fun setItem(itemStack: ItemStack)
    fun getItem(): ItemStack

    fun setRotation(rotation: Int)
    fun getRotation(): Int

}