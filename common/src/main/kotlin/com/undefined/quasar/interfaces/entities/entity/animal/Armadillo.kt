package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.Animal

interface Armadillo : Animal {

    fun setState(state: State)
    fun getState(): State

    enum class State() {
        IDLE,
        ROLLING,
        SCARED,
        UNROLLING
    }
}