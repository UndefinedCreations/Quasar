package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Hoglin
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Hoglin : Animal(EntityType.HOGLIN), Hoglin {

    private var DATA_IMMUNE_TO_ZOMBIFICATION: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.hoglin.Hoglin::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Hoglin.DATA_IMMUNE_TO_ZOMBIFICATION
        )

    override fun setImmuneToZombification(immune: Boolean) = setEntityDataAccessor(DATA_IMMUNE_TO_ZOMBIFICATION, immune)

    override fun isImmuneToZombification(): Boolean = getEntityDataValue(DATA_IMMUNE_TO_ZOMBIFICATION) ?: true

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val hoglinJson = JsonObject()
        hoglinJson.addProperty("immune", isImmuneToZombification())
        animalJson.add("hoglin", hoglinJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val hoglinJson = jsonObject["hoglin"].asJsonObject
        setImmuneToZombification(hoglinJson["immune"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setImmuneToZombification(true)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.hoglin.Hoglin(net.minecraft.world.entity.EntityType.HOGLIN, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setImmuneToZombification(false)
                getTestMessage(this@Hoglin::class, "Set immune", isImmuneToZombification())
            },
            {
                setImmuneToZombification(true)
                getTestMessage(this@Hoglin::class, "Set immune", isImmuneToZombification())
            }
        )) }
}