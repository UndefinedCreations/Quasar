package com.undefined.quasar.v1_21_4.impl.entity.monster.boss

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.boss.EnderDragon
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.boss.EnderDragonPart
import net.minecraft.world.level.Level
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_21_R3.CraftWorld

class EnderDragon : LivingEntity(EntityType.ENDER_DRAGON), EnderDragon {

    private var phase = 0

    private var DATA_PHASE: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.boss.enderdragon.EnderDragon::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Boss.EnderDragon.DATA_PHASE
        )

    override fun setPhase(phase: Int) =
        setEntityDataAccessor(DATA_PHASE, phase) {
            this.phase = phase
        }

    override fun getPhase(): Int = phase

    override fun getEntityData(): JsonObject {
        val bossJson = super.getEntityData()
        val enderDragon = JsonObject()
        enderDragon.addProperty("phase", phase)
        bossJson.add("enderDragon", enderDragon)
        return bossJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val enderDragon = jsonObject["enderDragon"].asJsonObject
        phase = enderDragon["phase"].asInt
    }

    override fun updateEntity() {
        super.updateEntity()
        setPhase(phase)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.boss.enderdragon.EnderDragon(net.minecraft.world.entity.EntityType.ENDER_DRAGON, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setPhase(1)
                getTestMessage(this@EnderDragon::class, "Set phase", 1)
            },{
                setPhase(0)
                getTestMessage(this@EnderDragon::class, "Set phase", 0)
            },
        )) }
}