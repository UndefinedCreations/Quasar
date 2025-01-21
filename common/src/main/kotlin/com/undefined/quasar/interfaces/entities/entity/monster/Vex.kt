package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Monster

interface Vex : Monster {

    fun setCharging(charging: Boolean)
    fun isCharging(): Boolean

}