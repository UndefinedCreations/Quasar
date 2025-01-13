package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Armadillo
import com.undefined.quasar.interfaces.entities.entity.animal.Axolotl
import com.undefined.quasar.util.getPrivateField
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class Axolotl : LivingEntity(EntityType.AXOLOTL), Axolotl {

    private var variant = Axolotl.Variant.LUCY
    private var DATA_VARIANT: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.axolotl.Axolotl::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Axolotl.DATA_VARIANT
        )

    override fun setVariant(variant: Axolotl.Variant) {
        val entity = entity ?: return
        this.variant = variant
        entity.entityData.set(DATA_VARIANT, variant.id)
        sendEntityMetaData()
    }

    override fun getVariant(): Axolotl.Variant = variant

    override fun getEntityData(): JsonObject {
        val livingEntityJson = super.getEntityData()
        val axolotlJson = JsonObject()
        axolotlJson.addProperty("variant", variant.id)
        livingEntityJson.add("axolotl", axolotlJson)
        return livingEntityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val axolotlJson = jsonObject["axolotl"].asJsonObject
        variant = Axolotl.Variant.entries.first { it.id == axolotlJson["variant"].asInt }
    }

    override fun updateEntity() {
        super.updateEntity()
        setVariant(variant)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.axolotl.Axolotl(net.minecraft.world.entity.EntityType.AXOLOTL, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(Axolotl.Variant.entries.map {
            {
                setVariant(it)
                getTestMessage(this@Axolotl::class, "Set variant", it)
            }
        }) }
}