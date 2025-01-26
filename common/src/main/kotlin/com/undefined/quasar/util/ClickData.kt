package com.undefined.quasar.util

import com.undefined.quasar.interfaces.Entity
import org.bukkit.entity.Player

 class ClickData<T: Entity>(
    val player: Player,
    val click: ClickType,
    val entity: T
)

enum class ClickType {
    RIGHT,
    LEFT
}