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

    private var moving = false
    private var attackTarget = -1

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

    override fun setMoving(moving: Boolean) =
        setEntityDataAccessor(DATA_ID_MOVING, moving) {
            this.moving = moving
        }

    override fun isMoving(): Boolean = moving

    override fun setAttackTarget(attackTarget: Int) =
        setEntityDataAccessor(DATA_ID_ATTACK_TARGET, attackTarget) {
            this.attackTarget = attackTarget
        }

    override fun getAttackTarget(): Int = attackTarget

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val guardianJson = JsonObject()
        guardianJson.addProperty("moving", moving)
        guardianJson.addProperty("attackTarget", attackTarget)
        monsterJson.add("guardian", guardianJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val guardianJson = jsonObject["guardian"].asJsonObject
        moving = guardianJson["moving"].asBoolean
        attackTarget = guardianJson["attackTarget"].asInt
    }

    override fun updateEntity() {
        super.updateEntity()
        setMoving(moving)
        setAttackTarget(attackTarget)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Guardian(net.minecraft.world.entity.EntityType.GUARDIAN, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setMoving(true)
                getTestMessage(this@Guardian::class, "Set moving", true)
            },
            {
                setMoving(false)
                getTestMessage(this@Guardian::class, "Set moving", false)
            },
            {
                setAttackTarget(1)
                getTestMessage(this@Guardian::class, "Set moving", 1)
            },
            {
                setAttackTarget(-1)
                getTestMessage(this@Guardian::class, "Set moving", 1)
            }
        )) }
}