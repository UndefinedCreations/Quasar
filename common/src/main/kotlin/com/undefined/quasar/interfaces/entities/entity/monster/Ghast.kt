package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Monster

interface Ghast : Monster {

    fun setCharging(charging: Boolean)
    fun isCharging(): Boolean

}