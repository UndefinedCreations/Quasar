package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Raider


interface Pillager : Raider {

    fun setChargingCrossbow(charging: Boolean)
    fun isChargingCrossbow(): Boolean

}