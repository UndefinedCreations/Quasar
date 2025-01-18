package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Bee
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Bee : Animal(EntityType.BEE), Bee {

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

    override fun isAngy(): Boolean = getEntityDataValue(DATA_REMAINING_ANGER_TIME)?.let { it > 0 } ?: false

    override fun setAngy(angy: Boolean) = setEntityDataAccessor(DATA_REMAINING_ANGER_TIME, if (angy) Int.MAX_VALUE else -1)

    override fun hasNector(): Boolean = getFlag(NECTOR_ID)

    override fun setNector(nector: Boolean) = setFlag(NECTOR_ID, nector)

    override fun hasStung(): Boolean = getFlag(STUNG_ID)

    override fun setHasStung(stung: Boolean) = setFlag(STUNG_ID, stung)

    override fun isRolling(): Boolean = getFlag(ROLLING_ID)

    override fun setRolling(rolling: Boolean) = setFlag(ROLLING_ID, rolling)

    private fun setFlag(i: Int, flag: Boolean) {
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
    }

    private fun getFlag(i: Int): Boolean {
        val entity = entity ?: return false
        return ((entity.entityData.get(DATA_FLAGS_ID) as Byte).toInt() and i) != 0
    }

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val beeJson = JsonObject()
        beeJson.addProperty("angy", isAngy())
        beeJson.addProperty("nector", hasNector())
        beeJson.addProperty("stung", hasStung())
        beeJson.addProperty("rolling", isRolling())
        animalJson.add("bee", beeJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val beeJson = jsonObject["bee"].asJsonObject
        setAngy(beeJson["angy"].asBoolean)
        setNector(beeJson["nector"].asBoolean)
        setHasStung(beeJson["stung"].asBoolean)
        setRolling(beeJson["rolling"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setAngy(false)
        setNector(false)
        setHasStung(false)
        setRolling(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Bee(net.minecraft.world.entity.EntityType.BEE, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setAngy(true)
                getTestMessage(this::class, "Set angy", isAngy())
            },
            {
                setNector(true)
                getTestMessage(this::class, "Set nector", hasNector())
            },
            {
                setHasStung(true)
                getTestMessage(this::class, "Set stung", hasStung())
            },
            {
                setRolling(true)
                getTestMessage(this::class, "Set rolling", isRolling())
            },
            {
                setAngy(false)
                getTestMessage(this::class, "Set angy", isAngy())
            },
            {
                setNector(false)
                getTestMessage(this::class, "Set nector", hasNector())
            },
            {
                setHasStung(false)
                getTestMessage(this::class, "Set stung", hasStung())
            },
            {
                setRolling(false)
                getTestMessage(this::class, "Set rolling", isRolling())
            }
        )) }
}