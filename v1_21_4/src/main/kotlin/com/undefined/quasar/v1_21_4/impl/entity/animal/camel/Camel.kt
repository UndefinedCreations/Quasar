package com.undefined.quasar.v1_21_4.impl.entity.animal.camel

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.camel.Camel
import com.undefined.quasar.v1_21_4.impl.entity.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.Pose
import net.minecraft.world.level.Level

class Camel : Animal(EntityType.CAMEL), Camel {

    private var dashing = false
    private var animation = Camel.Animation.STANDING

    private var DASH: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.camel.Camel::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.AbstractHorse.Camel.DASH
        )

    override fun setDashing(dashing: Boolean) =
        setEntityDataAccessor(DASH, dashing) {
            this.dashing = dashing
        }

    override fun isDashing(): Boolean = dashing

    override fun setAnimation(animation: Camel.Animation) =
        setEntityDataAccessor(DATA_POSE, Pose.entries.first { it.id() == animation.id }) {
            this.animation = animation
        }

    override fun getAnimation(): Camel.Animation = animation

    override fun getEntityData(): JsonObject {
        val horseJson = super.getEntityData()
        val camelJson = JsonObject()
        camelJson.addProperty("dashing", dashing)
        horseJson.add("camel", camelJson)
        return horseJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val camelJson = jsonObject["camel"].asJsonObject
        dashing = camelJson["dashing"].asBoolean
    }

    override fun updateEntity() {
        super.updateEntity()
        setDashing(dashing)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.camel.Camel(net.minecraft.world.entity.EntityType.CAMEL, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply {
            addAll(mutableListOf(
                {
                    setDashing(true)
                    getTestMessage(this@Camel::class, "Set dashing", true)
                },
                {
                    setDashing(false)
                    getTestMessage(this@Camel::class, "Set dashing", false)
                }
            ))
            addAll(Camel.Animation.entries.map {
                {
                    setAnimation(it)
                    getTestMessage(this@Camel::class, "Set animation", it)
                }
            })
        }
}