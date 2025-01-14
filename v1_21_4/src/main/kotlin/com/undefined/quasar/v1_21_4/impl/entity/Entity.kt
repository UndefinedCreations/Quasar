package com.undefined.quasar.v1_21_4.impl.entity

import com.google.common.collect.ImmutableList
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.util.getPrivateMethod
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import com.undefined.quasar.v1_21_4.mappings.MethodMappings
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.game.*
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Pose
import net.minecraft.world.entity.PositionMoveRotation
import net.minecraft.world.scores.Team
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_21_R3.util.CraftChatMessage
import org.bukkit.entity.Player
import java.util.*
import kotlin.math.floor

abstract class Entity(
    override val entityType: EntityType
) : AbstractEntity(entityType), Entity {

    private var DATA_CUSTOM_NAME: EntityDataAccessor<Optional<Component>>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Entity::class.java,
            FieldMappings.Entity.Base.DATA_CUSTOM_NAME
        )
    private var DATA_CUSTOM_NAME_VISIBLE: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Entity::class.java,
            FieldMappings.Entity.Base.DATA_CUSTOM_NAME_VISIBLE
        )
    private var DATA_SILENT: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Entity::class.java,
            FieldMappings.Entity.Base.DATA_SILENT
        )
    private var DATA_NO_GRAVITY: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Entity::class.java,
            FieldMappings.Entity.Base.DATA_NO_GRAVITY
        )
    private var DATA_TICKS_FROZEN: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Entity::class.java,
            FieldMappings.Entity.Base.DATA_TICKS_FROZEN
        )
    var DATA_POSE: EntityDataAccessor<Pose>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Entity::class.java,
            FieldMappings.Entity.Base.DATA_POSE
        )

    private var FLAG_ONFIRE = 0
    private var FLAG_INVISIBLE = 5
    private var FLAG_GLOWING = 6

    private var customName: String? = null
    private var isCustomNameVisible = false
    private var fire = false
    private var freezing = false
    private var visible = true
    private var gravity = false
    private var silent = false
    private var collidable = false
    private var glowing = false
    private var glowingColor: ChatColor = ChatColor.WHITE
    private var passengers: MutableList<Entity> = mutableListOf()

    override fun setCustomName(name: String?) =
        setEntityDataAccessor(DATA_CUSTOM_NAME, Optional.ofNullable(CraftChatMessage.fromStringOrNull(name))) {
            customName = name
        }

    override fun getCustomName(): String? = customName

    override fun isCustomNameVisible(): Boolean = isCustomNameVisible

    override fun setCustomNameVisibility(visible: Boolean) =
        setEntityDataAccessor(DATA_CUSTOM_NAME_VISIBLE, visible) {
            if (!visible) setCustomName(null)
            isCustomNameVisible = visible
        }


    override fun teleport(location: Location) {
        val entity = entity ?: return

        entity.setPos(location.x, location.y, location.z)
        net.minecraft.world.entity.Entity::class.java.getPrivateMethod(MethodMappings.Entity.Base.SET_ROT, Float::class.java, Float::class.java)
            .invoke(entity, location.yaw, location.pitch)

        getLocation().x = location.x
        getLocation().y = location.y
        getLocation().z = location.z
        getLocation().yaw = location.yaw
        getLocation().pitch = location.pitch

        sendPackets(ClientboundTeleportEntityPacket(entity.id, PositionMoveRotation.of(entity), setOf(), false))
    }

    override fun moveTo(location: Location) {
        val entity = entity ?: return

        if (getLocation().distance(location) > 8) return

        val deltaX = toDeltaValue(getLocation().x, location.x)
        val deltaY = toDeltaValue(getLocation().y, location.y)
        val deltaZ = toDeltaValue(getLocation().z, location.z)
        val isOnGround = location.clone().subtract(0.0, 1.0, 0.0).block.type.isSolid

        val headRotationYaw = toRotationValue(location.yaw)
        val headRotationPitch = toRotationValue(location.pitch)

        sendPackets(
            ClientboundMoveEntityPacket.PosRot(
            entity.id,
            deltaX,
            deltaY,
            deltaZ,
            headRotationYaw,
            headRotationPitch,
            isOnGround
            ), ClientboundRotateHeadPacket(
                entity,
                headRotationYaw
            )
        )
        setLocation(location)
    }

    override fun setVisualFire(fire: Boolean) =
        setSharedFlag(FLAG_ONFIRE, fire) {
            this.fire = fire
        }

    override fun isVisualFire(): Boolean = fire

    override fun setVisualFreezing(freezing: Boolean) =
        setEntityDataAccessor(DATA_TICKS_FROZEN, if(freezing) Int.MAX_VALUE else -1) {
            this.freezing = freezing
        }

    override fun isFreezing(): Boolean = freezing

    override fun setVisible(visible: Boolean) =
        setSharedFlag(FLAG_INVISIBLE, !visible) {
            this.visible = visible
        }

    override fun isVisible(): Boolean = visible

    override fun setCollidable(collidable: Boolean) {
        entity ?: return
        this.collidable = collidable
        entityTeam.collisionRule = if (this.collidable) Team.CollisionRule.ALWAYS else Team.CollisionRule.NEVER
        sendPackets(
            ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(entityTeam, true)
        )
    }

    override fun isCollidable(): Boolean = collidable

    override fun setRotation(yaw: Float, pitch: Float) {
        val entity = entity ?: return

        net.minecraft.world.entity.Entity::class.java.getPrivateMethod(MethodMappings.Entity.Base.SET_ROT, Float::class.java, Float::class.java)
            .invoke(entity, yaw, pitch)

        getLocation().yaw = yaw
        getLocation().pitch = pitch

        sendPackets(ClientboundTeleportEntityPacket(entity.id, PositionMoveRotation.of(entity), setOf(), false))
    }

    override fun addPassenger(passenger: Entity) {
        val entity = this.entity ?: return
        val passengerEntity = (passenger as AbstractEntity).entity ?: return

        if (entity.passengers.isEmpty()) {
            entity.passengers = ImmutableList.of(passengerEntity)
            passengers.add(passenger)
        } else {
            val list: MutableList<net.minecraft.world.entity.Entity> = mutableListOf(passengerEntity)

            if (!entity.level().isClientSide && passengerEntity is Player && entity.passengers !is Player) {
                list.add(0, passengerEntity)
                passengers.add(0, passenger)
            } else {
                list.add(passengerEntity)
                passengers.add(passenger)
            }
            entity.passengers = ImmutableList.copyOf(list)
        }
        sendPackets(ClientboundSetPassengersPacket(entity))
    }

    override fun removePassenger(passenger: Entity) {
        val entity = entity ?: return
        val passengerEntity = (passenger as AbstractEntity).entity ?: return

        passengers.remove(passenger)
        if (entity.passengers.contains(passengerEntity)) {
            val list: MutableList<net.minecraft.world.entity.Entity> = entity.passengers
            list.remove(passengerEntity)
            entity.passengers = ImmutableList.copyOf(list)
            sendPackets(ClientboundSetPassengersPacket(entity))
        }
    }

    override fun clearPassenger() {
        passengers.forEach { removePassenger(it) }
        passengers.clear()
    }

    override fun getPassengers(): List<Entity> = passengers

    override fun setGlowing(glow: Boolean) =
        setSharedFlag(FLAG_GLOWING, glow) {
            this.glowing = glow
        }

    override fun setGlowingColor(chatColor: ChatColor) {
        entity ?: return
        glowingColor = chatColor
        entityTeam.color = ChatFormatting.valueOf(chatColor.name)
        sendPackets(
            ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(entityTeam, true)
        )
    }

    override fun getGlowingColor(): ChatColor = glowingColor

    override fun isGlowing(): Boolean = glowing

    override fun setGravity(gravity: Boolean) =
        setEntityDataAccessor(DATA_NO_GRAVITY, !gravity) {
            this.gravity = gravity
        }

    override fun hasGravity(): Boolean = gravity

    override fun setSilent(silent: Boolean) =
        setEntityDataAccessor(DATA_SILENT, silent) {
            this.silent = silent
        }

    override fun isSilent(): Boolean = silent

    fun toRotationValue(yaw: Float): Byte = floor(yaw * 256.0f / 360.0f).toInt().toByte()

    private fun toDeltaValue(value: Double, newValue: Double) = (((newValue - value) * 32 * 128).toInt().toShort())

    override fun getEntityData(): JsonObject {
        val json = super.getEntityData()
        val entityJson = JsonObject()

        entityJson.addProperty("customName", customName)
        entityJson.addProperty("isCustomNameVisible", isCustomNameVisible)

        entityJson.addProperty("fire", fire)
        entityJson.addProperty("freezing", freezing)
        entityJson.addProperty("visible", visible)
        entityJson.addProperty("gravity", gravity)

        entityJson.addProperty("collidable", collidable)
        entityJson.addProperty("glowing", glowing)
        entityJson.addProperty("glowingColor", glowingColor.name)

        val passengersArray = JsonArray()
        passengers.forEach {
            passengersArray.add((it as AbstractEntity).entity?.id)
        }
        entityJson.add("passengers", passengersArray)

        json.add("entity", entityJson)
        return json
    }

    override fun setEntityData(jsonObject: JsonObject) {
        val entityJson = jsonObject["entity"].asJsonObject

        entityJson["customName"].asString.run {
            customName = if(this == "null") null else this
        }
        isCustomNameVisible = entityJson["isCustomNameVisible"].asBoolean

        fire = entityJson["fire"].asBoolean
        freezing = entityJson["freezing"].asBoolean
        visible = entityJson["visible"].asBoolean
        gravity = entityJson["gravity"].asBoolean

        collidable = entityJson["collidable"].asBoolean
        glowing = entityJson["glowing"].asBoolean
        glowingColor = ChatColor.valueOf(entityJson["glowingColor"].asString)
    }

    override fun updateEntity() {
        customName?.let { setCustomName(it) }
        setCustomNameVisibility(isCustomNameVisible)

        setVisualFreezing(fire)
        setVisualFreezing(freezing)
        setVisible(visible)
        setGravity(gravity)

        setCollidable(collidable)
        setGlowing(gravity)
        setGlowingColor(glowingColor)
    }

    override fun getTests(): MutableList<() -> String> =
        mutableListOf(
            {
                val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
                val name = (1..10)
                    .map { allowedChars.random() }
                    .joinToString("")
                setCustomName(name)
                setCustomNameVisibility(true)
                getTestMessage(this::class, "Set custom name", name)
            },
            {
                setCustomNameVisibility(false)
                getTestMessage(this::class, "Set custom name visibility", false)
            },
            {
                setVisualFreezing(true)
                getTestMessage(this::class, "Set visual freezing", true)
            },
            {
                setVisualFreezing(false)
                getTestMessage(this::class, "Set visual freezing", false)
            },
            {
                setVisualFire(true)
                getTestMessage(this::class, "Set visual fire", true)
            },
            {
                setVisualFire(false)
                getTestMessage(this::class, "Set visual fire", false)
            },
            {
                setVisible(false)
                getTestMessage(this::class, "Set visible", false)
            },
            {
                setVisible(true)
                getTestMessage(this::class, "Set visible", true)
            },
            {
                setGravity(true)
                getTestMessage(this::class, "Set gravity", true)
            },
            {
                setGravity(false)
                getTestMessage(this::class, "Set gravity", false)
            },
            {
                setCollidable(true)
                getTestMessage(this::class, "Set collidable", true)
            },
            {
                setCollidable(false)
                getTestMessage(this::class, "Set collidable", false)
            },
            {
                setGlowing(true)
                getTestMessage(this::class, "Set glowing", true)
            },
            {
                val color = ChatColor.entries.filter { it.isColor }.random()
                setGlowingColor(color)
                getTestMessage(this::class, "Set glowing color", color.name.lowercase())
            },
            {
                setGlowing(false)
                getTestMessage(this::class, "Set glowing", false)
            },
            {
                setSilent(true)
                getTestMessage(this::class, "Set silent", true)
            },
            {
                setSilent(false)
                getTestMessage(this::class, "Set silent", false)
            },
            {
                val location = getLocation().clone().add(0.0, 5.0, 0.0)
                teleport(location)
                getTestMessage(this::class, "Teleport", Math.round(location.x), Math.round(location.y), Math.round(location.z))
            },
            {
                val location = getLocation().clone().subtract(0.0, 5.0, 0.0)
                teleport(location)
                getTestMessage(this::class, "Teleport", Math.round(location.x), Math.round(location.y), Math.round(location.z))
            },
            {
                val yaw = Random().nextFloat(-180f, 180f)
                val pitch = Random().nextFloat(-180f, 180f)
                setRotation(yaw, pitch)
                getTestMessage(this::class, "Set Rotation", pitch, yaw)
            },
            {
                setRotation(0f, 0f)
                getTestMessage(this::class, "Set Rotation", 0f, 0f)
            },
            {
                val location = getLocation().clone().add(0.0, 5.0, 0.0)
                moveTo(location)
                getTestMessage(this::class, "Move to", Math.round(location.x), Math.round(location.y), Math.round(location.z))
            },
            {
                val location = getLocation().clone().subtract(0.0, 5.0, 0.0)
                moveTo(location)
                getTestMessage(this::class, "Move to", Math.round(location.x), Math.round(location.y), Math.round(location.z))
            }
        )

}