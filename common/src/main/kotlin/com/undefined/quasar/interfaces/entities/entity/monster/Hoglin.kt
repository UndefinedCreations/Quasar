package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Animal

interface Hoglin : Animal {

    fun setImmuneToZombification(immune: Boolean)
    fun isImmuneToZombification(): Boolean

}