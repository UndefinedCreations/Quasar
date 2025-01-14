package com.undefined.quasar.interfaces

import org.bukkit.Material

interface AbstractHorse : Animal {

    fun setSaddle(saddle: Boolean) = if (saddle) setItem(0, Material.SADDLE) else setItem(0, null)

    fun hasSaddle(): Boolean = (getItem(0)?.type ?: Material.AIR) == Material.SADDLE

}