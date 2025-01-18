package com.undefined.quasar.v1_21_4.impl.entity.abstracts

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.abstracts.TamableAnimal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor


abstract class TamableAnimal(entityType: EntityType) : Animal(entityType), TamableAnimal {

    private var DATA_FLAGS_ID: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.TamableAnimal::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.TamableAnimal.DATA_FLAGS_ID
        )

    override fun setTamed(tamed: Boolean) {
        val entity = entity ?: return
        val b0 = entity.entityData.get(DATA_FLAGS_ID) as Byte
        if (tamed) {
            entity.entityData.set(DATA_FLAGS_ID, (b0.toInt() or 4).toByte())
        } else {
            entity.entityData.set(DATA_FLAGS_ID, (b0.toInt() and -5).toByte())
        }
        sendEntityMetaData()
    }

    override fun isTamed(): Boolean {
        val entity = entity ?: return false
        return ((entity.entityData.get(DATA_FLAGS_ID) as Byte).toInt() and 4) != 0;
    }

    override fun setSitting(sitting: Boolean) {
        val entity = entity ?: return
        val b0 = entity.entityData.get(DATA_FLAGS_ID) as Byte
        if (sitting) {
            entity.entityData.set(DATA_FLAGS_ID, (b0.toInt() or 1).toByte())
        } else {
            entity.entityData.set(DATA_FLAGS_ID, (b0.toInt() and -2).toByte())
        }
        sendEntityMetaData()
    }

    override fun isSitting(): Boolean {
        val entity = entity ?: return false
        return ((entity.entityData.get(DATA_FLAGS_ID) as Byte).toInt() and 1) != 0;
    }

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val tamableJson = JsonObject()
        tamableJson.addProperty("tamed", isTamed())
        tamableJson.addProperty("sitting", isSitting())
        animalJson.add("tamable", tamableJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val tamableJson = jsonObject["tamable"].asJsonObject
        setTamed(tamableJson["tamed"].asBoolean)
        setSitting(tamableJson["sitting"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setTamed(false)
        setSitting(false)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setTamed(true)
                getTestMessage(this@TamableAnimal::class, "Set tamed", isTamed())
            },
            {
                setTamed(false)
                getTestMessage(this@TamableAnimal::class, "Set tamed", isTamed())
            },
            {
                setSitting(true)
                getTestMessage(this@TamableAnimal::class, "Set sitting", isSitting())
            },
            {
                setSitting(false)
                getTestMessage(this@TamableAnimal::class, "Set sitting", isSitting())
            }
        )) }
}