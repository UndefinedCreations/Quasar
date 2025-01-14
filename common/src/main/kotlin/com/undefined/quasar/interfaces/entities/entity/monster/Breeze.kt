package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.Monster

interface Breeze : Monster {

    fun setAnimation(animation: Animation)
    fun getAnimation(): Animation

    enum class Animation(val id: Int) {
        IDLE(0),
        SLIDE(15),
        LONG_JUMP(6),
        SHOOT(16),
        INHALE(17)
    }
}