package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Vex
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Vex : LivingEntity(EntityType.VEX), Vex {

    private var DATA_FLAGS_ID: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Vex::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Vex.DATA_FLAGS_ID
        )

    override fun setCharging(charging: Boolean) {
        val entity = entity ?: return
        var data = (entity.entityData.get(DATA_FLAGS_ID).toByte()).toInt()
        data = if (charging) data or 1 else data and (1).inv()
        entity.entityData.set(DATA_FLAGS_ID, (data and 255).toByte())
        sendEntityMetaData()
    }

    override fun isCharging(): Boolean {
        val entity = entity ?: return false
        return (entity.entityData.get(DATA_FLAGS_ID).toInt() and 1) != 0
    }

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val vexJson = JsonObject()
        vexJson.addProperty("charging", isCharging())
        monsterJson.add("vex", vexJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val vexJson = jsonObject["vex"].asJsonObject
        setCharging(vexJson["charging"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setCharging(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Vex(net.minecraft.world.entity.EntityType.VEX, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setCharging(true)
                getTestMessage(this@Vex::class, "Set charging", isCharging())
            }
        )) }
}