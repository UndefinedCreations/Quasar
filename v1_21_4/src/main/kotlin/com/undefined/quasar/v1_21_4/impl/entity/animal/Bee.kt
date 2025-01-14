package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Bee
import com.undefined.quasar.v1_21_4.impl.entity.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Bee : Animal(EntityType.BEE), Bee {

    private var angy = false
    private var nector = false
    private var stung = false
    private var rolling = false

    private var DATA_FLAGS_ID: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Bee::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Bee.DATA_FLAGS_ID
        )
    private var DATA_REMAINING_ANGER_TIME: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Bee::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Bee.DATA_REMAINING_ANGER_TIME
        )

    private val NECTOR_ID = 8
    private val STUNG_ID = 4
    private val ROLLING_ID = 2

    override fun isAngy(): Boolean = angy

    override fun setAngy(angy: Boolean) =
        setEntityDataAccessor(DATA_REMAINING_ANGER_TIME, if (angy) Int.MAX_VALUE else -1) {
            this.angy = angy
        }

    override fun hasNector(): Boolean = nector

    override fun setNector(nector: Boolean) =
        setSharedFlag(NECTOR_ID, nector) {
            this.nector = nector
        }

    override fun hasStung(): Boolean = stung

    override fun setHasStung(stung: Boolean) =
        setFlag(STUNG_ID, stung) {
            this.stung = stung
        }

    override fun isRolling(): Boolean = rolling

    override fun setRolling(rolling: Boolean) =
        setFlag(ROLLING_ID, rolling) {
            this.rolling = rolling
        }

    private fun setFlag(i: Int, flag: Boolean, runnable: (Unit) -> Unit) {
        val entity = entity ?: return
        if (flag) {
            entity.entityData.set(
                DATA_FLAGS_ID, ((entity.entityData.get(DATA_FLAGS_ID) as Byte).toInt() or i).toByte()
            )
        } else {
            entity.entityData.set(
                DATA_FLAGS_ID, ((entity.entityData.get(DATA_FLAGS_ID) as Byte).toInt() and i.inv()).toByte()
            )
        }
        sendEntityMetaData()
        runnable(Unit)
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
        super<Animal>.setEntityData(jsonObject)
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