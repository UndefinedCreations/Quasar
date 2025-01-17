package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Fox
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Fox : Animal(EntityType.FOX), Fox {

    private var variant = Fox.Variant.RED

    private val FLAG_SITTING = 1
    private val FLAG_CROUCHING = 4
    private val FLAG_INTERESTED = 8
    private val FLAG_POUNCING = 16
    private val FLAG_SLEEPING = 32
    private val FLAG_FACEPLANTED = 64
    private val FLAG_DEFENDING = 128

    private var DATA_TYPE_ID: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Fox::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Fox.DATA_TYPE_ID
        )

    private var DATA_FLAGS_ID: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Fox::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Fox.DATA_FLAGS_ID
        )

    override fun setVariant(variant: Fox.Variant) =
        setEntityDataAccessor(DATA_TYPE_ID, variant.id) {
            this.variant = variant
        }

    override fun getVariant(): Fox.Variant = variant

    override fun setSitting(sitting: Boolean) = setFlag(FLAG_SITTING, sitting) {}

    override fun isSitting(): Boolean = getFlag(FLAG_SITTING)

    override fun setCrouching(crouching: Boolean) = setFlag(FLAG_CROUCHING, crouching) {}

    override fun isCrouching(): Boolean = getFlag(FLAG_CROUCHING)

    override fun setInterested(interested: Boolean) = setFlag(FLAG_INTERESTED, interested) {}

    override fun isInterested(): Boolean = getFlag(FLAG_INTERESTED)

    override fun setPouncing(pouncing: Boolean) = setFlag(FLAG_POUNCING, pouncing) {}

    override fun isPouncing(): Boolean = getFlag(FLAG_POUNCING)

    override fun setFoxSleeping(sleeping: Boolean) = setFlag(FLAG_SLEEPING, sleeping) {}

    override fun isFoxSleeping(): Boolean = getFlag(FLAG_SLEEPING)

    override fun setFacePlanted(facePlanted: Boolean) = setFlag(FLAG_FACEPLANTED, facePlanted) {}

    override fun isFacePlanted(): Boolean = getFlag(FLAG_FACEPLANTED)

    override fun setDefending(defending: Boolean) = setFlag(FLAG_DEFENDING, defending) {}

    override fun isDefending(): Boolean = getFlag(FLAG_DEFENDING)

    private fun setFlag(i: Int, flag: Boolean, runnable: (Unit) -> Unit) {
        val entity = entity ?: return
        if (flag) {
            entity.entityData.set(DATA_FLAGS_ID, ((entity.entityData.get(DATA_FLAGS_ID) as Byte).toInt() or i).toByte()
            )
        } else {
            entity.entityData.set(DATA_FLAGS_ID, ((entity.entityData.get(DATA_FLAGS_ID) as Byte).toInt() and i.inv()).toByte())
        }
        sendEntityMetaData()
        runnable(Unit)
    }

    private fun getFlag(i: Int): Boolean {
        val entity = entity ?: return false
        return ((entity.entityData.get(DATA_FLAGS_ID) as Byte).toInt() and i) != 0
    }

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val foxJson = JsonObject()
        foxJson.addProperty("variant", variant.id)
        foxJson.addProperty("sitting", isSitting())
        foxJson.addProperty("interested", isInterested())
        foxJson.addProperty("pouncing", isPouncing())
        foxJson.addProperty("sleeping", isFoxSleeping())
        foxJson.addProperty("facePlanted", isFacePlanted())
        foxJson.addProperty("defending", isDefending())
        animalJson.add("fox", foxJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val foxJson = jsonObject["fox"].asJsonObject
        variant = Fox.Variant.entries.first { it.id == foxJson["variant"].asInt }
        setSitting(foxJson["sitting"].asBoolean)
        setInterested(foxJson["interested"].asBoolean)
        setPouncing(foxJson["pouncing"].asBoolean)
        setFoxSleeping(foxJson["sleeping"].asBoolean)
        setFacePlanted(foxJson["facePlanted"].asBoolean)
        setDefending(foxJson["defending"].asBoolean)
    }

    override fun updateEntity() {
        super.updateEntity()
        setVariant(variant)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Fox(net.minecraft.world.entity.EntityType.FOX, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setVariant(Fox.Variant.SNOW)
                getTestMessage(this@Fox::class, "Set variant", "snow")
            },
            {
                setSitting(true)
                getTestMessage(this@Fox::class, "Set sitting", true)
            },
            {
                setSitting(false)
                getTestMessage(this@Fox::class, "Set sitting", false)
            },
            {
                setCrouching(true)
                getTestMessage(this@Fox::class, "Set crouching", true)
            },
            {
                setCrouching(false)
                getTestMessage(this@Fox::class, "Set crouching", false)
            },
            {
                setInterested(true)
                getTestMessage(this@Fox::class, "Set interested", true)
            },
            {
                setInterested(false)
                getTestMessage(this@Fox::class, "Set interested", false)
            },
            {
                setPouncing(true)
                getTestMessage(this@Fox::class, "Set pouncing", true)
            },
            {
                setPouncing(false)
                getTestMessage(this@Fox::class, "Set pouncing", false)
            },
            {
                setFoxSleeping(true)
                getTestMessage(this@Fox::class, "Set fox sleeping", true)
            },
            {
                setFoxSleeping(false)
                getTestMessage(this@Fox::class, "Set fox sleeping", false)
            },
            {
                setFacePlanted(true)
                getTestMessage(this@Fox::class, "Set face planted", true)
            },
            {
                setFacePlanted(false)
                getTestMessage(this@Fox::class, "Set face planted", false)
            },
            {
                setDefending(true)
                getTestMessage(this@Fox::class, "Set defending", true)
            },
            {
                setDefending(false)
                getTestMessage(this@Fox::class, "Set defending", false)
            }
        )) }
}