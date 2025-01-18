package com.undefined.quasar.v1_21_4.impl.entity.decoration

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.decoration.HangingEntity
import com.undefined.quasar.util.getPrivateField
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.core.Direction
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_21_R3.CraftWorld

abstract class HangingEntity(entityType: EntityType) : BlockAttachedEntity(entityType), HangingEntity {

    private var direction = HangingEntity.Direction.SOUTH


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
        super.spawn(location)
    }

    override fun getEntityData(): JsonObject {
        val entityJson = super.getEntityData()
        val hangingEntityJson = JsonObject()
        hangingEntityJson.addProperty("direction", direction.id)
        entityJson.add("hangingEntity", hangingEntityJson)
        return entityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<BlockAttachedEntity>.setEntityData(jsonObject)
        setDirection(HangingEntity.Direction.entries.first { it.id == jsonObject["direction"].asInt })
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(HangingEntity.Direction.entries.map {
            {
                setDirection(it)
                getTestMessage(this@HangingEntity::class, "Set direction", getDirection().name.lowercase())
            }
        }) }
}