package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Parrot
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Parrot : Animal(EntityType.PARROT), Parrot {

    private var DATA_VARIANT_ID: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Parrot::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Parrot.DATA_VARIANT_ID
        )

    override fun setVariant(variant: Parrot.Variant) = setEntityDataAccessor(DATA_VARIANT_ID, variant.id)

    override fun getVariant(): Parrot.Variant = getEntityDataValue(DATA_VARIANT_ID)?.let { data ->
        Parrot.Variant.entries.first { it.id == data }
    } ?: Parrot.Variant.GREEN

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val parrotJson = JsonObject()
        parrotJson.addProperty("variant", getVariant().id)
        animalJson.add("parrot", parrotJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val parrotJson = jsonObject["parrot"].asJsonObject
        setVariant(Parrot.Variant.entries.first { it.id == parrotJson["variant"].asInt })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setVariant(Parrot.Variant.GREEN)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Parrot(net.minecraft.world.entity.EntityType.PARROT, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(Parrot.Variant.entries.map {
            {
                setVariant(it)
                getTestMessage(this@Parrot::class, "Set variant", getVariant().name.lowercase())
            }
        }
        ) }
}