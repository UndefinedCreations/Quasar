package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Zoglin
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Zoglin : LivingEntity(EntityType.ZOGLIN), Zoglin {

    private var DATA_BABY_ID: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Zoglin::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Zoglin.DATA_BABY_ID
        )

    override fun setBaby(baby: Boolean) = setEntityDataAccessor(DATA_BABY_ID, baby)

    override fun isBaby(): Boolean = getEntityDataValue(DATA_BABY_ID) ?: false

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val zoglinJson = JsonObject()
        zoglinJson.addProperty("baby", isBaby())
        monsterJson.add("zoglin", zoglinJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val zoglinJson = jsonObject["zoglin"].asJsonObject
        setBaby(zoglinJson["baby"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setBaby(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Zoglin(net.minecraft.world.entity.EntityType.ZOGLIN, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setBaby(true)
                getTestMessage(this@Zoglin::class, "Set baby", isBaby())
            },
            {
                setBaby(false)
                getTestMessage(this@Zoglin::class, "Set baby", isBaby())
            }
        )) }
}