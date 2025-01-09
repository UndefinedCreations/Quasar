package com.undefined.quasar.interfaces.entities.entity

import com.google.gson.JsonArray
import com.undefined.quasar.interfaces.LivingEntity

interface ArmorStand: LivingEntity {

    fun isSmall(): Boolean
    fun setSmall(small: Boolean)

    fun isShowingBasePlate(): Boolean
    fun setShowingBasePlate(showing: Boolean)

    fun isShowingArms(): Boolean
    fun setShowingArms(showing: Boolean)

    fun getHeadRotation(): Rotations
    fun setHeadRotation(rotations: Rotations)
    fun setHeadRotation(firstRot: Float, secondRot: Float, thridRot: Float) =
        setHeadRotation(Rotations(firstRot, secondRot, thridRot))

    fun getBodyRotation(): Rotations
    fun setBodyRotation(rotations: Rotations)
    fun setBodyRotation(firstRot: Float, secondRot: Float, thridRot: Float) =
        setBodyRotation(Rotations(firstRot, secondRot, thridRot))

    fun getLeftArmRotation(): Rotations
    fun setLeftArmRotation(rotations: Rotations)
    fun setLeftArmRotation(firstRot: Float, secondRot: Float, thridRot: Float) =
        setLeftArmRotation(Rotations(firstRot, secondRot, thridRot))

    fun getRightArmRotation(): Rotations
    fun setRightArmRotation(rotations: Rotations)
    fun setRightArmRotation(firstRot: Float, secondRot: Float, thridRot: Float) =
        setRightArmRotation(Rotations(firstRot, secondRot, thridRot))

    fun getLeftLegRotation(): Rotations
    fun setLeftLegRotation(rotations: Rotations)
    fun setLeftLegRotation(firstRot: Float, secondRot: Float, thridRot: Float) =
        setLeftLegRotation(Rotations(firstRot, secondRot, thridRot))

    fun getRightLegRotation(): Rotations
    fun setRightLegRotation(rotations: Rotations)
    fun setRightLegRotation(firstRot: Float, secondRot: Float, thridRot: Float) =
        setRightLegRotation(Rotations(firstRot, secondRot, thridRot))


    class Rotations(var firstRot: Float, var secondRot: Float, var thridRot: Float) {

        constructor(jsonArray: JsonArray): this(jsonArray[0].asFloat, jsonArray[1].asFloat, jsonArray[2].asFloat)

        fun add(num: Float): Rotations {
            firstRot += num
            secondRot += num
            thridRot += num
            return this
        }

        fun subtract(num: Float): Rotations {
            firstRot -= num
            secondRot -= num
            thridRot -= num
            return this
        }

        fun multiple(num: Float): Rotations {
            firstRot *= num
            secondRot *= num
            thridRot *= num
            return this
        }

        fun set(rotations: Rotations): Rotations {
            firstRot = rotations.firstRot
            secondRot = rotations.secondRot
            thridRot = rotations.thridRot
            return this
        }

        fun clone(): Rotations = Rotations(firstRot, secondRot, thridRot)

        fun json(): JsonArray = JsonArray().apply {
            add(firstRot)
            add(secondRot)
            add(thridRot)
        }

        override fun toString(): String = "$firstRot,$secondRot,$thridRot"
    }
}