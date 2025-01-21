package com.undefined.quasar.v1_21_4.impl.entity.item

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.item.PrimedTnt
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.chat.Component
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_21_R3.block.data.CraftBlockData

class PrimedTnt : Entity(EntityType.TNT), PrimedTnt {

    private var DATA_BLOCK_STATE_ID: EntityDataAccessor<BlockState>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.item.PrimedTnt::class.java,
            FieldMappings.Entity.PrimedTnt.DATA_BLOCK_STATE_ID
        )

    override fun setBlock(material: Material) = setEntityDataAccessor(DATA_BLOCK_STATE_ID, (material.createBlockData() as CraftBlockData).state)

    override fun getBlock(): Material = getEntityDataValue(DATA_BLOCK_STATE_ID)?.let { data ->
        CraftBlockData.fromData(data).material
    } ?: Material.TNT

    override fun getEntityData(): JsonObject {
        val entityJson = super.getEntityData()
        val primedTntJson = JsonObject()
        primedTntJson.addProperty("block", getBlock().name)
        entityJson.add("primedTnt", primedTntJson)
        return entityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val primedTnt = jsonObject["primedTnt"].asJsonObject
        setBlock(Material.valueOf(primedTnt["block"].asString))
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setBlock(Material.TNT)
    }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.item.PrimedTnt(net.minecraft.world.entity.EntityType.TNT, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setBlock(Material.entries.filter { it.isBlock }.random())
                getTestMessage(this@PrimedTnt::class, "Set block", getBlock().name)
            }
        )) }
}