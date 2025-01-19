package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.Animal

interface Pig : Animal {

    fun setSaddle(saddle: Boolean)
    fun hasSaddle(): Boolean

}