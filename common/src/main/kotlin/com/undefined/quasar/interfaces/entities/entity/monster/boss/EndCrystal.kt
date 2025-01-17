package com.undefined.quasar.interfaces.entities.entity.monster.boss

import com.google.gson.JsonObject
import com.undefined.quasar.interfaces.Entity
import org.bukkit.Location

interface EndCrystal : Entity {

    fun setShowingBottom(showing: Boolean)
    fun isShowingBottom(): Boolean

    fun setBeamTarget(target: BlockPos?)
    fun getBeamTarget(): BlockPos?
    fun removeBeam() = setBeamTarget(null)
    fun hasBeam(): Boolean { return getBeamTarget() != null }

    class BlockPos(val x: Int, val y: Int, val z: Int) {

        constructor(jsonObject: JsonObject) : this(jsonObject["x"].asInt, jsonObject["y"].asInt, jsonObject["z"].asInt)

        fun toJson(): JsonObject {
            val locationJson = JsonObject()
            locationJson.addProperty("x", x)
            locationJson.addProperty("y", y)
            locationJson.addProperty("z", z)
            return locationJson
        }

    }

}

fun Location.toBlockPos(): EndCrystal.BlockPos = EndCrystal.BlockPos(blockX, blockY, blockZ)