package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Mob

interface Slime : Mob {

    fun setSize(size: Size) = setSlimeSize(size.id)
    fun getSize(): Size = Size.entries.first { it.id == getSlimeSize() }

    fun setSlimeSize(size: Int)
    fun getSlimeSize(): Int

    enum class Size(val id: Int) {
        SMALL(0),
        MEDIUM(1),
        BIG(2)
    }
}