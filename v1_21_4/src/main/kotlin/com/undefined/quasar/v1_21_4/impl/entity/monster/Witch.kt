package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Witch
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Raider
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Witch : Raider(EntityType.WITCH), Witch {

    private var DATA_USING_ITEM: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Witch::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Witch.DATA_USING_ITEM
        )

    override fun setUsingItem(using: Boolean) = setEntityDataAccessor(DATA_USING_ITEM, using)

    override fun isUsingItem(): Boolean = getEntityDataValue(DATA_USING_ITEM) ?: false

    override fun getEntityData(): JsonObject {
        val raiderJson = super.getEntityData()
        val witchJson = JsonObject()
        witchJson.addProperty("usingItem", isUsingItem())
        raiderJson.add("witch", witchJson)
        return raiderJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Raider>.setEntityData(jsonObject)
        val witchJson = jsonObject["witch"].asJsonObject
        setUsingItem(witchJson["usingItem"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setUsingItem(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Witch(net.minecraft.world.entity.EntityType.WITCH, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setItem(ItemStack(Material.POTION))
                setUsingItem(true)
                getTestMessage(this@Witch::class, "Set usingItem", isUsingItem())
            }
        )) }
}