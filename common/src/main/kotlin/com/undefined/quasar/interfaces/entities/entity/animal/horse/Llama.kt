package com.undefined.quasar.interfaces.entities.entity.animal.horse

import com.undefined.quasar.interfaces.abstracts.AbstractChestHorse
import org.bukkit.Material

interface Llama : AbstractChestHorse {

    fun setVariant(variant: Variant)
    fun getVariant(): Variant

    fun setCarpet(carpets: Carpets) = setItem(0, carpets.material)
    fun getCarpet(): Carpets = getItem(0)?.let { item ->
        Carpets.entries.first { it.material == item.type }
    } ?: Carpets.NONE

    enum class Carpets(val material: Material) {
        NONE(Material.AIR),
        WHITE(Material.WHITE_CARPET),
        LIGHT_GRAY(Material.LIGHT_GRAY_CARPET),
        GRAY(Material.GRAY_CARPET),
        BLACK(Material.BLACK_CARPET),
        BROWN(Material.BROWN_CARPET),
        RED(Material.RED_CARPET),
        ORANGE(Material.ORANGE_CARPET),
        YELLOW(Material.YELLOW_CARPET),
        PINK(Material.PINK_CARPET),
        MAGENTA(Material.MAGENTA_CARPET),
        PURPLE(Material.PURPLE_CARPET),
        BLUE(Material.BLUE_CARPET),
        LIGHT_BLUE(Material.LIGHT_BLUE_CARPET),
        CYAN(Material.CYAN_CARPET),
        GREEN(Material.GREEN_CARPET),
        LIME(Material.LIME_CARPET)
    }

    enum class Variant(val id: Int) {
        CREAMY(0),
        WHITE(1),
        BROWN(2),
        GRAY(3)
    }
}