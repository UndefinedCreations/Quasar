package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.projectile.Arrow
import com.undefined.quasar.util.getPrivateField
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.level.Level
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.entity.Player
import kotlin.random.Random

class Arrow : Entity(EntityType.ARROW), Arrow {

    private var color: Color? = null
    private var ID_EFFECT_COLOR: EntityDataAccessor<Int>? = null
        get() {
            if (field != null) return field
            if (entity == null) return null
            field = entity!!.getPrivateField(
                net.minecraft.world.entity.projectile.Arrow::class.java,
                FieldMappings.Entity.Projectile.ARROW.ID_EFFECT_COLOR
            )
            return field
        }
    override fun getEffectColor(): Color? = color
    override fun setEffectColor(color: Color?) {
        val entity = entity ?: return
        this.color = color
        entity.entityData.set(ID_EFFECT_COLOR, color?.asARGB() ?: -1)
        sendEntityMetaData()
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
    override fun runTest(logger: Player, delayTime: Int, testStage: (Exception?) -> Unit, done: (Unit) -> Unit): Int {
        super.runTest(logger, delayTime, { e ->
            trycatch({
                if (e != null) return@trycatch
                var time = 0

                com.undefined.quasar.util.repeat(2, delayTime) {
                    when (time) {
                        0 -> {
                            val color = Color.fromRGB(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
                            setEffectColor(color)
                            logger.sendMessage("${ChatColor.GRAY} Arrow | Set arrow color {${ChatColor.GREEN}Success!${ChatColor.GRAY}} [${ChatColor.AQUA}${color.red}, ${color.green}, ${color.blue}${ChatColor.GRAY}]")
                        }
                        1 -> done(Unit)
                    }
                    time++
                }

            }, testStage)
        }, done)

        return 3
    }
    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.projectile.Arrow(net.minecraft.world.entity.EntityType.ARROW, level)
}