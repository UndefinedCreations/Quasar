package com.undefined.quasar

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.Entity
import com.undefined.stellar.StellarCommand
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    val spawnedEntities: MutableList<Entity> = mutableListOf()

    override fun onEnable() {

        println("Running")

        val quasar = Quasar()

        val mainCommand = StellarCommand("quasar")

        val entitySub = mainCommand.addArgument("entities").addEnumArgument<EntityType>("type")

        entitySub.addArgument("spawn")
            .addExecution<Player> {
                val type = getArgument<EntityType>("type")
                val entity = quasar.createQuasarEntity(type)
                entity.addViewer(sender)
                spawnedEntities.add(entity)
                val location = sender.location
                entity.spawn(location)
                sender.sendMessage("${ChatColor.GREEN} $type has been spawned at [${Math.round(location.x)}, ${Math.round(location.y)}, ${Math.round(location.z)}]")
            }

        entitySub.addArgument("kill")
            .addExecution<Player> {
                val type = getArgument<EntityType>("type")
                val entities = spawnedEntities.filter { it.entityType == type }
                entities.forEach { it.kill() }
                spawnedEntities.removeAll(entities)
                sender.sendMessage("${ChatColor.RED} All version of type ${type.name.lowercase()} as been removed! [${entities.size}]")
            }

        mainCommand.register(this)

    }

    override fun onDisable() {

    }


}