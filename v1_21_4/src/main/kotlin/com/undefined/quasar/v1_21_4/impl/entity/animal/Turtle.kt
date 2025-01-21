package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Turtle
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Turtle : Animal(EntityType.TURTLE), Turtle {

    private var TRAVELLING: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Turtle::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Turtle.TRAVELLING
        )

    override fun setTravelling(travelling: Boolean) = setEntityDataAccessor(TRAVELLING, travelling)

    override fun isTravelling(): Boolean = getEntityDataValue(TRAVELLING) ?: false

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val turtleJson = JsonObject()
        turtleJson.addProperty("travelling", isTravelling())
        animalJson.add("turtle", turtleJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val turtleJson = jsonObject["turtle"].asJsonObject
        setTravelling(turtleJson["travelling"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setTravelling(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Turtle(net.minecraft.world.entity.EntityType.TURTLE, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(
            mutableListOf(
                {
                    setTravelling(true)
                    getTestMessage(this@Turtle::class, "Set travelling", isTravelling())
                }
            )
        ) }
}