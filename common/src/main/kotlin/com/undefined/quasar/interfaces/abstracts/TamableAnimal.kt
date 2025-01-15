package com.undefined.quasar.interfaces.abstracts

interface TamableAnimal : Animal {

    fun setTamed(tamed: Boolean)
    fun isTamed(): Boolean

    fun setSitting(sitting: Boolean)
    fun isSitting(): Boolean

}