package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Monster

interface Skeleton : Monster {

    fun setStrayConversion(conversion: Boolean)
    fun isStrayConversion(): Boolean

}