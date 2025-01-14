package com.undefined.quasar.interfaces.entities.entity.animal.camel

import com.undefined.quasar.interfaces.AbstractHorse

interface Camel : AbstractHorse {
    fun setDashing(dashing: Boolean)
    fun isDashing(): Boolean

    fun setAnimation(animation: Animation)
    fun getAnimation(): Animation

    enum class Animation(val id: Int) {
        STANDING(0),
        SIT(2),
        CROUCHING(5),
//        IDLE()
    }

}