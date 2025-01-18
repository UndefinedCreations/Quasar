package com.undefined.quasar.interfaces.entities.entity.monster.boss

import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.util.BlockPos
import org.bukkit.Location

interface EndCrystal : Entity {

    fun setShowingBottom(showing: Boolean)
    fun isShowingBottom(): Boolean

    fun setBeamTarget(target: BlockPos?)
    fun getBeamTarget(): BlockPos?
    fun removeBeam() = setBeamTarget(null)
    fun hasBeam(): Boolean { return getBeamTarget() != null }

}

fun Location.toBlockPos(): BlockPos = BlockPos(blockX, blockY, blockZ)