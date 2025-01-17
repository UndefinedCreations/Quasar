package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.interfaces.abstracts.Animal

interface Frog : Animal {

    fun setVariant(variant: Variant)
    fun getVariant(): Variant

    fun setTongueTarget(entity: Entity?)
    fun getTongueTarget(): Entity?

    enum class Variant {
        TEMPERATE,
        WARM,
        COLD
    }
}