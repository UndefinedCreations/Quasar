package com.unedfined.quasar.v1_21_4

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.util.executePrivateMethod
import com.unedfined.quasar.v1_21_4.mappings.MethodMappings
import com.unedfined.quasar.v1_21_4.util.sendPackets
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
import net.minecraft.server.level.ServerEntity
import net.minecraft.world.level.Level
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_21_R3.CraftWorld
import org.bukkit.entity.Player
import java.util.UUID

abstract class AbstractEntity(
    override val entityType: EntityType
) : Entity {

    var entity: net.minecraft.world.entity.Entity? = null
    private var location: Location? = null
    private var viewers: MutableList<UUID> = mutableListOf()

    override fun addViewer(player: Player) {
        viewers.add(player.uniqueId)
    }

    override fun removeViewer(player: Player) {
        viewers.remove(player.uniqueId)
    }

    override fun hasViewer(player: Player) = viewers.contains(player.uniqueId)

    override fun spawn(location: Location) {
        this.location = location

        val craftWorld = location.world as CraftWorld
        val entity = getEntityClass(craftWorld.handle)

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
        sendPackets(packet)

        this.entity = entity
        updateEntity()
    }

    fun sendEntityMetaData() {
        if (entity == null) return
        val data = entity!!.entityData.packDirty()
        val packet = ClientboundSetEntityDataPacket(entity!!.id, data)
        sendPackets(packet)
    }

    override fun kill() {
        entity?.let { sendPackets(ClientboundRemoveEntitiesPacket(it.id)) }
    }

    override fun isAlive(): Boolean = entity != null

    override fun setEntityData(jsonObject: JsonObject) { /* In the base entity there is no data to set */ }

    override fun getEntityData(): JsonObject = JsonObject().also {
        it.addProperty("entityType", entityType.name)
    }


    override fun toString(): String =
        getEntityData().toString()

    abstract fun getEntityClass(level: Level): net.minecraft.world.entity.Entity

    fun sendPackets(vararg packet: Packet<*>) {
        for (viewer in viewers)
            Bukkit.getOfflinePlayer(viewer)?.player?.let { it.sendPackets(packet.toList()) }
    }
}