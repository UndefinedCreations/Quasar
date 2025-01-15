package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Monster

interface Spider : Monster {

    fun setClimbing(climbing: Boolean)
    fun isClimbing(): Boolean

}