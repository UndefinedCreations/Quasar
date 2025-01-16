package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Monster

interface Guardian : Monster {

    fun setMoving(moving: Boolean)
    fun isMoving(): Boolean

    fun setAttackTarget(attackTarget: Int)
    fun getAttackTarget(): Int

}