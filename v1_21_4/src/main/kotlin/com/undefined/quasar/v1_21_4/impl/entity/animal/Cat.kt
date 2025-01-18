package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Cat
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.TamableAnimal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.core.Holder
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.animal.CatVariant
import net.minecraft.world.level.Level
import org.bukkit.Color
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftCat
import kotlin.random.Random

class Cat : TamableAnimal(EntityType.CAT), Cat {

    private var DATA_VARIANT_ID: EntityDataAccessor<Holder<CatVariant>>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Cat::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.TamableAnimal.Cat.DATA_VARIANT_ID
        )
    private var IS_LYING: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Cat::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.TamableAnimal.Cat.IS_LYING
        )
    private var RELAX_STATE_ONE: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Cat::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.TamableAnimal.Cat.RELAX_STATE_ONE
        )
    private var DATA_COLLAR_COLOR: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.Cat::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.TamableAnimal.Cat.DATA_COLLAR_COLOR
        )

    override fun setVarinat(varinat: Cat.Varinat) = setEntityDataAccessor(DATA_VARIANT_ID, CraftCat.CraftType.bukkitToMinecraftHolder(org.bukkit.entity.Cat.Type.valueOf(varinat.name)))

    override fun getVariant(): Cat.Varinat = getEntityDataValue(DATA_VARIANT_ID)?.let { data ->
        Cat.Varinat.valueOf(CraftCat.CraftType.minecraftHolderToBukkit(data).name())
    } ?: Cat.Varinat.WHITE

    override fun setLying(lying: Boolean) = setEntityDataAccessor(IS_LYING, lying)

    override fun isLying(): Boolean = getEntityDataValue(IS_LYING) ?: false

    override fun setRelaxingState(relaxing: Boolean) = setEntityDataAccessor(RELAX_STATE_ONE, relaxing)

    override fun getRelaxingState(): Boolean = getEntityDataValue(RELAX_STATE_ONE) ?: false

    override fun setCollarColor(color: Color) = setEntityDataAccessor(DATA_COLLAR_COLOR, color.asARGB())

    override fun getCollarColor(): Color = Color.fromARGB(getEntityDataValue(DATA_COLLAR_COLOR) ?: Color.RED.asARGB())

    override fun getEntityData(): JsonObject {
        val tamableAnimalJson = super.getEntityData()
        val catJson = JsonObject()
        catJson.addProperty("varinat", getVariant().name)
        catJson.addProperty("lying", isLying())
        catJson.addProperty("relaxing", getRelaxingState())
        catJson.addProperty("collarColor", getCollarColor().asARGB())
        tamableAnimalJson.add("cat", catJson)
        return tamableAnimalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<TamableAnimal>.setEntityData(jsonObject)
        val catJson = jsonObject["cat"].asJsonObject
        setVarinat(Cat.Varinat.valueOf(catJson["varinat"].asString))
        setLying(catJson["lying"].asBoolean)
        setRelaxingState(catJson["relaxing"].asBoolean)
        setCollarColor(Color.fromARGB(catJson["collarColor"].asInt))
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setVarinat(Cat.Varinat.WHITE)
        setLying(false)
        setRelaxingState(false)
        setCollarColor(Color.RED)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Cat(net.minecraft.world.entity.EntityType.CAT, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply {
            addAll(
                Cat.Varinat.entries.map {
                    {
                        setVarinat(it)
                        getTestMessage(this@Cat::class, "Set varinat", getVariant().name.lowercase())
                    }
                }
            )

            addAll(mutableListOf(
                {
                    setLying(true)
                    getTestMessage(this@Cat::class, "Set lying", isLying())
                },
                {
                    setLying(false)
                    getTestMessage(this@Cat::class, "Set lying", isLying())
                },
                {
                    setRelaxingState(true)
                    getTestMessage(this@Cat::class, "Set ralaxing state", getRelaxingState())
                },
                {
                    setRelaxingState(false)
                    getTestMessage(this@Cat::class, "Set ralaxing state", getRelaxingState())
                },
                {
                    val color = Color.fromRGB(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
                    setCollarColor(color)
                    getTestMessage(this@Cat::class, "Set collar color", getCollarColor().red, getCollarColor().green, getCollarColor().blue)
                }
            ))
        }
}