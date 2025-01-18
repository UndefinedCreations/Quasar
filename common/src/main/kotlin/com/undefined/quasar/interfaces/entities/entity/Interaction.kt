package com.undefined.quasar.interfaces.entities.entity

import com.undefined.quasar.interfaces.Entity

interface Interaction : Entity {

    fun setWidth(width: Float)
    fun getWidth(): Float

    fun setHeight(height: Float)
    fun getHeight(): Float

}