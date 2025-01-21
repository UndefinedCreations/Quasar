package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Strider
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Strider : Animal(EntityType.STRIDER), Strider {

    private var DATA_SUFFOCATING: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Strider::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Strider.DATA_SUFFOCATING
        )

    private var DATA_SADDLE_ID: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Strider::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Strider.DATA_SADDLE_ID
        )

    override fun setSaddle(saddle: Boolean) = setEntityDataAccessor(DATA_SADDLE_ID, saddle)

    override fun hasSaddle(): Boolean = getEntityDataValue(DATA_SUFFOCATING) ?: false

    override fun setSuffocation(suffocation: Boolean) = setEntityDataAccessor(DATA_SUFFOCATING, suffocation)

    override fun isSuffocating(): Boolean = getEntityDataValue(DATA_SUFFOCATING) ?: false

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val striderJson = JsonObject()
        striderJson.addProperty("saddle", hasSaddle())
        striderJson.addProperty("suffocation", isSuffocating())
        animalJson.add("strider", striderJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val striderJson = jsonObject["strider"].asJsonObject
        setSaddle(striderJson["saddle"].asBoolean)
        setSuffocation(striderJson["suffocation"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setSuffocation(false)
        setSaddle(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Strider(net.minecraft.world.entity.EntityType.STRIDER, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setSaddle(true)
                getTestMessage(this@Strider::class, "Set saddle", hasSaddle())
            },
            {
                setSaddle(false)
                getTestMessage(this@Strider::class, "Set saddle", hasSaddle())
            },
            {
                setSuffocation(true)
                getTestMessage(this@Strider::class, "Set suffocation", hasSaddle())
            },
            {
                setSuffocation(false)
                getTestMessage(this@Strider::class, "Set suffocation", hasSaddle())
            }
        )) }
}