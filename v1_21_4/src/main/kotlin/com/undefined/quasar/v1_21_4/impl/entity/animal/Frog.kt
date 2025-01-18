package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Frog
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.core.Holder
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.animal.FrogVariant
import net.minecraft.world.level.Level
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftFrog
import java.util.*

class Frog : Animal(EntityType.FROG), Frog {

    private var tongueTarget: com.undefined.quasar.interfaces.Entity? = null

    private var DATA_VARIANT_ID: EntityDataAccessor<Holder<FrogVariant>>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.frog.Frog::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Frog.DATA_VARIANT_ID
        )

    private var DATA_TONGUE_TARGET_ID: EntityDataAccessor<OptionalInt>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.frog.Frog::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Frog.DATA_TONGUE_TARGET_ID
        )

    override fun setVariant(variant: Frog.Variant) = setEntityDataAccessor(DATA_VARIANT_ID, CraftFrog.CraftVariant.bukkitToMinecraftHolder(org.bukkit.entity.Frog.Variant.valueOf(variant.name)))

    override fun getVariant(): Frog.Variant = getEntityDataValue(DATA_VARIANT_ID)?.let { data ->
        Frog.Variant.valueOf(CraftFrog.CraftVariant.minecraftHolderToBukkit(data).name())
    } ?: Frog.Variant.TEMPERATE

    override fun setTongueTarget(entity: com.undefined.quasar.interfaces.Entity?) =
        setEntityDataAccessor(DATA_TONGUE_TARGET_ID,
            (entity as? com.undefined.quasar.v1_21_4.impl.entity.Entity)?.entity?.id?.let { OptionalInt.of(it) })

    override fun getTongueTarget(): com.undefined.quasar.interfaces.Entity? = tongueTarget

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val frogJson = JsonObject()
        frogJson.addProperty("variant", getVariant().name)
        animalJson.add("frog", frogJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val frogJson = jsonObject["frog"].asJsonObject
        setVariant(Frog.Variant.valueOf(frogJson["variant"].asString))
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setVariant(Frog.Variant.TEMPERATE)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.frog.Frog(net.minecraft.world.entity.EntityType.FROG, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(
            Frog.Variant.entries.map {
                {
                    setVariant(it)
                    getTestMessage(this@Frog::class, "Set frog variant", getVariant().name.lowercase())
                }
            }
        ) }
}