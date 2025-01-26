package com.undefined.quasar.v1_21_4.listener

import com.undefined.quasar.util.*
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.AbstractEntity
import com.undefined.quasar.v1_21_4.loader.QuasarNMS
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import com.undefined.quasar.v1_21_4.mappings.MethodMappings
import net.minecraft.network.Connection
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket.Action
import net.minecraft.network.protocol.game.ServerboundInteractPacket
import net.minecraft.server.network.ServerCommonPacketListenerImpl
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_21_R3.CraftWorld
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftWolf
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityInteractEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.UUID

class PacketListener : Listener {

    private val idMap: HashMap<UUID, UUID> = hashMapOf()

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {

        idMap[e.player.uniqueId] = UUID.randomUUID()

        val interactCoolDown: MutableList<UUID> = mutableListOf()

        val serverPlayer = (e.player as CraftPlayer).handle
        val connection = serverPlayer.connection.getPrivateField<Connection>(ServerCommonPacketListenerImpl::class.java, FieldMappings.PacketListener.ServerCommonPacketListenerImpl.CONNECTION)
        val channel = connection.channel
        val pipeline = channel.pipeline()
        pipeline.addBefore("packet_handler", idMap[e.player.uniqueId].toString(), DuplexHandler(
            {
                if (this is ServerboundInteractPacket) {
                    val entityID = this.getPrivateField<Int>(ServerboundInteractPacket::class.java, FieldMappings.PacketListener.ServerboundInteractPacket.ENTITY_ID)
                    val entity = QuasarNMS.spawnedEntities.firstOrNull { (it as AbstractEntity).entity?.bukkitEntity?.entityId == entityID }
                    if (entity == null) return@DuplexHandler
                    val action = this.getPrivateField<Any>(ServerboundInteractPacket::class.java, FieldMappings.PacketListener.ServerboundInteractPacket.ACTION)
                    val actionType = action::class.java.getPrivateMethod(MethodMappings.ServerboundInteractionPacket.ActionType.GET_TYPE).execute(action)
                    when(actionType.toString()) {
                        "ATTACK" -> {
                            (entity as Entity).clickData.forEach { it.invoke(ClickData(e.player, ClickType.LEFT, entity)) }
                        }
                        "INTERACT" -> {
                            if (interactCoolDown.contains(entity.getUUID())) {
                                interactCoolDown.remove(entity.getUUID())
                                return@DuplexHandler
                            }
                            (entity as Entity).clickData.forEach { it.invoke(ClickData(e.player, ClickType.RIGHT, entity)) }
                            interactCoolDown.add(entity.getUUID())
                        }
                    }

                }
            },
            {
                Bukkit.getScheduler().runTaskAsynchronously(QuasarNMS.PLUGIN, Runnable {
                    when(this) {
                        is ClientboundLevelChunkWithLightPacket -> {
                            QuasarNMS.spawnedEntities
                                .filterIsInstance<AbstractEntity>()
                                .filter { it.isAutoLoading() && it.isAlive() }
                                .filter { it.getLocation().chunk.x == this.x && it.getLocation().chunk.z == this.z }
                                .forEach {
                                    it.addViewer(e.player)
                                    println("SPAWN")
                                    if (!it.isBukkitEntity) it.spawn(it.getLocation(), e.player)
                                }
                        }
                        is ClientboundForgetLevelChunkPacket -> {
                            QuasarNMS.spawnedEntities
                                .filterIsInstance<AbstractEntity>()
                                .filter { it.isAutoLoading() }
                                .filter { it.getLocation().chunk.x == this.pos.x && it.getLocation().chunk.z == this.pos.z }
                                .forEach {
                                    it.removeViewer(e.player)
                                    println("REMOVE")
                                    if (!it.isBukkitEntity) it.kill(e.player)
                                }
                        }
                    }
                })
            }
        ))
    }

    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        val serverPlayer = (e.player as CraftPlayer).handle
        val connection = serverPlayer.connection.getPrivateField<Connection>(ServerCommonPacketListenerImpl::class.java, FieldMappings.PacketListener.ServerCommonPacketListenerImpl.CONNECTION)
        val channel = connection.channel
        channel.eventLoop().submit {
            channel.pipeline().remove(idMap[serverPlayer.uuid].toString())
        }
        idMap.remove(serverPlayer.uuid)

        Bukkit.getScheduler().runTaskAsynchronously(QuasarNMS.PLUGIN, Runnable {
            for (entire in QuasarNMS.loadedChunk) {
                if (entire.value.contains(e.player.uniqueId)) {
                    val data = entire.value
                    data.remove(e.player.uniqueId)
                    QuasarNMS.loadedChunk[entire.key] = data
                }
            }
        })
    }

}