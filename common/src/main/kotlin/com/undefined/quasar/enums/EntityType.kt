package com.undefined.quasar.enums

import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.interfaces.entities.entity.animal.Allay
import com.undefined.quasar.interfaces.entities.entity.decoration.ArmorStand
import com.undefined.quasar.interfaces.entities.entity.vehicle.Minecart
import kotlin.reflect.KClass

enum class EntityType(val clazz: KClass<out Entity>) {
    ALLAY(Allay::class),
    MINECART(Minecart::class),
    ARMORSTAND(ArmorStand::class)
}