package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Goat
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Goat : Animal(EntityType.GOAT), Goat {

    private var DATA_IS_SCREAMING_GOAT: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.goat.Goat::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Goat.DATA_IS_SCREAMING_GOAT
        )
    private var DATA_HAS_LEFT_HORN: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.goat.Goat::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Goat.DATA_HAS_LEFT_HORN
        )
    private var DATA_HAS_RIGHT_HORN: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.goat.Goat::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Goat.DATA_HAS_RIGHT_HORN
        )

    override fun setScreamingGoat(screamingGoat: Boolean) = setEntityDataAccessor(DATA_IS_SCREAMING_GOAT, screamingGoat)

    override fun isScreamingGoat(): Boolean = getEntityDataValue(DATA_IS_SCREAMING_GOAT) ?: false

    override fun setLeftHorn(leftHorn: Boolean) = setEntityDataAccessor(DATA_HAS_LEFT_HORN, leftHorn)

    override fun hasLeftHorn(): Boolean = getEntityDataValue(DATA_HAS_LEFT_HORN) ?: true

    override fun setRightHorn(rightHorn: Boolean) = setEntityDataAccessor(DATA_HAS_RIGHT_HORN, rightHorn)

    override fun hasRightHorn(): Boolean = getEntityDataValue(DATA_HAS_RIGHT_HORN) ?: true

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val goatJson = JsonObject()
        goatJson.addProperty("screaming", isScreamingGoat())
        goatJson.addProperty("leftHorn", hasLeftHorn())
        goatJson.addProperty("rightHorn", hasRightHorn())
        animalJson.add("goat", goatJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val goatJson = jsonObject["goat"].asJsonObject
        setScreamingGoat(goatJson["screaming"].asBoolean)
        setLeftHorn(goatJson["leftHorn"].asBoolean)
        setRightHorn(goatJson["rightHorn"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setScreamingGoat(false)
        setLeftHorn(true)
        setRightHorn(true)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.goat.Goat(net.minecraft.world.entity.EntityType.GOAT, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setScreamingGoat(true)
                getTestMessage(this@Goat::class, "Set screaming", isScreamingGoat())
            },
            {
                setScreamingGoat(false)
                getTestMessage(this@Goat::class, "Set screaming", isScreamingGoat())
            },
            {
                setLeftHorn(false)
                getTestMessage(this@Goat::class, "Set left horn", hasLeftHorn())
            },
            {
                setRightHorn(false)
                getTestMessage(this@Goat::class, "Set right horn", hasRightHorn())
            },
            {
                setLeftHorn(true)
                getTestMessage(this@Goat::class, "Set left horn", hasLeftHorn())
            },
            {
                setRightHorn(true)
                getTestMessage(this@Goat::class, "Set right horn", hasRightHorn())
            }
        )) }
}