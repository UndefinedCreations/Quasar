package com.undefined.quasar

import com.google.gson.GsonBuilder
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.Entity
import com.undefined.stellar.StellarCommand
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

class Main : JavaPlugin() {

    private val spawnedEntities: MutableList<Entity> = mutableListOf()

    lateinit var quasar: Quasar

    override fun onEnable() {
        quasar = Quasar(this)

        val mainCommand = StellarCommand("quasar")

        val testAll = mainCommand.addArgument("testAll")
            .addExecution<Player> { runAllTests(sender, sender.location) }

        testAll.addLocationArgument("location")
            .addExecution<Player> { runAllTests(sender, getArgument("location")) }
            .addIntegerArgument("time")
            .addExecution<Player> { runAllTests(sender, getArgument("location"), getArgument("time")) }
            .addIntegerArgument("perRow")
            .addExecution<Player> { runAllTests(sender, getArgument("location"), getArgument("time"), getArgument("perRow")) }



        mainCommand.addArgument("randomEntityData")
            .addExecution<Player> {
                sender.sendMessage(GsonBuilder().setPrettyPrinting().create().toJson(spawnedEntities.random().getEntityData()))
            }

        val entityArgument = mainCommand.addArgument("entities").addEnumArgument<EntityType>("type")
        entityArgument.addArgument("spawn")
            .addExecution<Player> {
                val type = getArgument<EntityType>("type")
                val entity = quasar.createQuasarEntity(type)
                entity.addViewer(sender)
                spawnedEntities.add(entity)
                val location = sender.location
                entity.spawn(location)
                sender.sendMessage("${ChatColor.GREEN} $type has been spawned at [${Math.round(location.x)}, ${Math.round(location.y)}, ${Math.round(location.z)}]")
            }

        entityArgument.addArgument("kill")
            .addExecution<Player> {
                val type = getArgument<EntityType>("type")
                val entities = spawnedEntities.filter { it.entityType == type }
                entities.forEach { it.kill() }
                spawnedEntities.removeAll(entities)
                sender.sendMessage("${ChatColor.RED} All version of type ${type.name.lowercase()} as been removed! [${entities.size}]")
            }

        val runTestArgument = entityArgument.addArgument("run-test")
            .addExecution<Player> {
                val type = getArgument<EntityType>("type")
                val entity = quasar.createQuasarEntity(type)
                runTest(sender, entity, sender.location)
            }

        runTestArgument.addIntegerArgument("time")
            .addExecution<Player> {
                val type = getArgument<EntityType>("type")
                val entity = quasar.createQuasarEntity(type)
                runTest(sender, entity, sender.location, getArgument<Int>("time"))
            }

        mainCommand.register(this)
    }

    private fun runAllTests(logger: Player, location: Location, time: Int = 10, perRow: Int = 5) {
        var inRow = 1.0
        var row = 0.0
        EntityType.entries.forEach {
            val testLocation = location.clone().add(5*inRow, 0.0, 5*row)
            if (inRow.toInt() == perRow) {
                inRow = 1.0
                row++
            } else {
                inRow++
            }
            runTest(logger, quasar.createQuasarEntity(it), testLocation, time, false)
        }
    }

    private fun runTest(logger: Player, entity: Entity, location: Location, time: Int = 10, sendMessage: Boolean = true) {
        logger.sendMessage("${ChatColor.GRAY} ${entity.entityType.name} | {${ChatColor.GREEN}Tests Started!${ChatColor.GRAY}}")
        entity.addViewer(logger)
        entity.spawn(location)
        if (sendMessage) logger.sendMessage("${ChatColor.GRAY} ${entity.entityType.name} | Spawning {${ChatColor.GREEN}Success!${ChatColor.GRAY}}")
        entity.runTest(logger,
            time,
            {
                logger.sendMessage("${ChatColor.GRAY} ${entity.entityType.name} | ERROR ${ChatColor.RED} Tests failed! ${ChatColor.GRAY}[${ChatColor.AQUA}${it::class.java.simpleName}${ChatColor.GRAY}] Check console for more.")
                Bukkit.getLogger().log(Level.SEVERE, it.stackTraceToString())
            }, {
                finishTest(logger, entity)
            },
            sendMessage
        )
    }

    private fun finishTest(logger: Player, entity: Entity) {
        entity.kill()
        logger.sendMessage("${ChatColor.GRAY} ${entity.entityType.name} | {${ChatColor.GREEN}Tests Passed!${ChatColor.GRAY}}")
    }

    override fun onDisable() {

    }
}