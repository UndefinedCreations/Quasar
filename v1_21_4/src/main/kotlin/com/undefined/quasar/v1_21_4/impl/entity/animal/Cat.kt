package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Cat
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.TamableAnimal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.core.Holder
import net.minecraft.network.chat.Component
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.animal.CatVariant
import net.minecraft.world.level.Level
import org.bukkit.Color
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftCat
import java.util.*
import kotlin.random.Random

class Cat : TamableAnimal(EntityType.CAT), Cat {

    private var varinat = Cat.Varinat.WHITE
    private var lying = false
    private var relaxingState = false
    private var collarColor = Color.RED

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

    override fun setVarinat(varinat: Cat.Varinat) =
        setEntityDataAccessor(DATA_VARIANT_ID, CraftCat.CraftType.bukkitToMinecraftHolder(org.bukkit.entity.Cat.Type.valueOf(varinat.name))) {
            this.varinat = varinat
        }

    override fun getVariant(): Cat.Varinat = varinat

    override fun setLying(lying: Boolean) =
        setEntityDataAccessor(IS_LYING, lying) {
            this.lying = lying
        }

    override fun isLying(): Boolean = lying

    override fun setRelaxingState(relaxing: Boolean) =
        setEntityDataAccessor(RELAX_STATE_ONE, relaxing) {
            this.relaxingState = relaxing
        }

    override fun getRelaxingState(): Boolean = relaxingState

    override fun setCollarColor(color: Color) =
        setEntityDataAccessor(DATA_COLLAR_COLOR, color.asARGB()) {
            this.collarColor = color
        }

    override fun getCollarColor(): Color = collarColor

    override fun getEntityData(): JsonObject {
        val tamableAnimalJson = super.getEntityData()
        val catJson = JsonObject()
        catJson.addProperty("varinat", varinat.name)
        catJson.addProperty("lying", lying)
        catJson.addProperty("relaxing", relaxingState)
        catJson.addProperty("collarColor", collarColor.asARGB())
        tamableAnimalJson.add("cat", catJson)
        return tamableAnimalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<TamableAnimal>.setEntityData(jsonObject)
        val catJson = jsonObject["cat"].asJsonObject
        varinat = Cat.Varinat.valueOf(catJson["varinat"].asString)
        lying = catJson["lying"].asBoolean
        relaxingState = catJson["relaxing"].asBoolean
        collarColor = Color.fromARGB(catJson["collarColor"].asInt)
    }

    override fun updateEntity() {
        super.updateEntity()
        setVarinat(varinat)
        setLying(lying)
        setRelaxingState(relaxingState)
        setCollarColor(collarColor)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.Cat(net.minecraft.world.entity.EntityType.CAT, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply {
            addAll(
                Cat.Varinat.entries.map {
                    {
                        setVarinat(it)
                        getTestMessage(this@Cat::class, "Set varinat", it.name.lowercase())
                    }
                }
            )

            addAll(mutableListOf(
                {
                    setLying(true)
                    getTestMessage(this@Cat::class, "Set lying", true)
                },
                {
                    setLying(false)
                    getTestMessage(this@Cat::class, "Set lying", false)
                },
                {
                    setRelaxingState(true)
                    getTestMessage(this@Cat::class, "Set ralaxing state", true)
                },
                {
                    setRelaxingState(false)
                    getTestMessage(this@Cat::class, "Set ralaxing state", false)
                },
                {
                    val color = Color.fromRGB(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
                    setCollarColor(color)
                    getTestMessage(this@Cat::class, "Set collar color", color.red, color.green, color.blue)
                }
            ))
        }
}