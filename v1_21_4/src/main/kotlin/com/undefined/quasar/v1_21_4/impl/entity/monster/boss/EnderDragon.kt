package com.undefined.quasar.v1_21_4.impl.entity.monster.boss

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.boss.EnderDragon
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class EnderDragon : LivingEntity(EntityType.ENDER_DRAGON), EnderDragon {

    private var DATA_PHASE: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.boss.enderdragon.EnderDragon::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Boss.EnderDragon.DATA_PHASE
        )

    override fun setPhase(phase: Int) = setEntityDataAccessor(DATA_PHASE, phase)

    override fun getPhase(): Int = getEntityDataValue(DATA_PHASE) ?: 0

    override fun getEntityData(): JsonObject {
        val bossJson = super.getEntityData()
        val enderDragon = JsonObject()
        enderDragon.addProperty("phase", getPhase())
        bossJson.add("enderDragon", enderDragon)
        return bossJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val enderDragon = jsonObject["enderDragon"].asJsonObject
        setPhase(enderDragon["phase"].asInt)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setPhase(0)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.boss.enderdragon.EnderDragon(net.minecraft.world.entity.EntityType.ENDER_DRAGON, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setPhase(1)
                getTestMessage(this@EnderDragon::class, "Set phase", getPhase())
            },{
                setPhase(0)
                getTestMessage(this@EnderDragon::class, "Set phase", getPhase())
            },
        )) }
}