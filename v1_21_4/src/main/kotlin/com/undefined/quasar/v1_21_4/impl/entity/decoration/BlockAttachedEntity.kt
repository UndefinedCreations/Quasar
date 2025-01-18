package com.undefined.quasar.v1_21_4.impl.entity.decoration

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.decoration.BlockAttachedEntity
import com.undefined.quasar.util.BlockPos
import com.undefined.quasar.util.getPrivateField
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import org.bukkit.Location

abstract class BlockAttachedEntity(entityType: EntityType) : Entity(entityType), BlockAttachedEntity {

    private var blockPos = BlockPos(0, 0,0)

    override fun setPos(blockPos: BlockPos) {
        this.blockPos = blockPos
        respawn()
    }

    override fun getPos(): BlockPos {
        val entity = entity ?: BlockPos(0, 0, 0)
        return entity.getPrivateField<net.minecraft.core.BlockPos>(
            net.minecraft.world.entity.decoration.BlockAttachedEntity::class.java,
            FieldMappings.Entity.Decoration.BlockAttackedEntity.POS
        ).let { BlockPos(it.x, it.y, it.z) }
    }

    override fun getEntityData(): JsonObject {
        val entityJson = super.getEntityData()
        val blockAttachedJson = JsonObject()
        blockAttachedJson.add("pos", blockPos.toJson())
        entityJson.add("blockAttachedEntity", blockAttachedJson)
        return entityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val blockAttachedJson = jsonObject["blockAttachedEntity"].asJsonObject
        setPos(BlockPos(blockAttachedJson["pos"].asJsonObject))
    }

    override fun spawn(location: Location) {
        if (blockPos.x != 0 || blockPos.y != 0 || blockPos.z != 0) {
            net.minecraft.world.entity.decoration.BlockAttachedEntity::class.java.getDeclaredField(FieldMappings.Entity.Decoration.BlockAttackedEntity.POS).apply {
                isAccessible = true
            }.set(entity, net.minecraft.core.BlockPos(blockPos.x, blockPos.y, blockPos.z))
        }
        super.spawn(location)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setPos(BlockPos(getPos().z, getPos().y + 5, getPos().z))
                getTestMessage(this@BlockAttachedEntity::class, "Set block pos", getPos().x, getPos().y, getPos().z)
            },
            {
                setPos(BlockPos(getPos().z, getPos().y - 5, getPos().z))
                getTestMessage(this@BlockAttachedEntity::class, "Set block pos", getPos().x, getPos().y, getPos().z)
            }
        )) }
}