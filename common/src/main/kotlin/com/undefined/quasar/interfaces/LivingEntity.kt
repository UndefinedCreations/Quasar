package com.undefined.quasar.interfaces

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

interface LivingEntity : Entity {

    fun useItem(offhand: Boolean)
    fun stopUsingItem()
    fun isUsingItem(offhand: Boolean): Boolean

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