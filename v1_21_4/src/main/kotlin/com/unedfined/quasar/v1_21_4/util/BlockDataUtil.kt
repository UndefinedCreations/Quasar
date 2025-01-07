package com.unedfined.quasar.v1_21_4.util

import net.minecraft.world.level.block.Block
import org.bukkit.block.data.BlockData
import org.bukkit.craftbukkit.v1_21_R3.block.data.CraftBlockData


object BlockDataUtil {
    fun getID(blockData: BlockData): Int = Block.getId((blockData as CraftBlockData).state)
    fun getBlockData(id: Int): BlockData = CraftBlockData.fromData(Block.stateById(id))
}