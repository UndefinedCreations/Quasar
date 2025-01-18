package com.undefined.quasar.v1_21_4.impl.entity.display

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.display.ItemDisplay
import com.undefined.quasar.util.ItemStackDeserializer
import com.undefined.quasar.util.serializer
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

class ItemDisplay : Display(EntityType.ITEM_DISPLAY), ItemDisplay {

    private var DATA_ITEM_STACK_ID: EntityDataAccessor<net.minecraft.world.item.ItemStack>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display.ItemDisplay::class.java,
            FieldMappings.Entity.Display.ItemDisplay.DATA_ITEM_STACK_ID
        )

    private var DATA_ITEM_DISPLAY_ID: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display.ItemDisplay::class.java,
            FieldMappings.Entity.Display.ItemDisplay.DATA_ITEM_DISPLAY_ID
        )

    override fun setItem(itemStack: ItemStack) = setEntityDataAccessor(DATA_ITEM_STACK_ID, CraftItemStack.asNMSCopy(itemStack))

    override fun getItem(): ItemStack = getEntityDataValue(DATA_ITEM_STACK_ID)?.let { CraftItemStack.asBukkitCopy(it) } ?: ItemStack(Material.AIR)

    override fun setContext(context: ItemDisplay.Context) = setEntityDataAccessor(DATA_ITEM_DISPLAY_ID, context.id)

    override fun getContext(): ItemDisplay.Context = getEntityDataValue(DATA_ITEM_DISPLAY_ID)?.let { data ->
        ItemDisplay.Context.entries.first { it.id == data } } ?: ItemDisplay.Context.NONE

    override fun getEntityData(): JsonObject {
        val displayJson = super.getEntityData()
        val itemDisplayJson = JsonObject()
        itemDisplayJson.addProperty("item", getItem().serializer())
        itemDisplayJson.addProperty("context", getContext().id)
        displayJson.add("itemDisplay", itemDisplayJson)
        return displayJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Display>.setEntityData(jsonObject)
        val itemDisplayJson = jsonObject["itemDisplay"].asJsonObject
        setItem(ItemStackDeserializer.deserializer(itemDisplayJson["item"].asString))
        setContext(ItemDisplay.Context.entries.first { it.id == jsonObject["context"].asByte })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setItem(ItemStack(Material.STONE))
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.Display.ItemDisplay(net.minecraft.world.entity.EntityType.ITEM_DISPLAY, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setItem(ItemStack(Material.entries.random()))
                getTestMessage(this@ItemDisplay::class, "Set item", getItem().type.name.lowercase())
            }
        ))
        addAll(ItemDisplay.Context.entries.map {
            {
                setContext(it)
                getTestMessage(this@ItemDisplay::class, "Set context", getContext().name.lowercase())
            }
        })}
}