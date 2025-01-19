package com.undefined.quasar.interfaces.entities.entity.animal.water

interface Salmon : WaterAnimal {

    fun setSize(size: Size)
    fun getSize(): Size

    enum class Size(val id: Int) {
        SMALL(0),
        MEDIUM(1),
        LARGE(2),
    }
}