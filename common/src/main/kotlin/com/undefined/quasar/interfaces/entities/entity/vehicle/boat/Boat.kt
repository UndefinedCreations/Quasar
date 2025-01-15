package com.undefined.quasar.interfaces.entities.entity.vehicle.boat

import com.undefined.quasar.interfaces.abstracts.VehicleEntity

interface Boat : VehicleEntity {

    fun setLeftPaddle(paddle: Boolean)
    fun getLeftPaddle(): Boolean

    fun setRightPaddle(paddle: Boolean)
    fun getRightPaddle(): Boolean

    fun setBubbleTime(time: Int)
    fun getBubbleTime(): Int

}