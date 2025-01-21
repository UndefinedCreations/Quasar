package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Wolf
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.TamableAnimal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.core.Holder
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.animal.WolfVariant
import net.minecraft.world.level.Level
import org.bukkit.Color
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftWolf
import kotlin.random.Random

class Wolf : TamableAnimal(EntityType.WOLF), Wolf {

    private var DATA_COLLAR_COLOR: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Wolf::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Wolf.DATA_COLLAR_COLOR
        )

    private var DATA_REMAINING_ANGER_TIME: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Wolf::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Wolf.DATA_REMAINING_ANGER_TIME
        )

    private var DATA_VARIANT_ID: EntityDataAccessor<Holder<WolfVariant>>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Wolf::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Wolf.DATA_VARIANT_ID
        )

    override fun setCollarColor(color: Color) = setEntityDataAccessor(DATA_COLLAR_COLOR, color.asARGB())

    override fun getCollarColor(): Color = getEntityDataValue(DATA_COLLAR_COLOR)?.let { data ->
        Color.fromARGB(data)
    } ?: Color.RED

    override fun setAngy(angy: Boolean) = setEntityDataAccessor(DATA_REMAINING_ANGER_TIME, if (angy) Int.MAX_VALUE else 0)

    override fun isAngy(): Boolean = getEntityDataValue(DATA_REMAINING_ANGER_TIME)?.let { it > 0 } ?: false

    override fun setVariant(variant: Wolf.Variant) = setEntityDataAccessor(DATA_VARIANT_ID, CraftWolf.CraftVariant.bukkitToMinecraftHolder(variant.toBukkit()))

    override fun getVariant(): Wolf.Variant = getEntityDataValue(DATA_VARIANT_ID)?.let { data ->
        CraftWolf.CraftVariant.minecraftHolderToBukkit(data).key.let { Wolf.Variant.valueOf(it.key.uppercase()) }
    } ?: Wolf.Variant.WOODS

    override fun getEntityData(): JsonObject {
        val tamableAnimalJson = super.getEntityData()
        val wolfJson = JsonObject()
        wolfJson.addProperty("collarColor", getCollarColor().asARGB())
        wolfJson.addProperty("angy", isAngy())
        wolfJson.addProperty("variant", getVariant().name)
        tamableAnimalJson.add("wolf", tamableAnimalJson)
        return wolfJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<TamableAnimal>.setEntityData(jsonObject)
        val wolfJson = JsonObject()
        setCollarColor(Color.fromARGB(wolfJson["collarColor"].asInt))
        setAngy(wolfJson["angy"].asBoolean)
        setVariant(Wolf.Variant.valueOf(wolfJson["variant"].asString))
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setAngy(false)
        setCollarColor(Color.RED)
        setVariant(Wolf.Variant.WOODS)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Wolf(net.minecraft.world.entity.EntityType.WOLF, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                val color = Color.fromRGB(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
                setCollarColor(color)
                getTestMessage(this@Wolf::class, "Set collar color", getCollarColor().red, getCollarColor().green, getCollarColor().blue)
            },
            {
                setAngy(true)
                getTestMessage(this@Wolf::class, "Set angy", isAngy())
            },
            {
                setAngy(false)
                getTestMessage(this@Wolf::class, "Set angy", isAngy())
            }
        ))
        addAll(Wolf.Variant.entries.map {
            {
                setVariant(it)
                getTestMessage(this@Wolf::class, "Set variant", getVariant().name.lowercase())
            }
        })}
}