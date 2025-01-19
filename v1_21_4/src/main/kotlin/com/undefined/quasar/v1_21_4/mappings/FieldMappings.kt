package com.undefined.quasar.v1_21_4.mappings

object FieldMappings {
    object Entity {
        object Base {
            // net/minecraft/world/entity/Entity.html
            const val DATA_CUSTOM_NAME = "aO"
            const val DATA_CUSTOM_NAME_VISIBLE = "aP"
            const val DATA_SILENT = "aQ"
            const val DATA_NO_GRAVITY = "aR"
            const val DATA_TICKS_FROZEN = "aS"
            const val DATA_POSE = "aq"
        }

        object Vehicle {
            object Minecart {
                // net/minecraft/world/entity/vehicle/AbstractMinecart.html
                const val DATA_ID_DISPLAY_BLOCK = "c"
                const val DATA_ID_DISPLAY_OFFSET = "d"
                const val DATA_ID_CUSTOM_DISPLAY = "h"

                object MinecartFurnace {
                    // net/minecraft/world/entity/vehicle/MinecartFurnace.html
                    const val DATA_ID_FUEL = "c"
                }

            }
            object Boat {
                // net/minecraft/world/entity/vehicle/AbstractBoat.html
                const val DATA_ID_PADDLE_LEFT = "h"
                const val DATA_ID_PADDLE_RIGHT = "i"
                const val DATA_ID_BUBBLE_TIME = "j"
            }
        }

        object LivingEntity {
            const val DATA_LIVING_ENTITY_FLAGS = "aC"

            object Mob {
                object Animal {
                    const val DATA_BABY_ID = "bY"
                    object TamableAnimal {
                        // net/minecraft/world/entity/TamableAnimal.html
                        const val DATA_FLAGS_ID = "bZ"
                        object Cat {
                            // net/minecraft/world/entity/animal/Cat.html
                            const val DATA_VARIANT_ID = "cf"
                            const val IS_LYING = "cg"
                            const val RELAX_STATE_ONE = "ch"
                            const val DATA_COLLAR_COLOR = "ci"
                        }
                    }
                    object WaterAnimal {
                        // net/minecraft/world/entity/animal/Dolphin.html
                        const val GOT_FISH = "cc"
                        const val MOISTNESS_LEVEL = "cd"
                    }
                    object Salmon {
                        // net/minecraft/world/entity/animal/Salmon.html
                        const val DATA_TYPE = "b"
                    }
                    object Pufferfish {
                        // net/minecraft/world/entity/animal/Pufferfish.html
                        const val PUFF_STATE = "bY"
                    }
                    object GlowSquid {
                        // net/minecraft/world/entity/GlowSquid.html
                        const val DATA_DARK_TICKS_REMAINING = "cg"
                    }
                    object Allay {
                        // net/minecraft/world/entity/animal/allay/Allay.html
                        const val DATA_DANCING = "cf"
                    }
                    object Armadillo {
                        // net/minecraft/world/entity/animal/armadillo/Armadillo.html
                        const val ARMADILLO_STATE = "ch"

