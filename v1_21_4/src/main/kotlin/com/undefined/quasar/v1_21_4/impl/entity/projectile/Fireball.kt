package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.projectile.Fireball
import com.undefined.quasar.util.ItemStackDeserializer
import com.undefined.quasar.util.serializer
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

abstract class Fireball(entityType: EntityType) : Entity(entityType), Fireball {

    private var item: ItemStack = ItemStack(Material.FIRE_CHARGE)

    private var DATA_ITEM_STACK: EntityDataAccessor<net.minecraft.world.item.ItemStack>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.projectile.Fireball::class.java,
            FieldMappings.Entity.Projectile.FireBall.DATA_ITEM_STACK
        )

    override fun setItem(item: ItemStack?) =
        setEntityDataAccessor(DATA_ITEM_STACK, CraftItemStack.asNMSCopy(item ?: ItemStack(Material.FIRE_CHARGE))) {
            this.item = item ?: ItemStack(Material.FIRE_CHARGE)
        }

    override fun getItem(): ItemStack? = item

    override fun getEntityData(): JsonObject {
        val projectileJson = super.getEntityData()
        val fireBallJson = JsonObject()
        fireBallJson.addProperty("item", item.serializer())
        projectileJson.add("fireball", fireBallJson)
        return projectileJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val fireBallJson = jsonObject["fireball"].asJsonObject
        item = ItemStackDeserializer.deserializer(fireBallJson["item"].asString)
    }

    override fun updateEntity() {
        super.updateEntity()
        setItem(item)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setItem(ItemStack(Material.entries.filter { it.isBlock }.random()))
                getTestMessage(this@Fireball::class, "Set item", getItem()?.type?.name?.lowercase()!!)
            }
        )) }
}