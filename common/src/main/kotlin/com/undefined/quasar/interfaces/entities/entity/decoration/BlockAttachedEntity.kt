package com.undefined.quasar.interfaces.entities.entity.decoration

import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.util.BlockPos

interface BlockAttachedEntity : Entity {

    fun setPos(blockPos: BlockPos)
    fun getPos(): BlockPos

}