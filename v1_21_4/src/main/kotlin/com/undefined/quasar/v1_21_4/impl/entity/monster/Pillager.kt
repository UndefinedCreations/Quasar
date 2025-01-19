package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Pillager
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Raider
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Pillager : Raider(EntityType.PILLAGER), Pillager {

    private var IS_CHARGING_CROSSBOW: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Pillager::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Raider.Pillager.IS_CHARGING_CROSSBOW
        )

    override fun setChargingCrossbow(charging: Boolean) = setEntityDataAccessor(IS_CHARGING_CROSSBOW, charging)

    override fun isChargingCrossbow(): Boolean = getEntityDataValue(IS_CHARGING_CROSSBOW) ?: false

    override fun getEntityData(): JsonObject {
        val raiderJson = super.getEntityData()
        val pillagerJson = JsonObject()
        pillagerJson.addProperty("charging", isChargingCrossbow())
        raiderJson.add("pillager", pillagerJson)
        return raiderJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Raider>.setEntityData(jsonObject)
        val pillagerJson = jsonObject["pillager"].asJsonObject
        setChargingCrossbow(pillagerJson["charging"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setChargingCrossbow(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Pillager(net.minecraft.world.entity.EntityType.PILLAGER, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setItem(0, ItemStack(Material.CROSSBOW))
                setChargingCrossbow(true)
                getTestMessage(this@Pillager::class, "Set charging crossbow", isChargingCrossbow())
            },
            {
                setChargingCrossbow(false)
                getTestMessage(this@Pillager::class, "Set charging crossbow", isChargingCrossbow())
            }
        )) }
}