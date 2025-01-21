package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Animal

interface Strider : Animal {

    fun setSaddle(saddle: Boolean)
    fun hasSaddle(): Boolean

    fun setSuffocation(suffocation: Boolean)
    fun isSuffocating(): Boolean

}