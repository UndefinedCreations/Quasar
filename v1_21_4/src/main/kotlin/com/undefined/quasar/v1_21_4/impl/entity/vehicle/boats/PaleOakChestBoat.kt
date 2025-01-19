package com.undefined.quasar.v1_21_4.impl.entity.vehicle.boats

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.vehicle.boat.PaleOakChestBoat
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.BoatItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level

class PaleOakChestBoat : Boat(EntityType.PALE_OAK_CHEST_BOAT), PaleOakChestBoat {
    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.vehicle.ChestBoat(net.minecraft.world.entity.EntityType.PALE_OAK_CHEST_BOAT, level
        ) { BoatItem(net.minecraft.world.entity.EntityType.PALE_OAK_CHEST_BOAT, Item.Properties().stacksTo(1)) }
}