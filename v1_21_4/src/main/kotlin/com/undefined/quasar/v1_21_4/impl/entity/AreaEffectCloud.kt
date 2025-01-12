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
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.entity.Player
import kotlin.random.Random

class AreaEffectCloud : Entity(EntityType.AREA_EFFECT_CLOUD), AreaEffectCloud {
    private var radius = 3f
    private var color = Color.PURPLE
    private var DATA_RADIUS: EntityDataAccessor<Float>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(
                net.minecraft.world.entity.AreaEffectCloud::class.java,
                FieldMappings.Entity.AreaEffectCloud.DATA_RADIUS
            )
            return field
        }
    private var DATA_PARTICLE: EntityDataAccessor<ParticleOptions>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(
                net.minecraft.world.entity.AreaEffectCloud::class.java,
                FieldMappings.Entity.AreaEffectCloud.DATA_PARTICLE
            )
            return field
        }
    override fun isInCloud(location: Location): Boolean = if (getLocation().distance(location) <= radius) true else false
    override fun setParticleColor(color: Color) {
        val entity = entity ?: return
        this.color = color
        entity.entityData.set(DATA_PARTICLE,
            ColorParticleOption.create(
                ParticleTypes.ENTITY_EFFECT, color.red.toFloat(), color.green.toFloat(), color.blue.toFloat()
            )
        )
        sendEntityMetaData()
    }
    override fun getParticleColor(): Color? = color
    override fun getRadius(): Float = radius
    override fun setRadius(float: Float) {
        val entity = entity ?: return
        this.radius = float
        entity.entityData.set(DATA_RADIUS,
            float
        )
        sendEntityMetaData()
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
    override fun runTest(logger: Player, delayTime: Int, testStage: (Exception?) -> Unit, done: (Unit) -> Unit): Int {
        super.runTest(logger, delayTime, { e ->
            trycatch({
                if (e != null) return@trycatch
                var time = 0

                com.undefined.quasar.util.repeat(4, delayTime) {
                    when (time) {
                        0 -> {
                            val color = Color.fromRGB(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
                            setParticleColor(color)
                            logger.sendMessage("${ChatColor.GRAY} Area Effect Cloud | Set particle color {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${color.red}, ${color.green}, ${color.blue}${ChatColor.GRAY}]")
                        }
                        1 -> {
                            val radius = Random.nextDouble(25.0)
                            setRadius(radius)
                            logger.sendMessage("${ChatColor.GRAY} Area Effect Cloud | Set radius {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${radius}${ChatColor.GRAY}]")
                        }
                        2 -> {

                            logger.sendMessage("${ChatColor.GRAY} Area Effect Cloud | Set radius {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${isInCloud(logger)}${ChatColor.GRAY}]")
                        }
                        3 -> done(Unit)
                    }
                    time++
                }

            }, testStage)
        }, done)

        return 3
    }
    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.AreaEffectCloud(net.minecraft.world.entity.EntityType.AREA_EFFECT_CLOUD, level)
}