package com.undefined.quasar.interfaces.entities.entity.vehicle.minecart

interface MinecartCommandBlock : Minecart {

    fun setCommand(string: String)
    fun getCommand(): String

    fun invokeCommand()

}