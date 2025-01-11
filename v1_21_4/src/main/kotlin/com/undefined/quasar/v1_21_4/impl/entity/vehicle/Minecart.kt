package com.undefined.quasar.v1_21_4.impl.entity.vehicle

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.vehicle.Minecart
import com.undefined.quasar.util.delay
import com.undefined.quasar.util.getPrivateField
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import com.undefined.quasar.v1_21_4.util.BlockDataUtil
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.vehicle.AbstractMinecart
import net.minecraft.world.level.Level
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.entity.Player
import kotlin.random.Random

class Minecart: Minecart, Entity(EntityType.MINECART) {

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
        setDisplayBlockOffset(displayBlockOffset)
        setCustomDisplay(customDisplay)
    }

    override fun setEntityData(jsonObject: JsonObject) {
        val minecartJson = jsonObject["minecartData"].asJsonObject
        val blockID = minecartJson["displayBlock"].asInt
        displayBlock = if (blockID == -1) null else BlockDataUtil.getBlockData(blockID)
        displayBlockOffset = minecartJson["displayBlockOffset"].asInt
        customDisplay = minecartJson["customDisplay"].asBoolean
    }

    override fun getEntityData(): JsonObject {
        val json = super.getEntityData()
        val minecartJson = JsonObject()
        minecartJson.addProperty("displayBlock", BlockDataUtil.getID(displayBlock))
        minecartJson.addProperty("displayBlockOffset", displayBlockOffset)
        minecartJson.addProperty("customDisplay", customDisplay)
        json.add("minecartData", minecartJson)
        return json
    }

    @Suppress("LABEL_NAME_CLASH")
    override fun runTest(logger: Player, delayTime: Int, testStage: (Exception?) -> Unit, done: (Unit) -> Unit): Int {
        super.runTest(logger,
            delayTime, {
            if (it != null) return@runTest

            trycatch({
                val randomMaterial = Material.entries.filter { material -> material.isBlock }.random()
                setDisplayBlock(randomMaterial.createBlockData())
                logger.sendMessage("${ChatColor.GRAY} Minecart | Set display block {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${randomMaterial.name.lowercase()}${ChatColor.GRAY}]")

                delay(delayTime) {
                    val randomOffset = Random.nextInt(50)
                    setDisplayBlockOffset(randomOffset)
                    logger.sendMessage("${ChatColor.GRAY} Minecart | Set display block offset {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}$randomOffset${ChatColor.GRAY}]")

                    delay(delayTime) {
                        setCustomDisplay(false)
                        logger.sendMessage("${ChatColor.GRAY} Minecart | Set custom display {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}false${ChatColor.GRAY}]")

                        delay(delayTime) {
                            done(Unit)
                        }
                    }
                }
            }, testStage)

        }, done)

        return 2
    }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.vehicle.Minecart(net.minecraft.world.entity.EntityType.MINECART, level)

}