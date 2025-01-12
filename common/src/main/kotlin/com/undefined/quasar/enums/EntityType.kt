package com.undefined.quasar.enums

import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.interfaces.entities.entity.AreaEffectCloud
import com.undefined.quasar.interfaces.entities.entity.ambient.Bat
import com.undefined.quasar.interfaces.entities.entity.animal.Allay
import com.undefined.quasar.interfaces.entities.entity.animal.Armadillo
import com.undefined.quasar.interfaces.entities.entity.animal.Axolotl
import com.undefined.quasar.interfaces.entities.entity.animal.Bee
import com.undefined.quasar.interfaces.entities.entity.decoration.ArmorStand
import com.undefined.quasar.interfaces.entities.entity.monster.Blaze
import com.undefined.quasar.interfaces.entities.entity.projectile.Arrow
import com.undefined.quasar.interfaces.entities.entity.vehicle.Minecart
import kotlin.reflect.KClass

enum class EntityType(val clazz: KClass<out Entity>) {
    ALLAY(Allay::class),
    AREA_EFFECT_CLOUD(AreaEffectCloud::class),
    ARMADILLO(Armadillo::class),
    ARMORSTAND(ArmorStand::class),
    ARROW(Arrow::class),
    AXOLOTL(Axolotl::class),
    BAT(Bat::class),
    BEE(Bee::class),
    BLAZE(Blaze::class),
    MINECART(Minecart::class)

}