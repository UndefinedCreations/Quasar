package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Monster

interface Creeper : Monster {

    fun setSwell(swell: Int)
    fun getSwell(): Int

    fun setIgnite(ignite: Boolean)
    fun isIgnite(): Boolean

}