package com.unedfined.quasar.v1_21_4.impl.entity

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.util.getPrivateField
import com.unedfined.quasar.v1_21_4.Entity
import com.unedfined.quasar.v1_21_4.mappings.FieldMappings
import com.unedfined.quasar.v1_21_4.util.BlockDataUtil
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.vehicle.AbstractMinecart
import net.minecraft.world.level.Level
import org.bukkit.block.data.BlockData

class Minecart: com.undefined.quasar.interfaces.entities.entity.Minecart, Entity(EntityType.MINECART) {

    private var displayBlock: BlockData? = null
    private var displayBlockOffset: Int = 0
    private var customDisplay = false

    private var DATA_ID_DISPLAY_BLOCK: EntityDataAccessor<Int>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(AbstractMinecart::class.java, FieldMappings.Entity.Vehicle.Minecart.DATA_ID_DISPLAY_BLOCK)
            return field
        }

    private var DATA_ID_DISPLAY_OFFSET: EntityDataAccessor<Int>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(AbstractMinecart::class.java, FieldMappings.Entity.Vehicle.Minecart.DATA_ID_DISPLAY_OFFSET)
            return field
        }

    private var DATA_ID_CUSTOM_DISPLAY: EntityDataAccessor<Boolean>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(AbstractMinecart::class.java, FieldMappings.Entity.Vehicle.Minecart.DATA_ID_CUSTOM_DISPLAY)
            return field
        }


    override fun setDisplayBlock(block: BlockData) {
        if (entity == null) return
        entity!!.entityData.set(DATA_ID_DISPLAY_BLOCK, BlockDataUtil.getID(block))
        this.displayBlock = block
        setCustomDisplay(true)
    }

    override fun setDisplayBlockOffset(offset: Int) {
        entity!!.entityData.set(DATA_ID_DISPLAY_OFFSET, offset)
        this.displayBlockOffset = offset
        setCustomDisplay(true)
    }

    override fun setCustomDisplay(customDisplay: Boolean) {
        entity!!.entityData.set(DATA_ID_CUSTOM_DISPLAY, customDisplay)
        this.customDisplay = customDisplay
        sendEntityMetaData()
    }

    override fun getCustomDisplay(): Boolean = customDisplay
    override fun getDisplayBlockOffset(): Int = displayBlockOffset
    override fun getDisplayBlock(): BlockData? = displayBlock

    override fun updateEntity() {
        super.updateEntity()
        displayBlock?.run { setDisplayBlock(this) }
        if (displayBlockOffset != 0) setDisplayBlockOffset(displayBlockOffset)
    }

    override fun setEntityData(json: JsonObject) {
        val minecartJson = json["minecartData"].asJsonObject
        minecartJson["displayBlock"]?.run {
            displayBlock = BlockDataUtil.getBlockData(this.asInt)
        }
        minecartJson["displayBlockOffset"]?.run {
            displayBlockOffset = this.asInt
        }
    }

    override fun getEntityData(): JsonObject {
        val json = super.getEntityData()
        val minecartJson = JsonObject()
        displayBlock?.run {
            minecartJson.addProperty("displayBlock", BlockDataUtil.getID(this))
        }
        if (displayBlockOffset != 0)
            minecartJson.addProperty("displayBlockOffset", displayBlockOffset)
        if (!minecartJson.isEmpty) json.add("minecartData", minecartJson)
        return json
    }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.vehicle.Minecart(net.minecraft.world.entity.EntityType.MINECART, level)


}