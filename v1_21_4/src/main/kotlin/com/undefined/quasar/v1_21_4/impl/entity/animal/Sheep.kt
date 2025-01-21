package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Sheep
import com.undefined.quasar.util.Color
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Sheep : Animal(EntityType.SHEEP), Sheep {

    private var DATA_WOOL_ID: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Sheep::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Sheep.DATA_WOOL_ID
        )

    override fun setSheared(sheared: Boolean) {
        val entity = entity ?: return
        val b0 = entity.entityData.get(DATA_WOOL_ID) as Byte
        if (sheared) {
            entity.entityData.set(DATA_WOOL_ID, (b0.toInt() or 16).toByte())
        } else {
            entity.entityData.set(
                DATA_WOOL_ID,
                (b0.toInt() and -17).toByte()
            )
        }
        sendEntityMetaData()
    }

    override fun isSheared(): Boolean {
        val entity = entity ?: return false
        return ((entity.entityData.get(DATA_WOOL_ID) as Byte).toInt() and 16) != 0
    }

    override fun setColor(color: Color) {
        val entity = entity ?: return
        val b0 = entity.entityData.get(DATA_WOOL_ID) as Byte
        entity.entityData.set(DATA_WOOL_ID, (b0.toInt() and 240 or (color.id and 15)).toByte())
        sendEntityMetaData()
    }

    override fun getColor(): Color {
        val entity = entity ?: return Color.WHITE
        return Color.entries.first { it.id == (entity.entityData.get(DATA_WOOL_ID) as Byte).toInt() and 15 }
    }

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val sheepJson = JsonObject()
        sheepJson.addProperty("sheared", isSheared())
        sheepJson.addProperty("color", getColor().id)
        animalJson.add("sheep", sheepJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val sheepJson = jsonObject["sheep"].asJsonObject
        setSheared(sheepJson["sheared"].asBoolean)
        setColor(Color.entries.first { it.id == jsonObject["color"].asInt })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setSheared(false)
        setColor(Color.WHITE)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Sheep(net.minecraft.world.entity.EntityType.SHEEP, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setSheared(true)
                getTestMessage(this@Sheep::class, "Set sheared", isSheared())
            },
            {
                setSheared(false)
                getTestMessage(this@Sheep::class, "Set sheared", isSheared())
            }
        ))
        addAll(Color.entries.map {
            {
                setColor(it)
                getTestMessage(this@Sheep::class, "Set color", getColor().name.lowercase())
            }
        })}
}