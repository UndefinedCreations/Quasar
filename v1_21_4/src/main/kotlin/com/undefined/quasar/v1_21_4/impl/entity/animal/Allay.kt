package com.undefined.quasar.v1_21_4.impl.entity.animal

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.animal.Allay
import com.undefined.quasar.v1_21_4.impl.entity.Animal
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Allay : Allay, Animal(EntityType.ALLAY) {

    override fun setItem(slot: Int, itemStack: ItemStack?) =
        super<Animal>.setItem(0, itemStack)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                val item = ItemStack(Material.entries.random())
                setHoldingItem(item)
                getTestMessage(this@Allay::class, "Set holding", item.type.name.lowercase())
            },
            {
                setHoldingItem(null)
                getTestMessage(this@Allay::class, "Set holding", "EMPTY")
            }
        )) }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.animal.allay.Allay(net.minecraft.world.entity.EntityType.ALLAY, level)

}