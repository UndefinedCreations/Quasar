package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Rabbit
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Rabbit : Animal(EntityType.RABBIT), Rabbit {

    private var DATA_TYPE_ID: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Rabbit::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Rabbit.DATA_TYPE_ID
        )

    override fun setVariant(variant: Rabbit.Variant) = setEntityDataAccessor(DATA_TYPE_ID, variant.id)

    override fun getVariant(): Rabbit.Variant = getEntityDataValue(DATA_TYPE_ID)?.let { data ->
        Rabbit.Variant.entries.first { it.id == data }
    } ?: Rabbit.Variant.WHITE

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val rabbitJson = JsonObject()
        rabbitJson.addProperty("variant", getVariant().id)
        animalJson.add("rabbit", rabbitJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val rabbitJson = jsonObject["rabbit"].asJsonObject
        setVariant(Rabbit.Variant.entries.first { it.id == jsonObject["variant"].asInt })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setVariant(Rabbit.Variant.WHITE)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Rabbit(net.minecraft.world.entity.EntityType.RABBIT, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(
            Rabbit.Variant.entries.map {
                {
                    setVariant(it)
                    getTestMessage(this@Rabbit::class, "Set variant", getVariant().name.lowercase())
                }
            }
        ) }
}