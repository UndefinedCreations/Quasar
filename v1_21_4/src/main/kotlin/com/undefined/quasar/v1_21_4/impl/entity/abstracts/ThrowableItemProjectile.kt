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

    private var DATA_ITEM_STACK: EntityDataAccessor<net.minecraft.world.item.ItemStack>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.projectile.ThrowableItemProjectile::class.java,
            FieldMappings.Entity.Projectile.ThrowableItemProjectile.DATA_ITEM_STACK
        )

    override fun setItem(item: ItemStack) =
        setEntityDataAccessor(DATA_ITEM_STACK, CraftItemStack.asNMSCopy(item))

    override fun getItem(): ItemStack? = CraftItemStack.asBukkitCopy(getEntityDataValue(DATA_ITEM_STACK))

    override fun getEntityData(): JsonObject {
        val projectileJson = super.getEntityData()
        val throwableItemProjectileJson = JsonObject()
        throwableItemProjectileJson.addProperty("items", getItem()?.serializer())
        projectileJson.add("throwableItemProjectile", throwableItemProjectileJson)
        return projectileJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val throwableItemProjectileJson = jsonObject["throwableItemProjectile"].asJsonObject
        setItem(ItemStackDeserializer.deserializer(throwableItemProjectileJson["item"].asString))
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setItem(ItemStack(Material.STONE))
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setItem(ItemStack(Material.entries.random()))
                getTestMessage(this@ThrowableItemProjectile::class, "Set throwable item", getItem()?.type?.name?.lowercase()!!)
            }
        )) }
}