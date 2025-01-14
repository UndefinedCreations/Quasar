package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Breeze
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.Pose
import net.minecraft.world.level.Level

class Breeze : LivingEntity(EntityType.BREEZE), Breeze {

    private var animation = Breeze.Animation.IDLE

    override fun setAnimation(animation: Breeze.Animation) =
        setEntityDataAccessor(DATA_POSE, Pose.entries.first { it.id() == animation.id }) {
            this.animation = animation
        }

    override fun getAnimation(): Breeze.Animation = animation

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val breezeJson = JsonObject()
        breezeJson.addProperty("animation", animation.id)
        monsterJson.add("breeze", breezeJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val breezeJson = jsonObject["breeze"].asJsonObject
        animation = Breeze.Animation.entries.first { it.id == breezeJson["animation"].asInt }
    }

    override fun updateEntity() {
        super.updateEntity()
        setAnimation(animation)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.breeze.Breeze(net.minecraft.world.entity.EntityType.BREEZE, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(Breeze.Animation.entries.map {
            {
                setAnimation(it)
                getTestMessage(this@Breeze::class, "Setting animation", it)
            }
        }) }
}