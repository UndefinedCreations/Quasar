package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.Monster

interface Bogged : Monster {

    fun isSheared(): Boolean

    fun setSheared(sheared: Boolean)

}