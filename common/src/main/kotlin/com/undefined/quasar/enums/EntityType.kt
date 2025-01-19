package com.undefined.quasar.enums

import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.interfaces.entities.entity.*
import com.undefined.quasar.interfaces.entities.entity.item.ItemEntity
import com.undefined.quasar.interfaces.entities.entity.ambient.Bat
import com.undefined.quasar.interfaces.entities.entity.animal.*
import com.undefined.quasar.interfaces.entities.entity.animal.camel.Camel
import com.undefined.quasar.interfaces.entities.entity.animal.horse.Donkey
import com.undefined.quasar.interfaces.entities.entity.animal.horse.Horse
import com.undefined.quasar.interfaces.entities.entity.animal.horse.Llama
import com.undefined.quasar.interfaces.entities.entity.animal.horse.Mule
import com.undefined.quasar.interfaces.entities.entity.animal.water.*
import com.undefined.quasar.interfaces.entities.entity.decoration.*
import com.undefined.quasar.interfaces.entities.entity.display.BlockDisplay
import com.undefined.quasar.interfaces.entities.entity.display.ItemDisplay
import com.undefined.quasar.interfaces.entities.entity.item.FallingBlockEntity
import com.undefined.quasar.interfaces.entities.entity.monster.*
import com.undefined.quasar.interfaces.entities.entity.monster.boss.EndCrystal
import com.undefined.quasar.interfaces.entities.entity.monster.boss.EnderDragon
import com.undefined.quasar.interfaces.entities.entity.projectile.*
import com.undefined.quasar.interfaces.entities.entity.vehicle.boat.*
import com.undefined.quasar.interfaces.entities.entity.vehicle.minecart.*
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
    GLOW_SQUID(GlowSquid::class),
    GOAT(Goat::class),
    GUARDIAN(Guardian::class),
    HOGLIN(Hoglin::class),
    HOPPER_MINECART(MinecartHopper::class),
    HORSE(Horse::class),
    HUSK(Husk::class),
    ILLUSIONER(Illusioner::class),
    INTERACTION(Interaction::class),
    IRON_GOLEM(IronGolem::class),
    ITEM(ItemEntity::class),
    ITEM_DISPLAY(ItemDisplay::class),
    ITEM_FRAME(ItemFrame::class),
    JUNGLE_BOAT(JungleBoat::class),
    JUNGLE_CHEST_BOAT(JungleChestBoat::class),
    LEASH_KNOT(LeashFenceKnotEntity::class),
    LIGHTNING_BOLT(LightningBolt::class),
    LLAMA(Llama::class),
    LLAMA_SPIT(LlamaSpit::class),
    MAGMA_CUBE(MagmaCube::class),
    MANGROVE_BOAT(MangroveBoat::class),
    MANGROVE_CHEST_BOAT(MangroveChestBoat::class),
    MINECART(Minecart::class),
    MOOSHROOM(MooshroomCow::class),
    MULE(Mule::class),
    OAK_BOAT(OakBoat::class),
    OAK_CHEST_BOAT(OakChestBoat::class),
    OCELOT(Ocelot::class),
    OMINOUS_ITEM_SPAWNER(OminousItemSpawner::class),
    PAINTING(Painting::class),
    PALE_OAK_BOAT(PaleOakBoat::class),
    PALE_OAK_CHEST_BOAT(PaleOakChestBoat::class),
    PANDA(Panda::class),
    PARROT(Parrot::class),
    PHANTOM(Phantom::class),
    PIG(Pig::class),
    PIGLIN(Piglin::class),
    PIGLIN_BRUTE(PiglinBrute::class),
    PILLAGER(Pillager::class),
    POLAR_BEAR(PolarBear::class),
    POTION(ThrownPotion::class),
    PUFFERFISH(Pufferfish::class),
    RABBIT(Rabbit::class),
    RAVAGER(Ravager::class),
    SALMON(Salmon::class),



    SLIME(Slime::class),
    SQUID(Squid::class),
    ZOMBIE(Zombie::class),
    SPIDER(Spider::class)
}