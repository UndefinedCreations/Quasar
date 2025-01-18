package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Ghast
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Ghast : LivingEntity(EntityType.GHAST), Ghast {

    private var DATA_IS_CHARGING_ID: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Ghast::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Ghast.DATA_IS_CHARGING
        )

    override fun setCharging(charging: Boolean) = setEntityDataAccessor(DATA_IS_CHARGING_ID, charging)

    override fun isCharging(): Boolean = getEntityDataValue(DATA_IS_CHARGING_ID) ?: false

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val ghastJson = JsonObject()
        ghastJson.addProperty("charging", isCharging())
        monsterJson.add("ghast", ghastJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val ghastJson = jsonObject["ghast"].asJsonObject
        setCharging(ghastJson["charging"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setCharging(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Ghast(net.minecraft.world.entity.EntityType.GHAST, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setCharging(true)
                getTestMessage(this@Ghast::class, "Set charging", isCharging())
            },
            {
                setCharging(false)
                getTestMessage(this@Ghast::class, "Set charging", isCharging())
            }
        )) }
}