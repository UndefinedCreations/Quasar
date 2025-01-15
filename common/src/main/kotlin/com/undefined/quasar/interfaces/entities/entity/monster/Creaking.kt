package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Monster

interface Creaking : Monster {

    fun setActive(active: Boolean)
    fun isActive(): Boolean

    fun setTearingDown(tearing: Boolean)
    fun isTearingDown(): Boolean

}