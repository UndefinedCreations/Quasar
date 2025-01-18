package com.undefined.quasar.util

import com.google.gson.JsonObject

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