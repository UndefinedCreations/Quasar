package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Allay
import com.undefined.quasar.util.repeat
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Allay : Allay, LivingEntity(EntityType.ALLAY) {

    override fun setItem(slot: Int, itemStack: ItemStack?) =
        super<LivingEntity>.setItem(0, itemStack)


    override fun runTest(logger: Player, delayTime: Int, testStage: (Exception?) -> Unit, done: (Unit) -> Unit): Int {
        super.runTest(logger, delayTime, { e ->
            trycatch({
                if (e != null) return@trycatch
                var time = 0

                repeat(3, delayTime) {
                    when(time) {
                        0 -> {
                            val item = ItemStack(Material.entries.random())
                            setHoldingItem(item)
                            logger.sendMessage("${ChatColor.GRAY} Allay | Set holding {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${item.type.name.lowercase()}${ChatColor.GRAY}]")
                        }
                        1 -> {
                            setHoldingItem(null)
                            logger.sendMessage("${ChatColor.GRAY} Allay | Set holding {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}EMPTY${ChatColor.GRAY}]")
                        }
                        2 -> done(Unit)
                    }
                    time++
                }

            }, testStage)
        }, done)

        return 4
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.allay.Allay(net.minecraft.world.entity.EntityType.ALLAY, level)
}