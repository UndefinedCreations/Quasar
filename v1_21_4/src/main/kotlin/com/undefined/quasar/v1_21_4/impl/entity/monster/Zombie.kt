package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Zombie
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

open class Zombie(entityType: EntityType = EntityType.ZOMBIE) : LivingEntity(entityType), Zombie {

    private var DATA_BABY_ID: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Zombie::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Zombie.DATA_BABY_ID
        )
    private var DATA_DROWNED_CONVERSION_ID: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Zombie::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Zombie.DATA_DROWNED_CONVERSION_ID
        )

    override fun isConverting(): Boolean = getEntityDataValue(DATA_DROWNED_CONVERSION_ID) ?: false

    override fun setConversion(conversion: Boolean) = setEntityDataAccessor(DATA_DROWNED_CONVERSION_ID, conversion)

    override fun isBaby(): Boolean = getEntityDataValue(DATA_BABY_ID) ?: false

    override fun setBaby(baby: Boolean) = setEntityDataAccessor(DATA_BABY_ID, baby)

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val zombieJson = JsonObject()
        zombieJson.addProperty("baby", isBaby())
        zombieJson.addProperty("conversion", isConverting())
        monsterJson.add("zombie", zombieJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val zombieJson = jsonObject["zombie"].asJsonObject
        setBaby(zombieJson["baby"].asBoolean)
        setConversion(zombieJson["conversion"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setBaby(false)
        setConversion(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Zombie(net.minecraft.world.entity.EntityType.ZOMBIE, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setBaby(true)
                getTestMessage(this@Zombie::class, "Set baby", isBaby())
            },
            {
                setBaby(false)
                getTestMessage(this@Zombie::class, "Set baby", isBaby())
            },
            {
                setConversion(true)
                getTestMessage(this@Zombie::class, "Set conversion", isConverting())
            },
            {
                setConversion(false)
                getTestMessage(this@Zombie::class, "Set conversion", isConverting())
            }
        )) }
}