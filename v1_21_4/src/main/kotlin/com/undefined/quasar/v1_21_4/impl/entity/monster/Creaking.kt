package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Creaking
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Creaking : LivingEntity(EntityType.CREAKING), Creaking {

    private var IS_ACTIVE: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.creaking.Creaking::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Creaking.IS_ACTIVE
        )
    private var IS_TEARING_DOWN: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.creaking.Creaking::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Creaking.IS_TEARING_DOWN
        )

    override fun setActive(active: Boolean) = setEntityDataAccessor(IS_ACTIVE, active)

    override fun isActive(): Boolean = getEntityDataValue(IS_ACTIVE) ?: false

    override fun setTearingDown(tearing: Boolean) = setEntityDataAccessor(IS_TEARING_DOWN, tearing)

    override fun isTearingDown(): Boolean = getEntityDataValue(IS_TEARING_DOWN) ?: false

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val creakingJson = JsonObject()
        creakingJson.addProperty("active", isActive())
        creakingJson.addProperty("isTearing", isTearingDown())
        monsterJson.add("creaking", creakingJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val creakingJson = jsonObject["creaking"].asJsonObject
        setActive(creakingJson["active"].asBoolean)
        setTearingDown(creakingJson["isTearing"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setActive(true)
        setTearingDown(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.creaking.Creaking(net.minecraft.world.entity.EntityType.CREAKING, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setActive(false)
                getTestMessage(this@Creaking::class, "Set active", isActive())
            },
            {
                setActive(true)
                getTestMessage(this@Creaking::class, "Set active", isActive())
            },
            {
                setTearingDown(true)
                getTestMessage(this@Creaking::class, "Set tearing down", isTearingDown())
            },
            {
                setTearingDown(false)
                getTestMessage(this@Creaking::class, "Set tearing down", isTearingDown())
            }
        )) }
}