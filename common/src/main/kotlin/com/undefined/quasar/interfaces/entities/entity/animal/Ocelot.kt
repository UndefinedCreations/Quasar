package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.Animal

interface Ocelot : Animal {

    fun setTrusting(trusting: Boolean)
    fun isTrusting(): Boolean

}