package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.Animal
import com.undefined.quasar.util.Color

interface Sheep : Animal {

    fun setSheared(sheared: Boolean)
    fun isSheared(): Boolean

    fun setColor(color: Color)
    fun getColor(): Color


}