package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.Animal
import org.bukkit.inventory.ItemStack

interface Fox : Animal {

    fun setVariant(variant: Variant)
    fun getVariant(): Variant

    fun setSitting(sitting: Boolean)
    fun isSitting(): Boolean

    fun setCrouching(crouching: Boolean)
    fun isCrouching(): Boolean

    fun setInterested(interested: Boolean)
    fun isInterested(): Boolean

    fun setPouncing(pouncing: Boolean)
    fun isPouncing(): Boolean

    fun setFoxSleeping(sleeping: Boolean)
    fun isFoxSleeping(): Boolean

    fun setFacePlanted(facePlanted: Boolean)
    fun isFacePlanted(): Boolean

    fun setDefending(defending: Boolean)
    fun isDefending(): Boolean

    fun setHoldingItem(itemStack: ItemStack?) = setItem(0, itemStack)
    fun getHoldingItem(): ItemStack? = getItem(0)

    enum class Variant(val id: Int) {
        RED(0),
        SNOW(1)
    }
}