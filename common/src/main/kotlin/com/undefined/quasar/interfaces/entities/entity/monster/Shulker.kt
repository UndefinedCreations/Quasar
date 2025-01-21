package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Monster
import com.undefined.quasar.util.Color
import com.undefined.quasar.util.Direction

interface Shulker : Monster {

    fun setDirection(direction: Direction)
    fun getDirection(): Direction

    fun setPeeking(peeking: Int)
    fun getPeeking(): Int

    fun setColor(color: Color?)
    fun getColor(): Color?

}