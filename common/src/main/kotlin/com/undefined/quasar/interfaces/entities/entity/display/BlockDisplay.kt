package com.undefined.quasar.interfaces.entities.entity.display

import org.bukkit.Material
import org.bukkit.block.data.BlockData

interface BlockDisplay : Display {

    fun setBlock(blockData: BlockData)
    fun setBlock(material: Material) = setBlock(material.createBlockData())
    fun getBlock(): BlockData

}