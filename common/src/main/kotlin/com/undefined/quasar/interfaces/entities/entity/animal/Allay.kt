package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.Animal
import org.bukkit.inventory.ItemStack

interface Allay : Animal {
    fun setHoldingItem(itemStack: ItemStack?) = setItem(0, itemStack)
    fun getHoldingItem(): ItemStack? = getItem(0)

    fun setDancing(dancing: Boolean)
    fun isDancing(): Boolean
}