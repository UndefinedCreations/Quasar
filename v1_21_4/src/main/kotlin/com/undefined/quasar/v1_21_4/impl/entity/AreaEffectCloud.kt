package com.undefined.quasar.v1_21_4.impl.entity

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.AreaEffectCloud
import com.undefined.quasar.util.getPrivateField
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
    private var radius = 3f
    private var color = Color.PURPLE
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

    override fun isInCloud(location: Location): Boolean = if (getLocation().distance(location) <= radius) true else false

    override fun setParticleColor(color: Color) =
        setEntityDataAccessor(DATA_PARTICLE,
            ColorParticleOption.create(
                ParticleTypes.ENTITY_EFFECT, color.red.toFloat(), color.green.toFloat(), color.blue.toFloat()
            )) { this.color = color }

    override fun getParticleColor(): Color? = color

    override fun getRadius(): Float = radius

    override fun setRadius(float: Float) =
        setEntityDataAccessor(DATA_RADIUS, float) {
            this.radius = float
        }

    override fun getEntityData(): JsonObject {
        val entityJson = super.getEntityData()
        val areaEffectCloudJson = JsonObject()
        areaEffectCloudJson.addProperty("radius", radius)
        areaEffectCloudJson.addProperty("color", color.asARGB())
        entityJson.add("areaEffectCloud", areaEffectCloudJson)
        return entityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val areaEffectCloudJson = jsonObject["areaEffectCloud"].asJsonObject
        this.radius = areaEffectCloudJson["radius"].asFloat
        this.color = Color.fromARGB(areaEffectCloudJson["color"].asInt)
    }

    override fun updateEntity() {
        super.updateEntity()
        setParticleColor(color)
        setRadius(radius)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                val color = Color.fromRGB(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
                setParticleColor(color)
                getTestMessage(this::class, "Set particle color", color.red, color.green, color.blue)
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