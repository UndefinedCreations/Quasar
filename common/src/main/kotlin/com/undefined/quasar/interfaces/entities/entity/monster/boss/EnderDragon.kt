package com.undefined.quasar.interfaces.entities.entity.monster.boss

import com.undefined.quasar.interfaces.abstracts.Boss

interface EnderDragon : Boss {

    fun setPhase(phase: Int)
    fun getPhase(): Int

}