package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Allay
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.*

class Allay : Allay, Animal(EntityType.ALLAY) {

    private var dancing = false

    private var DATA_DANCING: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.animal.allay.Allay::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Animal.Allay.DATA_DANCING
        )

    override fun isDancing(): Boolean = dancing

    override fun setDancing(dancing: Boolean) =
        setEntityDataAccessor(DATA_DANCING, dancing) {
            this.dancing = dancing
        }

    override fun setItem(slot: Int, itemStack: ItemStack?) =
        super<Animal>.setItem(0, itemStack)

    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val allayJson = JsonObject()
        allayJson.addProperty("dancing", dancing)
        animalJson.add("allay", allayJson)
        return animalJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val allayJson = jsonObject["allay"].asJsonObject
        dancing = jsonObject["dancing"].asBoolean
    }

    override fun updateEntity() {
        super.updateEntity()
        setDancing(dancing)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                val item = ItemStack(Material.entries.random())
                setHoldingItem(item)
                getTestMessage(this@Allay::class, "Set holding", item.type.name.lowercase())
            },
            {
                setHoldingItem(null)
                getTestMessage(this@Allay::class, "Set holding", "EMPTY")
            },
            {
                setDancing(true)
                getTestMessage(this@Allay::class, "Set dancing", true)
            },
            {
                setDancing(false)
                getTestMessage(this@Allay::class, "Set dancing", false)
            }
        )) }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.allay.Allay(net.minecraft.world.entity.EntityType.ALLAY, level)

}