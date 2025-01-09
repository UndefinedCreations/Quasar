package com.unedfined.quasar.v1_21_4

import com.google.common.collect.ImmutableList
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.util.execute
import com.undefined.quasar.util.getPrivateField
import com.undefined.quasar.util.getPrivateMethod
import com.unedfined.quasar.v1_21_4.mappings.FieldMappings
import com.unedfined.quasar.v1_21_4.mappings.MethodMappings
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.PositionMoveRotation
import net.minecraft.world.scores.Team
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_21_R3.util.CraftChatMessage
import org.bukkit.entity.Player
import java.util.*

abstract class Entity(
    override val entityType: EntityType
) : AbstractEntity(entityType), Entity {

    private var DATA_CUSTOM_NAME: EntityDataAccessor<Optional<Component>>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(net.minecraft.world.entity.Entity::class.java, FieldMappings.Entity.Base.DATA_CUSTOM_NAME)
            return field
        }
    private var DATA_CUSTOM_NAME_VISIBLE: EntityDataAccessor<Boolean>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(net.minecraft.world.entity.Entity::class.java, FieldMappings.Entity.Base.DATA_CUSTOM_NAME_VISIBLE)
            return field
        }
    private var DATA_SILENT: EntityDataAccessor<Boolean>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(net.minecraft.world.entity.Entity::class.java, FieldMappings.Entity.Base.DATA_SILENT)
            return field
        }
    private var DATA_NO_GRAVITY: EntityDataAccessor<Boolean>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(net.minecraft.world.entity.Entity::class.java, FieldMappings.Entity.Base.DATA_NO_GRAVITY)
            return field
        }
    private var DATA_TICKS_FROZEN: EntityDataAccessor<Int>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(net.minecraft.world.entity.Entity::class.java, FieldMappings.Entity.Base.DATA_TICKS_FROZEN)
            return field
        }

    private var FLAG_ONFIRE = 0
    private var FLAG_INVISIBLE = 5
    private var FLAG_GLOWING = 6

    private var customName: String? = null
    private var isCustomNameVisible = false
    private var fire = false
    private var freezing = false
    private var visible = false
    private var gravity = false
    private var silent = false
    private var collidable = false
    private var glowing = false
    private var glowingColor: ChatColor = ChatColor.WHITE
    private var passengers: MutableList<Entity> = mutableListOf()

    override fun setCustomName(name: String?) {
        val entity = entity ?: return
        customName = name
        entity.entityData.set(DATA_CUSTOM_NAME, Optional.ofNullable(CraftChatMessage.fromStringOrNull(name)))
        sendEntityMetaData()
    }
    override fun getCustomName(): String? = customName
    override fun isCustomNameVisible(): Boolean = isCustomNameVisible
    override fun setCustomNameVisibility(visible: Boolean) {
        val entity = entity ?: return
        if (!visible) setCustomName(null)
        isCustomNameVisible = visible
        entity.entityData.set(DATA_CUSTOM_NAME_VISIBLE, visible)
        sendEntityMetaData()
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
    override fun setVisualFire(fire: Boolean) {
        val entity = entity ?: return
        this.fire = fire
        entity.setSharedFlag(FLAG_ONFIRE, fire)
        sendEntityMetaData()
    }
    override fun isVisualFire(): Boolean = fire
    override fun setVisualFreezing(freezing: Boolean) {
        val entity = entity ?: return
        this.freezing = freezing
        if (freezing) entity.entityData.set(DATA_TICKS_FROZEN, Int.MAX_VALUE) else entity.entityData.set(DATA_TICKS_FROZEN, 0)
        entity.isInPowderSnow = freezing
        sendEntityMetaData()
    }
    override fun isFreezing(): Boolean = freezing
    override fun setVisible(visible: Boolean) {
        val entity = entity ?: return
        this.visible = visible
        entity.setSharedFlag(FLAG_INVISIBLE, visible)
        sendEntityMetaData()
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
    override fun setGlowing(glow: Boolean) {
        val entity = entity ?: return
        this.glowing = glow
        entity.setSharedFlag(FLAG_GLOWING, glow)
        sendEntityMetaData()
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
    override fun setGravity(gravity: Boolean) {
        val entity = entity ?: return
        this.gravity = gravity
        entity.entityData.set(DATA_NO_GRAVITY, !gravity)
        sendEntityMetaData()
    }
    override fun hasGravity(): Boolean = gravity
    override fun setSilent(silent: Boolean) {
        val entity = entity ?: return
        this.silent = silent
        entity.entityData.set(DATA_SILENT, silent)
        sendEntityMetaData()
    }
    override fun isSilent(): Boolean = silent
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
    override fun runTest(
        logger: Player,
        delayTime: Int,
        stageOneTest: (Exception?) -> Unit,
        stageTwoTest: (Exception?) -> Unit,
        stageThreeTest: (Exception?) -> Unit
    ): Int {
        trycatch({
            var time = 0
            com.undefined.quasar.util.repeat(22, delayTime) {
                when(time) {
                    0 -> {
                        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
                        val name = (1..10)
                            .map { allowedChars.random() }
                            .joinToString("")
                        setCustomName(name)
                        setCustomNameVisibility(true)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set custom name {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${name}${ChatColor.GRAY}]")
                    }
                    1 -> {
                        setCustomNameVisibility(false)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set custom name visibility {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}false${ChatColor.GRAY}]")
                    }
                    2 -> {
                        setVisualFreezing(true)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set visual freezing {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}true${ChatColor.GRAY}]")
                    }
                    3 -> {
                        setVisualFreezing(false)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set visual freezing {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}false${ChatColor.GRAY}]")
                    }
                    4 -> {
                        setVisualFire(true)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set visual fire {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}true${ChatColor.GRAY}]")
                    }
                    5 -> {
                        setVisualFire(false)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set visual fire {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}false${ChatColor.GRAY}]")
                    }
                    6 -> {
                        setVisible(false)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set visibility {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}false${ChatColor.GRAY}]")
                    }
                    7 -> {
                        setVisible(true)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set visibility {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}true${ChatColor.GRAY}]")
                    }
                    8 -> {
                        setGravity(true)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set gravity {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}true${ChatColor.GRAY}]")
                    }
                    9 -> {
                        setGravity(false)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set gravity {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}false${ChatColor.GRAY}]")
                    }
                    10 -> {
                        setCollidable(true)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set collidable {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}true${ChatColor.GRAY}]")
                    }
                    11 -> {
                        setCollidable(false)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set collidable {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}false${ChatColor.GRAY}]")
                    }
                    12 -> {
                        setGlowing(true)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set glowing {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}true${ChatColor.GRAY}]")
                    }
                    13 -> {
                        val color = ChatColor.entries.filter { it.isColor }.random()
                        setGlowingColor(color)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set glowing color {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${color.name.lowercase()}${ChatColor.GRAY}]")
                    }
                    14 -> {
                        setGlowing(false)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set glowing {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}false${ChatColor.GRAY}]")
                    }
                    15 -> {
                        setSilent(true)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set silent {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}true${ChatColor.GRAY}]")
                    }
                    16 -> {
                        setSilent(false)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set silent {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}false${ChatColor.GRAY}]")
                    }
                    17 -> {
                        val location = getLocation().clone().add(0.0, 5.0, 0.0)
                        teleport(location)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Teleport {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${Math.round(location.x)}, ${Math.round(location.y)}, ${Math.round(location.z)}]${ChatColor.GRAY}]")
                    }
                    18 -> {
                        val location = getLocation().clone().subtract(0.0, 5.0, 0.0)
                        teleport(location)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Teleport {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${Math.round(location.x)}, ${Math.round(location.y)}, ${Math.round(location.z)}]${ChatColor.GRAY}]")
                    }
                    19 -> {
                        val yaw = Random().nextFloat(-180f, 180f)
                        val pitch = Random().nextFloat(-180f, 180f)
                        setRotation(yaw, pitch)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set rotation {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}$yaw, $pitch]${ChatColor.GRAY}]")
                    }
                    20 -> {
                        setRotation(0f, 0f)
                        logger.sendMessage("${ChatColor.GRAY} Entity | Set rotation {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}0, 0]${ChatColor.GRAY}]")
                    }
                    21 -> {
                        stageOneTest(null)
                    }
                }
                time++
            }
        },stageOneTest)

        return 1
    }

    fun trycatch(runnable: (Unit) -> Unit, exception: (Exception?) -> Unit) {
        try {
            runnable(Unit)
        }catch (e: Exception) {
            exception(e)
        }
    }
}