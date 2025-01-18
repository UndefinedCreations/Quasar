package com.undefined.quasar.v1_21_4.util

import net.minecraft.world.level.block.Block
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.craftbukkit.v1_21_R3.block.data.CraftBlockData

object BlockDataUtil {
    fun getID(blockData: BlockData?): Int = if (blockData != null) Block.getId((blockData as CraftBlockData).state) else -1
    fun getBlockData(id: Int): BlockData = if (id == -1) Material.AIR.createBlockData() else CraftBlockData.fromData(Block.stateById(id))
}