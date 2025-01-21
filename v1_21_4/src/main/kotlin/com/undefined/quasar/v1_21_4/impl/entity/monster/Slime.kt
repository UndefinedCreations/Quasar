package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Slime
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import kotlin.random.Random

open class Slime(entityType: EntityType = EntityType.SLIME) : LivingEntity(entityType), Slime {

    private var ID_SIZE: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Slime::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Slime.ID_SIZE
        )

    override fun setSize(size: Int) = setEntityDataAccessor(ID_SIZE, Math.clamp(size.toLong(), 1, 127))

    override fun getSize(): Int = getEntityDataValue(ID_SIZE) ?: 1

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val slimeJson = JsonObject()
        slimeJson.addProperty("size", getSize())
        monsterJson.add("slime", slimeJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val slimeJson = jsonObject["slime"].asJsonObject
        setSize(slimeJson["size"].asInt)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setSize(1)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Slime(net.minecraft.world.entity.EntityType.SLIME, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply {
            addAll(mutableListOf(
                {
                    setSize(Random.nextInt(5))
                    getTestMessage(this@Slime::class, "Set size", getSize())
                }
            ))
        }
}