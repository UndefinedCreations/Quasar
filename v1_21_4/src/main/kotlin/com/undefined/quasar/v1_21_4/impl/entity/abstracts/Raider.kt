package com.undefined.quasar.v1_21_4.impl.entity.abstracts

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.abstracts.Raider
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor

abstract class Raider(entityType: EntityType) : LivingEntity(entityType), Raider {

    private var IS_CELEBRATING: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.raid.Raider::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Raider.IS_CELEBRATING
        )

    override fun setCelebrating(celebrating: Boolean) = setEntityDataAccessor(IS_CELEBRATING, celebrating)

    override fun isCelebrating(): Boolean = getEntityDataValue(IS_CELEBRATING) ?: false

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val raiderJson = JsonObject()
        raiderJson.addProperty("celebrating", isCelebrating())
        monsterJson.add("raider", raiderJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val raiderJson = jsonObject["raider"].asJsonObject
        setCelebrating(raiderJson["celebrating"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setCelebrating(false)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setCelebrating(true)
                getTestMessage(this@Raider::class, "Set celebrating", isCelebrating())
            },
            {
                setCelebrating(false)
                getTestMessage(this@Raider::class, "Set celebrating", isCelebrating())
            }
        )) }
}