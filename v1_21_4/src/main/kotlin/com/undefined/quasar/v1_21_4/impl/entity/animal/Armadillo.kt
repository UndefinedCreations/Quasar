package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Armadillo
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.animal.armadillo.Armadillo.ArmadilloState
import net.minecraft.world.level.Level

class Armadillo : Animal(EntityType.ARMADILLO), Armadillo {

    private var ARMADILLO_STATE: EntityDataAccessor<ArmadilloState>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.armadillo.Armadillo::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Armadillo.ARMADILLO_STATE
        )

    override fun setState(state: Armadillo.State) = setEntityDataAccessor(ARMADILLO_STATE, getNMSState(state))

    override fun getState(): Armadillo.State = getEntityDataValue(ARMADILLO_STATE)?.let {
        data -> when(data)  {
        ArmadilloState.IDLE -> Armadillo.State.IDLE
        ArmadilloState.SCARED -> Armadillo.State.SCARED
        ArmadilloState.ROLLING -> Armadillo.State.ROLLING
        ArmadilloState.UNROLLING -> Armadillo.State.UNROLLING
        }
    } ?: Armadillo.State.IDLE

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val armadilloJson = JsonObject()
        armadilloJson.addProperty("state", getState().id)
        animalJson.add("armadillo", armadilloJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val armadilloJson = jsonObject["armadillo"].asJsonObject
        setState(Armadillo.State.entries.first { it.id == armadilloJson["state"].asInt })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setState(Armadillo.State.IDLE)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(Armadillo.State.entries.map {
            {
                setState(it)
                getTestMessage(this@Armadillo::class, "Set state", getState().name.lowercase())
            }
        }) }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.armadillo.Armadillo(net.minecraft.world.entity.EntityType.ARMADILLO, level)

    private fun getNMSState(state: Armadillo.State): ArmadilloState = when(state) {
        Armadillo.State.IDLE -> ArmadilloState.IDLE
        Armadillo.State.SCARED -> ArmadilloState.SCARED
        Armadillo.State.ROLLING -> ArmadilloState.ROLLING
        Armadillo.State.UNROLLING -> ArmadilloState.UNROLLING
    }

}