package com.undefined.quasar.v1_21_4.impl.entity.vehicle.minecart

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.vehicle.minecart.MinecartFurnace
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

class MinecartFurnace : Minecart(EntityType.FURNACE_MINECART), MinecartFurnace {

    private var DATA_ID_FUEL: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.vehicle.MinecartFurnace::class.java,
            FieldMappings.Entity.Vehicle.Minecart.MinecartFurnace.DATA_ID_FUEL
        )

    override fun setFueled(fueled: Boolean) = setEntityDataAccessor(DATA_ID_FUEL, fueled)

    override fun isFueled(): Boolean = getEntityDataValue(DATA_ID_FUEL) ?: false

    override fun getEntityData(): JsonObject {
        val minecart = super.getEntityData()
        val furnaceMinecart = JsonObject()
        furnaceMinecart.addProperty("fueled", isFueled())
        minecart.add("furnaceMinecart", furnaceMinecart)
        return minecart
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Minecart>.setEntityData(jsonObject)
        val furnaceMinecart = jsonObject["furnaceMinecart"].asJsonObject
        setFueled(furnaceMinecart["fueled"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setFueled(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.vehicle.MinecartFurnace(net.minecraft.world.entity.EntityType.FURNACE_MINECART, level)


    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setFueled(true)
                getTestMessage(this@MinecartFurnace::class, "Set fueled", isFueled())
            },
            {
                setFueled(false)
                getTestMessage(this@MinecartFurnace::class, "Set fueled", isFueled())
            }
        )) }
}