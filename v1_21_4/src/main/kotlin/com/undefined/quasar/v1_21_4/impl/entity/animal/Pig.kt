package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Pig
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Pig : Animal(EntityType.PIG), Pig {

    private var DATA_SADDLE_ID: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Pig::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Pig.DATA_SADDLE_ID
        )

    override fun setSaddle(saddle: Boolean) = setEntityDataAccessor(DATA_SADDLE_ID, saddle)

    override fun hasSaddle(): Boolean = getEntityDataValue(DATA_SADDLE_ID) ?: false

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val pigJson = JsonObject()
        pigJson.addProperty("saddle", hasSaddle())
        animalJson.add("pig", pigJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val pigJson = jsonObject["pig"].asJsonObject
        setSaddle(pigJson["saddle"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setSaddle(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Pig(net.minecraft.world.entity.EntityType.PIG, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setSaddle(true)
                getTestMessage(this@Pig::class, "Set saddle", hasSaddle())
            }
        )) }
}