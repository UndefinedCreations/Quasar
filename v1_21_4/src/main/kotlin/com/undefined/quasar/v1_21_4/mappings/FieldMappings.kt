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

        object AreaEffectCloud {
            // net/minecraft/world/entity/AreaEffectCloud.html
            const val DATA_RADIUS = "e"
            const val DATA_PARTICLE = "g"
        }

    }

}