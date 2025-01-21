package com.undefined.quasar.interfaces.entities.entity.decoration

import com.undefined.quasar.util.Direction

interface HangingEntity : BlockAttachedEntity {

    fun setDirection(direction: Direction)
    fun getDirection(): Direction

}