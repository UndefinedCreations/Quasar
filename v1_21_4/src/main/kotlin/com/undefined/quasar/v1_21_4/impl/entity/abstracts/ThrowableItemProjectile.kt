package com.undefined.quasar.v1_21_4.impl.entity.abstracts

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.abstracts.ThrowableItemProjectile
import com.undefined.quasar.util.ItemStackDeserializer
import com.undefined.quasar.util.serializer
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

abstract class ThrowableItemProjectile(entityType: EntityType) : Entity(entityType), ThrowableItemProjectile {

    private var item: ItemStack = ItemStack(Material.STONE)

    private var DATA_ITEM_STACK: EntityDataAccessor<net.minecraft.world.item.ItemStack>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.projectile.ThrowableItemProjectile::class.java,
            FieldMappings.Entity.Projectile.ThrowableItemProjectile.DATA_ITEM_STACK
        )

    override fun setItem(item: ItemStack) =
        setEntityDataAccessor(DATA_ITEM_STACK, CraftItemStack.asNMSCopy(item)) {
            this.item = item
        }

    override fun getItem(): ItemStack? = item

    override fun getEntityData(): JsonObject {
        val projectileJson = super.getEntityData()
        val throwableItemProjectileJson = JsonObject()
        throwableItemProjectileJson.addProperty("items", item.serializer())
        projectileJson.add("throwableItemProjectile", throwableItemProjectileJson)
        return projectileJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val throwableItemProjectileJson = jsonObject["throwableItemProjectile"].asJsonObject
        item = ItemStackDeserializer.deserializer(throwableItemProjectileJson["item"].asString)
    }

    override fun updateEntity() {
        super.updateEntity()
        setItem(item)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setItem(ItemStack(Material.entries.random()))
                getTestMessage(this@ThrowableItemProjectile::class, "Set throwable item", getItem()?.type?.name?.lowercase()!!)
            }
        )) }
}