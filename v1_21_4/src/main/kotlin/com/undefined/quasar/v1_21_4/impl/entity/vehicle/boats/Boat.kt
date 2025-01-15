package com.undefined.quasar.v1_21_4.impl.entity.vehicle.boats

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.vehicle.boat.Boat
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import kotlin.random.Random

abstract class Boat(entityType: EntityType) : Entity(entityType), Boat {

    private var leftPaddle = false
    private var rightPaddle = false
    private var bubbleTime = 0

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

    override fun setLeftPaddle(paddle: Boolean) =
        setEntityDataAccessor(DATA_ID_PADDLE_LEFT, paddle) {
            this.leftPaddle = paddle
        }

    override fun getLeftPaddle(): Boolean = leftPaddle

    override fun setRightPaddle(paddle: Boolean) =
        setEntityDataAccessor(DATA_ID_PADDLE_RIGHT, paddle) {
            this.rightPaddle = paddle
        }

    override fun getRightPaddle(): Boolean = rightPaddle

    override fun setBubbleTime(time: Int) =
        setEntityDataAccessor(DATA_ID_BUBBLE_TIME, time) {
            this.bubbleTime = time
        }

    override fun getBubbleTime(): Int = bubbleTime

    override fun getEntityData(): JsonObject {
        val vehicleJson = super.getEntityData()
        val boatJson = JsonObject()
        boatJson.addProperty("leftPaddle", leftPaddle)
        boatJson.addProperty("rightPaddle", rightPaddle)
        boatJson.addProperty("bubbleTime", bubbleTime)
        vehicleJson.add("boat", boatJson)
        return vehicleJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val boatJson = jsonObject["boat"].asJsonObject
        leftPaddle = boatJson["leftPaddle"].asBoolean
        rightPaddle = boatJson["rightPaddle"].asBoolean
        bubbleTime = boatJson["bubbleTime"].asInt
    }

    override fun updateEntity() {
        super.updateEntity()
        setLeftPaddle(leftPaddle)
        setRightPaddle(rightPaddle)
        setBubbleTime(bubbleTime)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setLeftPaddle(true)
                getTestMessage(this@Boat::class, "Set left paddle", true)
            },
            {
                setLeftPaddle(false)
                getTestMessage(this@Boat::class, "Set left paddle", false)
            },
            {
                setRightPaddle(true)
                getTestMessage(this@Boat::class, "Set right paddle", true)
            },
            {
                setRightPaddle(false)
                getTestMessage(this@Boat::class, "Set right paddle", false)
            },
            {
                setBubbleTime(Random.nextInt(100))
                getTestMessage(this@Boat::class, "Set bubble time", getBubbleTime())
            }
        )) }
}