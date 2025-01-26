package com.undefined.quasar.v1_21_4.impl.entity.npc.player

import com.google.gson.JsonObject
import com.mojang.authlib.properties.Property
import com.mojang.datafixers.util.Pair
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.npc.player.GameProfile
import com.undefined.quasar.interfaces.entities.entity.npc.player.Player
import com.undefined.quasar.interfaces.entities.entity.npc.player.Skin
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.AbstractEntity
import com.undefined.quasar.v1_21_4.loader.QuasarNMS
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import com.undefined.quasar.v1_21_4.util.sendPacket
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.*
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.server.level.ClientInformation
import net.minecraft.server.level.ServerEntity
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.CommonListenerCookie
import net.minecraft.server.network.ServerGamePacketListenerImpl
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.Pose
import net.minecraft.world.entity.PositionMoveRotation
import net.minecraft.world.entity.monster.Shulker
import net.minecraft.world.level.Level
import net.minecraft.world.scores.Team
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_21_R3.CraftServer
import org.bukkit.craftbukkit.v1_21_R3.CraftWorld
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftEntity
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftPlayer
import org.bukkit.event.player.PlayerRespawnEvent
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.floor

private val trueProfile: HashMap<UUID, GameProfile> = hashMapOf()

class Player : LivingEntity(EntityType.PLAYER), Player {

    private var reduceDebug = false

    override fun spawn(location: Location) {
        sendPackets(*getSpawnPackets(location).toTypedArray())
        QuasarNMS.spawnedEntities.add(this)
    }

    override fun spawn(location: Location, target: org.bukkit.entity.Player) {
        target.sendPacket(*getSpawnPackets(location).toTypedArray())
        QuasarNMS.spawnedEntities.add(this)
    }

    private fun getSpawnPackets(location: Location): List<Packet<*>> {
        this.setLocation(location)

        val skin = getSkin()
        val gameProfile = serverPlayer()?.gameProfile ?: com.mojang.authlib.GameProfile(UUID.randomUUID(), getName())
        gameProfile.properties.put("textures", Property("textures", skin.getTexture(), skin.getSignature()))

        val server = (Bukkit.getServer() as CraftServer).server
        val serverLevel = (location.world as CraftWorld).handle

        val fakeServerPlayer = ServerPlayer(server, serverLevel, gameProfile, ClientInformation.createDefault())
        fakeServerPlayer.setPos(location.x, location.y, location.z)
        fakeServerPlayer.moveTo(location.x, location.y, location.z, location.yaw, location.pitch)

        val connection = ServerGamePacketListenerImpl(
            server,
            EmptyConnection(),
            fakeServerPlayer,
            CommonListenerCookie.createInitial(gameProfile, false)
        )

        fakeServerPlayer.connection = connection

        if (serverPlayer() == null) setEntity(fakeServerPlayer.bukkitEntity)

        val serverEntity = ServerEntity(serverLevel, fakeServerPlayer, 0, false, {}, mutableSetOf())

        val addPlayer = ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, fakeServerPlayer)
        val addEntity = ClientboundAddEntityPacket(fakeServerPlayer, serverEntity)

        val data = fakeServerPlayer.entityData
        val bitmask: Byte = (0x01 or 0x04 or 0x08 or 0x10 or 0x20 or 0x40 or 127).toByte()
        data.set(EntityDataAccessor(17, EntityDataSerializers.BYTE), bitmask)
        val metaDataPacket = ClientboundSetEntityDataPacket(fakeServerPlayer.id, data.packDirty()!!)

        val createTeam = ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(entityTeam, true)
        val addToTeam = ClientboundSetPlayerTeamPacket.createPlayerPacket(entityTeam, fakeServerPlayer.uuid.toString(), ClientboundSetPlayerTeamPacket.Action.ADD)

        setViewers()

