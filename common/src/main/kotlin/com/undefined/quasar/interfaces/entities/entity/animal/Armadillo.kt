package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.Animal

interface Armadillo : Animal {
    fun setState(state: State)
    fun getState(): State

    enum class State(val id: Int) {
        IDLE(0),
        ROLLING(1),
        SCARED(2),
        UNROLLING(3)
    }
}