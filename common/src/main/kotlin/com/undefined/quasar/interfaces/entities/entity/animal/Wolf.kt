package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.TamableAnimal
import org.bukkit.Color
import org.bukkit.entity.Wolf

interface Wolf : TamableAnimal {

    fun setCollarColor(color: Color)
    fun getCollarColor(): Color

    fun setAngy(angy: Boolean)
    fun isAngy(): Boolean

    fun setVariant(variant: Variant)
    fun getVariant(): Variant

    enum class Variant() {
        PALE,
        SPOTTED,
        SNOWY,
        BLACK,
        ASHEN,
        RUSTY,
        WOODS,
        CHESTNUT,
        STRIPED;

        fun toBukkit(): Wolf.Variant =
            when(this) {
                PALE -> Wolf.Variant.PALE
                SPOTTED -> Wolf.Variant.SPOTTED
                SNOWY -> Wolf.Variant.SNOWY
                BLACK -> Wolf.Variant.BLACK
                ASHEN -> Wolf.Variant.ASHEN
                RUSTY -> Wolf.Variant.RUSTY
                WOODS -> Wolf.Variant.WOODS
                CHESTNUT -> Wolf.Variant.CHESTNUT
                STRIPED -> Wolf.Variant.STRIPED
            }
    }
}