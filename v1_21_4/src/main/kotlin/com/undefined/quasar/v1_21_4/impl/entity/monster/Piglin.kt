package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.Piglin
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.AbstractPiglin
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Piglin : AbstractPiglin(EntityType.PIGLIN), Piglin {

    private var DATA_BABY_ID: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.piglin.Piglin::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.AbstractPiglin.Piglin.DATA_BABY_ID
        )

    private var DATA_IS_CHARGING_CROSSBOW: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.piglin.Piglin::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.AbstractPiglin.Piglin.DATA_IS_CHARGING_CROSSBOW
        )

    private var DATA_IS_DANCING: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.piglin.Piglin::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.AbstractPiglin.Piglin.DATA_IS_DANCING
        )

    override fun setBaby(baby: Boolean) = setEntityDataAccessor(DATA_BABY_ID, baby)

    override fun isBaby(): Boolean = getEntityDataValue(DATA_BABY_ID) ?: false

    override fun setChargingCrossBow(charging: Boolean) = setEntityDataAccessor(DATA_IS_CHARGING_CROSSBOW, charging)

    override fun isChargingCrossBow(): Boolean = getEntityDataValue(DATA_IS_CHARGING_CROSSBOW) ?: false

    override fun setDancing(dancing: Boolean) = setEntityDataAccessor(DATA_IS_DANCING, dancing)

    override fun isDancing(): Boolean = getEntityDataValue(DATA_IS_DANCING) ?: false

    override fun getEntityData(): JsonObject {
        val abstractPiglinJson = super.getEntityData()
        val piglinJson = JsonObject()
        piglinJson.addProperty("baby", isBaby())
        piglinJson.addProperty("charging", isChargingCrossBow())
        piglinJson.addProperty("dancing", isDancing())
        abstractPiglinJson.add("piglin", piglinJson)
        return abstractPiglinJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<AbstractPiglin>.setEntityData(jsonObject)
        val piglinJson = jsonObject["piglin"].asJsonObject
        setBaby(piglinJson["baby"].asBoolean)
        setChargingCrossBow(piglinJson["charging"].asBoolean)
        setDancing(piglinJson["dancing"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setBaby(false)
        setChargingCrossBow(false)
        setDancing(false)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.piglin.Piglin(net.minecraft.world.entity.EntityType.PIGLIN, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setBaby(true)
                getTestMessage(this@Piglin::class, "Set baby", isBaby())
            },
            {
                setBaby(false)
                getTestMessage(this@Piglin::class, "Set baby", isBaby())
            },
            {
                setItem(0, ItemStack(Material.CROSSBOW))
                setChargingCrossBow(true)
                getTestMessage(this@Piglin::class, "Set charging cross bow", isChargingCrossBow())
            },
            {
                setChargingCrossBow(false)
                getTestMessage(this@Piglin::class, "Set charging cross bow", isChargingCrossBow())
            },
            {
                setDancing(true)
                getTestMessage(this@Piglin::class, "Set dancing", isDancing())
            },
            {
                setDancing(false)
                getTestMessage(this@Piglin::class, "Set dancing", isDancing())
            }
        )) }
}