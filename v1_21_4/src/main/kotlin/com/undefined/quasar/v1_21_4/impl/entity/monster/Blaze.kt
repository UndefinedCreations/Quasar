package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Blaze
import com.undefined.quasar.util.getPrivateField
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Blaze : LivingEntity(EntityType.BLAZE), Blaze {

    private var charged = false

    private var DATA_FLAGS_ID: EntityDataAccessor<Byte>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(
                net.minecraft.world.entity.monster.Blaze::class.java,
                FieldMappings.Entity.LivingEntity.Mob.Monster.Blaze.DATA_FLAGS_ID
            )
            return field
        }

    override fun isCharged(): Boolean = charged

    override fun setCharged(charged: Boolean) {
        val entity = entity ?: return
        this.charged = charged
        var var1 = entity.entityData.get(DATA_FLAGS_ID) as Byte
        var1 = if (charged) {
            (var1.toInt() or 1).toByte()
        } else {
            (var1.toInt() and -2).toByte()
        }
        entity.entityData.set(DATA_FLAGS_ID, var1)
        sendEntityMetaData()
    }

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val blazeJson = JsonObject()
        blazeJson.addProperty("charged", charged)
        monsterJson.add("blaze", blazeJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val blazeJson = jsonObject["blaze"].asJsonObject
        charged = blazeJson["charged"].asBoolean
    }

    override fun updateEntity() {
        super.updateEntity()
        setCharged(charged)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Blaze(net.minecraft.world.entity.EntityType.BLAZE, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setCharged(true)
                getTestMessage(this@Blaze::class, "Set charged", true)
            },
            {
                setCharged(false)
                getTestMessage(this@Blaze::class, "Set charged", false)
            }
        )) }
}