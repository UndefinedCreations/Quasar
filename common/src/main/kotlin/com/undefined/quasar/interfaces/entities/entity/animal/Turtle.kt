package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.Animal

interface Turtle : Animal {

    fun setTravelling(travelling: Boolean)
    fun isTravelling(): Boolean

}