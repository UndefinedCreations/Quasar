package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Breeze
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.Pose
import net.minecraft.world.level.Level

class Breeze : LivingEntity(EntityType.BREEZE), Breeze {

    override fun setAnimation(animation: Breeze.Animation) = setEntityDataAccessor(DATA_POSE, Pose.entries.first { it.id() == animation.id })

    override fun getAnimation(): Breeze.Animation = getEntityDataValue(DATA_POSE)?.let { data ->
        Breeze.Animation.entries.first { data.id() == it.id }
    } ?: Breeze.Animation.IDLE

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val breezeJson = JsonObject()
        breezeJson.addProperty("animation", getAnimation().id)
        monsterJson.add("breeze", breezeJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val breezeJson = jsonObject["breeze"].asJsonObject
        setAnimation(Breeze.Animation.entries.first { it.id == breezeJson["animation"].asInt })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setAnimation(Breeze.Animation.IDLE)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.breeze.Breeze(net.minecraft.world.entity.EntityType.BREEZE, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(Breeze.Animation.entries.map {
            {
                setAnimation(it)
                getTestMessage(this@Breeze::class, "Setting animation", getAnimation().name.lowercase())
            }
        }) }
}