package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Monster

interface Zoglin : Monster {

    fun setBaby(baby: Boolean)
    fun isBaby(): Boolean

}