package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Bogged
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Bogged : LivingEntity(EntityType.BOGGED), Bogged {

    private var DATA_SHEARED: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.Bogged::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Bogged.DATA_SHEARED
        )

    override fun isSheared(): Boolean = getEntityDataValue(DATA_SHEARED) ?: false

    override fun setSheared(sheared: Boolean) = setEntityDataAccessor(DATA_SHEARED, sheared)

    override fun getEntityData(): JsonObject {
        val monsterJson = super.getEntityData()
        val boggedJson = JsonObject()
        boggedJson.addProperty("sheared", isSheared())
        monsterJson.add("bogged", boggedJson)
        return monsterJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val boggedJson = jsonObject["bogged"].asJsonObject
        setSheared(boggedJson["sheared"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setSheared(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.Bogged(net.minecraft.world.entity.EntityType.BOGGED, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setSheared(true)
                getTestMessage(this@Bogged::class, "Set sheared", isSheared())
            },
            {
                setSheared(false)
                getTestMessage(this@Bogged::class, "Set sheared", isSheared())
            },
            {
                setItem(com.undefined.quasar.interfaces.LivingEntity.EquipmentSlot.MAINHAND, ItemStack(Material.BOW))
                useItem(false)
                getTestMessage(this@Bogged::class, "Use bow")
            },
            {
                stopUsingItem()
                getTestMessage(this@Bogged::class, "Stop using bow")
            }
        )) }
}