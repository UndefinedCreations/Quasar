package com.undefined.quasar.enums

import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.interfaces.entities.entity.AreaEffectCloud
import com.undefined.quasar.interfaces.entities.entity.ambient.Bat
import com.undefined.quasar.interfaces.entities.entity.animal.*
import com.undefined.quasar.interfaces.entities.entity.animal.camel.Camel
import com.undefined.quasar.interfaces.entities.entity.decoration.ArmorStand
import com.undefined.quasar.interfaces.entities.entity.display.BlockDisplay
import com.undefined.quasar.interfaces.entities.entity.monster.Blaze
import com.undefined.quasar.interfaces.entities.entity.monster.Bogged
import com.undefined.quasar.interfaces.entities.entity.monster.Breeze
import com.undefined.quasar.interfaces.entities.entity.projectile.Arrow
import com.undefined.quasar.interfaces.entities.entity.projectile.BreezeWindCharge
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
    BLOCK_DISPLAY(BlockDisplay::class),
    BOGGED(Bogged::class),
    BREEZE(Breeze::class),
    BREEZE_WIND_CHARGE(BreezeWindCharge::class),
    CAMEL(Camel::class),
    CAT(Cat::class),
    MINECART(Minecart::class)

}