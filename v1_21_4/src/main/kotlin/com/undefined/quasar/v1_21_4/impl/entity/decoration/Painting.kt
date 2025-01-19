package com.undefined.quasar.v1_21_4.impl.entity.decoration

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.decoration.Painting
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.core.Holder
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.decoration.PaintingVariant
import net.minecraft.world.level.Level
import org.bukkit.Art
import org.bukkit.NamespacedKey
import org.bukkit.craftbukkit.v1_21_R3.CraftArt

class Painting : HangingEntity(EntityType.PAINTING), Painting {

    private var DATA_PAINTING_VARIANT_ID: EntityDataAccessor<Holder<PaintingVariant>>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.decoration.Painting::class.java,
            FieldMappings.Entity.Decoration.Painting.DATA_PAINTING_VARIANT_ID
        )

    override fun setVariant(variant: Painting.Variant) {
        setEntityDataAccessor(DATA_PAINTING_VARIANT_ID, CraftArt.bukkitToMinecraftHolder(getNMSArt(variant.key)))
    }

    override fun getVariant(): Painting.Variant = getEntityDataValue(DATA_PAINTING_VARIANT_ID)?.let { data -> getArt(CraftArt.minecraftHolderToBukkit(data)) } ?: Painting.Variant.VOID

    private fun getNMSArt(key: String): Art? {
        return try {
            org.bukkit.Registry.ART.getOrThrow(NamespacedKey.minecraft(key))
        } catch (e: Exception) {
            null
        }
    }

    private fun getArt(craftArt: Art): Painting.Variant {
        return Painting.Variant.entries.first { it.key == craftArt.name().lowercase() }
    }

    override fun getEntityData(): JsonObject {
        val entityJson = super.getEntityData()
        val paintingJson = JsonObject()
        paintingJson.addProperty("art", getVariant().key)
        entityJson.add("painting", paintingJson)
        return entityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<HangingEntity>.setEntityData(jsonObject)
        val paintingJson = jsonObject["painting"].asJsonObject
        setVariant(Painting.Variant.entries.first { it.key == paintingJson["art"].asString })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setVariant(Painting.Variant.VOID)
    }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.decoration.Painting(net.minecraft.world.entity.EntityType.PAINTING, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(Painting.Variant.entries.map {
            {
                setVariant(it)
                getTestMessage(this@Painting::class, "Set variant", getVariant().name.lowercase())
            }
        }) }
}