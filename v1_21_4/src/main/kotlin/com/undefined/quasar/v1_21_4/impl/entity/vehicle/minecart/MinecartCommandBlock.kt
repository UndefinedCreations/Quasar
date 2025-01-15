package com.undefined.quasar.v1_21_4.impl.entity.vehicle.minecart

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.vehicle.minecart.MinecartCommandBlock
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.Bukkit

class MinecartCommandBlock : Minecart(EntityType.COMMAND_BLOCK_MINECART), MinecartCommandBlock {

    private var command = ""

    override fun setCommand(string: String) {
        this.command = string
    }

    override fun getCommand(): String = command

    override fun invokeCommand() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.vehicle.MinecartCommandBlock(net.minecraft.world.entity.EntityType.COMMAND_BLOCK_MINECART, level)
}