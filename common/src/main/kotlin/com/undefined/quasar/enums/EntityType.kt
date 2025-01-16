package com.undefined.quasar.enums

import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.interfaces.entities.entity.AreaEffectCloud
import com.undefined.quasar.interfaces.entities.entity.ambient.Bat
import com.undefined.quasar.interfaces.entities.entity.animal.*
import com.undefined.quasar.interfaces.entities.entity.animal.camel.Camel
import com.undefined.quasar.interfaces.entities.entity.animal.horse.Donkey
import com.undefined.quasar.interfaces.entities.entity.animal.water.Cod
import com.undefined.quasar.interfaces.entities.entity.animal.water.Dolphin
import com.undefined.quasar.interfaces.entities.entity.decoration.ArmorStand
import com.undefined.quasar.interfaces.entities.entity.display.BlockDisplay
import com.undefined.quasar.interfaces.entities.entity.monster.*
import com.undefined.quasar.interfaces.entities.entity.projectile.Arrow
import com.undefined.quasar.interfaces.entities.entity.projectile.BreezeWindCharge
import com.undefined.quasar.interfaces.entities.entity.projectile.DragonFireball
import com.undefined.quasar.interfaces.entities.entity.projectile.ThrownEgg
import com.undefined.quasar.interfaces.entities.entity.vehicle.minecart.Minecart
import com.undefined.quasar.interfaces.entities.entity.vehicle.boat.*
import com.undefined.quasar.interfaces.entities.entity.vehicle.minecart.MinecartChest
import com.undefined.quasar.interfaces.entities.entity.vehicle.minecart.MinecartCommandBlock
import kotlin.reflect.KClass

enum class EntityType(val clazz: KClass<out Entity>) {
    ACACIA_BOAT(AcaciaBoat::class),
    ACACIA_CHEST_BOAT(AcaciaChestBoat::class),
    ALLAY(Allay::class),
    AREA_EFFECT_CLOUD(AreaEffectCloud::class),
    ARMADILLO(Armadillo::class),
    ARMORSTAND(ArmorStand::class),
    ARROW(Arrow::class),
    AXOLOTL(Axolotl::class),
    BAT(Bat::class),
    BEE(Bee::class),
    BIRCH_BOAT(BirchBoat::class),
    BIRCH_CHEST_BOAT(BirchChestBoat::class),
    BLAZE(Blaze::class),
    BLOCK_DISPLAY(BlockDisplay::class),
    BOGGED(Bogged::class),
    BREEZE(Breeze::class),
    BREEZE_WIND_CHARGE(BreezeWindCharge::class),
    CAMEL(Camel::class),
    CAT(Cat::class),
    CAVE_SPIDER(CaveSpider::class),
    CHERRY_BOAT(CherryBoat::class),
    CHERRY_CHEST_BOAT(CherryChestBoat::class),
    CHEST_MINECART(MinecartChest::class),
    CHICKEN(Chicken::class),
    COD(Cod::class),
    COMMAND_BLOCK_MINECART(MinecartCommandBlock::class),
    COW(Cow::class),
    CREAKING(Creaking::class),
    CREEPER(Creeper::class),
    DARK_OAK_BOAT(DarkOakBoat::class),
    DARK_OAK_CHEST_BOAT(DarkOakChestBoat::class),
    DOLPHIN(Dolphin::class),
    DONKEY(Donkey::class),
    DRAGON_FIREBALL(DragonFireball::class),
    DROWNED(Drowned::class),
    EGG(ThrownEgg::class),
    ELDER_GUARDIAN(ElderGuardian::class),

    GUARDIAN(Guardian::class),
    ZOMBIE(Zombie::class),
    SPIDER(Spider::class),
    MINECART(Minecart::class)

}