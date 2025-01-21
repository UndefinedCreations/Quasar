package com.undefined.quasar.v1_21_4.impl.entity.animal.water

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.water.TropicalFish
import com.undefined.quasar.util.Color
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.animal.TropicalFish.Pattern
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.Level

class TropicalFish : LivingEntity(EntityType.TROPICAL_FISH), TropicalFish {

    private var DATA_ID_TYPE_VARIANT: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.TropicalFish::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.TropicalFish.DATA_ID_TYPE_VARIANT
        )

    override fun setPattern(pattern: TropicalFish.Pattern) {
        entity ?: return
        val data = getPackedVariant()
        val pattern = Pattern.entries.first { it.serializedName == pattern.id }
        val baseColor = getBaseColor(data)
        val patternColor = getPatternColor(data)
        setPacketVariant(packVariant(pattern, baseColor, patternColor))
        sendEntityMetaData()
    }

    override fun getPattern(): TropicalFish.Pattern {
        entity ?: return TropicalFish.Pattern.KOB
        val data = getPackedVariant()
        return TropicalFish.Pattern.entries.first { it.id == getPattern(data).serializedName }
    }

    override fun setBaseColor(color: Color) {
        entity ?: return
        val data = getPackedVariant()
        val pattern = getPattern(data)
        val baseColor = DyeColor.byId(color.id)
        val patternColor = getPatternColor(data)
        setPacketVariant(packVariant(pattern, baseColor, patternColor))
        sendEntityMetaData()
    }

    override fun getBaseColor(): Color {
        entity ?: return Color.RED
        val data = getPackedVariant()
        return Color.entries.first { it.id == getBaseColor(data).id }
    }

    override fun setPatternColor(color: Color) {
        entity ?: return
        val data = getPackedVariant()
        val pattern = getPattern(data)
        val baseColor = getBaseColor(data)
        val patternColor = DyeColor.byId(color.id)
        setPacketVariant(packVariant(pattern, baseColor, patternColor))
        sendEntityMetaData()
    }

    override fun getPatternColor(): Color {
        entity ?: return Color.RED
        val data = getPackedVariant()
        return Color.entries.first { it.id == getPatternColor(data).id }
    }

    private fun getBaseColor(var0: Int): DyeColor {
        return DyeColor.byId(var0 shr 16 and 255)
    }

    private fun getPatternColor(var0: Int): DyeColor {
        return DyeColor.byId(var0 shr 24 and 255)
    }

    private fun getPattern(var0: Int): Pattern {
        return Pattern.byId(var0 and '\uffff'.code)
    }

    private fun getPackedVariant(): Int = getEntityDataValue(DATA_ID_TYPE_VARIANT) ?: 0

    private fun setPacketVariant(data: Int) {
        setEntityDataAccessor(DATA_ID_TYPE_VARIANT, data)
        sendEntityMetaData()
    }

    private fun packVariant(var0: net.minecraft.world.entity.animal.TropicalFish.Pattern, var1: DyeColor, var2: DyeColor): Int {
        return var0.packedId and '\uffff'.code or ((var1.id and 255) shl 16) or ((var2.id and 255) shl 24)
    }

    override fun getEntityData(): JsonObject {
        val waterJson = super.getEntityData()
        val tropicalFishJson = JsonObject()
        tropicalFishJson.addProperty("data", getPackedVariant())
        waterJson.add("tropicalFish", tropicalFishJson)
        return waterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val tropicalFishJson = jsonObject["tropicalFish"].asJsonObject
        val data = tropicalFishJson["data"].asInt
        setPattern(TropicalFish.Pattern.entries.first { it.id == getPattern(data).serializedName })
        setBaseColor(Color.entries.first { it.id == getBaseColor(data).id })
        setPatternColor(Color.entries.first { it.id == getPatternColor(data).id })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setPattern(TropicalFish.Pattern.KOB)
        setBaseColor(Color.GRAY)
        setPatternColor(Color.RED)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.TropicalFish(net.minecraft.world.entity.EntityType.TROPICAL_FISH, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(
            TropicalFish.Pattern.entries.map {
                {
                    setPattern(it)
                    getTestMessage(this@TropicalFish::class, "Set pattern", getPattern().name.lowercase())
                }
            }
        )
        addAll(Color.entries.map {
            {
                setBaseColor(it)
                getTestMessage(this@TropicalFish::class, "Set base color", getBaseColor().name.lowercase())
            }
        })

        addAll(Color.entries.map {
            {
                setPatternColor(it)
                getTestMessage(this@TropicalFish::class, "Set pattern color", getPatternColor().name.lowercase())
            }
        })}
}