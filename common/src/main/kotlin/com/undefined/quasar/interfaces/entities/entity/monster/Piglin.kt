package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.AbstractPiglin

interface Piglin : AbstractPiglin {

    fun setBaby(baby: Boolean)
    fun isBaby(): Boolean

    fun setChargingCrossBow(charging: Boolean)
    fun isChargingCrossBow(): Boolean

    fun setDancing(dancing: Boolean)
    fun isDancing(): Boolean

}