package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.projectile.FireworkRocketEntity
import com.undefined.quasar.util.ItemStackDeserializer
import com.undefined.quasar.util.serializer
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.level.Level
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

class FireworkRocketEntity : Entity(EntityType.FIREWORK_ROCKET_ENTITY), FireworkRocketEntity {


    private var DATA_ID_FIREWORKS_ITEM: EntityDataAccessor<net.minecraft.world.item.ItemStack>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.projectile.FireworkRocketEntity::class.java,
            FieldMappings.Entity.Projectile.FireworkRocketEntity.DATA_ID_FIREWORKS_ITEM
        )

    private var DATA_SHOT_AT_ANGLE: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.projectile.FireworkRocketEntity::class.java,
            FieldMappings.Entity.Projectile.FireworkRocketEntity.DATA_SHOT_AT_ANGLE
        )

    override fun setItem(item: ItemStack) = setEntityDataAccessor(DATA_ID_FIREWORKS_ITEM, CraftItemStack.asNMSCopy(item))

    override fun getItem(): ItemStack = getEntityDataValue(DATA_ID_FIREWORKS_ITEM)?.let { CraftItemStack.asBukkitCopy(it) } ?: ItemStack(Material.FIREWORK_ROCKET)

    override fun setShotAtAngle(shotAtAngle: Boolean) = setEntityDataAccessor(DATA_SHOT_AT_ANGLE, shotAtAngle)

    override fun isShotAtAngle(): Boolean = getEntityDataValue(DATA_SHOT_AT_ANGLE) ?: false

    override fun getEntityData(): JsonObject {
        val projectileJson = super.getEntityData()
        val fireworkRocketJson = JsonObject()
        fireworkRocketJson.addProperty("item", getItem().serializer())
        fireworkRocketJson.addProperty("shotAtAngle", isShotAtAngle())
        projectileJson.add("fireworkRocket", fireworkRocketJson)
        return projectileJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val fireworkRocket = jsonObject["fireworkRocket"].asJsonObject
        setItem(ItemStackDeserializer.deserializer(fireworkRocket["item"].asString))
        setShotAtAngle(fireworkRocket["shotAtAngle"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setItem(ItemStack(Material.FIREWORK_ROCKET))
        setShotAtAngle(false)
    }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.projectile.FireworkRocketEntity(net.minecraft.world.entity.EntityType.FIREWORK_ROCKET, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setItem(ItemStack(Material.entries.random()))
                getTestMessage(this@FireworkRocketEntity::class, "Set item", getItem().type.name.lowercase())
            },
            {
                setShotAtAngle(true)
                getTestMessage(this@FireworkRocketEntity::class, "Set shot at angle", isShotAtAngle())
            },
            {
                setShotAtAngle(false)
                getTestMessage(this@FireworkRocketEntity::class, "Set shot at angle", isShotAtAngle())
            }
        )) }
}