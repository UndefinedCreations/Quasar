package com.undefined.quasar.interfaces.entities.entity.animal.water

interface GlowSquid : Squid {

    fun setDarkTicks(ticks: Int)
    fun setDarkTickMax() = setDarkTicks(Int.MAX_VALUE)
    fun setDarkTickMin() = setDarkTicks(0)
    fun getDarkTicks(): Int

}