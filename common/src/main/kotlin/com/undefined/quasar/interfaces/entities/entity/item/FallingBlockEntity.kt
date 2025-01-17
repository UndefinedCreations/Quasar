package com.undefined.quasar.interfaces.entities.entity.item

import com.undefined.quasar.interfaces.Entity
import org.bukkit.block.data.BlockData

interface FallingBlockEntity : Entity {
    fun setFallingBlock(block: BlockData)
    fun getFallingBlock(): BlockData
}