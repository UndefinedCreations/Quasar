package com.undefined.quasar.v1_21_4.impl.entity.display

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.display.Display
import com.undefined.quasar.util.Vector
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import org.bukkit.ChatColor
import org.bukkit.Color
import org.joml.Quaternionf
import org.joml.Vector3f
import kotlin.random.Random

abstract class Display(entityType: EntityType) : Entity(entityType), Display {

    private var DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display::class.java,
            FieldMappings.Entity.Display.DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID
        )
    private var DATA_POS_ROT_INTERPOLATION_DURATION_ID: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display::class.java,
            FieldMappings.Entity.Display.DATA_POS_ROT_INTERPOLATION_DURATION_ID
        )
    private var DATA_TRANSLATION_ID: EntityDataAccessor<Vector3f>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display::class.java,
            FieldMappings.Entity.Display.DATA_TRANSLATION_ID
        )
    private var DATA_SCALE_ID: EntityDataAccessor<Vector3f>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display::class.java,
            FieldMappings.Entity.Display.DATA_SCALE_ID
        )
    private var DATA_LEFT_ROTATION_ID: EntityDataAccessor<Quaternionf>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display::class.java,
            FieldMappings.Entity.Display.DATA_LEFT_ROTATION_ID
        )
    private var DATA_RIGHT_ROTATION_ID: EntityDataAccessor<Quaternionf>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display::class.java,
            FieldMappings.Entity.Display.DATA_RIGHT_ROTATION_ID
        )
    private var DATA_BILLBOARD_RENDER_CONSTRAINTS_ID: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display::class.java,
            FieldMappings.Entity.Display.DATA_BILLBOARD_RENDER_CONSTRAINTS_ID
        )
    private var DATA_BRIGHTNESS_OVERRIDE_ID: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display::class.java,
            FieldMappings.Entity.Display.DATA_BRIGHTNESS_OVERRIDE_ID
        )
    private var DATA_VIEW_RANGE_ID: EntityDataAccessor<Float>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display::class.java,
            FieldMappings.Entity.Display.DATA_VIEW_RANGE_ID
        )
    private var DATA_SHADOW_RADIUS_ID: EntityDataAccessor<Float>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display::class.java,
            FieldMappings.Entity.Display.DATA_SHADOW_RADIUS_ID
        )
    private var DATA_SHADOW_STRENGTH_ID: EntityDataAccessor<Float>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display::class.java,
            FieldMappings.Entity.Display.DATA_SHADOW_STRENGTH_ID
        )
    private var DATA_WIDTH_ID: EntityDataAccessor<Float>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display::class.java,
            FieldMappings.Entity.Display.DATA_WIDTH_ID
        )
    private var DATA_HEIGHT_ID: EntityDataAccessor<Float>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display::class.java,
            FieldMappings.Entity.Display.DATA_HEIGHT_ID
        )
    private var DATA_GLOW_COLOR_OVERRIDE_ID: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display::class.java,
            FieldMappings.Entity.Display.DATA_GLOW_COLOR_OVERRIDE_ID
        )

    override fun setInterpolationDurtation(duration: Int) = setEntityDataAccessor(DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID, duration)

    override fun getInterpolationDuration(): Int = getEntityDataValue(DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID) ?: 0

    override fun setRotationInterpolationDurtation(duration: Int) = setEntityDataAccessor(DATA_POS_ROT_INTERPOLATION_DURATION_ID, duration)

    override fun getRotationInterpolationDuration(): Int = getEntityDataValue(DATA_POS_ROT_INTERPOLATION_DURATION_ID) ?: 0

    override fun setTranslation(vector: Vector) = setEntityDataAccessor(DATA_TRANSLATION_ID, vector.toNMS())

    override fun getTranslation(): Vector = getEntityDataValue(DATA_TRANSLATION_ID)?.let { vector3fToVector(it) } ?: Vector(0.0, 0.0, 0.0)

    override fun setScale(vector: Vector) = setEntityDataAccessor(DATA_SCALE_ID, vector.toNMS())

    override fun getScale(): Vector = getEntityDataValue(DATA_SCALE_ID)?.let { vector3fToVector(it) } ?: Vector(0.0, 0.0, 0.0)

    override fun setLeftRotation(rotation: Quaternionf) = setEntityDataAccessor(DATA_LEFT_ROTATION_ID, rotation)

    override fun getLeftRatation(): Quaternionf = getEntityDataValue(DATA_LEFT_ROTATION_ID) ?: Quaternionf()

    override fun setRightRotation(rotation: Quaternionf) = setEntityDataAccessor(DATA_RIGHT_ROTATION_ID, rotation)

    override fun getRightRatation(): Quaternionf = getEntityDataValue(DATA_RIGHT_ROTATION_ID) ?: Quaternionf()

    override fun setBrightness(brightness: Display.Brightness?) = setEntityDataAccessor(DATA_BRIGHTNESS_OVERRIDE_ID, brightness?.pack() ?: -1)

    override fun getBrightness(): Display.Brightness = getEntityDataValue(DATA_BRIGHTNESS_OVERRIDE_ID)?.let { data ->
        Display.Brightness.unpack(data)
    } ?: Display.Brightness(-1, -1)

    override fun setViewRange(range: Float) = setEntityDataAccessor(DATA_VIEW_RANGE_ID, range)

    override fun getViewRange(): Float = getEntityDataValue(DATA_VIEW_RANGE_ID) ?: 1.0f

    override fun setShadowRadius(radius: Float) = setEntityDataAccessor(DATA_SHADOW_RADIUS_ID, radius)

    override fun getShadowRadius(): Float = getEntityDataValue(DATA_SHADOW_RADIUS_ID) ?: 0f

    override fun setShadowStrength(strength: Float) = setEntityDataAccessor(DATA_SHADOW_STRENGTH_ID, strength)

    override fun getShadowStrength(): Float = getEntityDataValue(DATA_SHADOW_STRENGTH_ID) ?: 1f

    override fun setWidth(width: Float) = setEntityDataAccessor(DATA_WIDTH_ID, width)

    override fun getWidth(): Float = getEntityDataValue(DATA_WIDTH_ID) ?: 1f

    override fun setHeight(height: Float) = setEntityDataAccessor(DATA_HEIGHT_ID, height)

    override fun getHeight(): Float = getEntityDataValue(DATA_HEIGHT_ID) ?: 1f

    override fun setGlowColorOverride(color: Color) = setEntityDataAccessor(DATA_GLOW_COLOR_OVERRIDE_ID, color.asARGB())

    override fun getGlowColorOverride(): Color = getEntityDataValue(DATA_GLOW_COLOR_OVERRIDE_ID)?.let { Color.fromARGB(it) } ?: Color.WHITE

    override fun setBillboardConstraints(billboardConstraints: Display.BillboardConstraints) = setEntityDataAccessor(DATA_BILLBOARD_RENDER_CONSTRAINTS_ID, billboardConstraints.id)

    override fun getBillboardConstraints(): Display.BillboardConstraints = getEntityDataValue(DATA_BILLBOARD_RENDER_CONSTRAINTS_ID)?.let { data ->
        Display.BillboardConstraints.entries.first { it.id == data }
    } ?: Display.BillboardConstraints.FIXED

    private fun Vector.toNMS(): Vector3f = Vector3f(x.toFloat(), y.toFloat(), z.toFloat())

    override fun getEntityData(): JsonObject {
        val entityJson = super.getEntityData()
        val displayJson = JsonObject()
        displayJson.addProperty("interpolationDuration", getInterpolationDuration())
        displayJson.addProperty("rotationInterpolationDuration", getRotationInterpolationDuration())
        displayJson.add("translation", getTranslation().json())
        displayJson.add("scale", getScale().json())
        displayJson.add("leftRotation", quaternionToJson(getLeftRatation()))
        displayJson.add("rightRotation", quaternionToJson(getRightRatation()))
        displayJson.addProperty("brightness", getBrightness().pack())
        displayJson.addProperty("viewRange", getViewRange())
        displayJson.addProperty("shadowRadius", getShadowRadius())
        displayJson.addProperty("shadowStrength", getShadowStrength())
        displayJson.addProperty("width", getWidth())
        displayJson.addProperty("height", getHeight())
        displayJson.addProperty("colorOverride", getGlowColorOverride().asARGB())
        displayJson.addProperty("billboardConstraints", getBillboardConstraints().id)
        entityJson.add("display", displayJson)
        return entityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val displayJson = jsonObject["display"].asJsonObject
        setInterpolationDurtation(displayJson["interpolationDuration"].asInt)
        setRotationInterpolationDurtation(displayJson["rotationInterpolationDuration"].asInt)
        setTranslation(Vector.deserialize(displayJson["translation"].asJsonObject))
        setScale(Vector.deserialize(displayJson["scale"].asJsonObject))
        setLeftRotation(jsonFromQuaternion(displayJson["leftRotation"].asJsonObject))
        setRightRotation(jsonFromQuaternion(displayJson["rightRotation"].asJsonObject))
        setBrightness(Display.Brightness.unpack(displayJson["brightness"].asInt))
        setViewRange(displayJson["viewRange"].asFloat)
        setShadowRadius(displayJson["shadowRadius"].asFloat)
        setShadowStrength(displayJson["shadowStrength"].asFloat)
        setWidth(displayJson["width"].asFloat)
        setHeight(displayJson["height"].asFloat)
        setGlowColorOverride(Color.fromARGB(displayJson["colorOverride"].asInt))
        setBillboardConstraints(Display.BillboardConstraints.entries.first { it.id == displayJson["billboardConstraints"].asByte })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setInterpolationDurtation(0)
        setRotationInterpolationDurtation(0)
        setTranslation(Vector(0.0, 0.0, 0.0))
        setScale(Vector(1.0, 1.0, 1.0))
        setLeftRotation(Quaternionf())
        setRightRotation(Quaternionf())
        setBrightness(Display.Brightness(-1, -1))
        setViewRange(1.0f)
        setShadowRadius(0.0f)
        setShadowStrength(1.0f)
        setWidth(1.0f)
        setHeight(1.0f)
        setGlowColorOverride(Color.WHITE)
        setBillboardConstraints(Display.BillboardConstraints.FIXED)
    }

    private fun quaternionToJson(quaternionf: Quaternionf): JsonObject =
        JsonObject().apply {
            addProperty("w", quaternionf.w)
            addProperty("x", quaternionf.x)
            addProperty("y", quaternionf.y)
            addProperty("z", quaternionf.z)
        }

    private fun jsonFromQuaternion(jsonObject: JsonObject): Quaternionf = Quaternionf(
        jsonObject["w"].asFloat,
        jsonObject["x"].asFloat,
        jsonObject["y"].asFloat,
        jsonObject["z"].asFloat
    )

    fun vector3fToVector(vector3f: Vector3f): Vector = Vector(vector3f.x.toDouble(), vector3f.y.toDouble(), vector3f.z.toDouble())

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setInterpolationDurtation(Random.nextInt(50))
                getTestMessage(this@Display::class, "Set interpolation duration", getInterpolationDuration())
            },
            {
                setRotationInterpolationDurtation(Random.nextInt(50))
                getTestMessage(this@Display::class, "Set rotation interpolation duration", getRotationInterpolationDuration())
            },
            {
                setTranslation(Vector(
                    Random.nextDouble(2.5),
                    Random.nextDouble(2.5),
                    Random.nextDouble(2.5)
                ))
                getTestMessage(this@Display::class, "Set translation", getTranslation().x, getTranslation().y, getTranslation().z)
            },
            {
                setTranslation(Vector(
                    0.0,
                    0.0,
                    0.0
                ))
                getTestMessage(this@Display::class, "Set translation", getTranslation().x, getTranslation().y, getTranslation().z)
            },
            {
                setScale(Vector(
                    Random.nextDouble(10.0),
                    Random.nextDouble(10.0),
                    Random.nextDouble(10.0)
                ))
                getTestMessage(this@Display::class, "Set scale", getScale().x, getScale().y, getScale().z)
            },
            {
                setScale(Vector(
                    1.0,
                    1.0,
                    1.0
                ))
                getTestMessage(this@Display::class, "Set scale", getScale().x, getScale().y, getScale().z)
            },
            {
                setBrightness(Display.Brightness(
                    Random.nextInt(25),
                    Random.nextInt(25)
                ))
                getTestMessage(this@Display::class, "Set brightness", getBrightness().block, getBrightness().sky)
            },
            {
                setBrightness(Display.Brightness(
                    -1,
                    -1
                ))
                getTestMessage(this@Display::class, "Set brightness", getBrightness().block, getBrightness().sky)
            },
            {
                setViewRange(Random.nextDouble(25.0))
                getTestMessage(this@Display::class, "Set view range", getViewRange())
            },
            {
                setViewRange(1.0)
                getTestMessage(this@Display::class, "Set view range", getViewRange())
            },
            {
                setShadowRadius(Random.nextDouble(50.0))
                getTestMessage(this@Display::class, "Set shadow radius", getShadowRadius())
            },
            {
                setShadowStrength(Random.nextDouble(50.0))
                getTestMessage(this@Display::class, "Set shadow strength", getShadowStrength())
            },
            {
                setShadowRadius(0.0)
                getTestMessage(this@Display::class, "Set shadow radius", getShadowRadius())
            },
            {
                setShadowStrength(0.0)
                getTestMessage(this@Display::class, "Set shadow strength", getShadowStrength())
            },
            {
                setWidth(Random.nextDouble(0.5))
                getTestMessage(this@Display::class, "Set width", getWidth())
            },
            {
                setWidth(1.0)
                getTestMessage(this@Display::class, "Set width", getWidth())
            },
            {
                setHeight(Random.nextDouble(0.5))
                getTestMessage(this@Display::class, "Set height", getHeight())
            },
            {
                setHeight(1.0)
                getTestMessage(this@Display::class, "Set height", getHeight())
            },
            {
                setGlowing(true)
                setGlowColorOverride(Color.fromARGB(
                    Random.nextInt(255),
                    Random.nextInt(255),
                    Random.nextInt(255),
                    Random.nextInt(255)
                ))
                getTestMessage(this@Display::class, "Set glow color override", getGlowColorOverride().red, getGlowColorOverride().green, getGlowColorOverride().blue, getGlowColorOverride().alpha)
            },
            {
                setGlowing(false)
                setGlowingColor(ChatColor.RED)
                getTestMessage(this@Display::class, "Set glow color override", isGlowing())
            },
            {
                setLeftRotation(Quaternionf(
                    Random.nextDouble(10.0),
                    Random.nextDouble(10.0),
                    Random.nextDouble(10.0),
                    Random.nextDouble(10.0)
                ))
                getTestMessage(this@Display::class, "Set left rotation", getLeftRatation().x, getLeftRatation().y, getLeftRatation().z, getLeftRatation().w)
            },
            {
                setLeftRotation(Quaternionf(
                    0.0,
                    0.0,
                    0.0,
                    0.0
                ))
                getTestMessage(this@Display::class, "Set left rotation", getLeftRatation().x, getLeftRatation().y, getLeftRatation().z, getLeftRatation().w)
            },
            {
                setRightRotation(Quaternionf(
                    Random.nextDouble(10.0),
                    Random.nextDouble(10.0),
                    Random.nextDouble(10.0),
                    Random.nextDouble(10.0)
                ))
                getTestMessage(this@Display::class, "Set right rotation", getRightRatation().x, getRightRatation().y, getRightRatation().z, getRightRatation().w)
            },
            {
                setRightRotation(Quaternionf(
                    0.0,
                    0.0,
                    0.0,
                    0.0
                ))
                getTestMessage(this@Display::class, "Set right rotation", getRightRatation().x, getRightRatation().y, getRightRatation().z, getRightRatation().w)
            }
        ))

        Display.BillboardConstraints.entries.forEach {
            add {
                setBillboardConstraints(it)
                getTestMessage(this@Display::class, "Set billboard constraints", getBillboardConstraints().name.lowercase())
            }
        }
            add{
                setBillboardConstraints(Display.BillboardConstraints.FIXED)
                getTestMessage(this@Display::class, "Set billboard constraints", getBillboardConstraints().name.lowercase())
            }
        }

}