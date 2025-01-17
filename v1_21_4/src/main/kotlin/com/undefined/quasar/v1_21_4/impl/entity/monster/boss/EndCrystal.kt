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
import org.bukkit.Location
import java.util.Optional

class EndCrystal : Entity(EntityType.END_CRYSTAL), EndCrystal {

    private var showingBottom = true
    private var beamTarget: EndCrystal.BlockPos? = null

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

    override fun setShowingBottom(showing: Boolean) =
        setEntityDataAccessor(DATA_SHOW_BOTTOM, showing) {
            this.showingBottom = showing
        }

    override fun isShowingBottom(): Boolean = showingBottom

    override fun setBeamTarget(target: EndCrystal.BlockPos?) =
        setEntityDataAccessor(DATA_BEAM_TARGET, if (target == null) Optional.empty() else Optional.of(target.let { BlockPos(it.x, it.y, it.z) })) {
            this.beamTarget = target
        }

    override fun getBeamTarget(): EndCrystal.BlockPos? = beamTarget

    override fun getEntityData(): JsonObject {
        val bossJson = super.getEntityData()
        val endCrystalJson = JsonObject()
        endCrystalJson.addProperty("showingBottom", showingBottom)
        val locationJson = beamTarget?.toJson()
        if (locationJson == null) endCrystalJson.addProperty("beamTarget", "EMPTY") else endCrystalJson.add("beamTarget", locationJson)
        bossJson.add("endCrystal", endCrystalJson)
        return bossJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val endCrystalJson = jsonObject["endCrystal"].asJsonObject
        showingBottom = endCrystalJson["showingBottom"].asBoolean
        val locationJson = if (jsonObject["beamTarget"].asString == "EMPTY") null else jsonObject["beamTarget"].asJsonObject
        beamTarget = if (locationJson == null) null else EndCrystal.BlockPos(locationJson)
    }

    override fun updateEntity() {
        super.updateEntity()
        setBeamTarget(beamTarget)
        setShowingBottom(showingBottom)
    }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.boss.enderdragon.EndCrystal(net.minecraft.world.entity.EntityType.END_CRYSTAL, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setShowingBottom(false)
                getTestMessage(this@EndCrystal::class, "Set showingBottom", false)
            },
            {
                setShowingBottom(true)
                getTestMessage(this@EndCrystal::class, "Set showingBottom", true)
            },
            {
                setBeamTarget(getLocation().clone().add(0.0, 10.0, 0.0).toBlockPos())
                getTestMessage(this@EndCrystal::class, "Set beam target", getBeamTarget()?.x!!, getBeamTarget()?.y!!, getBeamTarget()?.z!!)
            },
            {
                setBeamTarget(null)
                getTestMessage(this@EndCrystal::class, "Set beam target", "null")
            }
        )) }
}