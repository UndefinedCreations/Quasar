package com.undefined.quasar.v1_21_4.impl.entity.ambient

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.ambient.Bat
import com.undefined.quasar.util.getPrivateField
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Bat : LivingEntity(EntityType.BAT), Bat {

    private var resting = false

    private var DATA_ID_FLAGS: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.ambient.Bat::class.java,
            FieldMappings.Entity.LivingEntity.Mob.AmbientCreature.Bat.DATA_ID_FLAGS
        )

    override fun isResting(): Boolean = resting

    override fun setResting(resting: Boolean) {
        val entity = entity ?: return
        this.resting = resting
        val b0 = entity.entityData.get(DATA_ID_FLAGS) as Byte
        if (resting) {
            entity.entityData.set(DATA_ID_FLAGS, (b0.toInt() or 1).toByte())
        } else {
            entity.entityData.set(DATA_ID_FLAGS, (b0.toInt() and -2).toByte()
            )
        }
        sendEntityMetaData()
    }

    override fun getEntityData(): JsonObject {
        val ambientCreatureJson = super.getEntityData()
        val batJson = JsonObject()
        batJson.addProperty("resting", resting)
        ambientCreatureJson.add("bat", ambientCreatureJson)
        return ambientCreatureJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val ambientCreatureJson = jsonObject["bat"].asJsonObject
        resting = ambientCreatureJson["resting"].asBoolean
    }

    override fun updateEntity() {
        super.updateEntity()
        setResting(resting)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.ambient.Bat(net.minecraft.world.entity.EntityType.BAT, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
              setResting(true)
              getTestMessage(this@Bat::class, "Set resting", true)
         },
         {
                setResting(false)
             getTestMessage(this@Bat::class, "Set resting", false)
         }
         )) }
}