package com.undefined.quasar.interfaces.entities.entity.animal.water

import com.undefined.quasar.interfaces.LivingEntity
import org.bukkit.inventory.ItemStack

interface Dolphin : WaterAnimal, LivingEntity {

    fun setHoldingItem(item: ItemStack) = setItem(0, item)
    fun getHoldingItem(): ItemStack? = getItem(0)

    fun setGotFish(gotFish: Boolean)
    fun hasFish(): Boolean

    fun setMoistnessLevel(moistness: Int)
    fun getMoistnessLevel(): Int

}