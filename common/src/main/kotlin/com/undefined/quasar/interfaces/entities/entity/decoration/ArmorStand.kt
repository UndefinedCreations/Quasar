package com.undefined.quasar.interfaces.entities.entity.decoration

import com.google.gson.JsonArray
import com.undefined.quasar.interfaces.LivingEntity

interface ArmorStand : LivingEntity {

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


    class Rotations(var firstRotation: Float, var secondRotation: Float, var thirdRotation: Float) {

        constructor(jsonArray: JsonArray): this(jsonArray[0].asFloat, jsonArray[1].asFloat, jsonArray[2].asFloat)

        fun add(num: Float): Rotations {
            firstRotation += num
            secondRotation += num
            thirdRotation += num
            return this
        }

        fun subtract(num: Float): Rotations {
            firstRotation -= num
            secondRotation -= num
            thirdRotation -= num
            return this
        }

        fun multiple(num: Float): Rotations {
            firstRotation *= num
            secondRotation *= num
            thirdRotation *= num
            return this
        }

        fun set(rotations: Rotations): Rotations {
            firstRotation = rotations.firstRotation
            secondRotation = rotations.secondRotation
            thirdRotation = rotations.thirdRotation
            return this
        }

        fun clone(): Rotations = Rotations(firstRotation, secondRotation, thirdRotation)

        fun json(): JsonArray = JsonArray().apply {
            add(firstRotation)
            add(secondRotation)
            add(thirdRotation)
        }

        override fun toString(): String = "$firstRotation,$secondRotation,$thirdRotation"
    }
}