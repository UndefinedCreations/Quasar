package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.Mob
import org.bukkit.inventory.ItemStack

interface Allay : Mob {
    fun setHoldingItem(itemStack: ItemStack?) = setItem(0, itemStack)
    fun getHoldingItem(): ItemStack? = getItem(0)


}