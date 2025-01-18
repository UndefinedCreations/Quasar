package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Spider
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

open class Spider(entityType: EntityType = EntityType.SPIDER) : LivingEntity(entityType), Spider {

    private var DATA_FLAGS_ID: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Spider::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Spider.DATA_FLAGS_ID
        )

    override fun setClimbing(climbing: Boolean) {
        val entity = entity ?: return
        var b0 = entity.entityData.get(DATA_FLAGS_ID) as Byte
        b0 = if (climbing) {
            (b0.toInt() or 1).toByte()
        } else {
            (b0.toInt() and -2).toByte()
        }
        entity.entityData.set(DATA_FLAGS_ID, b0)
        sendEntityMetaData()
    }

    override fun isClimbing(): Boolean {
        val entity = entity ?: return false
        return ((entity.entityData.get(DATA_FLAGS_ID) as Byte).toInt() and 1) != 0
    }

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val spiderJson = JsonObject()
        spiderJson.addProperty("climbing", isClimbing())
        monsterJson.add("spider", spiderJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val spiderJson = jsonObject["spider"].asJsonObject
        setClimbing(spiderJson["climbing"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setClimbing(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Spider(net.minecraft.world.entity.EntityType.SPIDER, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setClimbing(true)
                getTestMessage(this@Spider::class, "Set climbing", isClimbing())
            },
            {
                setClimbing(false)
                getTestMessage(this@Spider::class, "Set climbing", isClimbing())
            },
        )) }
}