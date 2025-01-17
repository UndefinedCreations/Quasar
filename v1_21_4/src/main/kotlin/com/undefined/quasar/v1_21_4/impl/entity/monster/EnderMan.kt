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
import org.bukkit.craftbukkit.v1_21_R3.block.CraftBlockState
import org.bukkit.craftbukkit.v1_21_R3.block.data.CraftBlockData
import java.util.Optional

class EnderMan : LivingEntity(EntityType.ENDERMAN), EnderMan {

    private var holdingBlock: BlockData? = null
    private var creeping = false
    private var staredAt = false

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

    override fun getHoldingBlock(): BlockData? = holdingBlock

    override fun setCreeping(creeping: Boolean) =
        setEntityDataAccessor(DATA_CREEPY, creeping) {
            this.creeping = creeping
        }

    override fun isCreeping(): Boolean = creeping

    override fun setStaredAt(staring: Boolean) =
        setEntityDataAccessor(DATA_STARED_AT, staring) {
            this.staredAt = staring
        }

    override fun isBeaningStaredAt(): Boolean = staredAt

    override fun setHoldingBlock(block: BlockData?) =
        setEntityDataAccessor(DATA_CARRY_STATE, if (block == null) Optional.empty() else Optional.of((block as CraftBlockData).state)) {
            this.holdingBlock = block
        }

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val endermanJson = JsonObject()
        endermanJson.addProperty("blockData", BlockDataUtil.getID(holdingBlock))
        endermanJson.addProperty("staredAt", staredAt)
        endermanJson.addProperty("creeping", creeping)
        monsterJson.add("enderMan", endermanJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val enderManJson = jsonObject["enderMan"].asJsonObject
        holdingBlock = BlockDataUtil.getBlockData(enderManJson["blockData"].asInt)
        staredAt = enderManJson["staredAt"].asBoolean
        creeping = enderManJson["creeping"].asBoolean
    }

    override fun updateEntity() {
        super.updateEntity()
        setHoldingBlock(holdingBlock)
        setCreeping(creeping)
        setStaredAt(staredAt)
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
                getTestMessage(this@EnderMan::class, "Set holding block", "null")
            },
            {
                setCreeping(true)
                getTestMessage(this@EnderMan::class, "Set creeping", true)
            },
            {
                setCreeping(false)
                getTestMessage(this@EnderMan::class, "Set creeping", false)
            },
            {
                setStaredAt(true)
                getTestMessage(this@EnderMan::class, "Set stared at", true)
            },
            {
                setStaredAt(false)
                getTestMessage(this@EnderMan::class, "Set stared at", false)
            }
        )) }
}