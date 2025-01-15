package com.undefined.quasar.v1_21_4.impl.entity.abstracts

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.abstracts.AbstractChestHorse
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor

abstract class AbstractChestHorse(entityType: EntityType) : AbstractHorse(entityType), AbstractChestHorse {

    private var chest = false

    private var DATA_ID_CHEST: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.horse.AbstractChestedHorse::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.AbstractHorse.AbstractChestHorse.DATA_ID_CHEST
        )

    override fun setChest(chest: Boolean) =
        setEntityDataAccessor(DATA_ID_CHEST, chest) {
            this.chest = chest
        }

    override fun hasChest(): Boolean = chest

    override fun getEntityData(): JsonObject {
        val horseJson = super.getEntityData()
        val chestHorseJson = JsonObject()
        chestHorseJson.addProperty("chest", chest)
        horseJson.add("chestHorse", chestHorseJson)
        return horseJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<AbstractHorse>.setEntityData(jsonObject)
        val chestHorseJson = jsonObject["chestHorse"].asJsonObject
        chest = chestHorseJson["chest"].asBoolean
    }

    override fun updateEntity() {
        super.updateEntity()
        setChest(chest)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setChest(true)
                getTestMessage(this@AbstractChestHorse::class, "Set chest", true)
            },
            {
                setChest(false)
                getTestMessage(this@AbstractChestHorse::class, "Set chest", false)
            }
        )) }
}