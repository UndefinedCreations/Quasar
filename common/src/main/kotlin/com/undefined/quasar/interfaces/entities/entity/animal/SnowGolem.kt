package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.Mob

interface SnowGolem : Mob  {

    fun setPumpkin(pumpkin: Boolean)
    fun hasPumpkin(): Boolean

}