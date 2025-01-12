package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Bee
import com.undefined.quasar.util.getPrivateField
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Bee : LivingEntity(EntityType.BEE), Bee {

    private var angy = false
    private var nector = false
    private var stung = false
    private var rolling = false

    private var DATA_FLAGS_ID: EntityDataAccessor<Byte>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(
                net.minecraft.world.entity.animal.Bee::class.java,
                FieldMappings.Entity.LivingEntity.Mob.Animal.Bee.DATA_FLAGS_ID
            )
            return field
        }
    private var DATA_REMAINING_ANGER_TIME: EntityDataAccessor<Int>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(
                net.minecraft.world.entity.animal.Bee::class.java,
                FieldMappings.Entity.LivingEntity.Mob.Animal.Bee.DATA_REMAINING_ANGER_TIME
            )
            return field
        }

    override fun isAngy(): Boolean = angy

    override fun setAngy(angy: Boolean) {
        val entity = entity ?: return
        this.angy = angy
        entity.entityData.set(DATA_REMAINING_ANGER_TIME, if (angy) Int.MAX_VALUE else -1)
        sendEntityMetaData()
    }

    override fun hasNector(): Boolean = nector

    override fun setNector(nector: Boolean) {
        entity ?: return
        this.nector = nector
        setFlag(8, nector)
        sendEntityMetaData()
    }

    override fun hasStung(): Boolean = stung

    override fun setHasStung(stung: Boolean) {
        entity ?: return
        this.stung = stung
        setFlag(4, stung)
        sendEntityMetaData()
    }

    override fun isRolling(): Boolean = rolling

    override fun setRolling(rolling: Boolean) {
        entity ?: return
        this.rolling = rolling
        setFlag(2, rolling)
        sendEntityMetaData()
    }

    private fun setFlag(i: Int, flag: Boolean) {
        val entity = entity ?: return
        if (flag) {
            entity.entityData.set<Byte>(
                DATA_FLAGS_ID, ((entity.entityData.get(DATA_FLAGS_ID) as Byte).toInt() or i).toByte()
            )
        } else {
            entity.entityData.set(
                DATA_FLAGS_ID, ((entity.entityData.get(DATA_FLAGS_ID) as Byte).toInt() and i.inv()).toByte()
            )
        }
    }

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val beeJson = JsonObject()
        beeJson.addProperty("angy", angy)
        beeJson.addProperty("nector", nector)
        beeJson.addProperty("stung", stung)
        beeJson.addProperty("rolling", rolling)
        animalJson.add("bee", beeJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val beeJson = jsonObject["bee"].asJsonObject
        angy = beeJson["angy"].asBoolean
        nector = beeJson["nector"].asBoolean
        stung = beeJson["stung"].asBoolean
        rolling = beeJson["rolling"].asBoolean
    }

    override fun updateEntity() {
        super.updateEntity()
        setAngy(angy)
        setNector(nector)
        setHasStung(stung)
        setRolling(rolling)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Bee(net.minecraft.world.entity.EntityType.BEE, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setAngy(true)
                getTestMessage(this::class, "Set angy", true)
            },
            {
                setNector(true)
                getTestMessage(this::class, "Set nector", true)
            },
            {
                setHasStung(true)
                getTestMessage(this::class, "Set stung", true)
            },
            {
                setRolling(true)
                getTestMessage(this::class, "Set rolling", true)
            },
            {
                setAngy(false)
                getTestMessage(this::class, "Set angy", false)
            },
            {
                setNector(false)
                getTestMessage(this::class, "Set nector", false)
            },
            {
                setHasStung(false)
                getTestMessage(this::class, "Set stung", false)
            },
            {
                setRolling(false)
                getTestMessage(this::class, "Set rolling", false)
            }
        )) }
}