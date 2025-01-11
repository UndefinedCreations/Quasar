package com.undefined.quasar.interfaces

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Item
import org.bukkit.inventory.ItemStack

interface LivingEntity : Entity {

    fun moveTo(location: Location)
    fun moveTo(entity: org.bukkit.entity.Entity) { moveTo(entity.location) }

    fun moveOrTeleport(location: Location) =
        if (getLocation().distance(location) > 8) teleport(location) else moveTo(location)
    fun moveOrTeleport(entity: org.bukkit.entity.Entity) { moveOrTeleport(entity.location) }

    fun deathAnimation()
    fun damageAnimation()

    fun getItem(slot: Int): ItemStack?
    fun getItem(equipmentSlot: EquipmentSlot) = getItem(equipmentSlot.slot)

    fun setItem(slot: Int, itemStack: ItemStack?)
    fun setItem(slot: Int, material: Material) = setItem(slot, ItemStack(material))
    fun setItem(equipmentSlot: EquipmentSlot, itemStack: ItemStack?) = setItem(equipmentSlot.slot, itemStack)
    fun setItem(equipmentSlot: EquipmentSlot, material: Material) = setItem(equipmentSlot.slot, material)

    fun clearItems() = EquipmentSlot.entries.forEach { setItem(it, Material.AIR) }

    enum class EquipmentSlot(val slot: Int) {
        MAINHAND(0),
        OFFHAND(40),
        CHEST(38),
        LEGS(37),
        HEAD(39),
        FEET(36)
    }
}