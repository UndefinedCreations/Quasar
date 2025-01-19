package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.LivingEntity

interface Phantom : LivingEntity {

    fun setSize(size: Int)
    fun getSize(): Int

}