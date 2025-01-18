package com.undefined.quasar.v1_21_4.impl.entity.animal.water

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.water.Dolphin
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Dolphin : Animal(EntityType.DOLPHIN), Dolphin {

    private var GOT_FISH: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Dolphin::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.WaterAnimal.GOT_FISH
        )
    private var MOISTNESS_LEVEL: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Dolphin::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.WaterAnimal.MOISTNESS_LEVEL
        )

    override fun setGotFish(gotFish: Boolean) =
        setEntityDataAccessor(GOT_FISH, gotFish)

    override fun hasFish(): Boolean = getEntityDataValue(GOT_FISH) ?: false

    override fun setMoistnessLevel(moistness: Int) =
        setEntityDataAccessor(MOISTNESS_LEVEL, moistness)

    override fun getMoistnessLevel(): Int = getEntityDataValue(MOISTNESS_LEVEL) ?: 0

    override fun getEntityData(): JsonObject {
        val waterJson = super.getEntityData()
        val dolphinJson = JsonObject()
        dolphinJson.addProperty("gotFish", hasFish())
        dolphinJson.addProperty("moistnessLevel", getMoistnessLevel())
        waterJson.add("dolphin", dolphinJson)
        return waterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val dolphinJson = jsonObject["dolphin"].asJsonObject
        setGotFish(dolphinJson["gotFish"].asBoolean)
        setMoistnessLevel(dolphinJson["moistnessLevel"].asInt)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setGotFish(false)
        setMoistnessLevel(0)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Dolphin(net.minecraft.world.entity.EntityType.DOLPHIN, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setGotFish(true)
                getTestMessage(this@Dolphin::class, "Set got fish", hasFish())
            },
            {
                setGotFish(false)
                getTestMessage(this@Dolphin::class, "Set got fish", hasFish())
            },
            {
                setMoistnessLevel(0)
                getTestMessage(this@Dolphin::class, "Set moistness level", getMoistnessLevel())
            }
        )) }
}