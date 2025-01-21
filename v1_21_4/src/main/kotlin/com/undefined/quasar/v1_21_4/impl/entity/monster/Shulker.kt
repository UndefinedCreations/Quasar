package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Shulker
import com.undefined.quasar.util.Color
import com.undefined.quasar.util.Direction
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import kotlin.random.Random

class Shulker : LivingEntity(EntityType.SHULKER), Shulker {

    private var DATA_ATTACH_FACE_ID: EntityDataAccessor<net.minecraft.core.Direction>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Shulker::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Shulker.DATA_ATTACH_FACE_ID
        )

    private var DATA_PEEK_ID: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Shulker::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Shulker.DATA_PEEK_ID
        )

    private var DATA_COLOR_ID: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Shulker::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Shulker.DATA_COLOR_ID
        )

    override fun setDirection(direction: Direction) = setEntityDataAccessor(DATA_ATTACH_FACE_ID, net.minecraft.core.Direction.entries.first { it.get3DDataValue() == direction.id })

    override fun getDirection(): Direction = getEntityDataValue(DATA_ATTACH_FACE_ID)?.let { data ->
        Direction.entries.first { it.id == data.get3DDataValue() }
    } ?: Direction.DOWN

    override fun setPeeking(peeking: Int) = setEntityDataAccessor(DATA_PEEK_ID, peeking.toByte())

    override fun getPeeking(): Int = getEntityDataValue(DATA_PEEK_ID)?.toInt() ?: 0

    override fun setColor(color: Color?) = setEntityDataAccessor(DATA_COLOR_ID, color?.id?.toByte() ?: 16.toByte())

    override fun getColor(): Color? = getEntityDataValue(DATA_COLOR_ID)?.let { data -> Color.entries.firstOrNull() { it.id == data.toInt() } }

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val shulkerJson = JsonObject()
        shulkerJson.addProperty("direction", getDirection().id)
        shulkerJson.addProperty("peeking", getPeeking())
        shulkerJson.addProperty("color", getColor()?.id ?: -1)
        monsterJson.add("shulker", shulkerJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val shulkerJson = jsonObject["shulker"].asJsonObject
        setDirection(Direction.entries.first { it.id == shulkerJson["direction"].asInt })
        setPeeking(shulkerJson["peeking"].asInt)
        setColor(shulkerJson["color"].asInt.let { data ->
            if (data == -1) null else Color.entries.first { it.id == data }
        })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setDirection(Direction.DOWN)
        setPeeking(0)
        setColor(null)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Shulker(net.minecraft.world.entity.EntityType.SHULKER, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setPeeking(Random.nextInt(25))
                getTestMessage(this@Shulker::class, "Set peeking", getPeeking())
            },
            {
                setPeeking(0)
                getTestMessage(this@Shulker::class, "Set peeking", getPeeking())
            }
        ))
        addAll(Color.entries.map {
            {
                setColor(it)
                getTestMessage(this@Shulker::class, "Set color", getColor()?.name?.lowercase())
            }
        })
            addAll(Direction.entries.map {
                {
                    setDirection(it)
                    getTestMessage(this@Shulker::class, "Set direction", getDirection().name.lowercase())
                }
            })
        }
}