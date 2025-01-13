package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Armadillo
import com.undefined.quasar.util.getPrivateField
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.animal.armadillo.Armadillo.ArmadilloState
import net.minecraft.world.level.Level

class Armadillo : LivingEntity(EntityType.ARMADILLO), Armadillo {
    private var state = Armadillo.State.IDLE
    private var ARMADILLO_STATE: EntityDataAccessor<ArmadilloState>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.armadillo.Armadillo::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Armadillo.ARMADILLO_STATE
        )

    override fun setState(state: Armadillo.State) {
        val entity = entity ?: return
        this.state = state
        entity.entityData.set(ARMADILLO_STATE, getNMSState())
        sendEntityMetaData()
    }

    override fun getState(): Armadillo.State = state

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val armadilloJson = JsonObject()
        armadilloJson.addProperty("state", state.name)
        animalJson.add("armadillo", armadilloJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val armadilloJson = jsonObject["armadillo"].asJsonObject
        state = Armadillo.State.valueOf(armadilloJson["state"].asString)
    }

    override fun updateEntity() {
        super.updateEntity()
        setState(state)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(Armadillo.State.entries.map {
            {
                setState(it)
                getTestMessage(this@Armadillo::class, "Set state", it)
            }
        }) }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.armadillo.Armadillo(net.minecraft.world.entity.EntityType.ARMADILLO, level)

    private fun getNMSState(): ArmadilloState = ArmadilloState.valueOf(state.name)

}