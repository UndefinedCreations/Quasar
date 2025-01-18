package com.undefined.quasar.v1_21_4.impl.entity.monster.boss

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.boss.EndCrystal
import com.undefined.quasar.interfaces.entities.entity.monster.boss.toBlockPos
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.core.BlockPos
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.level.Level
import java.util.*

class EndCrystal : Entity(EntityType.END_CRYSTAL), EndCrystal {

    private var DATA_SHOW_BOTTOM: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.boss.enderdragon.EndCrystal::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Boss.EndCrystal.DATA_SHOW_BOTTOM
        )
    private var DATA_BEAM_TARGET: EntityDataAccessor<Optional<BlockPos>>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.boss.enderdragon.EndCrystal::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Boss.EndCrystal.DATA_BEAM_TARGET
        )

    override fun setShowingBottom(showing: Boolean) = setEntityDataAccessor(DATA_SHOW_BOTTOM, showing)

    override fun isShowingBottom(): Boolean = getEntityDataValue(DATA_SHOW_BOTTOM) ?: false

    override fun setBeamTarget(target: com.undefined.quasar.util.BlockPos?) = setEntityDataAccessor(DATA_BEAM_TARGET, if (target == null) Optional.empty() else Optional.of(target.let { BlockPos(it.x, it.y, it.z) }))

    override fun getBeamTarget(): com.undefined.quasar.util.BlockPos? = getEntityDataValue(DATA_BEAM_TARGET)?.let { data ->
        if (data.isPresent) com.undefined.quasar.util.BlockPos(data.get().x, data.get().y, data.get().z) else null
    }

    override fun getEntityData(): JsonObject {
        val bossJson = super.getEntityData()
        val endCrystalJson = JsonObject()
        endCrystalJson.addProperty("showingBottom", isShowingBottom())
        val locationJson = getBeamTarget()?.toJson()
        if (locationJson == null) endCrystalJson.addProperty("beamTarget", "EMPTY") else endCrystalJson.add("beamTarget", locationJson)
        bossJson.add("endCrystal", endCrystalJson)
        return bossJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val endCrystalJson = jsonObject["endCrystal"].asJsonObject
        setShowingBottom(endCrystalJson["showingBottom"].asBoolean)
        val locationJson = if (jsonObject["beamTarget"].asString == "EMPTY") null else jsonObject["beamTarget"].asJsonObject
        setBeamTarget(if (locationJson == null) null else com.undefined.quasar.util.BlockPos(locationJson))
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setBeamTarget(null)
        setShowingBottom(true)
    }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.boss.enderdragon.EndCrystal(net.minecraft.world.entity.EntityType.END_CRYSTAL, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setShowingBottom(false)
                getTestMessage(this@EndCrystal::class, "Set showingBottom", isShowingBottom())
            },
            {
                setShowingBottom(true)
                getTestMessage(this@EndCrystal::class, "Set showingBottom", isShowingBottom())
            },
            {
                setBeamTarget(getLocation().clone().add(0.0, 10.0, 0.0).toBlockPos())
                getTestMessage(this@EndCrystal::class, "Set beam target", getBeamTarget()?.x!!, getBeamTarget()?.y!!, getBeamTarget()?.z!!)
            },
            {
                setBeamTarget(null)
                getTestMessage(this@EndCrystal::class, "Set beam target", getBeamTarget())
            }
        )) }
}