package com.undefined.quasar.interfaces.entities.entity.animal

interface MooshroomCow : Cow {

    fun setVariant(variant: Variant)
    fun getVariant(): Variant

    enum class Variant(val id: String) {
        RED("red"),
        BROWN("brown")
    }
}