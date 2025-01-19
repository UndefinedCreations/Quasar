package com.undefined.quasar.v1_21_4.impl.entity.animal.water

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.water.Pufferfish
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import kotlin.random.Random

class Pufferfish : com.undefined.quasar.v1_21_4.impl.entity.Entity(EntityType.PUFFERFISH), Pufferfish {

    private var PUFF_STATE: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Pufferfish::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Pufferfish.PUFF_STATE
        )

    override fun setPuff(puff: Int) = setEntityDataAccessor(PUFF_STATE, puff)

    override fun getPuff(): Int = getEntityDataValue(PUFF_STATE) ?: 0

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val pufferFishJson = JsonObject()
        pufferFishJson.addProperty("puff", getPuff())
        animalJson.add("pufferfish", pufferFishJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<com.undefined.quasar.v1_21_4.impl.entity.Entity>.setEntityData(jsonObject)
        val pufferFishJson = jsonObject["pufferfish"].asJsonObject
        setPuff(pufferFishJson["puff"].asInt)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setPuff(0)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Pufferfish(net.minecraft.world.entity.EntityType.PUFFERFISH, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setPuff(Random.nextInt(2))
                getTestMessage(this@Pufferfish::class, "Set puff", getPuff())
            }
        )) }
}