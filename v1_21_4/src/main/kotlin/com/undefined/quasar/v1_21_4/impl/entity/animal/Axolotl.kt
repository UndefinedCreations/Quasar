package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Axolotl
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Axolotl : Animal(EntityType.AXOLOTL), Axolotl {

    private var DATA_VARIANT: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.axolotl.Axolotl::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Axolotl.DATA_VARIANT
        )

    override fun setVariant(variant: Axolotl.Variant) =
         setEntityDataAccessor(DATA_VARIANT, variant.id)

    override fun getVariant(): Axolotl.Variant = getEntityDataValue(DATA_VARIANT)?.let {
        data -> Axolotl.Variant.entries.first { it.id == data }
    } ?: Axolotl.Variant.LUCY

    override fun getEntityData(): JsonObject {
        val livingEntityJson = super.getEntityData()
        val axolotlJson = JsonObject()
        axolotlJson.addProperty("variant", getVariant().id)
        livingEntityJson.add("axolotl", axolotlJson)
        return livingEntityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val axolotlJson = jsonObject["axolotl"].asJsonObject
        setVariant(Axolotl.Variant.entries.first { it.id == axolotlJson["variant"].asInt })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setVariant(Axolotl.Variant.LUCY)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.axolotl.Axolotl(net.minecraft.world.entity.EntityType.AXOLOTL, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(Axolotl.Variant.entries.map {
            {
                setVariant(it)
                getTestMessage(this@Axolotl::class, "Set variant", getVariant().name.lowercase())
            }
        }) }
}