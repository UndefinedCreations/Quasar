package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.Animal

interface Parrot : Animal {

    fun setVariant(variant: Variant)
    fun getVariant(): Variant

    enum class Variant(val id: Int) {
        RED_BLUE(0),
        BLUE(1),
        GREEN(2),
        YELLOW_BLUE(3),
        GRAY(4)
    }
}