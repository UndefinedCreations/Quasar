package com.undefined.quasar.v1_21_4.impl.entity.decoration

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.decoration.ArmorStand
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.core.Rotations
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.level.Level
import kotlin.random.Random

class ArmorStand: ArmorStand, LivingEntity(EntityType.ARMORSTAND) {

    private val CLIENT_FLAG_SMALL = 1
    private val CLIENT_FLAG_SHOW_ARMS = 4
    private val CLIENT_FLAG_NO_BASEPLATE = 8

    private var DATA_CLIENT_FLAGS: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.decoration.ArmorStand::class.java,
            FieldMappings.Entity.LivingEntity.ArmorStand.DATA_CLIENT_FLAGS
        )
    private var DATA_HEAD_POSE: EntityDataAccessor<Rotations>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.decoration.ArmorStand::class.java,
            FieldMappings.Entity.LivingEntity.ArmorStand.DATA_HEAD_POSE
        )
    private var DATA_BODY_POSE: EntityDataAccessor<Rotations>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.decoration.ArmorStand::class.java,
            FieldMappings.Entity.LivingEntity.ArmorStand.DATA_BODY_POSE
        )
    private var DATA_LEFT_ARM_POSE: EntityDataAccessor<Rotations>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.decoration.ArmorStand::class.java,
            FieldMappings.Entity.LivingEntity.ArmorStand.DATA_LEFT_ARM_POSE
        )
    private var DATA_RIGHT_ARM_POSE: EntityDataAccessor<Rotations>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.decoration.ArmorStand::class.java,
            FieldMappings.Entity.LivingEntity.ArmorStand.DATA_RIGHT_ARM_POSE
        )
    private var DATA_LEFT_LEG_POSE: EntityDataAccessor<Rotations>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.decoration.ArmorStand::class.java,
            FieldMappings.Entity.LivingEntity.ArmorStand.DATA_LEFT_LEG_POSE
        )
    private var DATA_RIGHT_LEG_POSE: EntityDataAccessor<Rotations>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.decoration.ArmorStand::class.java,
            FieldMappings.Entity.LivingEntity.ArmorStand.DATA_RIGHT_LEG_POSE
        )

    override fun isSmall(): Boolean {
        val entity = entity ?: return false
        return ((entity.entityData.get(DATA_CLIENT_FLAGS) as Byte).toInt() and 1) != 0
    }

    override fun setSmall(small: Boolean) {
        val armorStand = entity ?: return
        armorStand.entityData.set(DATA_CLIENT_FLAGS,
            setBit(
                armorStand.entityData.get(DATA_CLIENT_FLAGS) as Byte,
                CLIENT_FLAG_SMALL,
                small
            )
        )
        sendEntityMetaData()
    }

    override fun isShowingBasePlate(): Boolean {
        val entity = entity ?: return false
        return ((entity.entityData.get(DATA_CLIENT_FLAGS) as Byte).toInt() and 16) != 0;
    }

    override fun setShowingBasePlate(showing: Boolean) {
        val armorStand = entity ?: return
        armorStand.entityData.set(DATA_CLIENT_FLAGS,
            setBit(
                armorStand.entityData.get(DATA_CLIENT_FLAGS) as Byte,
                CLIENT_FLAG_NO_BASEPLATE,
                showing
            )
        )
        sendEntityMetaData()
    }

    override fun isShowingArms(): Boolean {
        val entity = entity ?: return false
        return ((entity.entityData.get(DATA_CLIENT_FLAGS) as Byte).toInt() and 4) != 0;
    }

    override fun setShowingArms(showing: Boolean) {
        val armorStand = entity ?: return
        armorStand.entityData.set(DATA_CLIENT_FLAGS,
            setBit(
                armorStand.entityData.get(DATA_CLIENT_FLAGS) as Byte,
                CLIENT_FLAG_SHOW_ARMS,
                showing
            )
        )
        sendEntityMetaData()
    }

    override fun getHeadRotation(): ArmorStand.Rotations = getEntityDataValue(DATA_HEAD_POSE)?.let { nmsRotationToQuauas(it) } ?: ArmorStand.Rotations.EMPTY()

    override fun setHeadRotation(rotations: ArmorStand.Rotations) = setEntityDataAccessor(DATA_HEAD_POSE, rotations.toNMSRotations())

    override fun getBodyRotation(): ArmorStand.Rotations = getEntityDataValue(DATA_BODY_POSE)?.let { nmsRotationToQuauas(it) } ?: ArmorStand.Rotations.EMPTY()

    override fun setBodyRotation(rotations: ArmorStand.Rotations) = setEntityDataAccessor(DATA_BODY_POSE, rotations.toNMSRotations())

    override fun getLeftArmRotation(): ArmorStand.Rotations = getEntityDataValue(DATA_LEFT_ARM_POSE)?.let { nmsRotationToQuauas(it) } ?: ArmorStand.Rotations.EMPTY()

    override fun setLeftArmRotation(rotations: ArmorStand.Rotations) = setEntityDataAccessor(DATA_LEFT_ARM_POSE, rotations.toNMSRotations())


    override fun getRightArmRotation(): ArmorStand.Rotations = getEntityDataValue(DATA_RIGHT_ARM_POSE)?.let { nmsRotationToQuauas(it) } ?: ArmorStand.Rotations.EMPTY()

    override fun setRightArmRotation(rotations: ArmorStand.Rotations) = setEntityDataAccessor(DATA_RIGHT_ARM_POSE, rotations.toNMSRotations())

    override fun getLeftLegRotation(): ArmorStand.Rotations = getEntityDataValue(DATA_LEFT_LEG_POSE)?.let { nmsRotationToQuauas(it) } ?: ArmorStand.Rotations.EMPTY()

    override fun setLeftLegRotation(rotations: ArmorStand.Rotations) = setEntityDataAccessor(DATA_LEFT_LEG_POSE, rotations.toNMSRotations())

    override fun getRightLegRotation(): ArmorStand.Rotations = getEntityDataValue(DATA_RIGHT_LEG_POSE)?.let { nmsRotationToQuauas(it) } ?: ArmorStand.Rotations.EMPTY()

    override fun setRightLegRotation(rotations: ArmorStand.Rotations) = setEntityDataAccessor(DATA_RIGHT_LEG_POSE, rotations.toNMSRotations())

    override fun getEntityData(): JsonObject {
        val json = super.getEntityData()
        val armorStandJson = JsonObject()

        armorStandJson.addProperty("small", isSmall())
        armorStandJson.addProperty("basePlate", isShowingBasePlate())
        armorStandJson.addProperty("arms", isShowingArms())

        armorStandJson.add("headRotation", getHeadRotation().json())
        armorStandJson.add("bodyRotation", getBodyRotation().json())
        armorStandJson.add("leftArmRotation", getLeftArmRotation().json())
        armorStandJson.add("rightArmRotation", getRightArmRotation().json())
        armorStandJson.add("leftLegRotation", getLeftLegRotation().json())
        armorStandJson.add("rightLegRotation", getRightLegRotation().json())

        json.add("armorStandData", armorStandJson)
        return json
    }
    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)

        val armorStandJson = jsonObject["armorStandData"].asJsonObject

        setSmall(armorStandJson["small"].asBoolean)
        setShowingBasePlate(armorStandJson["basePlate"].asBoolean)
        setShowingArms(armorStandJson["arms"].asBoolean)

        setHeadRotation(ArmorStand.Rotations(armorStandJson["headRotation"].asJsonArray))
        setBodyRotation(ArmorStand.Rotations(armorStandJson["bodyRotation"].asJsonArray))
        setLeftArmRotation(ArmorStand.Rotations(armorStandJson["leftArmRotation"].asJsonArray))
        setRightArmRotation(ArmorStand.Rotations(armorStandJson["rightArmRotation"].asJsonArray))
        setLeftLegRotation(ArmorStand.Rotations(armorStandJson["leftLegRotation"].asJsonArray))
        setRightLegRotation(ArmorStand.Rotations(armorStandJson["rightLegRotation"].asJsonArray))
    }
    override fun setDefaultValues() {
        super.setDefaultValues()

        setSmall(false)
        setShowingBasePlate(true)
        setShowingArms(false)

        setHeadRotation(ArmorStand.Rotations(0.0f, 0.0f, 0.0f))
        setBodyRotation(ArmorStand.Rotations(0.0f, 0.0f, 0.0f))
        setLeftArmRotation(ArmorStand.Rotations(-10.0f, 0.0f, -10.0f))
        setRightArmRotation(ArmorStand.Rotations(-15.0f, 0.0f, 10.0f))
        setLeftLegRotation(ArmorStand.Rotations(-1.0f, 0.0f, -1.0f))
        setRightLegRotation(ArmorStand.Rotations(1.0f, 0.0f, 1.0f))
    }
    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setSmall(true)
                getTestMessage(this@ArmorStand::class, "Set small", isSmall())
            },
            {
                setSmall(false)
                getTestMessage(this@ArmorStand::class, "Set small", isSmall())
            },
            {
                setShowingBasePlate(false)
                getTestMessage(this@ArmorStand::class, "Set base plate", isShowingBasePlate())
            },
            {
                setShowingBasePlate(true)
                getTestMessage(this@ArmorStand::class, "Set base plate", isShowingBasePlate())
            },
            {
                setShowingArms(true)
                getTestMessage(this@ArmorStand::class, "Set arms", isShowingArms())
            },
            {
                setShowingArms(false)
                getTestMessage(this@ArmorStand::class, "Set arms", isShowingArms())
            },
            {
                setHeadRotation(
                    Random.nextDouble(-180.0, 180.0).toFloat(),
                    Random.nextDouble(-180.0, 180.0).toFloat(),
                    Random.nextDouble(-180.0, 180.0).toFloat()
                )
                getTestMessage(this@ArmorStand::class,
                    "Set head rotation",
                    getHeadRotation().firstRotation,
                    getHeadRotation().secondRotation,
                    getHeadRotation().thirdRotation
                )
            },
            {
                setBodyRotation(
                    Random.nextDouble(-180.0, 180.0).toFloat(),
                    Random.nextDouble(-180.0, 180.0).toFloat(),
                    Random.nextDouble(-180.0, 180.0).toFloat()
                )
                getTestMessage(this@ArmorStand::class,
                    "Set body rotation",
                    getBodyRotation().firstRotation,
                    getBodyRotation().secondRotation,
                    getBodyRotation().thirdRotation
                )
            },
            {
                setShowingArms(true)
                setLeftArmRotation(
                    Random.nextDouble(-180.0, 180.0).toFloat(),
                    Random.nextDouble(-180.0, 180.0).toFloat(),
                    Random.nextDouble(-180.0, 180.0).toFloat()
                )
                getTestMessage(this@ArmorStand::class,
                    "Set left arm rotation",
                    getLeftArmRotation().firstRotation,
                    getLeftArmRotation().secondRotation,
                    getLeftArmRotation().thirdRotation
                )
            }
            ,
            {
                setRightArmRotation(
                    Random.nextDouble(-180.0, 180.0).toFloat(),
                    Random.nextDouble(-180.0, 180.0).toFloat(),
                    Random.nextDouble(-180.0, 180.0).toFloat()
                )
                getTestMessage(this@ArmorStand::class,
                    "Set right arm rotation",
                    getRightArmRotation().firstRotation,
                    getRightArmRotation().secondRotation,
                    getRightArmRotation().thirdRotation
                )
            },
            {

                setLeftLegRotation(
                    Random.nextDouble(-180.0, 180.0).toFloat(),
                    Random.nextDouble(-180.0, 180.0).toFloat(),
                    Random.nextDouble(-180.0, 180.0).toFloat()
                )
                getTestMessage(this@ArmorStand::class,
                    "Set left leg rotation",
                    getLeftLegRotation().firstRotation,
                    getLeftLegRotation().secondRotation,
                    getLeftLegRotation().thirdRotation
                )
            },
            {
                setRightLegRotation(
                    Random.nextDouble(-180.0, 180.0).toFloat(),
                    Random.nextDouble(-180.0, 180.0).toFloat(),
                    Random.nextDouble(-180.0, 180.0).toFloat()
                )
                getTestMessage(this@ArmorStand::class,
                    "Set right leg rotation",
                    getRightLegRotation().firstRotation,
                    getRightLegRotation().secondRotation,
                    getRightLegRotation().thirdRotation
                )
            },
            {
                setHeadRotation(
                    0f,
                    0f,
                    0f
                )
                getTestMessage(this@ArmorStand::class,
                    "Set head rotation",
                    getHeadRotation().firstRotation,
                    getHeadRotation().secondRotation,
                    getHeadRotation().thirdRotation
                )
            },
            {
                setBodyRotation(
                    0f,
                    0f,
                    0f
                )
                getTestMessage(this@ArmorStand::class,
                    "Set body rotation",
                    getBodyRotation().firstRotation,
                    getBodyRotation().secondRotation,
                    getBodyRotation().thirdRotation
                )
            },
            {
                setLeftArmRotation(
                    0f,
                    0f,
                    0f
                )
                getTestMessage(this@ArmorStand::class,
                    "Set left arm rotation",
                    getLeftArmRotation().firstRotation,
                    getLeftArmRotation().secondRotation,
                    getLeftArmRotation().thirdRotation
                )
            }
            ,
            {
                setRightArmRotation(
                    0f,
                    0f,
                    0f
                )
                getTestMessage(this@ArmorStand::class,
                    "Set right arm rotation",
                    getRightArmRotation().firstRotation,
                    getRightArmRotation().secondRotation,
                    getRightArmRotation().thirdRotation
                )
            },
            {
                setLeftLegRotation(
                    0f,
                    0f,
                    0f
                )
                getTestMessage(this@ArmorStand::class,
                    "Set left leg rotation",
                    getLeftLegRotation().firstRotation,
                    getLeftLegRotation().secondRotation,
                    getLeftLegRotation().thirdRotation
                )
            },
            {
                setRightLegRotation(
                    0f,
                    0f,
                    0f
                )
                getTestMessage(this@ArmorStand::class,
                    "Set right leg rotation",
                    getRightLegRotation().firstRotation,
                    getRightLegRotation().secondRotation,
                    getRightLegRotation().thirdRotation
                )
            }
        )) }

    fun nmsRotationToQuauas(rotations: Rotations): ArmorStand.Rotations = ArmorStand.Rotations(rotations.x, rotations.y, rotations.z)

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.decoration.ArmorStand(net.minecraft.world.entity.EntityType.ARMOR_STAND, level)

    private fun ArmorStand.Rotations.toNMSRotations(): Rotations =
        Rotations(firstRotation, secondRotation, thirdRotation)

    private fun setBit(b0: Byte, i: Int, flag: Boolean): Byte {
        var b = b0
        b = if (flag) {
            (b.toInt() or i).toByte()
        } else {
            (b.toInt() and i.inv()).toByte()
        }
        return b
    }

}