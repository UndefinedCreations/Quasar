package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Creeper
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import kotlin.random.Random

class Creeper : LivingEntity(EntityType.CREEPER), Creeper {

    private var swell = 0
    private var ignite = false

    private var DATA_SWELL_DIR: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Creeper::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Creeper.DATA_SWELL_DIR
        )
    private var DATA_IS_IGNITED: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Creeper::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Creeper.DATA_IS_IGNITED
        )

    override fun setSwell(swell: Int) =
        setEntityDataAccessor(DATA_SWELL_DIR, swell) {
            this.swell = swell
        }

    override fun getSwell(): Int = swell

    override fun setIgnite(ignite: Boolean) =
        setEntityDataAccessor(DATA_IS_IGNITED, ignite) {
            this.ignite = ignite
        }

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val creeperJson = JsonObject()
        creeperJson.addProperty("swell", swell)
        creeperJson.addProperty("ignite", ignite)
        monsterJson.add("creeper", creeperJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val creeperJson = jsonObject["creeper"].asJsonObject
        this.swell = creeperJson["swell"].asInt
        this.ignite = creeperJson["ignite"].asBoolean
    }

    override fun updateEntity() {
        super.updateEntity()
        setSwell(swell)
        setIgnite(ignite)
    }

    override fun isIgnite(): Boolean = ignite

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Creeper(net.minecraft.world.entity.EntityType.CREEPER, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setIgnite(true)
                getTestMessage(this@Creeper::class, "Set ignite", ignite)
            },
            {
                setIgnite(false)
                getTestMessage(this@Creeper::class, "Set ignite", ignite)
            },
            {
                setSwell(Random.nextInt(1000))
                getTestMessage(this@Creeper::class, "Set swelling", getSwell())
            },
            {
                setSwell(0)
                getTestMessage(this@Creeper::class, "Set swelling", getSwell())
            },
        )) }
}