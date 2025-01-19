package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Phantom
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import kotlin.random.Random

class Phantom : LivingEntity(EntityType.PHANTOM), Phantom {

    private var ID_SIZE: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Phantom::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Phantom.ID_SIZE
        )

    override fun setSize(size: Int) = setEntityDataAccessor(ID_SIZE, Math.clamp(size.toLong(), 0, 64))

    override fun getSize(): Int = getEntityDataValue(ID_SIZE) ?: 0

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val phantomJson = JsonObject()
        phantomJson.addProperty("size", getSize())
        monsterJson.add("phantom", phantomJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val phantomJson = jsonObject["phantom"].asJsonObject
        setSize(phantomJson["size"].asInt)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setSize(0)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Phantom(net.minecraft.world.entity.EntityType.PHANTOM, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setSize(Random.nextInt(64))
                getTestMessage(this@Phantom::class, "Set size", getSize())
            }
        )) }
}