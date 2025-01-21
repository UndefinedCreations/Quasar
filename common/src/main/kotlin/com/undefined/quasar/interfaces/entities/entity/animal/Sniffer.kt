package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.Animal

interface Sniffer : Animal {

    fun setSate(state: State)
    fun getState(): State

    enum class State(val id: Int) {
        IDLING(0),
        FEELING_HAPPY(1),
        SCENTING(2),
        SNIFFING(3),
        SEARCHING(4),
        DIGGING(5),
        RISING(6);
    }
}