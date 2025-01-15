package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Monster

interface Zombie : Monster {

    fun setBaby(baby: Boolean)
    fun isBaby(): Boolean

    fun setConversion(conversion: Boolean)
    fun isConverting(): Boolean

}