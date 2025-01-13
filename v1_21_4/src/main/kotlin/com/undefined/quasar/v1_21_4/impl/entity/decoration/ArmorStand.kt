package com.undefined.quasar.v1_21_4.impl.entity.decoration

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.decoration.ArmorStand
import com.undefined.quasar.util.getPrivateField
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

    private var small = false
    private var basePlate = false
    private var arms = false

    private var headRotation = ArmorStand.Rotations(0.0f, 0.0f, 0.0f)
    private var bodyRotation = ArmorStand.Rotations(0.0f, 0.0f, 0.0f)
    private var leftArmRotation = ArmorStand.Rotations(-10.0f, 0.0f, -10.0f)
    private var rightArmRotation = ArmorStand.Rotations(-15.0f, 0.0f, 10.0f)
    private var leftLegRotation = ArmorStand.Rotations(-1.0f, 0.0f, -1.0f)
    private var rightLegRotation = ArmorStand.Rotations(1.0f, 0.0f, 1.0f)

    override fun isSmall(): Boolean = small

    override fun setSmall(small: Boolean) {
        val armorStand = entity ?: return
        this.small = small
        armorStand.entityData.set(DATA_CLIENT_FLAGS,
            setBit(
                armorStand.entityData.get(DATA_CLIENT_FLAGS) as Byte,
                CLIENT_FLAG_SMALL,
                small
            )
        )
        sendEntityMetaData()
    }

    override fun isShowingBasePlate(): Boolean = basePlate

    override fun setShowingBasePlate(showing: Boolean) {
        val armorStand = entity ?: return
        this.basePlate = showing
        armorStand.entityData.set(DATA_CLIENT_FLAGS,
            setBit(
                armorStand.entityData.get(DATA_CLIENT_FLAGS) as Byte,
                CLIENT_FLAG_NO_BASEPLATE,
                showing
            )
        )
        sendEntityMetaData()
    }

    override fun isShowingArms(): Boolean = arms

    override fun setShowingArms(showing: Boolean) {
        val armorStand = entity ?: return
        this.arms = showing
        armorStand.entityData.set(DATA_CLIENT_FLAGS,
            setBit(
                armorStand.entityData.get(DATA_CLIENT_FLAGS) as Byte,
                CLIENT_FLAG_SHOW_ARMS,
                showing
            )
        )
        sendEntityMetaData()
    }

    override fun getHeadRotation(): ArmorStand.Rotations = headRotation

    override fun setHeadRotation(rotations: ArmorStand.Rotations) {
        val entity = entity ?: return
        headRotation.set(rotations)
        entity.entityData.set(DATA_HEAD_POSE, rotations.toNMSRotations())
        sendEntityMetaData()
    }

    override fun getBodyRotation(): ArmorStand.Rotations = bodyRotation

    override fun setBodyRotation(rotations: ArmorStand.Rotations) {
        val entity = entity ?: return
        bodyRotation.set(rotations)
        entity.entityData.set(DATA_BODY_POSE, rotations.toNMSRotations())
        sendEntityMetaData()
    }

    override fun getLeftArmRotation(): ArmorStand.Rotations = leftArmRotation

    override fun setLeftArmRotation(rotations: ArmorStand.Rotations) {
        val entity = entity ?: return
        leftArmRotation.set(rotations)
        entity.entityData.set(DATA_LEFT_ARM_POSE, rotations.toNMSRotations())
        sendEntityMetaData()
    }

    override fun getRightArmRotation(): ArmorStand.Rotations = rightArmRotation

    override fun setRightArmRotation(rotations: ArmorStand.Rotations) {
        val entity = entity ?: return
        rightArmRotation.set(rotations)
        entity.entityData.set(DATA_RIGHT_ARM_POSE, rotations.toNMSRotations())
        sendEntityMetaData()
    }

    override fun getLeftLegRotation(): ArmorStand.Rotations = leftLegRotation

    override fun setLeftLegRotation(rotations: ArmorStand.Rotations) {
        val entity = entity ?: return
        leftLegRotation.set(rotations)
        entity.entityData.set(DATA_LEFT_LEG_POSE, rotations.toNMSRotations())
        sendEntityMetaData()
    }

    override fun getRightLegRotation(): ArmorStand.Rotations = rightLegRotation

    override fun setRightLegRotation(rotations: ArmorStand.Rotations) {
        val entity = entity ?: return
        rightLegRotation.set(rotations)
        entity.entityData.set(DATA_RIGHT_LEG_POSE, rotations.toNMSRotations())
        sendEntityMetaData()
    }

    override fun getEntityData(): JsonObject {
        val json = super.getEntityData()
        val armorStandJson = JsonObject()

        armorStandJson.addProperty("small", small)
        armorStandJson.addProperty("basePlate", basePlate)
        armorStandJson.addProperty("arms", arms)

        armorStandJson.add("headRotation", headRotation.json())
        armorStandJson.add("bodyRotation", bodyRotation.json())
        armorStandJson.add("leftArmRotation", leftArmRotation.json())
        armorStandJson.add("rightArmRotation", rightArmRotation.json())
        armorStandJson.add("leftLegRotation", leftLegRotation.json())
        armorStandJson.add("rightLegRotation", rightLegRotation.json())

        json.add("armorStandData", armorStandJson)
        return json
    }
    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)

        val armorStandJson = jsonObject["armorStandData"].asJsonObject

        small = armorStandJson["small"].asBoolean
        basePlate = armorStandJson["basePlate"].asBoolean
        arms = armorStandJson["arms"].asBoolean

        headRotation = ArmorStand.Rotations(armorStandJson["headRotation"].asJsonArray)
        headRotation = ArmorStand.Rotations(armorStandJson["bodyRotation"].asJsonArray)
        headRotation = ArmorStand.Rotations(armorStandJson["leftArmRotation"].asJsonArray)
        headRotation = ArmorStand.Rotations(armorStandJson["rightArmRotation"].asJsonArray)
        headRotation = ArmorStand.Rotations(armorStandJson["leftLegRotation"].asJsonArray)
        headRotation = ArmorStand.Rotations(armorStandJson["rightLegRotation"].asJsonArray)
    }
    override fun updateEntity() {
        super.updateEntity()

        setSmall(small)
        setShowingBasePlate(basePlate)
        setShowingArms(arms)

        setHeadRotation(headRotation)
        setBodyRotation(bodyRotation)
        setLeftArmRotation(leftArmRotation)
        setRightArmRotation(rightArmRotation)
        setLeftLegRotation(leftLegRotation)
        setRightLegRotation(rightLegRotation)
    }
    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setSmall(true)
                getTestMessage(this@ArmorStand::class, "Set small", true)
            },
            {
                setSmall(false)
                getTestMessage(this@ArmorStand::class, "Set small", false)
            },
            {
                setShowingBasePlate(false)
                getTestMessage(this@ArmorStand::class, "Set base plate", false)
            },
            {
                setShowingBasePlate(true)
                getTestMessage(this@ArmorStand::class, "Set base plate", true)
            },
            {
                setShowingArms(true)
                getTestMessage(this@ArmorStand::class, "Set arms", true)
            },
            {
                setShowingArms(false)
                getTestMessage(this@ArmorStand::class, "Set arms", false)
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