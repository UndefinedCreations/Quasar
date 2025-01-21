package com.undefined.quasar.interfaces.entities.entity.item

import com.undefined.quasar.interfaces.Entity
import org.bukkit.Material

interface PrimedTnt : Entity {

    fun setBlock(material: Material)
    fun getBlock(): Material

}