package com.undefined.quasar.entity.factories

import com.undefined.quasar.entity.EntityFactory
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.exception.EntityNotFoundException
import com.undefined.quasar.interfaces.Entity

import com.undefined.quasar.v1_21_4.impl.entity.AreaEffectCloud
import com.undefined.quasar.v1_21_4.impl.entity.ambient.Bat
import com.undefined.quasar.v1_21_4.impl.entity.animal.Allay
import com.undefined.quasar.v1_21_4.impl.entity.animal.Armadillo
import com.undefined.quasar.v1_21_4.impl.entity.animal.Axolotl
import com.undefined.quasar.v1_21_4.impl.entity.animal.Bee
import com.undefined.quasar.v1_21_4.impl.entity.animal.camel.Camel
import com.undefined.quasar.v1_21_4.impl.entity.decoration.ArmorStand
import com.undefined.quasar.v1_21_4.impl.entity.display.BlockDisplay
import com.undefined.quasar.v1_21_4.impl.entity.monster.Blaze
import com.undefined.quasar.v1_21_4.impl.entity.monster.Bogged
import com.undefined.quasar.v1_21_4.impl.entity.monster.Breeze
import com.undefined.quasar.v1_21_4.impl.entity.projectile.Arrow
import com.undefined.quasar.v1_21_4.impl.entity.projectile.BreezeWindCharge
import com.undefined.quasar.v1_21_4.impl.entity.vehicle.Minecart

class EntityFactory1_21_4 : EntityFactory {

    override fun createEntity(entityType: EntityType): Entity =
        when(entityType) {
            EntityType.ALLAY -> Allay()
            EntityType.AREA_EFFECT_CLOUD -> AreaEffectCloud()
            EntityType.ARMADILLO -> Armadillo()
            EntityType.ARMORSTAND -> ArmorStand()
            EntityType.ARROW -> Arrow()
            EntityType.AXOLOTL -> Axolotl()
            EntityType.BAT -> Bat()
            EntityType.BEE -> Bee() // all the work for nothing rip. Next entity is a display entity...... that sounds like a problem for tomorrow wdym yay? lol. Should we keep this in the commit log? sure thing lol
            EntityType.BLAZE -> Blaze() // Anyway, I'm going... Nice. Also paper doesn't allow for full CustomArgumentType support apparently :sad: Exactly. :(. YAY -> display entities! Bye bye. I want them. Definitely lol
            EntityType.BLOCK_DISPLAY -> BlockDisplay()
            EntityType.BOGGED -> Bogged()
            EntityType.BREEZE -> Breeze()
            EntityType.BREEZE_WIND_CHARGE -> BreezeWindCharge()
            EntityType.CAMEL -> Camel()
            EntityType.MINECART -> Minecart()
            else -> throw EntityNotFoundException(entityType.name)
        }

}