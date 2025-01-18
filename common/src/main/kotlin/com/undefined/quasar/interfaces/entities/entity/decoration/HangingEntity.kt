package com.undefined.quasar.interfaces.entities.entity.decoration

interface HangingEntity : BlockAttachedEntity {

    fun setDirection(direction: Direction)
    fun getDirection(): Direction

    enum class Direction(val id: Int) {
        DOWN(0),
        UP(1),
        NORTH(2),
        SOUTH(3),
        WEST(4),
        EAST(5)
    }
}