package com.undefined.quasar.interfaces.entities.entity.vehicle.minecart

interface MinecartFurnace : Minecart {

    fun setFueled(fueled: Boolean)
    fun isFueled(): Boolean

}