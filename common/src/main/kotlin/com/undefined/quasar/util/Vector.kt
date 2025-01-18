package com.undefined.quasar.util

import com.google.gson.JsonObject

class Vector(
    var x: Double,
    var y: Double,
    var z: Double
) {

    fun add(x: Double, y: Double, z: Double) {
        this.x += x
        this.y += y
        this.z += z
    }

    fun subtract(x: Double, y: Double, z: Double) {
        this.x -= x
        this.y -= y
        this.z -= z
    }

    fun add(vector: Vector) {
        this.x += vector.x
        this.y += vector.y
        this.z += vector.z
    }

    fun subtract(vector: Vector) {
        this.x -= vector.x
        this.y -= vector.y
        this.z -= vector.z
    }

    fun multeply(x: Double, y: Double, z: Double) {
        this.x *= x
        this.y *= y
        this.z *= z
    }

    fun multeply(vector: Vector) {
        this.x *= vector.x
        this.y *= vector.y
        this.z *= vector.z
    }

    fun clone(): Vector = Vector(x, y, z)

    fun json(): JsonObject = JsonObject().apply {
        addProperty("x", x)
        addProperty("y", y)
        addProperty("z", z)
    }

    companion object {
        fun deserialize(jsonObject: JsonObject): Vector = jsonObject.run { Vector(
            jsonObject["x"].asDouble,
            jsonObject["y"].asDouble,
            jsonObject["z"].asDouble
        ) }
    }

}