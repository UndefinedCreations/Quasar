package com.undefined.quasar.v1_21_4.impl.entity.display

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.display.Display
import com.undefined.quasar.util.Vector
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import org.bukkit.Color
import org.joml.Quaternionf
import org.joml.Vector3f
import kotlin.random.Random

abstract class Display(entityType: EntityType) : Entity(entityType), Display {

    private var interpolationDuration = 0
    private var rotationInterpolationDuration = 0
    private var translation = Vector(0.0, 0.0, 0.0)
    private var scale = Vector(1.0, 1.0, 1.0)
    private var leftRotation = Quaternionf()
    private var rightRotation = Quaternionf()
    private var brightness = Display.Brightness(-1, -1)
    private var viewRange = 1.0f
    private var shadowRadius = 0.0f
    private var shadowStrength = 1.0f
    private var height = 1.0f
    private var width = 1.0f
    private var colorOverride = Color.WHITE
    private var billboardConstraints = Display.BillboardConstraints.FIXED

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

    override fun setInterpolationDurtation(duration: Int) =
        setEntityDataAccessor(DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID, duration) {
            this.interpolationDuration = duration
        }

    override fun getInterpolationDuration(): Int = interpolationDuration

    override fun setRotationInterpolationDurtation(duration: Int) =
        setEntityDataAccessor(DATA_POS_ROT_INTERPOLATION_DURATION_ID, duration) {
            this.rotationInterpolationDuration = duration
        }

    override fun getRotationInterpolationDuration(): Int = rotationInterpolationDuration

    override fun setTranslation(vector: Vector) =
        setEntityDataAccessor(DATA_TRANSLATION_ID, vector.toNMS()) {
            this.translation = vector
        }

    override fun getTranslation(): Vector = translation

    override fun setScale(vector: Vector) =
        setEntityDataAccessor(DATA_SCALE_ID, vector.toNMS()) {
            this.scale = vector
        }

    override fun getScale(): Vector = scale

    override fun setLeftRotation(rotation: Quaternionf) =
        setEntityDataAccessor(DATA_LEFT_ROTATION_ID, rotation) {
            this.leftRotation = rotation
        }

    override fun getLeftRatation(): Quaternionf = leftRotation

    override fun setRightRotation(rotation: Quaternionf) =
        setEntityDataAccessor(DATA_RIGHT_ROTATION_ID, rotation) {
            this.rightRotation = rotation
        }

    override fun getRightRatation(): Quaternionf = rightRotation

    override fun setBrightness(brightness: Display.Brightness?) =
        setEntityDataAccessor(DATA_BRIGHTNESS_OVERRIDE_ID, brightness?.pack() ?: -1) {
            this.brightness = brightness ?: Display.Brightness(-1, -1)
        }

    override fun getBrightness(): Display.Brightness = brightness

    override fun setViewRange(range: Float) =
        setEntityDataAccessor(DATA_VIEW_RANGE_ID, range) {
            this.viewRange = range
        }

    override fun getViewRange(): Float = viewRange

    override fun setShadowRadius(radius: Float) =
        setEntityDataAccessor(DATA_SHADOW_RADIUS_ID, radius) {
            this.shadowRadius = radius
        }

    override fun getShadowRadius(): Float = shadowRadius

    override fun setShadowStrength(strength: Float) =
        setEntityDataAccessor(DATA_SHADOW_STRENGTH_ID, strength) {
            this.shadowStrength = strength
        }

    override fun getShadowStrength(): Float = shadowStrength

    override fun setWidth(width: Float) =
        setEntityDataAccessor(DATA_WIDTH_ID, width) {
            this.width = width
        }

    override fun getWidth(): Float = width

    override fun setHeight(height: Float) =
        setEntityDataAccessor(DATA_HEIGHT_ID, height) {
            this.height = height
        }

    override fun getHeight(): Float = height

    override fun setGlowColorOverride(color: Color) =
        setEntityDataAccessor(DATA_GLOW_COLOR_OVERRIDE_ID, color.asARGB()) {
            this.colorOverride = color
        }

