package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Monster

interface Warden : Monster {

    fun setAnimation(animation: Animation)
    fun getAnimation(): Animation

    enum class Animation(val id: Int) {
        STANDING(0),
        SNIFF(12),
        EMERGE(13),
        DIGGING(14),
        ROAR(11)
    }
}