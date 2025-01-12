package com.undefined.quasar.interfaces.entities.entity.ambient

import com.undefined.quasar.interfaces.AmbientCreature

interface Bat : AmbientCreature {

    fun isResting(): Boolean
    fun setResting(resting: Boolean)

}