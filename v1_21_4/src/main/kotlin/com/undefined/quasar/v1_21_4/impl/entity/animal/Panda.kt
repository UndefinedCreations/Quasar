package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Panda
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Panda : Animal(EntityType.PANDA), Panda {

    private var MAIN_GENE_ID: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Panda::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Panda.MAIN_GENE_ID
        )

    private var HIDDEN_GENE_ID: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Panda::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Panda.HIDDEN_GENE_ID
        )

    private var DATA_ID_FLAGS: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Panda::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Panda.DATA_ID_FLAGS
        )

    private var EAT_COUNTER: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Panda::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Panda.EAT_COUNTER
        )

    private var UNHAPPY_COUNTER: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Panda::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Panda.UNHAPPY_COUNTER
        )

    private val ROLLING = 4
    private val SIT = 8
    private val ON_BACK = 16
    private val SNEEZE = 2

    override fun setSitting(sit: Boolean) = setFlag(SIT, sit)

    override fun isSitting(): Boolean = getFlag(SIT)

    override fun setOnBack(onBack: Boolean) = setFlag(ON_BACK, onBack)

    override fun isOnBack(): Boolean = getFlag(ON_BACK)

    override fun setEating(eating: Boolean) = setEntityDataAccessor(EAT_COUNTER, if (eating) Int.MAX_VALUE else 0)

    override fun isEating(): Boolean = getEntityDataValue(EAT_COUNTER)?.let { it > 0 } ?: false

    override fun setRolling(rolling: Boolean) = setFlag(ROLLING, rolling)

    override fun isRolling(): Boolean = getFlag(ROLLING)

    override fun setSneezing(sneezing: Boolean) = setFlag(SNEEZE, sneezing)

    override fun isSneezing(): Boolean = getFlag(SNEEZE)

    override fun setUnhappy(unhappy: Boolean) = setEntityDataAccessor(UNHAPPY_COUNTER, if (unhappy) Int.MAX_VALUE else 0)

    override fun isUnhappy(): Boolean = getEntityDataValue(UNHAPPY_COUNTER)?.let { it > 0 } ?: false

    override fun setVariant(variant: Panda.Variant) {
        setEntityDataAccessor(MAIN_GENE_ID, variant.id.toByte())
        setEntityDataAccessor(HIDDEN_GENE_ID, variant.id.toByte())
    }

    override fun getVariant(): Panda.Variant = getEntityDataValue(MAIN_GENE_ID)?.let { data ->
        Panda.Variant.entries.first { it.id == data.toInt() }
    } ?: Panda.Variant.LAZY

    private fun getFlag(id: Int): Boolean {
        val entity = entity ?: return false
        return ((entity.entityData.get(DATA_ID_FLAGS) as Byte).toInt() and id) != 0
    }

    private fun setFlag(i: Int, flag: Boolean) {
        val entity = entity ?: return
        val b0 = entity.entityData.get(DATA_ID_FLAGS)
        if (flag) {
            entity.entityData.set(DATA_ID_FLAGS, (b0.toInt() or i).toByte())
        } else {
            entity.entityData.set(
                DATA_ID_FLAGS,
                (b0.toInt() and i.inv()).toByte()
            )
        }
        sendEntityMetaData()
    }

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val pandaJson = JsonObject()
        pandaJson.addProperty("sitting", isSitting())
        pandaJson.addProperty("sneezing", isSneezing())
        pandaJson.addProperty("eating", isEating())
        pandaJson.addProperty("rolling", isRolling())
        pandaJson.addProperty("onBack", isOnBack())
        pandaJson.addProperty("variant", getVariant().id)
        animalJson.add("panda", pandaJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val pandaJson = jsonObject["panda"].asJsonObject
        setSitting(pandaJson["sitting"].asBoolean)
        setSneezing(pandaJson["sneezing"].asBoolean)
        setEating(pandaJson["eating"].asBoolean)
        setRolling(pandaJson["rolling"].asBoolean)
        setOnBack(pandaJson["onBack"].asBoolean)
        setVariant(Panda.Variant.entries.first { it.id == pandaJson["variant"].asInt })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setSitting(false)
        setSneezing(false)
        setEating(false)
        setRolling(false)
        setOnBack(false)
        setVariant(Panda.Variant.LAZY)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Panda(net.minecraft.world.entity.EntityType.PANDA, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setSitting(true)
                getTestMessage(this@Panda::class, "Set sitting", isSitting())
            },
            {
                setSitting(false)
                getTestMessage(this@Panda::class, "Set sitting", isSitting())
            },
            {
                setSneezing(true)
                getTestMessage(this@Panda::class, "Set sneezing", isSneezing())
            },
            {
                setSneezing(false)
                getTestMessage(this@Panda::class, "Set sneezing", isSneezing())
            },
            {
                setEating(true)
                getTestMessage(this@Panda::class, "Set eating", isEating())
            },
            {
                setEating(false)
                getTestMessage(this@Panda::class, "Set eating", isEating())
            },
            {
                setRolling(true)
                getTestMessage(this@Panda::class, "Set rolling", isRolling())
            },
            {
                setRolling(false)
                getTestMessage(this@Panda::class, "Set rolling", isRolling())
            },
            {
                setOnBack(true)
                getTestMessage(this@Panda::class, "Set on back", isOnBack())
            },
            {
                setOnBack(false)
                getTestMessage(this@Panda::class, "Set on back", isOnBack())
            }
        ))
        addAll(Panda.Variant.entries.map {
            {
                setVariant(it)
                getTestMessage(this@Panda::class, "Set variant", getVariant().name.lowercase())
            }
        })}
}