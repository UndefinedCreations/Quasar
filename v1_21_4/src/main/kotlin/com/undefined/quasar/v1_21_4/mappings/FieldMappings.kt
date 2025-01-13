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
        }

        object Vehicle {
            object Minecart {
                // net/minecraft/world/entity/vehicle/AbstractMinecart.html
                const val DATA_ID_DISPLAY_BLOCK = "c"
                const val DATA_ID_DISPLAY_OFFSET = "d"
                const val DATA_ID_CUSTOM_DISPLAY = "h"
            }
        }

        object LivingEntity {
            object Mob {
                object Animal {
                    object Armadillo {
                        // net/minecraft/world/entity/animal/armadillo/Armadillo.html
                        const val ARMADILLO_STATE = "ch"
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
                }
                object Monster {
                    object Blaze {
                        // net/minecraft/world/entity/monster/Blaze.html
                        const val DATA_FLAGS_ID = "c"
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
            object ARROW {
                // net/minecraft/world/entity/projectile/Arrow.html
                const val ID_EFFECT_COLOR = "f"
            }
        }

        object Display {

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

        }

        object AreaEffectCloud {
            // net/minecraft/world/entity/AreaEffectCloud.html
            const val DATA_RADIUS = "e"
            const val DATA_PARTICLE = "g"
        }

    }

}