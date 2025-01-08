package com.undefined.quasar

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.Entity
import com.undefined.stellar.StellarCommand
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

class Main : JavaPlugin() {

    val spawnedEntities: MutableList<Entity> = mutableListOf()

    override fun onEnable() {

        println("Running")

        val quasar = Quasar(this)

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

        val entityTest = entitySub.addArgument("run-test")

        entityTest.addExecution<Player> {
                val type = getArgument<EntityType>("type")
                val entity = quasar.createQuasarEntity(type)
                runTest(sender, entity)
            }

        entityTest.addIntegerArgument("time")
            .addExecution<Player> {
                val type = getArgument<EntityType>("type")
                val entity = quasar.createQuasarEntity(type)
                runTest(sender, entity, getArgument("time"))
            }

        mainCommand.register(this)

    }

    fun runTest(logger: Player, entity: Entity, time: Int = 10) {
        logger.sendMessage("${ChatColor.GREEN} Tests started [${ChatColor.AQUA}${entity.entityType.name.lowercase()}${ChatColor.GRAY}]")
        entity.addViewer(logger)
        entity.spawn(logger.location)
        logger.sendMessage("${ChatColor.GRAY} Entity | Spawning {${ChatColor.GREEN}Success!${ChatColor.GRAY}}")
        entity.runTest(logger,
            time,
            {
                if (it == null) {
                    logger.sendMessage("${ChatColor.GRAY} Entity | {${ChatColor.GREEN}Tests Passed!${ChatColor.GRAY}}")
                } else {
                    logger.sendMessage("${ChatColor.GRAY} Entity | ${ChatColor.RED} Tests failed! ${ChatColor.GRAY}[${ChatColor.AQUA}${it::class.java.simpleName}${ChatColor.GRAY}] Check console for more.")
                    Bukkit.getLogger().log(Level.SEVERE, it.message.toString())
                }
            }
            ,{
                if (it == null) {
                    logger.sendMessage("${ChatColor.GRAY} ${entity.entityType.name} | {${ChatColor.GREEN}Tests Passed!${ChatColor.GRAY}}")

                    entity.kill()

                    logger.sendMessage("${ChatColor.GRAY} Entity | Removing {${ChatColor.GREEN}Success!${ChatColor.GRAY}}")

                } else {
                    logger.sendMessage("${ChatColor.GRAY} ${entity.entityType.name} | ${ChatColor.RED} Tests failed! ${ChatColor.GRAY}[${ChatColor.AQUA}${it::class.java.simpleName}${ChatColor.GRAY}] Check console for more.")
                    Bukkit.getLogger().log(Level.SEVERE, it.message.toString())
                }
            }
        )
    }

    override fun onDisable() {

    }


}