package com.undefined.quasar.v1_21_4.impl.entity.decoration

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.decoration.ArmorStand
import com.undefined.quasar.util.getPrivateField
import com.undefined.quasar.util.repeat
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.core.Rotations
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.level.Level
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import kotlin.random.Random

class ArmorStand: ArmorStand, LivingEntity(EntityType.ARMORSTAND) {

    private val CLIENT_FLAG_SMALL = 1
    private val CLIENT_FLAG_SHOW_ARMS = 4
    private val CLIENT_FLAG_NO_BASEPLATE = 8

    private var DATA_CLIENT_FLAGS: EntityDataAccessor<Byte>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(
                net.minecraft.world.entity.decoration.ArmorStand::class.java,
                FieldMappings.Entity.LivingEntity.ArmorStand.DATA_CLIENT_FLAGS
            )
            return field
        }
    private var DATA_HEAD_POSE: EntityDataAccessor<Rotations>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(
                net.minecraft.world.entity.decoration.ArmorStand::class.java,
                FieldMappings.Entity.LivingEntity.ArmorStand.DATA_HEAD_POSE
            )
            return field
        }
    private var DATA_BODY_POSE: EntityDataAccessor<Rotations>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(
                net.minecraft.world.entity.decoration.ArmorStand::class.java,
                FieldMappings.Entity.LivingEntity.ArmorStand.DATA_BODY_POSE
            )
            return field
        }
    private var DATA_LEFT_ARM_POSE: EntityDataAccessor<Rotations>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(
                net.minecraft.world.entity.decoration.ArmorStand::class.java,
                FieldMappings.Entity.LivingEntity.ArmorStand.DATA_LEFT_ARM_POSE
            )
            return field
        }
    private var DATA_RIGHT_ARM_POSE: EntityDataAccessor<Rotations>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(
                net.minecraft.world.entity.decoration.ArmorStand::class.java,
                FieldMappings.Entity.LivingEntity.ArmorStand.DATA_RIGHT_ARM_POSE
            )
            return field
        }
    private var DATA_LEFT_LEG_POSE: EntityDataAccessor<Rotations>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(
                net.minecraft.world.entity.decoration.ArmorStand::class.java,
                FieldMappings.Entity.LivingEntity.ArmorStand.DATA_LEFT_LEG_POSE
            )
            return field
        }
    private var DATA_RIGHT_LEG_POSE: EntityDataAccessor<Rotations>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(
                net.minecraft.world.entity.decoration.ArmorStand::class.java,
                FieldMappings.Entity.LivingEntity.ArmorStand.DATA_RIGHT_LEG_POSE
            )
            return field
        }

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
    private fun ArmorStand.Rotations.toNMSRotations(): Rotations =
        Rotations(firstRot, secondRot, thridRot)
    @Suppress("NAME_SHADOWING")
    private fun setBit(b0: Byte, i: Int, flag: Boolean): Byte {
        var b0 = b0
        b0 = if (flag) {
            (b0.toInt() or i).toByte()
        } else {
            (b0.toInt() and i.inv()).toByte()
        }
        return b0
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

    override fun runTest(logger: Player, delayTime: Int, testStage: (Exception?) -> Unit, done: (Unit) -> Unit): Int {
        super.runTest(logger, delayTime, { e ->
            trycatch({
                if (e != null) return@trycatch
                var time = 0

                repeat(20, delayTime) {
                    when (time) {
                        0 -> {
                            setSmall(true)
                            logger.sendMessage("${ChatColor.GRAY} ArmorStand | Set small {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}true${ChatColor.GRAY}]")
                        }

                        1 -> {
                            setSmall(false)
                            logger.sendMessage("${ChatColor.GRAY} ArmorStand | Set small {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}false${ChatColor.GRAY}]")
                        }

                        2 -> {
                            setShowingBasePlate(false)
                            logger.sendMessage("${ChatColor.GRAY} ArmorStand | Set base plate {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}false${ChatColor.GRAY}]")
                        }

                        3 -> {
                            setShowingBasePlate(true)
                            logger.sendMessage("${ChatColor.GRAY} ArmorStand | Set base plate {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}true${ChatColor.GRAY}]")
                        }

                        4 -> {
                            setShowingArms(true)
                            logger.sendMessage("${ChatColor.GRAY} ArmorStand | Set arms {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}true${ChatColor.GRAY}]")
                        }

                        6 -> {
                            setShowingArms(false)
                            logger.sendMessage("${ChatColor.GRAY} ArmorStand | Set arms {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}false${ChatColor.GRAY}]")
                        }

                        7 -> {
                            setHeadRotation(
                                Random.nextDouble(-180.0, 180.0).toFloat(),
                                Random.nextDouble(-180.0, 180.0).toFloat(),
                                Random.nextDouble(-180.0, 180.0).toFloat()
                            )
                            logger.sendMessage(
                                "${ChatColor.GRAY} ArmorStand | Set head rotation " +
                                        "{${ChatColor.GREEN}Success!${ChatColor.GRAY}} " +
                                        "[${ChatColor.AQUA}${getHeadRotation().firstRot}, ${getHeadRotation().secondRot}, ${getHeadRotation().thridRot}${ChatColor.GRAY}]"
                            )
                        }

                        8 -> {
                            setBodyRotation(
                                Random.nextDouble(-180.0, 180.0).toFloat(),
                                Random.nextDouble(-180.0, 180.0).toFloat(),
                                Random.nextDouble(-180.0, 180.0).toFloat()
                            )
                            logger.sendMessage(
                                "${ChatColor.GRAY} ArmorStand | Set body rotation " +
                                        "{${ChatColor.GREEN}Success!${ChatColor.GRAY}} " +
                                        "[${ChatColor.AQUA}${getBodyRotation().firstRot}, ${getBodyRotation().secondRot}, ${getBodyRotation().thridRot}${ChatColor.GRAY}]"
                            )
                        }

                        9 -> {
                            setShowingArms(true)
                            setLeftArmRotation(
                                Random.nextDouble(-180.0, 180.0).toFloat(),
                                Random.nextDouble(-180.0, 180.0).toFloat(),
                                Random.nextDouble(-180.0, 180.0).toFloat()
                            )
                            logger.sendMessage(
                                "${ChatColor.GRAY} ArmorStand | Set left arm rotation " +
                                        "{${ChatColor.GREEN}Success!${ChatColor.GRAY}} " +
                                        "[${ChatColor.AQUA}${getLeftArmRotation().firstRot}, ${getLeftArmRotation().secondRot}, ${getLeftArmRotation().thridRot}${ChatColor.GRAY}]"
                            )
                        }

                        10 -> {
                            setRightArmRotation(
                                Random.nextDouble(-180.0, 180.0).toFloat(),
                                Random.nextDouble(-180.0, 180.0).toFloat(),
                                Random.nextDouble(-180.0, 180.0).toFloat()
                            )
                            logger.sendMessage(
                                "${ChatColor.GRAY} ArmorStand | Set right arm rotation " +
                                        "{${ChatColor.GREEN}Success!${ChatColor.GRAY}} " +
                                        "[${ChatColor.AQUA}${getRightArmRotation().firstRot}, ${getRightArmRotation().secondRot}, ${getRightArmRotation().thridRot}${ChatColor.GRAY}]"
                            )
                        }

                        11 -> {
                            setLeftLegRotation(
                                Random.nextDouble(-180.0, 180.0).toFloat(),
                                Random.nextDouble(-180.0, 180.0).toFloat(),
                                Random.nextDouble(-180.0, 180.0).toFloat()
                            )
                            logger.sendMessage(
                                "${ChatColor.GRAY} ArmorStand | Set left leg rotation " +
                                        "{${ChatColor.GREEN}Success!${ChatColor.GRAY}} " +
                                        "[${ChatColor.AQUA}${getLeftLegRotation().firstRot}, ${getLeftLegRotation().secondRot}, ${getLeftLegRotation().thridRot}${ChatColor.GRAY}]"
                            )
                        }

                        12 -> {
                            setRightLegRotation(
                                Random.nextDouble(-180.0, 180.0).toFloat(),
                                Random.nextDouble(-180.0, 180.0).toFloat(),
                                Random.nextDouble(-180.0, 180.0).toFloat()
                            )
                            logger.sendMessage(
                                "${ChatColor.GRAY} ArmorStand | Set right leg rotation " +
                                        "{${ChatColor.GREEN}Success!${ChatColor.GRAY}} " +
                                        "[${ChatColor.AQUA}${getRightLegRotation().firstRot}, ${getRightLegRotation().secondRot}, ${getRightLegRotation().thridRot}${ChatColor.GRAY}]"
                            )
                        }

                        13 -> {
                            setHeadRotation(
                                0.0f,
                                0.0f,
                                0.0f
                            )
                            logger.sendMessage(
                                "${ChatColor.GRAY} ArmorStand | Set head rotation " +
                                        "{${ChatColor.GREEN}Success!${ChatColor.GRAY}} " +
                                        "[${ChatColor.AQUA}${getHeadRotation().firstRot}, ${getHeadRotation().secondRot}, ${getHeadRotation().thridRot}${ChatColor.GRAY}]"
                            )
                        }

                        14 -> {
                            setBodyRotation(
                                0.0f,
                                0.0f,
                                0.0f
                            )
                            logger.sendMessage(
                                "${ChatColor.GRAY} ArmorStand | Set body rotation " +
                                        "{${ChatColor.GREEN}Success!${ChatColor.GRAY}} " +
                                        "[${ChatColor.AQUA}${getBodyRotation().firstRot}, ${getBodyRotation().secondRot}, ${getBodyRotation().thridRot}${ChatColor.GRAY}]"
                            )
                        }

                        15 -> {
                            setLeftArmRotation(
                                0.0f,
                                0.0f,
                                0.0f
                            )
                            logger.sendMessage(
                                "${ChatColor.GRAY} ArmorStand | Set left arm rotation " +
                                        "{${ChatColor.GREEN}Success!${ChatColor.GRAY}} " +
                                        "[${ChatColor.AQUA}${getLeftArmRotation().firstRot}, ${getLeftArmRotation().secondRot}, ${getLeftArmRotation().thridRot}${ChatColor.GRAY}]"
                            )
                        }

                        16 -> {
                            setRightArmRotation(
                                0.0f,
                                0.0f,
                                0.0f
                            )
                            logger.sendMessage(
                                "${ChatColor.GRAY} ArmorStand | Set right arm rotation " +
                                        "{${ChatColor.GREEN}Success!${ChatColor.GRAY}} " +
                                        "[${ChatColor.AQUA}${getRightArmRotation().firstRot}, ${getRightArmRotation().secondRot}, ${getRightArmRotation().thridRot}${ChatColor.GRAY}]"
                            )
                        }

                        17 -> {
                            setLeftLegRotation(
                                0.0f,
                                0.0f,
                                0.0f
                            )
                            logger.sendMessage(
                                "${ChatColor.GRAY} ArmorStand | Set left leg rotation " +
                                        "{${ChatColor.GREEN}Success!${ChatColor.GRAY}} " +
                                        "[${ChatColor.AQUA}${getLeftLegRotation().firstRot}, ${getLeftLegRotation().secondRot}, ${getLeftLegRotation().thridRot}${ChatColor.GRAY}]"
                            )
                        }

                        18 -> {
                            setRightLegRotation(
                                0.0f,
                                0.0f,
                                0.0f
                            )
                            logger.sendMessage(
                                "${ChatColor.GRAY} ArmorStand | Set right leg rotation " +
                                        "{${ChatColor.GREEN}Success!${ChatColor.GRAY}} " +
                                        "[${ChatColor.AQUA}${getRightLegRotation().firstRot}, ${getRightLegRotation().secondRot}, ${getRightLegRotation().thridRot}${ChatColor.GRAY}]"
                            )
                        }

                        19 -> {
                            done(Unit)
                        }
                    }
                    time++
                }
            }, testStage)
        }, done)
        return 3
    }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.decoration.ArmorStand(net.minecraft.world.entity.EntityType.ARMOR_STAND, level)
}