    override fun getGlowColorOverride(): Color = colorOverride

    override fun setBillboardConstraints(billboardConstraints: Display.BillboardConstraints) =
        setEntityDataAccessor(DATA_BILLBOARD_RENDER_CONSTRAINTS_ID, billboardConstraints.id) {
            this.billboardConstraints = billboardConstraints
        }

    override fun getBillboardConstraints(): Display.BillboardConstraints = billboardConstraints

    private fun Vector.toNMS(): Vector3f = Vector3f(x.toFloat(), y.toFloat(), z.toFloat())

    override fun getEntityData(): JsonObject {
        val entityJson = super.getEntityData()
        val displayJson = JsonObject()
        displayJson.addProperty("interpolationDuration", interpolationDuration)
        displayJson.addProperty("rotationInterpolationDuration", rotationInterpolationDuration)
        displayJson.add("translation", translation.json())
        displayJson.add("scale", scale.json())
        displayJson.add("leftRotation", quaternionToJson(leftRotation))
        displayJson.add("rightRotation", quaternionToJson(rightRotation))
        displayJson.addProperty("brightness", brightness.pack())
        displayJson.addProperty("viewRange", viewRange)
        displayJson.addProperty("shadowRadius", shadowRadius)
        displayJson.addProperty("shadowStrength", shadowStrength)
        displayJson.addProperty("width", width)
        displayJson.addProperty("height", height)
        displayJson.addProperty("colorOverride", colorOverride.asARGB())
        displayJson.addProperty("billboardConstraints", billboardConstraints.name)
        entityJson.add("display", displayJson)
        return entityJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val displayJson = jsonObject["display"].asJsonObject
        interpolationDuration = displayJson["interpolationDuration"].asInt
        rotationInterpolationDuration = displayJson["rotationInterpolationDuration"].asInt
        translation = Vector.deserialize(displayJson["translation"].asJsonObject)
        scale = Vector.deserialize(displayJson["scale"].asJsonObject)
        leftRotation = jsonFromQuaternion(displayJson["leftRotation"].asJsonObject)
        rightRotation = jsonFromQuaternion(displayJson["rightRotation"].asJsonObject)
        brightness = Display.Brightness.unpack(displayJson["brightness"].asInt)
        viewRange = displayJson["viewRange"].asFloat
        shadowRadius = displayJson["shadowRadius"].asFloat
        shadowStrength = displayJson["shadowStrength"].asFloat
        width = displayJson["width"].asFloat
        height = displayJson["height"].asFloat
        colorOverride = Color.fromARGB(displayJson["colorOverride"].asInt)
        billboardConstraints = Display.BillboardConstraints.valueOf(displayJson["billboardConstraints"].asString)
    }

    override fun updateEntity() {
        super.updateEntity()
        setInterpolationDurtation(interpolationDuration)
        setRotationInterpolationDurtation(rotationInterpolationDuration)
        setTranslation(translation)
        setScale(scale)
        setLeftRotation(leftRotation)
        setRightRotation(rightRotation)
        setBrightness(brightness)
        setViewRange(viewRange)
        setShadowRadius(shadowRadius)
        setShadowStrength(shadowStrength)
        setWidth(width)
        setHeight(height)
        setGlowColorOverride(colorOverride)
        setBillboardConstraints(billboardConstraints)
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
                setWidth(Random.nextDouble(5.0))
                getTestMessage(this@Display::class, "Set width", getWidth())
            },
            {
                setWidth(1.0)
                getTestMessage(this@Display::class, "Set width", getWidth())
            },
            {
                setHeight(Random.nextDouble(5.0))
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
                getTestMessage(this@Display::class, "Set glow color override", false)
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

        Display.BillboardConstraints.entries.filter { it != billboardConstraints }.forEach {
            add {
                setBillboardConstraints(it)
                getTestMessage(this@Display::class, "Set billboard constraints", it)
            }
        }
            add{
                setBillboardConstraints(Display.BillboardConstraints.FIXED)
                getTestMessage(this@Display::class, "Set billboard constraints", Display.BillboardConstraints.FIXED)
            }
        }

}