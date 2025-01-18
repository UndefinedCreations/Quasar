package com.undefined.quasar.entity.factories

import com.undefined.quasar.entity.EntityFactory
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.exception.EntityNotFoundException
import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.v1_21_4.impl.entity.AreaEffectCloud
import com.undefined.quasar.v1_21_4.impl.entity.ExperienceOrb
import com.undefined.quasar.v1_21_4.impl.entity.ambient.Bat
import com.undefined.quasar.v1_21_4.impl.entity.animal.*
import com.undefined.quasar.v1_21_4.impl.entity.animal.camel.Camel
import com.undefined.quasar.v1_21_4.impl.entity.animal.horse.Donkey
import com.undefined.quasar.v1_21_4.impl.entity.animal.water.Cod
import com.undefined.quasar.v1_21_4.impl.entity.animal.water.Dolphin
import com.undefined.quasar.v1_21_4.impl.entity.decoration.ArmorStand
import com.undefined.quasar.v1_21_4.impl.entity.decoration.GlowItemFrame
import com.undefined.quasar.v1_21_4.impl.entity.decoration.ItemFrame
import com.undefined.quasar.v1_21_4.impl.entity.display.BlockDisplay
import com.undefined.quasar.v1_21_4.impl.entity.item.FallingBlockEntity
import com.undefined.quasar.v1_21_4.impl.entity.monster.*
import com.undefined.quasar.v1_21_4.impl.entity.monster.boss.EndCrystal
import com.undefined.quasar.v1_21_4.impl.entity.monster.boss.EnderDragon
import com.undefined.quasar.v1_21_4.impl.entity.projectile.*
import com.undefined.quasar.v1_21_4.impl.entity.vehicle.boats.*
import com.undefined.quasar.v1_21_4.impl.entity.vehicle.minecart.Minecart
import com.undefined.quasar.v1_21_4.impl.entity.vehicle.minecart.MinecartChest
import com.undefined.quasar.v1_21_4.impl.entity.vehicle.minecart.MinecartCommandBlock
import com.undefined.quasar.v1_21_4.impl.entity.vehicle.minecart.MinecartFurnace

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
            EntityType.BEE -> Bee()
            EntityType.BIRCH_BOAT -> BirchBoat()
            EntityType.BIRCH_CHEST_BOAT -> BirchChestBoat()
            EntityType.BLAZE -> Blaze()
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
            EntityType.ENDERMAN -> EnderMan()
            EntityType.ENDERMITE -> Endermite()
            EntityType.ENDER_DRAGON -> EnderDragon()
            EntityType.ENDER_PEARL -> ThrownEnderpearl()
            EntityType.END_CRYSTAL -> EndCrystal()
            EntityType.EVOKER -> Evoker()
            EntityType.EVOKER_FANGS -> EvokerFangs()
            EntityType.EXPERIENCE_BOTTLE -> ThrownExperienceBottle()
            EntityType.EXPERIENCE_ORB -> ExperienceOrb()
            EntityType.EYE_OF_ENDER -> EyeOfEnder()
            EntityType.FALLING_BLOCK -> FallingBlockEntity()
            EntityType.FIREBALL -> LargeFireball()
            EntityType.FIREWORK_ROCKET_ENTITY -> FireworkRocketEntity()
            EntityType.FOX -> Fox()
            EntityType.FROG -> Frog()
            EntityType.FURNACE_MINECART -> MinecartFurnace()
            EntityType.GHAST -> Ghast()
            EntityType.GIANT -> Giant()
            EntityType.GLOW_ITEM_FRAME -> GlowItemFrame()

            EntityType.ITEM_FRAME -> ItemFrame()
            EntityType.GUARDIAN -> Guardian()
            EntityType.ZOMBIE -> Zombie()
            EntityType.SPIDER -> Spider()
            EntityType.MINECART -> Minecart()
            else -> throw EntityNotFoundException(entityType.name)
        }

}