package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Guardian
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

open class Guardian(entityType: EntityType = EntityType.GUARDIAN) : LivingEntity(entityType), Guardian {


    private var DATA_ID_MOVING: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Guardian::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Guardian.DATA_ID_MOVING
        )
    private var DATA_ID_ATTACK_TARGET: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Guardian::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Guardian.DATA_ID_ATTACK_TARGET
        )

    override fun setMoving(moving: Boolean) = setEntityDataAccessor(DATA_ID_MOVING, moving)

    override fun isMoving(): Boolean = getEntityDataValue(DATA_ID_MOVING) ?: false

    override fun setAttackTarget(attackTarget: Int) = setEntityDataAccessor(DATA_ID_ATTACK_TARGET, attackTarget)

    override fun getAttackTarget(): Int = getEntityDataValue(DATA_ID_ATTACK_TARGET) ?: -1

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val guardianJson = JsonObject()
        guardianJson.addProperty("moving", isMoving())
        guardianJson.addProperty("attackTarget", getAttackTarget())
        monsterJson.add("guardian", guardianJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val guardianJson = jsonObject["guardian"].asJsonObject
        setMoving(guardianJson["moving"].asBoolean)
        setAttackTarget(guardianJson["attackTarget"].asInt)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setMoving(false)
        setAttackTarget(-1)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Guardian(net.minecraft.world.entity.EntityType.GUARDIAN, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setMoving(true)
                getTestMessage(this@Guardian::class, "Set moving", isMoving())
            },
            {
                setMoving(false)
                getTestMessage(this@Guardian::class, "Set moving", isMoving())
            },
            {
                setAttackTarget(1)
                getTestMessage(this@Guardian::class, "Set moving", getAttackTarget())
            },
            {
                setAttackTarget(-1)
                getTestMessage(this@Guardian::class, "Set moving", getAttackTarget())
            }
        )) }
}