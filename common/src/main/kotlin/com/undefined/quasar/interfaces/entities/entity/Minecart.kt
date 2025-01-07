package com.undefined.quasar.interfaces.entities.entity

import com.undefined.quasar.interfaces.Entity
import org.bukkit.block.data.BlockData

interface Minecart: Entity {
    fun setDisplayBlock(block: BlockData)
    fun getDisplayBlock(): BlockData?
    fun setDisplayBlockOffset(offset: Int)
    fun getDisplayBlockOffset(): Int
    fun setCustomDisplay(customDisplay: Boolean)
    fun getCustomDisplay(): Boolean
}