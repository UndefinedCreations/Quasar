package com.undefined.quasar.v1_21_4.impl.entity.display

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.display.BlockDisplay
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import com.undefined.quasar.v1_21_4.util.BlockDataUtil
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.craftbukkit.v1_21_R3.block.data.CraftBlockData

class BlockDisplay : Display(EntityType.BLOCK_DISPLAY), BlockDisplay {

    private var DATA_BLOCK_STATE_ID: EntityDataAccessor<BlockState>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display.BlockDisplay::class.java,
            FieldMappings.Entity.Display.BlockDisplay.DATA_BLOCK_STATE_ID
        )

    override fun setBlock(blockData: BlockData) = setEntityDataAccessor(DATA_BLOCK_STATE_ID, (blockData as CraftBlockData).state)

    override fun getBlock(): BlockData = getEntityDataValue(DATA_BLOCK_STATE_ID)?.let { CraftBlockData.fromData(it) } ?: Material.STONE.createBlockData()

    override fun getEntityData(): JsonObject {
        val displayJson = super.getEntityData()
        val blockDisplayJson = JsonObject()
        blockDisplayJson.addProperty("blockData", BlockDataUtil.getID(getBlock()))
        displayJson.add("blockDisplay", blockDisplayJson)
        return displayJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Display>.setEntityData(jsonObject)
        val blockDisplayJson = jsonObject["blockDisplay"].asJsonObject
        setBlock(BlockDataUtil.getBlockData(blockDisplayJson["blockData"].asInt))
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setBlock(Material.STONE.createBlockData())
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.Display.BlockDisplay(net.minecraft.world.entity.EntityType.BLOCK_DISPLAY, level)

    override fun getTests(): MutableList<() -> String> =
        mutableListOf(
            {
                setBlock(Material.entries.filter { it.isBlock }.random())
                getTestMessage(this@BlockDisplay::class, "Set block data", getBlock().material.name.lowercase())
            }
        ).apply { addAll(super.getTests()) }


}