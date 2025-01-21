package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.SnowGolem
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.level.Level

class SnowGolem : LivingEntity(EntityType.SNOW_GOLEM), SnowGolem {

    private var DATA_PUMPKIN_ID: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.SnowGolem::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.SnowGolem.DATA_PUMPKIN_ID
        )

    override fun setPumpkin(pumpkin: Boolean) {
        val entity = entity ?: return
        val b0 = entity.entityData.get(DATA_PUMPKIN_ID) as Byte
        if (pumpkin) {
            entity.entityData.set(DATA_PUMPKIN_ID, (b0.toInt() or 16).toByte())
        } else {
            entity.entityData.set(DATA_PUMPKIN_ID, (b0.toInt() and -17).toByte())
        }
        sendEntityMetaData()
    }

    override fun hasPumpkin(): Boolean {
        val entity = entity ?: return true
        return ((entity.entityData.get(DATA_PUMPKIN_ID) as Byte).toInt() and 16) != 0
    }

    override fun getEntityData(): JsonObject {
        val mobJson = super.getEntityData()
        val snowGolemJson = JsonObject()
        snowGolemJson.addProperty("pumpkin", hasPumpkin())
        mobJson.add("snowGolem", snowGolemJson)
        return mobJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val snowGolemJson = jsonObject["snowGolem"].asJsonObject
        setPumpkin(snowGolemJson["pumpkin"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setPumpkin(true)
    }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.animal.SnowGolem(net.minecraft.world.entity.EntityType.SNOW_GOLEM, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setPumpkin(false)
                getTestMessage(this@SnowGolem::class, "Set pumpkin", hasPumpkin())
            },
            {
                setPumpkin(true)
                getTestMessage(this@SnowGolem::class, "Set pumpkin", hasPumpkin())
            }
        )) }
}