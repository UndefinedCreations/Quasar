package com.undefined.quasar.entity.factories

import com.undefined.quasar.entity.EntityFactory
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.exception.EntityNotFoundException
import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.interfaces.entities.entity.monster.Drowned
import com.undefined.quasar.interfaces.entities.entity.vehicle.boat.DarkOakBoat

import com.undefined.quasar.v1_21_4.impl.entity.AreaEffectCloud
import com.undefined.quasar.v1_21_4.impl.entity.ambient.Bat
import com.undefined.quasar.v1_21_4.impl.entity.animal.*
import com.undefined.quasar.v1_21_4.impl.entity.animal.camel.Camel
import com.undefined.quasar.v1_21_4.impl.entity.animal.horse.Donkey
import com.undefined.quasar.v1_21_4.impl.entity.animal.water.Cod
import com.undefined.quasar.v1_21_4.impl.entity.animal.water.Dolphin
import com.undefined.quasar.v1_21_4.impl.entity.decoration.ArmorStand
import com.undefined.quasar.v1_21_4.impl.entity.display.BlockDisplay
import com.undefined.quasar.v1_21_4.impl.entity.monster.*
import com.undefined.quasar.v1_21_4.impl.entity.projectile.Arrow
import com.undefined.quasar.v1_21_4.impl.entity.projectile.BreezeWindCharge
import com.undefined.quasar.v1_21_4.impl.entity.projectile.DragonFireball
import com.undefined.quasar.v1_21_4.impl.entity.projectile.ThrownEgg
import com.undefined.quasar.v1_21_4.impl.entity.vehicle.minecart.Minecart
import com.undefined.quasar.v1_21_4.impl.entity.vehicle.boats.*
import com.undefined.quasar.v1_21_4.impl.entity.vehicle.minecart.MinecartChest
import com.undefined.quasar.v1_21_4.impl.entity.vehicle.minecart.MinecartCommandBlock

class EntityFactory1_21_4 : EntityFactory {

    override fun createEntity(entityType: EntityType): Entity =
        when(entityType) {
            EntityType.ACACIA_BOAT -> AcaciaBoat()
            EntityType.ACACIA_CHEST_BOAT -> AcaciaChestBoat()
            EntityType.ALLAY -> Allay()
            EntityType.AREA_EFFECT_CLOUD -> AreaEffectCloud()
            EntityType.ARMADILLO -> Armadillo()
            EntityType.ARMORSTAND -> ArmorStand()
            EntityType.ARROW -> Arrow()
            EntityType.AXOLOTL -> Axolotl()
            EntityType.BAT -> Bat()
            EntityType.BEE -> Bee() // all the work for nothing rip. Next entity is a display entity...... that sounds like a problem for tomorrow wdym yay? lol. Should we keep this in the commit log? sure thing lol
            EntityType.BIRCH_BOAT -> BirchBoat()
            EntityType.BIRCH_CHEST_BOAT -> BirchChestBoat()
            EntityType.BLAZE -> Blaze() // Anyway, I'm going... Nice. Also paper doesn't allow for full CustomArgumentType support apparently :sad: Exactly. :(. YAY -> display entities! Bye bye. I want them. Definitely lol
            EntityType.BLOCK_DISPLAY -> BlockDisplay()
            EntityType.BOGGED -> Bogged()
            EntityType.BREEZE -> Breeze()
            EntityType.BREEZE_WIND_CHARGE -> BreezeWindCharge()
            EntityType.CAMEL -> Camel()
            EntityType.CAT -> Cat()
            EntityType.CAVE_SPIDER -> CaveSpider()
            EntityType.CHERRY_BOAT -> CherryBoat()
            EntityType.CHERRY_CHEST_BOAT -> CherryChestBoat()
            EntityType.CHEST_MINECART -> MinecartChest()
            EntityType.CHICKEN -> Chicken()
            EntityType.COD -> Cod()
            EntityType.COMMAND_BLOCK_MINECART -> MinecartCommandBlock()
            EntityType.COW -> Cow()
            EntityType.CREAKING -> Creaking()
            EntityType.CREEPER -> Creeper()
            EntityType.DARK_OAK_BOAT -> DarkOakBoat()
            EntityType.DARK_OAK_CHEST_BOAT -> DarkOakChestBoat()
            EntityType.DOLPHIN -> Dolphin()
            EntityType.DONKEY -> Donkey()
            EntityType.DRAGON_FIREBALL -> DragonFireball()
            EntityType.DROWNED -> Drowned()
            EntityType.EGG -> ThrownEgg()
            EntityType.ELDER_GUARDIAN -> ElderGuardian()

            EntityType.GUARDIAN -> Guardian()
            EntityType.ZOMBIE -> Zombie()
            EntityType.SPIDER -> Spider()
            EntityType.MINECART -> Minecart()
            else -> throw EntityNotFoundException(entityType.name)
        }

}