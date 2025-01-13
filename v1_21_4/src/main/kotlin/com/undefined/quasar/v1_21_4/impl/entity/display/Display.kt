package com.undefined.quasar.v1_21_4.impl.entity.display

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.display.Display
import com.undefined.quasar.util.Vector
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import org.bukkit.Color
import org.joml.Quaternionf

abstract class Display(entityType: EntityType) : Entity(entityType), Display {

    override fun setInterpolationDurtation(duration: Int) {
        TODO("Not yet implemented")
    }

    override fun getInterpolationDuration(): Int {
        TODO("Not yet implemented")
    }

    override fun setRotationInterpolationDurtation(duration: Int) {
        TODO("Not yet implemented")
    }

    override fun getRotationInterpolationDuration(): Int {
        TODO("Not yet implemented")
    }

    override fun setTranslation(vector: Vector) {
        TODO("Not yet implemented")
    }

    override fun getTranslation(): Vector {
        TODO("Not yet implemented")
    }

    override fun setScale(vector: Vector) {
        TODO("Not yet implemented")
    }

    override fun getScale(): Vector {
        TODO("Not yet implemented")
    }

    override fun setLeftRotation(rotation: Quaternionf) {
        TODO("Not yet implemented")
    }

    override fun getLeftRatation(): Quaternionf {
        TODO("Not yet implemented")
    }

    override fun setRightRotation(rotation: Quaternionf) {
        TODO("Not yet implemented")
    }

    override fun getRightRatation(): Quaternionf {
        TODO("Not yet implemented")
    }

    override fun setBrightness(brightness: Display.Brightness) {
        TODO("Not yet implemented")
    }

    override fun getBrightness(): Display.Brightness {
        TODO("Not yet implemented")
    }

    override fun setViewRange(range: Float) {
        TODO("Not yet implemented")
    }

    override fun getViewRange(): Double {
        TODO("Not yet implemented")
    }

    override fun setShadowRadius(radius: Float) {
        TODO("Not yet implemented")
    }

    override fun getShadowRadius(): Double {
        TODO("Not yet implemented")
    }

    override fun setShadowStrength(strength: Float) {
        TODO("Not yet implemented")
    }

    override fun getShadowStrength(): Double {
        TODO("Not yet implemented")
    }

    override fun setWidth(width: Float) {
        TODO("Not yet implemented")
    }

    override fun getWidth(): Double {
        TODO("Not yet implemented")
    }

    override fun setHeight(height: Float) {
        TODO("Not yet implemented")
    }

    override fun getHeight(): Double {
        TODO("Not yet implemented")
    }

    override fun setGlowColorOverride(color: Color) {
        TODO("Not yet implemented")
    }

    override fun getGlowColorOverride(): Color {
        TODO("Not yet implemented")
    }
}