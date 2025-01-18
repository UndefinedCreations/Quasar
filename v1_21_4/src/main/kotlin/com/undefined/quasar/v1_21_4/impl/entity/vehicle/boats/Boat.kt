package com.undefined.quasar.v1_21_4.impl.entity.vehicle.boats

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.vehicle.boat.Boat
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import kotlin.random.Random

abstract class Boat(entityType: EntityType) : Entity(entityType), Boat {

    private var DATA_ID_PADDLE_LEFT: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.vehicle.AbstractBoat::class.java,
            FieldMappings.Entity.Vehicle.Boat.DATA_ID_PADDLE_LEFT
        )
    private var DATA_ID_PADDLE_RIGHT: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.vehicle.AbstractBoat::class.java,
            FieldMappings.Entity.Vehicle.Boat.DATA_ID_PADDLE_RIGHT
        )
    private var DATA_ID_BUBBLE_TIME: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.vehicle.AbstractBoat::class.java,
            FieldMappings.Entity.Vehicle.Boat.DATA_ID_BUBBLE_TIME
        )

    override fun setLeftPaddle(paddle: Boolean) = setEntityDataAccessor(DATA_ID_PADDLE_LEFT, paddle)

    override fun getLeftPaddle(): Boolean = getEntityDataValue(DATA_ID_PADDLE_LEFT) ?: false

    override fun setRightPaddle(paddle: Boolean) = setEntityDataAccessor(DATA_ID_PADDLE_RIGHT, paddle)

    override fun getRightPaddle(): Boolean = getEntityDataValue(DATA_ID_PADDLE_RIGHT) ?: false

    override fun setBubbleTime(time: Int) = setEntityDataAccessor(DATA_ID_BUBBLE_TIME, time)

    override fun getBubbleTime(): Int = getEntityDataValue(DATA_ID_BUBBLE_TIME) ?: -1

    override fun getEntityData(): JsonObject {
        val vehicleJson = super.getEntityData()
        val boatJson = JsonObject()
        boatJson.addProperty("leftPaddle", getLeftPaddle())
        boatJson.addProperty("rightPaddle", getRightPaddle())
        boatJson.addProperty("bubbleTime", getBubbleTime())
        vehicleJson.add("boat", boatJson)
        return vehicleJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val boatJson = jsonObject["boat"].asJsonObject
        setLeftPaddle(boatJson["leftPaddle"].asBoolean)
        setRightPaddle(boatJson["rightPaddle"].asBoolean)
        setBubbleTime(boatJson["bubbleTime"].asInt)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setLeftPaddle(false)
        setRightPaddle(false)
        setBubbleTime(-1)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setLeftPaddle(true)
                getTestMessage(this@Boat::class, "Set left paddle", getLeftPaddle())
            },
            {
                setLeftPaddle(false)
                getTestMessage(this@Boat::class, "Set left paddle", getLeftPaddle())
            },
            {
                setRightPaddle(true)
                getTestMessage(this@Boat::class, "Set right paddle", getRightPaddle())
            },
            {
                setRightPaddle(false)
                getTestMessage(this@Boat::class, "Set right paddle", getRightPaddle())
            },
            {
                setBubbleTime(Random.nextInt(100))
                getTestMessage(this@Boat::class, "Set bubble time", getBubbleTime())
            }
        )) }
}