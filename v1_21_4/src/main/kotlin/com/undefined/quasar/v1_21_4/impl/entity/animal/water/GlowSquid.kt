package com.undefined.quasar.v1_21_4.impl.entity.animal.water

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.water.GlowSquid
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class GlowSquid : Squid(EntityType.GLOW_SQUID), GlowSquid {

    private var DATA_DARK_TICKS_REMAINING: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.GlowSquid::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.GlowSquid.DATA_DARK_TICKS_REMAINING
        )

    override fun setDarkTicks(ticks: Int) = setEntityDataAccessor(DATA_DARK_TICKS_REMAINING, ticks)

    override fun getDarkTicks(): Int = getEntityDataValue(DATA_DARK_TICKS_REMAINING) ?: 0

    override fun getEntityData(): JsonObject {
        val squidJson = super.getEntityData()
        val glowSquidJson = JsonObject()
        glowSquidJson.addProperty("darkTicks", getDarkTicks())
        squidJson.add("glowSquid", glowSquidJson)
        return squidJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Squid>.setEntityData(jsonObject)
        val glowSquidJson = jsonObject["glowSquid"].asJsonObject
        setDarkTicks(glowSquidJson["darkTicks"].asInt)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setDarkTicks(0)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.GlowSquid(net.minecraft.world.entity.EntityType.GLOW_SQUID, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setDarkTicks(Int.MAX_VALUE)
                getTestMessage(this@GlowSquid::class, "Set dark ticks", getDarkTicks())
            },
            {
                setDarkTicks(0)
                getTestMessage(this@GlowSquid::class, "Set dark ticks", getDarkTicks())
            }
        )) }
}