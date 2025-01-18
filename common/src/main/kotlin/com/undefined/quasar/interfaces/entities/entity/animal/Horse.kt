package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.AbstractHorse

interface Horse : AbstractHorse {

    fun setVariant(variant: Variant)
    fun getVariant(): Variant

    enum class Variant(val id: Int) {
        WHITE(0),
        CREAMY(1),
        CHESTNUT(2),
        BROWN(3),
        BLACK(4),
        GRAY(5),
        DARK_BROWN(6)
    }
}