package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Allay
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Allay : Allay, Animal(EntityType.ALLAY) {

    private var DATA_DANCING: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.allay.Allay::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Allay.DATA_DANCING
        )

    override fun isDancing(): Boolean = getEntityDataValue(DATA_DANCING) ?: false

    override fun setDancing(dancing: Boolean) =
        setEntityDataAccessor(DATA_DANCING, dancing)
    override fun setItem(slot: Int, itemStack: ItemStack?) =
        super<Animal>.setItem(0, itemStack)

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val allayJson = JsonObject()
        allayJson.addProperty("dancing", isDancing())
        animalJson.add("allay", allayJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val allayJson = jsonObject["allay"].asJsonObject
        setDancing(allayJson["dancing"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setDancing(false)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                val item = ItemStack(Material.entries.random())
                setHoldingItem(item)
                getTestMessage(this@Allay::class, "Set holding", getHoldingItem()?.type?.name?.lowercase())
            },
            {
                setHoldingItem(null)
                getTestMessage(this@Allay::class, "Set holding", getHoldingItem()?.type?.name?.lowercase())
            },
            {
                setDancing(true)
                getTestMessage(this@Allay::class, "Set dancing", isDancing())
            },
            {
                setDancing(false)
                getTestMessage(this@Allay::class, "Set dancing", isDancing())
            }
        )) }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.allay.Allay(net.minecraft.world.entity.EntityType.ALLAY, level)

}