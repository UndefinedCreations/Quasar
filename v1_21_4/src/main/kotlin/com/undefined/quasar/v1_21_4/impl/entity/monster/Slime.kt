package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Slime
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

open class Slime(entityType: EntityType = EntityType.SLIME) : LivingEntity(entityType), Slime {

    private var ID_SIZE: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Slime::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Slime.ID_SIZE
        )

    override fun setSlimeSize(size: Int) = setEntityDataAccessor(ID_SIZE, size)

    override fun getSlimeSize(): Int = getEntityDataValue(ID_SIZE) ?: 0

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val slimeJson = JsonObject()
        slimeJson.addProperty("size", getSlimeSize())
        monsterJson.add("slime", slimeJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val slimeJson = jsonObject["slime"].asJsonObject
        setSlimeSize(slimeJson["size"].asInt)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setSize(Slime.Size.SMALL)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Slime(net.minecraft.world.entity.EntityType.SLIME, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply {
            addAll(Slime.Size.entries.map {
                {
                    setSize(it)
                    getTestMessage(this@Slime::class, "Set size", getSize().name.lowercase())
                }
            })
        }
}