package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Monster
import org.bukkit.Material
import org.bukkit.block.data.BlockData

interface EnderMan : Monster {

    fun setHoldingBlock(block: BlockData?)
    fun getHoldingBlock(): BlockData?

    fun setCreeping(creeping: Boolean)
    fun isCreeping(): Boolean

    fun setStaredAt(staring: Boolean)
    fun isBeaningStaredAt(): Boolean

}