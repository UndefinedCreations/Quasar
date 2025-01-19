package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.Animal

interface Panda : Animal {

    fun setSitting(sit: Boolean)
    fun isSitting(): Boolean

    fun setOnBack(onBack: Boolean)
    fun isOnBack(): Boolean

    fun setEating(eating: Boolean)
    fun isEating(): Boolean

    fun setRolling(rolling: Boolean)
    fun isRolling(): Boolean

    fun setSneezing(sneezing: Boolean)
    fun isSneezing(): Boolean

    fun setUnhappy(unhappy: Boolean)
    fun isUnhappy(): Boolean

    fun setVariant(variant: Variant)
    fun getVariant(): Variant

    enum class Variant(val id: Int) {
        NORMAL(0),
        LAZY(1),
        WORRIED(2),
        PLAYFUL(3),
        BROWN(4),
        WEAK(5),
        AGGRESSIVE(6),
    }

}