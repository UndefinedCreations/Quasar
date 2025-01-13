package com.undefined.quasar.interfaces.entities.entity.display

import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.util.Vector
import org.bukkit.Color
import org.joml.Quaternionf

interface Display : Entity {

    fun setInterpolationDurtation(duration: Int)
    fun getInterpolationDuration(): Int

    fun setRotationInterpolationDurtation(duration: Int)
    fun getRotationInterpolationDuration(): Int

    fun setTranslation(vector: Vector)
    fun setTranslation(x: Double = 1.0, y: Double = 1.0, z: Double = 1.0) = setTranslation(Vector(x, y, z))
    fun getTranslation(): Vector

    fun setScale(vector: Vector)
    fun setScale(x: Double = 1.0, y: Double = 1.0, z: Double = 1.0) = setScale(Vector(x, y, z))
    fun getScale(): Vector

    fun setLeftRotation(rotation: Quaternionf)
    fun setLeftRotation(w: Float, x: Float, y: Float, z: Float) = setLeftRotation(Quaternionf(w,x,y,z))
    fun getLeftRatation(): Quaternionf

    fun setRightRotation(rotation: Quaternionf)
    fun setRightRotation(w: Float, x: Float, y: Float, z: Float) = setRightRotation(Quaternionf(w,x,y,z))
    fun getRightRatation(): Quaternionf

    fun setBrightness(brightness: Brightness)
    fun getBrightness(): Brightness

    fun setViewRange(range: Float)
    fun setViewRange(range: Double) = setViewRange(range.toFloat())
    fun getViewRange(): Double

    fun setShadowRadius(radius: Float)
    fun setShadowRadius(radius: Double) = setShadowRadius(radius.toFloat())
    fun getShadowRadius(): Double

    fun setShadowStrength(strength: Float)
    fun setShadowStrength(strength: Double) = setShadowStrength(strength.toFloat())
    fun getShadowStrength(): Double

    fun setWidth(width: Float)
    fun setWidth(width: Double) = setWidth(width.toFloat())
    fun getWidth(): Double

    fun setHeight(height: Float)
    fun setHeight(height: Double) = setHeight(height.toFloat())
    fun getHeight(): Double

    fun setGlowColorOverride(color: Color)
    fun getGlowColorOverride(): Color

    enum class BillboardConstraints(val id: Byte) {
        FIXED(0),
        VERTICAL(1),
        HORIZONTAL(2),
        CENTER(3)
    }

    class Brightness(var block: Int, var sky: Int) {
        fun pack(): Int {
            return this.block shl 4 or (this.sky shl 20)
        }
        fun unpack(var0: Int): Brightness {
            val var1 = var0 shr 4 and '\uffff'.code
            val var2 = var0 shr 20 and '\uffff'.code
            return Brightness(var1, var2)
        }
    }

}