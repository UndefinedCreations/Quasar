package com.undefined.quasar.v1_21_4.impl.entity

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.AreaEffectCloud
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.core.particles.ColorParticleOption
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.level.Level
import org.bukkit.Color
import org.bukkit.Location
import kotlin.random.Random

class AreaEffectCloud : Entity(EntityType.AREA_EFFECT_CLOUD), AreaEffectCloud {

    private var DATA_RADIUS: EntityDataAccessor<Float>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.AreaEffectCloud::class.java,
            FieldMappings.Entity.AreaEffectCloud.DATA_RADIUS
        )
    private var DATA_PARTICLE: EntityDataAccessor<ParticleOptions>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.AreaEffectCloud::class.java,
            FieldMappings.Entity.AreaEffectCloud.DATA_PARTICLE
        )

    override fun isInCloud(location: Location): Boolean = if (getLocation().distance(location) <= getRadius()) true else false

    override fun setParticleColor(color: Color) =
        setEntityDataAccessor(DATA_PARTICLE,
            ColorParticleOption.create(
                ParticleTypes.ENTITY_EFFECT, color.red.toFloat(), color.green.toFloat(), color.blue.toFloat()
            ))

    override fun getParticleColor(): Color = getEntityDataValue(DATA_PARTICLE)?.let { data ->
        (data as ColorParticleOption).let { Color.fromARGB( it.alpha.toInt(), it.red.toInt(), it.green.toInt(), it.blue.toInt()) }
    } ?: Color.PURPLE

    override fun getRadius(): Float = getEntityDataValue(DATA_RADIUS) ?: 3f

    override fun setRadius(float: Float) = setEntityDataAccessor(DATA_RADIUS, float)

    override fun getEntityData(): JsonObject {
        val entityJson = super.getEntityData()
        val areaEffectCloudJson = JsonObject()
        areaEffectCloudJson.addProperty("radius", getRadius())
        areaEffectCloudJson.addProperty("color", getParticleColor().asARGB())
        entityJson.add("areaEffectCloud", areaEffectCloudJson)
        return entityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val areaEffectCloudJson = jsonObject["areaEffectCloud"].asJsonObject
        setRadius(areaEffectCloudJson["radius"].asFloat)
        setParticleColor(Color.fromARGB(areaEffectCloudJson["color"].asInt))
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setParticleColor(Color.PURPLE)
        setRadius(3f)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                val color = Color.fromRGB(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
                setParticleColor(color)
                getTestMessage(this::class, "Set particle color", getParticleColor().red, getParticleColor().green, getParticleColor().blue)
            },
            {
                val radius = Random.nextDouble(25.0)
                setRadius(radius)
                getTestMessage(this::class, "Set radius", getRadius())
            }
        )) }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.AreaEffectCloud(net.minecraft.world.entity.EntityType.AREA_EFFECT_CLOUD, level)
}