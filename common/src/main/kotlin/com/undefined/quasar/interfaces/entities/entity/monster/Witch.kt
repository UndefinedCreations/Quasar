package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Raider
import org.bukkit.inventory.ItemStack

interface Witch : Raider {

    fun setItem(item: ItemStack?) = setItem(0, item)
    fun getItem(): ItemStack? = getItem(0)

    fun setUsingItem(using: Boolean)
    fun isUsingItem(): Boolean

}