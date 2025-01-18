package com.undefined.quasar.interfaces.entities.entity.display

import org.bukkit.inventory.ItemStack

interface ItemDisplay : Display {

    fun setItem(itemStack: ItemStack)
    fun getItem(): ItemStack

    fun setContext(context: Context)
    fun getContext(): Context

    enum class Context(val id: Byte) {
        NONE(0),
        THIRD_PERSON_LEFT_HAND(1),
        THIRD_PERSON_RIGHT_HAND(2),
        FIRST_PERSON_LEFT_HAND(3),
        FIRST_PERSON_RIGHT_HAND(4),
        HEAD(5),
        GUI(6),
        GROUND(7),
        FIXED(8),
    }
}