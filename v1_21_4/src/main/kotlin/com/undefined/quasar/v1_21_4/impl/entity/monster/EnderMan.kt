package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.EnderMan
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import com.undefined.quasar.v1_21_4.util.BlockDataUtil
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.craftbukkit.v1_21_R3.block.data.CraftBlockData
import java.util.*

class EnderMan : LivingEntity(EntityType.ENDERMAN), EnderMan {

    private var DATA_CARRY_STATE: EntityDataAccessor<Optional<BlockState>>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.EnderMan::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.EnderMan.DATA_CARRY_STATE
        )

    private var DATA_CREEPY: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.EnderMan::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.EnderMan.DATA_CREEPY
        )

    private var DATA_STARED_AT: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.EnderMan::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.EnderMan.DATA_STARED_AT
        )

    override fun getHoldingBlock(): BlockData? = getEntityDataValue(DATA_CARRY_STATE)?.let { data ->
        if (data.isPresent) CraftBlockData.fromData(data.get()) else null
    }

    override fun setCreeping(creeping: Boolean) = setEntityDataAccessor(DATA_CREEPY, creeping)

    override fun isCreeping(): Boolean = getEntityDataValue(DATA_CREEPY) ?: false

    override fun setStaredAt(staring: Boolean) = setEntityDataAccessor(DATA_STARED_AT, staring)

    override fun isBeaningStaredAt(): Boolean = getEntityDataValue(DATA_STARED_AT) ?: false

    override fun setHoldingBlock(block: BlockData?) = setEntityDataAccessor(DATA_CARRY_STATE, if (block == null) Optional.empty() else Optional.of((block as CraftBlockData).state))

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val endermanJson = JsonObject()
        endermanJson.addProperty("blockData", BlockDataUtil.getID(getHoldingBlock()))
        endermanJson.addProperty("staredAt", isBeaningStaredAt())
        endermanJson.addProperty("creeping", isCreeping())
        monsterJson.add("enderMan", endermanJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val enderManJson = jsonObject["enderMan"].asJsonObject
        setHoldingBlock(BlockDataUtil.getBlockData(enderManJson["blockData"].asInt))
        setStaredAt(enderManJson["staredAt"].asBoolean)
        setCreeping(enderManJson["creeping"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setHoldingBlock(null)
        setCreeping(false)
        setStaredAt(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.EnderMan(net.minecraft.world.entity.EntityType.ENDERMAN, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setHoldingBlock(Material.entries.filter { it.isBlock }.random().createBlockData())
                getTestMessage(this@EnderMan::class, "Set holding block", getHoldingBlock()?.material?.name?.lowercase()!!)
            },
            {
                setHoldingBlock(null)
                getTestMessage(this@EnderMan::class, "Set holding block", getHoldingBlock())
            },
            {
                setCreeping(true)
                getTestMessage(this@EnderMan::class, "Set creeping", isCreeping())
            },
            {
                setCreeping(false)
                getTestMessage(this@EnderMan::class, "Set creeping", isCreeping())
            },
            {
                setStaredAt(true)
                getTestMessage(this@EnderMan::class, "Set stared at", isBeaningStaredAt())
            },
            {
                setStaredAt(false)
                getTestMessage(this@EnderMan::class, "Set stared at", isBeaningStaredAt())
            }
        )) }
}