                        object State {
                            // net/minecraft/world/entity/animal/armadillo/Armadillo$ArmadilloState.html
                            const val ID = "k"
                        }
                    }
                    object Axolotl {
                        // net/minecraft/world/entity/animal/axolotl/Axolotl.html
                        const val DATA_VARIANT = "ck"
                    }
                    object Bee {
                        // net/minecraft/world/entity/animal/Bee.html
                        const val DATA_FLAGS_ID = "ci"
                        const val DATA_REMAINING_ANGER_TIME = "cj"
                    }
                    object Fox {
                        // net/minecraft/world/entity/animal/Fox.html
                        const val DATA_TYPE_ID = "cc"
                        const val DATA_FLAGS_ID = "cd"
                    }
                    object Frog {
                        // net/minecraft/world/entity/animal/frog/Frog.html
                        const val DATA_VARIANT_ID = "cg"
                        const val DATA_TONGUE_TARGET_ID = "ch"
                    }
                    object Goat {
                        // net/minecraft/world/entity/animal/goat/Goat.html
                        const val DATA_IS_SCREAMING_GOAT = "ch"
                        const val DATA_HAS_LEFT_HORN = "ci"
                        const val DATA_HAS_RIGHT_HORN = "cj"
                    }
                    object Mushroom {
                        // net/minecraft/world/entity/animal/MushroomCow.html
                        const val DATA_TYPE = "bY"
                    }
                    object Ocelot {
                        // net/minecraft/world/entity/animal/Ocelot.html
                        const val DATA_TRUSTING = "cc"
                    }
                    object Panda {
                        // net/minecraft/world/entity/animal/Panda.html
                        const val MAIN_GENE_ID = "ce"
                        const val HIDDEN_GENE_ID = "cf"
                        const val DATA_ID_FLAGS = "cg"
                        const val EAT_COUNTER = "cd"
                        const val UNHAPPY_COUNTER = "ca"
                    }
                    object Parrot {
                        // net/minecraft/world/entity/animal/Parrot.html
                        const val DATA_VARIANT_ID = "cg"
                    }
                    object Pig {
                        // net/minecraft/world/entity/animal/Pig.html
                        const val DATA_SADDLE_ID = "bY"
                    }
                    object PolarBear {
                        // net/minecraft/world/entity/animal/PolarBear.html
                        const val DATA_STANDING_ID = "bY"
                    }
                    object Rabbit {
                        // net/minecraft/world/entity/animal/Rabbit.html
                        const val DATA_TYPE_ID = "ce"
                    }
                    object AbstractHorse {
                        object Camel {
                            // net/minecraft/world/entity/animal/camel/Camel.html
                            const val DASH = "cc"
                        }
                        object AbstractChestHorse {
                            // net/minecraft/world/entity/animal/horse/AbstractChestedHorse.html
                            const val DATA_ID_CHEST = "bY"
                        }
                        object Horse {
                            // net/minecraft/world/entity/animal/horse/Horse.html
                            const val DATA_ID_TYPE_VARIANT = "bY"
                        }
                        object Llama {
                            // net/minecraft/world/entity/animal/horse/Llama.html
                            const val DATA_VARIANT_ID = "ca"
                        }
                    }
                }
                object Boss {
                    object EnderDragon {
                        // net/minecraft/world/entity/boss/enderdragon/EnderDragon.html
                        const val DATA_PHASE = "a"
                    }
                    object EndCrystal {
                        // net/minecraft/world/entity/boss/enderdragon/EndCrystal.html
                        const val DATA_BEAM_TARGET = "b"
                        const val DATA_SHOW_BOTTOM = "c"
                    }
                }
                object Monster {
                    object Raider {
                        // net/minecraft/world/entity/raid/Raider.html
                        const val IS_CELEBRATING = "c"
                        object SpellcasterIllager {
                            // net/minecraft/world/entity/monster/SpellcasterIllager.html
                            const val DATA_SPELL_CASTING_ID = "a"
                        }
                        object Pillager {
                            // net/minecraft/world/entity/monster/Pillager.html
                            const val IS_CHARGING_CROSSBOW = "a"
                        }
                    }
                    object Blaze {
                        // net/minecraft/world/entity/monster/Blaze.html
                        const val DATA_FLAGS_ID = "c"
                    }
                    object Bogged {
                        // net/minecraft/world/entity/monster/Bogged.html
                        const val DATA_SHEARED = "d"
                    }
                    object Spider {
                        // net/minecraft/world/entity/monster/Spider.html
                        const val DATA_FLAGS_ID = "a"
                    }
                    object Creaking {
                        // net/minecraft/world/entity/monster/creaking/Creaking.html
                        const val IS_ACTIVE = "ce"
                        const val IS_TEARING_DOWN = "cf"
                    }
                    object Creeper {
                        // net/minecraft/world/entity/monster/Creeper.html
                        const val DATA_SWELL_DIR = "a"
                        const val DATA_IS_IGNITED = "c"
                    }
                    object Zombie {
                        // net/minecraft/world/entity/monster/Zombie.html
                        const val DATA_BABY_ID = "ce"
                        const val DATA_DROWNED_CONVERSION_ID = "cg"
                    }
                    object Guardian {
                        // net/minecraft/world/entity/monster/Guardian.html
                        const val DATA_ID_MOVING = "a"
                        const val DATA_ID_ATTACK_TARGET = "d"
                    }
                    object EnderMan {
                        // net/minecraft/world/entity/monster/EnderMan.html
                        const val DATA_CARRY_STATE = "ca"
                        const val DATA_CREEPY = "cb"
                        const val DATA_STARED_AT = "cc"
                    }
                    object Ghast {
                        // net/minecraft/world/entity/monster/Ghast.html
                        const val DATA_IS_CHARGING = "a"
                    }
                    object Hoglin {
                        // net/minecraft/world/entity/monster/hoglin/Hoglin.html
                        const val DATA_IMMUNE_TO_ZOMBIFICATION = "cc"
                    }
                    object Slime {
                        // net/minecraft/world/entity/monster/Slime.html
                        const val ID_SIZE = "bZ"
                    }
                    object Phantom {
                        // net/minecraft/world/entity/monster/Phantom.html
                        const val ID_SIZE = "c"
                    }
                    object AbstractPiglin {
                        // net/minecraft/world/entity/monster/piglin/AbstractPiglin.html
                        const val DATA_IMMUNE_TO_ZOMBIFICATION = "a"

