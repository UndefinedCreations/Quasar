package com.undefined.quasar.enums

import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.interfaces.entities.entity.AreaEffectCloud
import com.undefined.quasar.interfaces.entities.entity.ExperienceOrb
import com.undefined.quasar.interfaces.entities.entity.ambient.Bat
import com.undefined.quasar.interfaces.entities.entity.animal.*
import com.undefined.quasar.interfaces.entities.entity.animal.camel.Camel
import com.undefined.quasar.interfaces.entities.entity.animal.horse.Donkey
import com.undefined.quasar.interfaces.entities.entity.animal.water.Cod
import com.undefined.quasar.interfaces.entities.entity.animal.water.Dolphin
import com.undefined.quasar.interfaces.entities.entity.decoration.ArmorStand
import com.undefined.quasar.interfaces.entities.entity.decoration.GlowItemFrame
import com.undefined.quasar.interfaces.entities.entity.decoration.ItemFrame
import com.undefined.quasar.interfaces.entities.entity.display.BlockDisplay
import com.undefined.quasar.interfaces.entities.entity.item.FallingBlockEntity
import com.undefined.quasar.interfaces.entities.entity.monster.*
import com.undefined.quasar.interfaces.entities.entity.monster.boss.EndCrystal
import com.undefined.quasar.interfaces.entities.entity.monster.boss.EnderDragon
import com.undefined.quasar.interfaces.entities.entity.projectile.*
import com.undefined.quasar.interfaces.entities.entity.vehicle.boat.*
import com.undefined.quasar.interfaces.entities.entity.vehicle.minecart.Minecart
import com.undefined.quasar.interfaces.entities.entity.vehicle.minecart.MinecartChest
import com.undefined.quasar.interfaces.entities.entity.vehicle.minecart.MinecartCommandBlock
import com.undefined.quasar.interfaces.entities.entity.vehicle.minecart.MinecartFurnace
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
    ENDERMAN(EnderMan::class),
    ENDERMITE(Endermite::class),
    ENDER_DRAGON(EnderDragon::class),
    ENDER_PEARL(ThrownEnderpearl::class),
    END_CRYSTAL(EndCrystal::class),
    EVOKER(Evoker::class),
    EVOKER_FANGS(EvokerFangs::class),
    EXPERIENCE_BOTTLE(ThrownExperienceBottle::class),
    EXPERIENCE_ORB(ExperienceOrb::class),
    EYE_OF_ENDER(EyeOfEnder::class),
    FALLING_BLOCK(FallingBlockEntity::class),
    FIREBALL(LargeFireball::class),
    FIREWORK_ROCKET_ENTITY(FireworkRocketEntity::class),
    FOX(Fox::class),
    FROG(Frog::class),
    FURNACE_MINECART(MinecartFurnace::class),
    GHAST(Ghast::class),
    GIANT(Giant::class),
    GLOW_ITEM_FRAME(GlowItemFrame::class),

    ITEM_FRAME(ItemFrame::class),
    GUARDIAN(Guardian::class),
    ZOMBIE(Zombie::class),
    SPIDER(Spider::class),
    MINECART(Minecart::class)
}