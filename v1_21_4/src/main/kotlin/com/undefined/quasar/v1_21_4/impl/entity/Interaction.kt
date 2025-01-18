package com.undefined.quasar.v1_21_4.impl.entity

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.Interaction
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.level.Level
import kotlin.random.Random

class Interaction : Entity(EntityType.INTERACTION), Interaction {

    private var DATA_WIDTH_ID: EntityDataAccessor<Float>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Interaction::class.java,
            FieldMappings.Entity.Interaction.DATA_WIDTH_ID
        )
    private var DATA_HEIGHT_ID: EntityDataAccessor<Float>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Interaction::class.java,
            FieldMappings.Entity.Interaction.DATA_HEIGHT_ID
        )

    override fun setWidth(width: Float) = setEntityDataAccessor(DATA_WIDTH_ID, width)

    override fun getWidth(): Float = getEntityDataValue(DATA_WIDTH_ID) ?: 1f

    override fun setHeight(height: Float) = setEntityDataAccessor(DATA_HEIGHT_ID, height)

    override fun getHeight(): Float = getEntityDataValue(DATA_WIDTH_ID) ?: 1f

    override fun getEntityData(): JsonObject {
        val entityJson = super.getEntityData()
        val interactionJson = JsonObject()
        interactionJson.addProperty("width", getWidth())
        interactionJson.addProperty("height", getHeight())
        entityJson.add("interaction", interactionJson)
        return entityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val interactionJson = jsonObject["interaction"].asJsonObject
        setWidth(interactionJson["width"].asFloat)
        setHeight(interactionJson["height"].asFloat)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setWidth(1f)
        setHeight(1f)
    }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.Interaction(net.minecraft.world.entity.EntityType.INTERACTION, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setWidth(Random.nextDouble(2.0).toFloat())
                getTestMessage(this@Interaction::class, "Set width", getWidth())
            },
            {
                setHeight(Random.nextDouble(2.0).toFloat())
                getTestMessage(this@Interaction::class, "Set height", getHeight())
            },
            {
                setWidth(0f)
                getTestMessage(this@Interaction::class, "Set width", getWidth())
            },
            {
                setHeight(0f)
                getTestMessage(this@Interaction::class, "Set height", getHeight())
            }
        )) }
}