package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.Animal

interface Axolotl : Animal {

    fun setVariant(variant: Variant)
    fun getVariant(): Variant

    enum class Variant(val id: Int) {
        LUCY(0),
        WILD(1),
        GOLD(2),
        CYAN(3),
        BLUE(4)
    }
}