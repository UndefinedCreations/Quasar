package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Skeleton
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

open class Skeleton(entityType: EntityType = EntityType.SKELETON) : LivingEntity(entityType), Skeleton {

    private var DATA_STRAY_CONVERSION_ID: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Skeleton::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Skeleton.DATA_STRAY_CONVERSION_ID
        )

    override fun setStrayConversion(conversion: Boolean) = setEntityDataAccessor(DATA_STRAY_CONVERSION_ID, conversion)

    override fun isStrayConversion(): Boolean = getEntityDataValue(DATA_STRAY_CONVERSION_ID) ?: false

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val skeletonJson = JsonObject()
        skeletonJson.addProperty("conversion", isStrayConversion())
        monsterJson.add("skeleton", skeletonJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val skeletonJson = jsonObject["skeleton"].asJsonObject
        setStrayConversion(skeletonJson["conversion"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setStrayConversion(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Skeleton(net.minecraft.world.entity.EntityType.SKELETON, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setStrayConversion(true)
                getTestMessage(this@Skeleton::class, "Set conversion", isStrayConversion())
            },
            {
                setStrayConversion(false)
                getTestMessage(this@Skeleton::class, "Set conversion", isStrayConversion())
            }
        )) }
}