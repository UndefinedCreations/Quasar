package com.undefined.quasar.v1_21_4.impl.entity.animal.horse

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.horse.Horse
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.AbstractHorse
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Horse : AbstractHorse(EntityType.HORSE), Horse {

    private var DATA_ID_TYPE_VARIANT: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.horse.Horse::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.AbstractHorse.Horse.DATA_ID_TYPE_VARIANT
        )

    override fun setVariant(variant: Horse.Variant) = setEntityDataAccessor(DATA_ID_TYPE_VARIANT, variant.id)

    override fun getVariant(): Horse.Variant = getEntityDataValue(DATA_ID_TYPE_VARIANT)?.let { data ->
        Horse.Variant.entries.first { it.id == data }
    } ?: Horse.Variant.WHITE

    override fun getEntityData(): JsonObject {
        val abstractHorseJson = super.getEntityData()
        val horseJson = JsonObject()
        horseJson.addProperty("variant", getVariant().id)
        abstractHorseJson.add("horse", horseJson)
        return abstractHorseJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<AbstractHorse>.setEntityData(jsonObject)
        val horseJson = jsonObject["horse"].asJsonObject
        setVariant(Horse.Variant.entries.first { it.id == horseJson["variant"].asInt })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setVariant(Horse.Variant.WHITE)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.horse.Horse(net.minecraft.world.entity.EntityType.HORSE, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(
            Horse.Variant.entries.map {
                {
                    setVariant(it)
                    getTestMessage(this@Horse::class, "Set variant", getVariant().name.lowercase())
                }
            }
        ) }
}