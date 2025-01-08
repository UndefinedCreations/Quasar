package com.unedfined.quasar.v1_21_4.util

import net.minecraft.network.protocol.Packet
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftPlayer
import org.bukkit.entity.Player

fun Player.sendPacket(vararg packet: Packet<*>) = sendPackets(packet.toList())

fun Player.sendPackets(packetList: List<Packet<*>>) {
    val cPlayer = this as CraftPlayer
    val sPlayer = cPlayer.handle
    val connection = sPlayer.connection
    packetList.forEach { connection.send(it) }
}