package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Mob

interface Slime : Mob {

    fun setSize(size: Int)
    fun getSize(): Int

    enum class Size(val id: Int) {
        SMALL(0),
        MEDIUM(1),
        BIG(2)
    }
}