package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.projectile.Arrow
import com.undefined.quasar.util.getPrivateField
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.level.Level
import org.bukkit.Color
import kotlin.random.Random

class Arrow : Entity(EntityType.ARROW), Arrow {

    private var color: Color? = null
    private var ID_EFFECT_COLOR: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.projectile.Arrow::class.java,
            FieldMappings.Entity.Projectile.ARROW.ID_EFFECT_COLOR
        )

    override fun getEffectColor(): Color? = color

    override fun setEffectColor(color: Color?) =
        setEntityDataAccessor(ID_EFFECT_COLOR, color?.asARGB() ?: -1) {
            this.color = color
        }

    override fun getEntityData(): JsonObject {
        val projectileJson = super.getEntityData()
        val arrowJson = JsonObject()
        arrowJson.addProperty("color", color?.asARGB() ?: -1)
        projectileJson.add("arrow", arrowJson)
        return projectileJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val arrowJson = jsonObject["arrow"].asJsonObject
        this.color = Color.fromARGB(arrowJson["color"].asInt)
    }

    override fun updateEntity() {
        super.updateEntity()
        setEffectColor(color)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                val color = Color.fromRGB(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
                setEffectColor(color)
                getTestMessage(this@Arrow::class, "Set arrow effect color", color.red, color.green, color.blue)
            }
        )) }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.projectile.Arrow(net.minecraft.world.entity.EntityType.ARROW, level)
}