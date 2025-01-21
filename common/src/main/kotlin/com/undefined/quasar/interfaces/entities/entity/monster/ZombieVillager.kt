package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.LivingEntity
import com.undefined.quasar.interfaces.entities.entity.npc.Profession
import com.undefined.quasar.interfaces.entities.entity.npc.Type

interface ZombieVillager : LivingEntity {

    fun setConverting(converting: Boolean)
    fun isConverting(): Boolean

    fun setProfession(profession: Profession)
    fun getProfession(): Profession

    fun setType(type: Type)
    fun getType(): Type

}