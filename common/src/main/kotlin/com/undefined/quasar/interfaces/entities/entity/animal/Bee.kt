package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.Animal

interface Bee : Animal {

    fun isAngy(): Boolean
    fun setAngy(angy: Boolean)

    fun hasNector(): Boolean
    fun setNector(nector: Boolean)

    fun hasStung(): Boolean
    fun setHasStung(stung: Boolean)

    fun isRolling(): Boolean
    fun setRolling(rolling: Boolean)

}