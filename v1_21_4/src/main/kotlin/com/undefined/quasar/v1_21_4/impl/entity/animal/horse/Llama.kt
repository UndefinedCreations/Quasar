package com.undefined.quasar.v1_21_4.impl.entity.animal.horse

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.horse.Llama
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.AbstractChestHorse
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level


class Llama : AbstractChestHorse(EntityType.LLAMA), Llama {

    private var DATA_VARIANT_ID: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.horse.Llama::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.AbstractHorse.Llama.DATA_VARIANT_ID
        )

    override fun setVariant(variant: Llama.Variant) = setEntityDataAccessor(DATA_VARIANT_ID, variant.id)

    override fun getVariant(): Llama.Variant = getEntityDataValue(DATA_VARIANT_ID)?.let { data ->
        Llama.Variant.entries.first { it.id == data }
    } ?: Llama.Variant.WHITE

    override fun getEntityData(): JsonObject {
        val abstractChestHorseJson = super.getEntityData()
        val llamaJson = JsonObject()
        llamaJson.addProperty("variant", getVariant().id)
        abstractChestHorseJson.add("llama", llamaJson)
        return abstractChestHorseJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<AbstractChestHorse>.setEntityData(jsonObject)
        val llamaJson = jsonObject["llama"].asJsonObject
        setVariant(Llama.Variant.entries.first { llamaJson["variant"].asInt == it.id })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setVariant(Llama.Variant.WHITE)
        setCarpet(Llama.Carpets.NONE)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.horse.Llama(net.minecraft.world.entity.EntityType.LLAMA, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(Llama.Variant.entries.map {
            {
                setVariant(it)
                getTestMessage(this@Llama::class, "Set variant", getVariant().name.lowercase())
            }
        })
            addAll(Llama.Carpets.entries.map {
                {
                    setItem(0, it.material) //TODO Fix carpets
                    getTestMessage(this@Llama::class, "Set carpet", getCarpet().name.lowercase())
                }
            })
        }
}