package com.undefined.quasar.interfaces.entities.entity.vehicle.minecart

import com.undefined.quasar.interfaces.abstracts.VehicleEntity
import org.bukkit.block.data.BlockData

interface Minecart : VehicleEntity {
    fun setDisplayBlock(block: BlockData?)
    fun getDisplayBlock(): BlockData?
    fun setDisplayBlockOffset(offset: Int)
    fun getDisplayBlockOffset(): Int
    fun setCustomDisplay(customDisplay: Boolean)
    fun getCustomDisplay(): Boolean
}