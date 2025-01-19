package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Ocelot
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Ocelot : Animal(EntityType.OCELOT), Ocelot {

    private var DATA_TRUSTING: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Ocelot::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Ocelot.DATA_TRUSTING
        )

    override fun setTrusting(trusting: Boolean) = setEntityDataAccessor(DATA_TRUSTING, trusting)

    override fun isTrusting(): Boolean = getEntityDataValue(DATA_TRUSTING) ?: false

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val ocelotJson = JsonObject()
        ocelotJson.addProperty("trusting", isTrusting())
        animalJson.add("ocelot", ocelotJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val ocelotJson = jsonObject["ocelot"].asJsonObject
        setTrusting(ocelotJson["trusting"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setTrusting(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Ocelot(net.minecraft.world.entity.EntityType.OCELOT, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setTrusting(true)
                getTestMessage(this@Ocelot::class, "Set trusting", isTrusting())
            }
        )) }
}