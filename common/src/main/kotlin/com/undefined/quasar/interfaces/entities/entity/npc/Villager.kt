package com.undefined.quasar.interfaces.entities.entity.npc

import com.undefined.quasar.interfaces.abstracts.Animal

interface Villager : Animal {

    fun setProfession(profession: Profession)
    fun getProfession(): Profession

    fun setType(type: Type)
    fun getType(): Type

}