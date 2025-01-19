package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.Animal

interface PolarBear : Animal {

    fun setStanding(standing: Boolean)
    fun isStanding(): Boolean

}