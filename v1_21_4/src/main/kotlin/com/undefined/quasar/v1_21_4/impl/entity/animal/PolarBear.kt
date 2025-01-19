package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.PolarBear
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class PolarBear : Animal(EntityType.POLAR_BEAR), PolarBear {

    private var DATA_STANDING_ID: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.PolarBear::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.PolarBear.DATA_STANDING_ID
        )

    override fun setStanding(standing: Boolean) = setEntityDataAccessor(DATA_STANDING_ID, standing)

    override fun isStanding(): Boolean = getEntityDataValue(DATA_STANDING_ID) ?: false

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val polarBearJson = JsonObject()
        polarBearJson.addProperty("standing", isStanding())
        animalJson.add("polarBear", polarBearJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val polarBearJson = jsonObject["polarBear"].asJsonObject
        setStanding(polarBearJson["standing"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setStanding(true)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.PolarBear(net.minecraft.world.entity.EntityType.POLAR_BEAR, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setStanding(false)
                getTestMessage(this@PolarBear::class, "Set standing", isStanding())
            },
            {
                setStanding(true)
                getTestMessage(this@PolarBear::class, "Set standing", isStanding())
            }
        )) }
}