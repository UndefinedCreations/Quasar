package com.undefined.quasar.interfaces.entities.entity

import com.undefined.quasar.interfaces.Entity
import org.bukkit.inventory.ItemStack

interface OminousItemSpawner : Entity {

    fun setItem(item: ItemStack)
    fun getItem(): ItemStack

}