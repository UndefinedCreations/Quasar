package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.Animal

interface Goat : Animal {

    fun setScreamingGoat(screamingGoat: Boolean)
    fun isScreamingGoat(): Boolean

    fun setLeftHorn(leftHorn: Boolean)
    fun hasLeftHorn(): Boolean

    fun setRightHorn(rightHorn: Boolean)
    fun hasRightHorn(): Boolean

}