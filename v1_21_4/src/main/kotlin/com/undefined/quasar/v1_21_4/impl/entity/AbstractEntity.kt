package com.undefined.quasar.v1_21_4.impl.entity

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.util.getPrivateField
import com.undefined.quasar.util.repeat
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
    private var viewers: MutableList<UUID> = mutableListOf()

    override fun getUUID(): UUID = uuid

    override fun addViewer(player: Player) {
        viewers.add(player.uniqueId)
    }

    override fun removeViewer(player: Player) {
        viewers.remove(player.uniqueId)
    }

    override fun hasViewer(player: Player) = player.uniqueId in viewers

    override fun spawn(location: Location) {
        this.location = location

        val craftWorld = location.world as CraftWorld
        val entity = getEntityClass(craftWorld.handle)

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

        val packet = entity.getAddEntityPacket(serverEntity)
        sendPackets(
            packet,
            ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(entityTeam, true),
            ClientboundSetPlayerTeamPacket.createPlayerPacket(entityTeam, entity.uuid.toString(), ClientboundSetPlayerTeamPacket.Action.ADD)
        )

        this.entity = entity
        updateEntity()
    }

    override fun kill() {
        entity?.let { sendPackets(ClientboundRemoveEntitiesPacket(it.id)) }
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

    fun sendEntityMetaData() {
        if (entity == null) return
        val data = entity!!.entityData.packDirty() ?: return
        val packet = ClientboundSetEntityDataPacket(entity!!.id, data)
        sendPackets(packet)
    }

    fun <T> getEntityDataAccessor(field: EntityDataAccessor<*>?, clazz: Class<*>, name: String): T? {
        if (field != null) return field as T
        if (entity == null) return null
        return entity!!.getPrivateField(
            clazz,
            name
        )
    }

    fun <T> setEntityDataAccessor(accessor: EntityDataAccessor<T>?, data: T?, runnable: (Unit) -> Unit) {
        if (entity == null) return
        entity!!.entityData.set(accessor, data)
        sendEntityMetaData()
        runnable(Unit)
    }

    fun setSharedFlag(flag: Int, boolean: Boolean, runnable: (Unit) -> Unit) {
        if (entity == null) return
        entity!!.setSharedFlag(flag, boolean)
        sendEntityMetaData()
        runnable(Unit)
    }

    fun setLocation(location: Location) { this.location = location }

    fun getTestMessage(entity: KClass<*>, infoText: String, vararg data: Any): String =
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