                        object Piglin {
                            // net/minecraft/world/entity/monster/piglin/Piglin.html
                            const val DATA_BABY_ID = "bZ"
                            const val DATA_IS_CHARGING_CROSSBOW = "ca"
                            const val DATA_IS_DANCING = "cb"
                        }
                    }
                }
                object AmbientCreature {
                    object Bat {
                        // net/minecraft/world/entity/ambient/Bat.html
                        const val DATA_ID_FLAGS = "bX"
                    }
                }
            }

            object ArmorStand {
                // net/minecraft/world/entity/decoration/ArmorStand.html
                const val DATA_CLIENT_FLAGS = "bI"
                const val DATA_HEAD_POSE = "bJ"
                const val DATA_BODY_POSE = "bK"
                const val DATA_LEFT_ARM_POSE = "bL"
                const val DATA_RIGHT_ARM_POSE = "bM"
                const val DATA_LEFT_LEG_POSE = "bN"
                const val DATA_RIGHT_LEG_POSE = "bO"
            }
        }

        object Projectile {
            object Arrow {
                // net/minecraft/world/entity/projectile/Arrow.html
                const val ID_EFFECT_COLOR = "f"
            }
            object ThrowableItemProjectile {
                // net/minecraft/world/entity/projectile/ThrowableItemProjectile.html
                const val DATA_ITEM_STACK = "a"
            }
            object EyeOfEnder {
                // net/minecraft/world/entity/projectile/EyeOfEnder.html
                const val DATA_ITEM_STACK = "b"
            }
            object FireBall {
                // net/minecraft/world/entity/projectile/Fireball.html
                const val DATA_ITEM_STACK = "e"
            }
            object FireworkRocketEntity {
                // net/minecraft/world/entity/projectile/FireworkRocketEntity.html
                const val DATA_ID_FIREWORKS_ITEM = "a"
                const val DATA_SHOT_AT_ANGLE = "c"
            }
        }

        object Display {
            // net/minecraft/world/entity/Display.html
            const val DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID = "q"
            const val DATA_POS_ROT_INTERPOLATION_DURATION_ID = "r"
            const val DATA_TRANSLATION_ID = "s"
            const val DATA_SCALE_ID = "t"
            const val DATA_LEFT_ROTATION_ID = "u"
            const val DATA_RIGHT_ROTATION_ID = "ay"
            const val DATA_BILLBOARD_RENDER_CONSTRAINTS_ID = "az"
            const val DATA_BRIGHTNESS_OVERRIDE_ID = "aA"
            const val DATA_VIEW_RANGE_ID = "aB"
            const val DATA_SHADOW_RADIUS_ID = "aC"
            const val DATA_SHADOW_STRENGTH_ID = "aD"
            const val DATA_WIDTH_ID = "aE"
            const val DATA_HEIGHT_ID = "aF"
            const val DATA_GLOW_COLOR_OVERRIDE_ID = "aG"

            object BlockDisplay {
                // net/minecraft/world/entity/Display$BlockDisplay.html
                const val DATA_BLOCK_STATE_ID = "p"
            }

            object ItemDisplay {
                // net/minecraft/world/entity/Display$ItemDisplay.html
                const val DATA_ITEM_STACK_ID = "q"
                const val DATA_ITEM_DISPLAY_ID = "r"
            }

        }

        object Decoration {

            object BlockAttackedEntity {
                // net/minecraft/world/entity/decoration/BlockAttachedEntity.html
                const val POS = "a"
            }

            object HangingEntity {
                // net/minecraft/world/entity/decoration/HangingEntity.html
                const val DIRECTION = "c"
            }

            object ItemFrame {
                // net/minecraft/world/entity/decoration/ItemFrame.html
                const val DATA_ITEM = "e"
                const val DATA_ROTATION = "f"
            }

            object Painting {
                // net/minecraft/world/entity/decoration/Painting.html
                const val DATA_PAINTING_VARIANT_ID = "g"
            }

        }

        object AreaEffectCloud {
            // net/minecraft/world/entity/AreaEffectCloud.html
            const val DATA_RADIUS = "e"
            const val DATA_PARTICLE = "g"
        }

        object FallingBlockEntity {
            // net/minecraft/world/entity/item/FallingBlockEntity.html
            const val BLOCK = "g"
        }

        object ItemEntity {
            // net/minecraft/world/entity/item/ItemEntity.html
            const val DATA_ITEM = "c"
        }

        object Interaction {
            // net/minecraft/world/entity/Interaction.html
            const val DATA_WIDTH_ID = "b"
            const val DATA_HEIGHT_ID = "c"
        }

        object OminousItemSpawner {
            // net/minecraft/world/entity/OminousItemSpawner.html
            const val DATA_ITEM = "f"
        }

    }

}