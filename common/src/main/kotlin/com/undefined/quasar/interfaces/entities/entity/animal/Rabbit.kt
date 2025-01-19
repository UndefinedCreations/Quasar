package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.Animal

interface Rabbit : Animal {

    fun setVariant(variant: Variant)
    fun getVariant(): Variant

    enum class Variant(val id: Int) {
        BROWN(0),
        WHITE(1),
        BLACK(2),
        WHITE_SPLOTCHED(3),
        GOLD(4),
        SALT(5),
        EVIL(6),
    }
}