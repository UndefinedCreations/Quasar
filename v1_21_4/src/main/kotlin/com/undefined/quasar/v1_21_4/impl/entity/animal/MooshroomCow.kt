package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.MooshroomCow
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.core.Holder
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.animal.FrogVariant
import net.minecraft.world.level.Level

class MooshroomCow : Cow(EntityType.MOOSHROOM), MooshroomCow {

    private var DATA_TYPE: EntityDataAccessor<String>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.MushroomCow::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Mushroom.DATA_TYPE
        )

    override fun setVariant(variant: MooshroomCow.Variant) = setEntityDataAccessor(DATA_TYPE, variant.id)

    override fun getVariant(): MooshroomCow.Variant = getEntityDataValue(DATA_TYPE)?.let { data ->
        MooshroomCow.Variant.entries.first { it.id == data }
    } ?: MooshroomCow.Variant.RED

    override fun getEntityData(): JsonObject {
        val cowJson = super.getEntityData()
        val mooshRoomJson = JsonObject()
        mooshRoomJson.addProperty("variant", getVariant().id)
        cowJson.add("mooshroom", mooshRoomJson)
        return cowJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Cow>.setEntityData(jsonObject)
        val mooshRoomJson = jsonObject["mooshroom"].asJsonObject
        setVariant(MooshroomCow.Variant.entries.first { it.id == mooshRoomJson["variant"].asString })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setVariant(MooshroomCow.Variant.RED)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.MushroomCow(net.minecraft.world.entity.EntityType.MOOSHROOM, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply {
            addAll(MooshroomCow.Variant.entries.map {
                {
                    setVariant(it)
                    getTestMessage(this@MooshroomCow::class, "Set variant", getVariant().name.lowercase())
                }
            })
        }
}