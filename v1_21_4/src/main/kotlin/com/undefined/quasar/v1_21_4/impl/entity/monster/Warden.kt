package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Warden
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.Pose
import net.minecraft.world.level.Level

class Warden : LivingEntity(EntityType.WARDEN), Warden {

    private var animation = Warden.Animation.STANDING

    override fun setAnimation(animation: Warden.Animation) {
        entity ?: return
        this.animation = animation
        setEntityDataAccessor(DATA_POSE, Pose.entries.first { animation.id == it.id() })
    }

    override fun getAnimation(): Warden.Animation = animation


    override fun setPoseStanding() {
        setAnimation(Warden.Animation.STANDING)
    }

    override fun isPoseStanding(): Boolean {
        return animation == Warden.Animation.STANDING
    }

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val wardenJson = JsonObject()
        wardenJson.addProperty("animation", getAnimation().id)
        monsterJson.add("warden", wardenJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val wardenJson = jsonObject["warden"].asJsonObject
        setAnimation(Warden.Animation.entries.first { it.id == wardenJson["animation"].asInt })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setAnimation(Warden.Animation.STANDING)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.warden.Warden(net.minecraft.world.entity.EntityType.WARDEN, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply {
            addAll(Warden.Animation.entries.map {
                {
                    setAnimation(it)
                    getTestMessage(this@Warden::class, "Set animation", getAnimation().name.lowercase())
                }
            })
        }
}