package com.undefined.quasar.interfaces.entities.entity.vehicle

import com.undefined.quasar.interfaces.Entity
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import kotlin.random.Random

interface Minecart: Entity {
    fun setDisplayBlock(block: BlockData)
    fun getDisplayBlock(): BlockData?
    fun setDisplayBlockOffset(offset: Int)
    fun getDisplayBlockOffset(): Int
    fun setCustomDisplay(customDisplay: Boolean)
    fun getCustomDisplay(): Boolean
}