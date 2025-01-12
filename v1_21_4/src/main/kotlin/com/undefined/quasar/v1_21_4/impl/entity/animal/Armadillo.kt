package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Armadillo
import com.undefined.quasar.util.getPrivateField
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.animal.armadillo.Armadillo.ArmadilloState
import net.minecraft.world.level.Level
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class Armadillo : LivingEntity(EntityType.ARMADILLO), Armadillo {

    private var state = Armadillo.State.IDLE
    private var ARMADILLO_STATE: EntityDataAccessor<ArmadilloState>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(
                net.minecraft.world.entity.animal.armadillo.Armadillo::class.java,
                FieldMappings.Entity.LivingEntity.Mob.Animal.Armadillo.ARMADILLO_STATE
            )
            return field
        }
    override fun setState(state: Armadillo.State) {
        val entity = entity ?: return
        this.state = state
        entity.entityData.set(ARMADILLO_STATE, getNMSState())
        sendEntityMetaData()
    }
    override fun getState(): Armadillo.State = state
    private fun getNMSState(): ArmadilloState = ArmadilloState.valueOf(state.name)
    override fun getEntityData(): JsonObject {
        val animalJson = super.getEntityData()
        val armadilloJson = JsonObject()
        armadilloJson.addProperty("state", state.name)
        animalJson.add("armadillo", armadilloJson)
        return animalJson
    }
    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val armadilloJson = jsonObject["armadillo"].asJsonObject
        state = Armadillo.State.valueOf(armadilloJson["state"].asString)
    }
    override fun updateEntity() {
        super.updateEntity()
        setState(state)
    }
    override fun runTest(logger: Player, delayTime: Int, testStage: (Exception?) -> Unit, done: (Unit) -> Unit): Int {
        super.runTest(logger, delayTime, { e ->
            trycatch({
                if (e != null) return@trycatch
                var time = 0

                com.undefined.quasar.util.repeat(5, delayTime) {
                    when (time) {
                        0 -> {
                            val state = Armadillo.State.ROLLING
                            setState(state)
                            logger.sendMessage("${ChatColor.GRAY} Armadillo | Set state {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${state.name.lowercase()}${ChatColor.GRAY}]")
                        }
                        1 -> {
                            val state = Armadillo.State.IDLE
                            setState(state)
                            logger.sendMessage("${ChatColor.GRAY} Armadillo | Set state {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${state.name.lowercase()}${ChatColor.GRAY}]")
                        }
                        2 -> {
                            val state = Armadillo.State.SCARED
                            setState(state)
                            logger.sendMessage("${ChatColor.GRAY} Armadillo | Set state {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${state.name.lowercase()}${ChatColor.GRAY}]")
                        }
                        3 -> {
                            val state = Armadillo.State.UNROLLING
                            setState(state)
                            logger.sendMessage("${ChatColor.GRAY} Armadillo | Set state {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${state.name.lowercase()}${ChatColor.GRAY}]")
                        }
                        4 -> done(Unit)
                    }
                    time++
                }

            }, testStage)
        }, done)

        return 3
    }
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.armadillo.Armadillo(net.minecraft.world.entity.EntityType.ARMADILLO, level)
}