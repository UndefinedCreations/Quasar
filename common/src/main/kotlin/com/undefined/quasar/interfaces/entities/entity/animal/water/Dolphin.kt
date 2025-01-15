package com.undefined.quasar.interfaces.entities.entity.animal.water

import org.bukkit.inventory.ItemStack

interface Dolphin : WaterAnimal {

    fun setHoldingItem(item: ItemStack) = setItem(0, item)
    fun getHoldingItem(): ItemStack? = getItem(0)

    fun setGotFish(gotFish: Boolean)
    fun hasFish(): Boolean

    fun setMoistnessLevel(moistness: Int)
    fun getMoistnessLevel(): Int

}