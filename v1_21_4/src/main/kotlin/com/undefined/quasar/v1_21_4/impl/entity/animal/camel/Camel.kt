package com.undefined.quasar.v1_21_4.impl.entity.animal.camel

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.camel.Camel
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.AbstractHorse
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.Pose
import net.minecraft.world.level.Level

class Camel : AbstractHorse(EntityType.CAMEL), Camel {

    private var DASH: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.camel.Camel::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.AbstractHorse.Camel.DASH
        )

    override fun setDashing(dashing: Boolean) = setEntityDataAccessor(DASH, dashing)

    override fun isDashing(): Boolean = getEntityDataValue(DASH) ?: false

    override fun setAnimation(animation: Camel.Animation) = setEntityDataAccessor(DATA_POSE, Pose.entries.first { it.id() == animation.id })

    override fun getAnimation(): Camel.Animation = getEntityDataValue(DATA_POSE)?.let {
        data -> Camel.Animation.entries.first { it.id == data.id() }
    } ?: Camel.Animation.STANDING

    override fun getEntityData(): JsonObject {
        val horseJson = super.getEntityData()
        val camelJson = JsonObject()
        camelJson.addProperty("dashing", isDashing())
        camelJson.addProperty("animation", getAnimation().id)
        horseJson.add("camel", camelJson)
        return horseJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<AbstractHorse>.setEntityData(jsonObject)
        val camelJson = jsonObject["camel"].asJsonObject
        setDashing(camelJson["dashing"].asBoolean)
        setAnimation(Camel.Animation.entries.first { it.id == camelJson["animation"].asInt })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setDashing(false)
        setAnimation(Camel.Animation.STANDING)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.camel.Camel(net.minecraft.world.entity.EntityType.CAMEL, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply {
            addAll(mutableListOf(
                {
                    setDashing(true)
                    getTestMessage(this@Camel::class, "Set dashing", isDashing())
                },
                {
                    setDashing(false)
                    getTestMessage(this@Camel::class, "Set dashing", isDashing())
                }
            ))
            addAll(Camel.Animation.entries.map {
                {
                    setAnimation(it)
                    getTestMessage(this@Camel::class, "Set animation", getAnimation().name.lowercase())
                }
            })
        }
}