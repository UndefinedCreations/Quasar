package com.undefined.quasar.v1_21_4.impl.entity.animal.water

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.water.Salmon
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.level.Level

class Salmon : Entity(EntityType.SALMON), Salmon {

    private var DATA_TYPE: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Salmon::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Salmon.DATA_TYPE
        )

    override fun setSize(size: Salmon.Size) = setEntityDataAccessor(DATA_TYPE, size.id)

    override fun getSize(): Salmon.Size = getEntityDataValue(DATA_TYPE)?.let { data ->
        Salmon.Size.entries.find { it.id == data }
    } ?: Salmon.Size.MEDIUM

    override fun getEntityData(): JsonObject {
        val entityJson = super.getEntityData()
        val salmonJson = JsonObject()
        salmonJson.addProperty("size", getSize().id)
        entityJson.add("salmon", salmonJson)
        return entityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val salmonJson = jsonObject["salmon"].asJsonObject
        setSize(Salmon.Size.entries.first { it.id == salmonJson["size"].asInt })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setSize(Salmon.Size.MEDIUM)
    }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.animal.Salmon(net.minecraft.world.entity.EntityType.SALMON, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(Salmon.Size.entries.map {
            {
                setSize(it)
                getTestMessage(this@Salmon::class, "Set size", getSize().name.lowercase())
            }
        }) }
}