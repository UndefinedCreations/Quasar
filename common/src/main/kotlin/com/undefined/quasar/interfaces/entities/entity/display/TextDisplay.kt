package com.undefined.quasar.interfaces.entities.entity.display

import org.bukkit.Color

interface TextDisplay : Display {

    fun setText(string: String)
    fun getText(): String

    fun setLineWidth(width: Int)
    fun getLineWidth(): Int

    fun setBackgroundColor(color: Color)
    fun getBackgroundColor(): Color

    fun setTextOpacity(opacity: Byte)
    fun getTextOpacity(): Byte

    fun setShadowed(shadow: Boolean)
    fun isShadowed(): Boolean

    fun setSeeThrough(seeThrough: Boolean)
    fun getSeeThrough(): Boolean

    fun setDefaultBackground(defaultBackground: Boolean)
    fun isDefaultBackground(): Boolean

    fun setAlignment(alignment: Alignment)
    fun getAlignment(): Alignment


    enum class Alignment() {
        LEFT,
        RIGHT,
        CENTER
    }

}