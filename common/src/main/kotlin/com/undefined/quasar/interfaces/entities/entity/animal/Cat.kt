package com.undefined.quasar.interfaces.entities.entity.animal

import com.undefined.quasar.interfaces.abstracts.TamableAnimal
import org.bukkit.Color

interface Cat : TamableAnimal {

    fun setVarinat(varinat: Varinat)
    fun getVariant(): Varinat

    fun setLying(lying: Boolean)
    fun isLying(): Boolean

    fun setRelaxingState(relaxing: Boolean)
    fun getRelaxingState(): Boolean

    fun setCollarColor(color: Color)
    fun getCollarColor(): Color


    enum class Varinat() {
        TABBY,
        BLACK,
        RED,
        SIAMESE,
        BRITISH_SHORTHAIR,
        CALICO,
        PERSIAN,
        RAGDOLL,
        WHITE,
        JELLIE,
        ALL_BLACK
    }
}