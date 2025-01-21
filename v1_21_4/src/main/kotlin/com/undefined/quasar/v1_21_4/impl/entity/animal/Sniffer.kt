package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Sniffer
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.animal.sniffer.Sniffer.State
import net.minecraft.world.level.Level

class Sniffer : Animal(EntityType.SNIFFER), Sniffer {

    private var DATA_STATE: EntityDataAccessor<State>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.sniffer.Sniffer::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Sniffer.DATA_STATE
        )

    override fun setSate(state: Sniffer.State) = setEntityDataAccessor(DATA_STATE, State.entries.first { it.id() == state.id })

    override fun getState(): Sniffer.State = getEntityDataValue(DATA_STATE)?.let { data ->
        Sniffer.State.entries.first { it.id == data.id() }
    } ?: Sniffer.State.IDLING

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val snifferJson = JsonObject()
        snifferJson.addProperty("state", getState().id)
        animalJson.add("sniffer", snifferJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val snifferJson = jsonObject["sniffer"].asJsonObject
        setSate(Sniffer.State.entries.first { it.id == snifferJson["state"].asInt })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setSate(Sniffer.State.IDLING)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.sniffer.Sniffer(net.minecraft.world.entity.EntityType.SNIFFER, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(
            Sniffer.State.entries.map {
                {
                    setSate(it)
                    getTestMessage(this@Sniffer::class, "Set state", getState().name.lowercase())
                }
            }
        ) }
}