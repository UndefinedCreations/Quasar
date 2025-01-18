package com.undefined.quasar.v1_21_4.impl.entity.decoration

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.decoration.HangingEntity
import com.undefined.quasar.util.BlockPos
import com.undefined.quasar.util.getPrivateField
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.core.Direction
import net.minecraft.world.entity.decoration.BlockAttachedEntity
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_21_R3.CraftWorld

abstract class HangingEntity(entityType: EntityType) : Entity(entityType), HangingEntity {

    private var direction = HangingEntity.Direction.SOUTH
    private var blockPos = BlockPos(0, 0,0)

    override fun setDirection(direction: HangingEntity.Direction) {
        this.direction = direction
        respawn()
    }

    override fun getDirection(): HangingEntity.Direction {
        val entity = entity ?: return HangingEntity.Direction.SOUTH
        return entity.getPrivateField<Direction>(net.minecraft.world.entity.decoration.HangingEntity::class.java, FieldMappings.Entity.Decoration.HangingEntity.DIRECTION).let { data ->
            HangingEntity.Direction.valueOf(data.name)
        }
    }

    override fun spawn(location: Location) {
        val craftWorld = location.world as CraftWorld
        this.entity = getEntityClass(craftWorld.handle)
        net.minecraft.world.entity.decoration.HangingEntity::class.java.getDeclaredField(FieldMappings.Entity.Decoration.HangingEntity.DIRECTION).apply {
            isAccessible = true
        }.set(entity, Direction.valueOf(direction.name))
        if (blockPos.x != 0 || blockPos.y != 0 || blockPos.z != 0) {
            BlockAttachedEntity::class.java.getDeclaredField(FieldMappings.Entity.Decoration.BlockAttackedEntity.POS).apply {
                isAccessible = true
            }.set(entity, net.minecraft.core.BlockPos(blockPos.x, blockPos.y, blockPos.z))
        }
        super.spawn(location)
    }

    override fun setPos(blockPos: BlockPos) {
        this.blockPos = blockPos
        respawn()
    }

    override fun getPos(): BlockPos {
        val entity = entity ?: BlockPos(0, 0, 0)
        return entity.getPrivateField<net.minecraft.core.BlockPos>(
            BlockAttachedEntity::class.java,
            FieldMappings.Entity.Decoration.BlockAttackedEntity.POS
        ).let { BlockPos(it.x, it.y, it.z) }
    }

    override fun getEntityData(): JsonObject {
        val entityJson = super.getEntityData()
        val hangingEntityJson = JsonObject()
        hangingEntityJson.addProperty("direction", direction.id)
        hangingEntityJson.add("pos", blockPos.toJson())
        entityJson.add("hangingEntity", hangingEntityJson)
        return entityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        setDirection(HangingEntity.Direction.entries.first { it.id == jsonObject["direction"].asInt })
        setPos(BlockPos(jsonObject["pos"].asJsonObject))
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(HangingEntity.Direction.entries.map {
            {
                setDirection(it)
                getTestMessage(this@HangingEntity::class, "Set direction", getDirection().name.lowercase())
            }
        })
        addAll(mutableListOf(
            {
                setPos(BlockPos(getPos().z, getPos().y + 5, getPos().z))
                getTestMessage(this@HangingEntity::class, "Set block pos", getPos().x, getPos().y, getPos().z)
            },
            {
                setPos(BlockPos(getPos().z, getPos().y - 5, getPos().z))
                getTestMessage(this@HangingEntity::class, "Set block pos", getPos().x, getPos().y, getPos().z)
            }
        ))}
}