package com.undefined.quasar.v1_21_4.impl.entity.item

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.item.FallingBlockEntity
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.craftbukkit.v1_21_R3.CraftWorld
import org.bukkit.craftbukkit.v1_21_R3.block.data.CraftBlockData

class FallingBlockEntity : Entity(EntityType.FALLING_BLOCK), FallingBlockEntity {

    private var blockData = Material.STONE.createBlockData()

    override fun setFallingBlock(block: BlockData) {
        blockData = block
        kill()
        spawn(getLocation())
    }

    override fun getFallingBlock(): BlockData = blockData

    override fun spawn(location: Location) {

        val craftWorld = location.world as CraftWorld
        this.entity = getEntityClass(craftWorld.handle)

        net.minecraft.world.entity.item.FallingBlockEntity::class.java.getDeclaredField("g").apply {
            isAccessible = true
            set(entity, (blockData as CraftBlockData).state)
        }

        super.spawn(location)
    }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.item.FallingBlockEntity(net.minecraft.world.entity.EntityType.FALLING_BLOCK, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                getTestMessage(this@FallingBlockEntity::class, "Get location", getLocation().y)
            },
            {
                setFallingBlock(Material.entries.filter { it.isBlock }.random().createBlockData())
                getTestMessage(this@FallingBlockEntity::class, "Set block data", getFallingBlock().material.name.lowercase())
            }
        )) }
}