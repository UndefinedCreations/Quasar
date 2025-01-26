package com.undefined.quasar.v1_21_4.impl.entity.abstracts

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.util.Option
import com.undefined.quasar.util.getPrivateField
import com.undefined.quasar.util.repeat
import com.undefined.quasar.v1_21_4.loader.QuasarNMS
import com.undefined.quasar.v1_21_4.util.sendPacket
import com.undefined.quasar.v1_21_4.util.sendPackets
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.server.level.ServerEntity
import net.minecraft.world.level.Level
import net.minecraft.world.scores.PlayerTeam
import net.minecraft.world.scores.Scoreboard
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_21_R3.CraftWorld
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftEntity
import org.bukkit.entity.Player
import java.util.*
import kotlin.reflect.KClass

abstract class AbstractEntity(
    override val entityType: EntityType
) : Entity {

    private val uuid = UUID.randomUUID()

    private var scoreboard: Scoreboard = Scoreboard()
    var entityTeam: PlayerTeam = scoreboard.addPlayerTeam("qausar_${UUID.randomUUID()}")
    var entity: net.minecraft.world.entity.Entity? = null
    private var location: Location? = null
    val viewers: MutableList<UUID> = mutableListOf()

    var isBukkitEntity = false

    private var autoLoader = QuasarNMS.ENTITY_LOADER
    private var sendMetaData = QuasarNMS.SEND_META_PACKET

    override fun setAutoLoader(autoLoader: Option) {
        this.autoLoader = autoLoader
    }

    override fun isAutoLoading(): Boolean = autoLoader == Option.AUTOMATIC

    override fun setAutoMetaDataPacket(metaDataPacket: Option) {
        this.sendMetaData = metaDataPacket
    }

    override fun isAutoMetaDataPacket(): Boolean = autoLoader == Option.AUTOMATIC

    override fun getUUID(): UUID = uuid

    override fun addViewer(player: Player) {
        viewers.add(player.uniqueId)
    }

    override fun removeViewer(player: Player) {
        viewers.remove(player.uniqueId)
    }

    override fun setEntity(entity: org.bukkit.entity.Entity) {
        this.entity = (entity as CraftEntity).handle
        QuasarNMS.spawnedEntities.add(this)
        isBukkitEntity = true
    }

    override fun hasViewer(player: Player) = player.uniqueId in viewers

    override fun spawn(location: Location) {
        sendPackets(*getSpawnPackets(location).toTypedArray())
        println(QuasarNMS.loadedChunk.getOrElse(Pair(getLocation().chunk.x, getLocation().chunk.z), { mutableListOf() }))
        setDefaultValues()
        QuasarNMS.spawnedEntities.add(this)
    }

    override fun spawn(location: Location, target: Player) {
        target.sendPacket(*getSpawnPackets(location).toTypedArray())
        println(QuasarNMS.loadedChunk.getOrElse(Pair(getLocation().chunk.x, getLocation().chunk.z), { mutableListOf() }))
        resendPackets(target)
        QuasarNMS.spawnedEntities.add(this)
    }

    private fun getSpawnPackets(location: Location): List<Packet<*>> {
        this.location = location
        val craftWorld = location.world as CraftWorld

        val entity = if (this.entity == null) getEntityClass(craftWorld.handle) else this.entity!!
        entity.uuid = uuid
        entity.setPos(location.x, location.y, location.z)
        val serverEntity = ServerEntity(
            craftWorld.handle,
            entity,
            0,
            false,
            {},
            mutableSetOf()
        )
        this.entity = entity
        val packet = entity.getAddEntityPacket(serverEntity)

        setViewers()

        return listOf(
            packet,
            ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(entityTeam, true),
            ClientboundSetPlayerTeamPacket.createPlayerPacket(entityTeam, entity.uuid.toString(), ClientboundSetPlayerTeamPacket.Action.ADD)
        )
    }

    fun setViewers() {
        if (isAutoLoading()) {
            viewers.addAll(Bukkit.getOnlinePlayers()
                .filter { it.world == getLocation().world }
                .filter { it.location.distance(getLocation()) < 16 * Bukkit.getServer().viewDistance + 1 }.map { it.uniqueId }
            )
        }
    }

    override fun kill(player: Player?) {
        entity?.let {
            if (player == null) this.entity = null
            val packet = ClientboundRemoveEntitiesPacket(it.id)
            if (player == null) sendPackets(packet) else player.sendPacket(packet)
        }
        if (player == null) QuasarNMS.spawnedEntities.remove(this)
    }

    override fun isAlive(): Boolean = entity != null

    override fun setEntityData(jsonObject: JsonObject) { /* In the base entity there is no data to set */ }

    override fun getEntityData(): JsonObject = JsonObject().also { it.addProperty("entityType", entityType.name) }

    override fun toString(): String =
        getEntityData().toString()

    abstract fun getEntityClass(level: Level): net.minecraft.world.entity.Entity

    fun sendPackets(vararg packet: Packet<*>) {
        for (viewer in viewers)
            Bukkit.getOfflinePlayer(viewer).player?.sendPackets(packet.toList())
    }

    fun sendEntityMetaData(player: Player? = null, manual: Boolean = false) {
        if (entity == null) return
        if (!manual && sendMetaData == Option.MANUAL) return
        val data = entity!!.entityData.packDirty() ?: return
        val packet = ClientboundSetEntityDataPacket(entity!!.id, data)
        if (player == null) sendPackets(packet) else player.sendPacket(packet)
    }

    override fun resendPackets() {
        sendEntityMetaData(manual = true)
    }

    override fun resendPackets(player: Player) {
        sendEntityMetaData(player, true)
    }

    fun <T> getEntityDataAccessor(field: EntityDataAccessor<*>?, clazz: Class<*>, name: String): T? {
        if (field != null) return field as T
        if (entity == null) return null
        return entity!!.getPrivateField(
            clazz,
            name
        )
    }

    fun <T> getEntityDataValue(accessor: EntityDataAccessor<T>?): T? {
        if (entity != null) {
            return entity!!.entityData.get(accessor)
        }
        return null
    }

    fun <T> setEntityDataAccessor(accessor: EntityDataAccessor<T>?, data: T?) {
        if (entity == null) return
        entity!!.entityData.set(accessor, data)
        sendEntityMetaData()
    }

    fun setSharedFlag(flag: Int, boolean: Boolean) {
        if (entity == null) return
        entity!!.setSharedFlag(flag, boolean)
        sendEntityMetaData()
    }

    fun getSharedFlag(flag: Int): Boolean {
        val entity = entity ?: return false
        return entity.getSharedFlag(flag)
    }

    fun setLocation(location: Location) { this.location = location }

    fun getTestMessage(entity: KClass<*>, infoText: String, vararg data: Any?): String =
        "${ChatColor.GRAY} ${entity.simpleName} | $infoText {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${
            data.joinToString(", ") { it.toString() }
        }${ChatColor.GRAY}]"

    override fun runTest(logger: Player, delayTime: Int, exception: (Exception) -> Unit, done: () -> Unit, sendMessage: Boolean) {
        val tests = getTests()
        var time = 0
        repeat(tests.size + 1, delayTime) {
            if (time == tests.size) return@repeat done()

            val test = tests[time]
            try {
                if (sendMessage) logger.sendMessage(test()) else test()
            } catch (e: Exception) {
                exception(e)
            }
            time++
        }
    }

    override fun getLocation(): Location = location ?: Location(Bukkit.getWorlds().first(), 0.0, 0.0, 0.0)
}