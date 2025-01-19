package com.undefined.quasar.v1_21_4.impl.entity.abstracts

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.abstracts.AbstractPiglin
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor

abstract class AbstractPiglin(entityType: EntityType) : LivingEntity(entityType), AbstractPiglin {

    private var DATA_IMMUNE_TO_ZOMBIFICATION: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.piglin.AbstractPiglin::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.AbstractPiglin.DATA_IMMUNE_TO_ZOMBIFICATION
        )

    override fun setImmuneToZombification(immune: Boolean) = setEntityDataAccessor(DATA_IMMUNE_TO_ZOMBIFICATION, immune)

    override fun isImmuneToZOmbification(): Boolean = getEntityDataValue(DATA_IMMUNE_TO_ZOMBIFICATION) ?: false

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val abstractPiglinJson = JsonObject()
        abstractPiglinJson.addProperty("immune", isImmuneToZOmbification())
        monsterJson.add("abstractPiglin", abstractPiglinJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val abstractPiglinJson = jsonObject["abstractPiglin"].asJsonObject
        setImmuneToZombification(abstractPiglinJson["immune"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setImmuneToZombification(false)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setImmuneToZombification(true)
                getTestMessage(this@AbstractPiglin::class, "Set immune", isImmuneToZOmbification())
            },
            {
                setImmuneToZombification(false)
                getTestMessage(this@AbstractPiglin::class, "Set immune", isImmuneToZOmbification())
            }
        )) }
}