        return listOf(addPlayer, addEntity, metaDataPacket, createTeam, addToTeam)
    }

    override fun respawn() {
        respawnPacket()
    }

    private fun respawnPacket() {
        val serverPlayer = serverPlayer() ?: return
        val removeEntity = ClientboundRemoveEntitiesPacket(serverPlayer.id)
        val removeEntityInfo = ClientboundPlayerInfoRemovePacket(listOf(serverPlayer.uuid))
        val addEntityInfo = ClientboundPlayerInfoUpdatePacket.createPlayerInitializing(listOf(serverPlayer))
        val addEntity = ClientboundAddEntityPacket(serverPlayer, 0, serverPlayer.blockPosition())
        val updateSkin = ClientboundSetEntityDataPacket(serverPlayer.id, serverPlayer.entityData.nonDefaultValues ?: emptyList())
        val updateStatus = ClientboundEntityEventPacket(serverPlayer, 28)
        val position = ClientboundPlayerPositionPacket(serverPlayer.id, PositionMoveRotation.of(serverPlayer), HashSet())
        val headRotation = ClientboundRotateHeadPacket(serverPlayer, floor(serverPlayer.getYHeadRot() * 256.0f / 360.0f).toInt().toByte())
        viewers.forEach {
            Bukkit.getPlayer(it)?.run {
                if (it != serverPlayer.uuid) {
                    sendPacket(removeEntity, addEntity, headRotation, updateSkin)
                }
                sendPacket(removeEntityInfo, addEntityInfo)
                for (slot in EquipmentSlot.entries) {
                    val item = serverPlayer.getItemBySlot(slot)
                    if (!item.isEmpty) {
                        sendPacket(ClientboundSetEquipmentPacket(serverPlayer.id, listOf(Pair.of(slot, item))))
                    }
                }
                if (it == serverPlayer.uuid) {
                    val serverPlayerList = (Bukkit.getServer() as CraftServer).handle
                    serverPlayerList.respawn(serverPlayer, true, Entity.RemovalReason.KILLED, PlayerRespawnEvent.RespawnReason.PLUGIN, serverPlayer.bukkitEntity.location)
                    sendPacket(position, updateSkin, updateStatus)
                    serverPlayer.onUpdateAbilities()
                    serverPlayer.resetSentInfo()
                    serverPlayer.bukkitEntity.recalculatePermissions()
                }
            }
        }
    }

    override fun getSkin(): Skin {
        val serverPlayer = serverPlayer() ?: return Skin.STEVE
        val gameProfile = serverPlayer.gameProfile
        val property = gameProfile.properties["textures"].first()
        return Skin(property.value as String, property.signature as String)
    }

    override fun getName(): String = serverPlayer()?.gameProfile?.name ?: "STEVE"

    override fun getTrueProfile(): GameProfile? {
        return trueProfile[getUUID()]
    }

    override fun resetProfile() {
        if (getTrueProfile() != null) setGameProfile(getTrueProfile()!!) else setGameProfile(GameProfile(Skin.STEVE, "STEVE"))
    }

    override fun setGameProfile(gameProfile: GameProfile) {
        val serverPlayer = serverPlayer() ?: return
        serverPlayer.bukkitEntity.setDisplayName(gameProfile.getName())
        net.minecraft.world.entity.player.Player::class.java.getDeclaredField(FieldMappings.Entity.LivingEntity.Npc.Player.GAME_PROFILE).apply {
            isAccessible = true
        }.set(serverPlayer, com.mojang.authlib.GameProfile(serverPlayer.uuid, gameProfile.getName()).apply {
            val skin = gameProfile.getSkin()
            properties.put("textures", Property("textures", skin.getTexture(), skin.getSignature()))
        })
        respawn()
    }

    override fun getGameProfile(): GameProfile {
        val serverPlayer = serverPlayer() ?: return GameProfile(Skin.STEVE, "STEVE")
        val gameProfile = serverPlayer.gameProfile.properties["textures"].first()
        return GameProfile(Skin(gameProfile.value, gameProfile.signature as String), gameProfile.name)
    }

    override fun setEntity(entity: org.bukkit.entity.Entity) {
        this.entity = ((entity as org.bukkit.entity.Player) as CraftPlayer).handle
        if (!trueProfile.containsKey(entity.uniqueId)) trueProfile[entity.uniqueId] = getGameProfile()
        QuasarNMS.spawnedEntities.add(this)
        isBukkitEntity = true
    }

    override fun hideName(hide: Boolean) {
        serverPlayer() ?: return
        entityTeam.nameTagVisibility = if (hide) Team.Visibility.NEVER else Team.Visibility.ALWAYS
        sendPackets(
            ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(entityTeam, true)
        )
    }

    override fun isNameHidden(): Boolean = entityTeam.nameTagVisibility == Team.Visibility.NEVER

    override fun setCrouching(crouching: Boolean) = setEntityDataAccessor(DATA_POSE, Pose.CROUCHING)

    override fun isCrouching(): Boolean = getEntityDataValue(DATA_POSE) == Pose.CROUCHING

    override fun setSwimming(swimming: Boolean) = setEntityDataAccessor(DATA_POSE, Pose.SWIMMING)

    override fun isSwimming(): Boolean = getEntityDataValue(DATA_POSE) == Pose.SWIMMING

    override fun setRiptideAttack(riptide: Boolean) = setEntityDataAccessor(DATA_POSE, Pose.SPIN_ATTACK)

    override fun isInRiptideAttack(): Boolean = getEntityDataValue(DATA_POSE) == Pose.SPIN_ATTACK

    override fun moveMainHand() {
        val serverPlayer = serverPlayer() ?: return
        sendPackets(ClientboundAnimatePacket(
            serverPlayer,
            0
        ))
    }

    override fun moveOffHand() {
        val serverPlayer = serverPlayer() ?: return
        sendPackets(ClientboundAnimatePacket(
            serverPlayer,
            3
        ))
    }

    override fun setCamara(entity: org.bukkit.entity.Entity?) {
        serverPlayer() ?: return
        sendPackets(ClientboundSetCameraPacket((entity as CraftEntity).handle))
    }

    override fun setCamara(entity: com.undefined.quasar.interfaces.Entity) {
        serverPlayer() ?: return
        sendPackets(ClientboundSetCameraPacket((entity as AbstractEntity).entity))
    }

    override fun setReduceDebug(reduceDebug: Boolean) {
        val serverPlayer = serverPlayer() ?: return
        this.reduceDebug = reduceDebug
        sendPackets(ClientboundEntityEventPacket(serverPlayer, if (reduceDebug) 22 else 23))
    }

    override fun isReduceDebug(): Boolean = reduceDebug

    override fun sendDisconnectedPacket() {
        val serverPlayer = serverPlayer() ?: return
        val removeEntity = ClientboundRemoveEntitiesPacket(serverPlayer.id)
        val removeEntityInfo = ClientboundPlayerInfoRemovePacket(listOf(serverPlayer.uuid))
        viewers.filter { it != serverPlayer.uuid }.forEach { Bukkit.getPlayer(it)?.sendPacket(removeEntity, removeEntityInfo) }
    }

    override fun sendReconnectPacket() {
        val serverPlayer = serverPlayer() ?: return
        val addEntityInfo = ClientboundPlayerInfoUpdatePacket.createPlayerInitializing(listOf(serverPlayer))
        val addEntity = ClientboundAddEntityPacket(serverPlayer, 0, serverPlayer.blockPosition())
        val updateSkin = ClientboundSetEntityDataPacket(serverPlayer.id, serverPlayer.entityData.nonDefaultValues ?: emptyList())
        val updateStatus = ClientboundEntityEventPacket(serverPlayer, 28)
        val position = ClientboundPlayerPositionPacket(serverPlayer.id, PositionMoveRotation.of(serverPlayer), HashSet())
        val headRotation = ClientboundRotateHeadPacket(serverPlayer, floor(serverPlayer.getYHeadRot() * 256.0f / 360.0f).toInt().toByte())
        viewers.filter { it != serverPlayer.uuid }.forEach { Bukkit.getPlayer(it)?.sendPacket(addEntity, addEntityInfo, updateStatus, updateSkin, position, headRotation) }
    }

    override fun getEntityData(): JsonObject {
        val livingEntityJson = super.getEntityData()
        val playerJson = JsonObject()
        playerJson.add("skin", getSkin().json())
        playerJson.addProperty("name", getName())
        playerJson.addProperty("hideName", isNameHidden())
        playerJson.addProperty("crouching", isCrouching())
        playerJson.addProperty("swimming", isSwimming())
        playerJson.addProperty("riptide", isInRiptideAttack())
        livingEntityJson.add("player", playerJson)
        return livingEntityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val playerJson = jsonObject["player"].asJsonObject
        setSkin(Skin.of(playerJson["skin"].asJsonObject))
        hideName(playerJson["hideName"].asBoolean)
        setName(playerJson["name"].asString)
        setCrouching(playerJson["crouching"].asBoolean)
        setSwimming(playerJson["swimming"].asBoolean)
        setRiptideAttack(playerJson["riptide"].asBoolean)
    }

    private fun serverPlayer(): ServerPlayer? = entity as? ServerPlayer

    override fun getEntityClass(level: Level): Entity {
        return Shulker(net.minecraft.world.entity.EntityType.SHULKER, level)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setSkin(Skin.of(mutableListOf("TheRedMagic", "StillLutto").random()))
                getTestMessage(this@Player::class, "Set skin", getSkin().json())
            },
            {
                val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
                val name = (1..10)
                    .map { allowedChars.random() }
                    .joinToString("")
                setName(name)
                getTestMessage(this@Player::class, "Set name", getName())
            },
            {
                hideName(true)
                getTestMessage(this@Player::class, "Set name visibly", isNameHidden())
            },
            {
                hideName(false)
                getTestMessage(this@Player::class, "Set name visibly", isNameHidden())
            },
            {
                setCrouching(true)
                getTestMessage(this@Player::class, "Set crouching", isCrouching())
            },
            {
                setCrouching(false)
                getTestMessage(this@Player::class, "Set crouching", isCrouching())
            },
            {
                setSwimming(true)
                getTestMessage(this@Player::class, "Set swimming", isSwimming())
            },
            {
                setSwimming(false)
                getTestMessage(this@Player::class, "Set swimming", isSwimming())
            },
            {
                setRiptideAttack(true)
                getTestMessage(this@Player::class, "Set riptide", isInRiptideAttack())
            },
            {
                setRiptideAttack(false)
                getTestMessage(this@Player::class, "Set riptide", isInRiptideAttack())
            },
            {
                moveMainHand()
                getTestMessage(this@Player::class, "Move main hand")
            },
            {
                moveOffHand()
                getTestMessage(this@Player::class, "Move main hand")
            }
        )) }
}