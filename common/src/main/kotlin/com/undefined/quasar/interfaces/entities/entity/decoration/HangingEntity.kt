package com.undefined.quasar.interfaces.entities.entity.decoration

import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.util.BlockPos

interface HangingEntity : Entity {

    fun setDirection(direction: Direction)
    fun getDirection(): Direction

    fun setPos(blockPos: BlockPos)
    fun getPos(): BlockPos

    enum class Direction(val id: Int) {
        DOWN(0),
        UP(1),
        NORTH(2),
        SOUTH(3),
        WEST(4),
        EAST(5)
    }
}