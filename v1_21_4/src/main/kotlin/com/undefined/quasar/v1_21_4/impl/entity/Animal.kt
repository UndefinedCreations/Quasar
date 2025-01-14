package com.undefined.quasar.v1_21_4.impl.entity

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor

abstract class Animal(entityType: EntityType) : LivingEntity(entityType), Animal {

    private var bady = false

    private var DATA_BABY_ID: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.AgeableMob::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.DATA_BABY_ID
        )

    override fun setBady(bady: Boolean) =
        setEntityDataAccessor(DATA_BABY_ID, bady) {
            this.bady = bady
        }

    override fun isBady(): Boolean = false

    override fun getEntityData(): JsonObject {
        val livingEntityJson = super.getEntityData()
        val animalJson = JsonObject()
        animalJson.addProperty("bady", bady)
        livingEntityJson.add("animal", animalJson)
        return livingEntityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val animalJson = jsonObject["animal"].asBoolean
    }

    override fun updateEntity() {
        super.updateEntity()
        setBady(bady)
    }
}