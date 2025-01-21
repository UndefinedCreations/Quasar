package com.undefined.quasar.v1_21_4.impl.entity.display

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.display.TextDisplay
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.chat.Component
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import org.bukkit.Color
import org.bukkit.craftbukkit.v1_21_R3.util.CraftChatMessage
import kotlin.random.Random

class TextDisplay : Display(EntityType.TEXT_DISPLAY), TextDisplay {

    private var DATA_TEXT_ID: EntityDataAccessor<Component>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display.TextDisplay::class.java,
            FieldMappings.Entity.Display.TextDisplay.DATA_TEXT_ID
        )

    private var DATA_LINE_WIDTH_ID: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display.TextDisplay::class.java,
            FieldMappings.Entity.Display.TextDisplay.DATA_LINE_WIDTH_ID
        )

    private var DATA_BACKGROUND_COLOR_ID: EntityDataAccessor<Int>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display.TextDisplay::class.java,
            FieldMappings.Entity.Display.TextDisplay.DATA_BACKGROUND_COLOR_ID
        )

    private var DATA_TEXT_OPACITY_ID: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display.TextDisplay::class.java,
            FieldMappings.Entity.Display.TextDisplay.DATA_TEXT_OPACITY_ID
        )

    private var DATA_STYLE_FLAGS_ID: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.Display.TextDisplay::class.java,
            FieldMappings.Entity.Display.TextDisplay.DATA_STYLE_FLAGS_ID
        )


    private val FLAG_SHADOW = 1
    private val FLAG_SEE_THROUGH = 2
    private val FLAG_USE_DEFAULT_BACKGROUND = 4
    private val FLAG_ALIGN_LEFT = 8
    private val FLAG_ALIGN_RIGHT = 16

    override fun setText(string: String) = setEntityDataAccessor(DATA_TEXT_ID, CraftChatMessage.fromString(string, true)[0])

    override fun getText(): String = getEntityDataValue(DATA_TEXT_ID)?.let { CraftChatMessage.fromComponent(it) } ?: ""

    override fun setLineWidth(width: Int) = setEntityDataAccessor(DATA_LINE_WIDTH_ID, width)

    override fun getLineWidth(): Int = getEntityDataValue(DATA_LINE_WIDTH_ID) ?: 1

    override fun setBackgroundColor(color: Color) = setEntityDataAccessor(DATA_BACKGROUND_COLOR_ID, color.asARGB())

    override fun getBackgroundColor(): Color = getEntityDataValue(DATA_BACKGROUND_COLOR_ID)?.let { Color.fromARGB(it) } ?: Color.GRAY

    override fun setTextOpacity(opacity: Byte) = setEntityDataAccessor(DATA_TEXT_OPACITY_ID, opacity)

    override fun getTextOpacity(): Byte = getEntityDataValue(DATA_TEXT_OPACITY_ID) ?: -1

    override fun setShadowed(shadow: Boolean) = setFlag(FLAG_SHADOW, shadow)

    override fun isShadowed(): Boolean = getFlag(FLAG_SHADOW)

    override fun setSeeThrough(seeThrough: Boolean) = setFlag(FLAG_SEE_THROUGH, seeThrough)

    override fun getSeeThrough(): Boolean = getFlag(FLAG_SEE_THROUGH)

    override fun setDefaultBackground(defaultBackground: Boolean) = setFlag(FLAG_USE_DEFAULT_BACKGROUND, defaultBackground)

    override fun isDefaultBackground(): Boolean = getFlag(FLAG_USE_DEFAULT_BACKGROUND)

    override fun setAlignment(alignment: TextDisplay.Alignment) {
        entity ?: return
        when(alignment) {
            TextDisplay.Alignment.LEFT -> {
                setFlag(FLAG_ALIGN_LEFT, true, sendPacket = false)
                setFlag(FLAG_ALIGN_RIGHT, false, sendPacket = false)
                sendEntityMetaData()
            }
            TextDisplay.Alignment.RIGHT -> {
                setFlag(FLAG_ALIGN_LEFT, false, sendPacket = false)
                setFlag(FLAG_ALIGN_RIGHT, true, sendPacket = false)
                sendEntityMetaData()
            }
            TextDisplay.Alignment.CENTER -> {
                setFlag(FLAG_ALIGN_LEFT, false, sendPacket = false)
                setFlag(FLAG_ALIGN_RIGHT, false, sendPacket = false)
                sendEntityMetaData()
            }
        }
    }

    override fun getAlignment(): TextDisplay.Alignment {
        entity ?: return TextDisplay.Alignment.CENTER
        return getAlign(getFlagsBits())
    }

    private fun getAlign(var0: Byte): TextDisplay.Alignment {
        return if ((var0.toInt() and 8) != 0) {
            TextDisplay.Alignment.LEFT
        } else {
            if ((var0.toInt() and 16) != 0) TextDisplay.Alignment.RIGHT else TextDisplay.Alignment.CENTER
        }
    }

    private fun getFlag(flag: Int): Boolean {
        val entity = entity ?: return false
        return (getFlagsBits().toInt() and flag) != 0
    }

    private fun setFlag(flag: Int, set: Boolean, sendPacket: Boolean = true) {
        val entity = entity ?: return
        var flagBits: Byte = getFlagsBits()
        flagBits = if (set) {
            (flagBits.toInt() or flag).toByte()
        } else {
            (flagBits.toInt() and flag.inv()).toByte()
        }
        setFlagBits(flagBits, sendPacket)
    }

    private fun getFlagsBits(): Byte {
        val entity = entity ?: return 0
        return entity.entityData.get(DATA_STYLE_FLAGS_ID).toByte()
    }

    private fun setFlagBits(byte: Byte, sendPacket: Boolean) {
        val entity = entity ?: return
        entity.entityData.set(DATA_STYLE_FLAGS_ID, byte)
        if (sendPacket) sendEntityMetaData()
    }

    override fun getEntityData(): JsonObject {
        val displayJson = super.getEntityData()
        val textDisplayJson = JsonObject()
        textDisplayJson.addProperty("text", getText())
        textDisplayJson.addProperty("lineWidth", getLineWidth())
        textDisplayJson.addProperty("backgroundColor", getBackgroundColor().asARGB())
        textDisplayJson.addProperty("textOpacity", getTextOpacity())
        textDisplayJson.addProperty("shadowed", isShadowed())
        textDisplayJson.addProperty("seeThrough", getSeeThrough())
        textDisplayJson.addProperty("defaultBackground", isDefaultBackground())
        textDisplayJson.addProperty("alignment", getAlignment().name)
        displayJson.add("textDisplay", textDisplayJson)
        return displayJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Display>.setEntityData(jsonObject)
        val textDisplayJson = jsonObject["textDisplay"].asJsonObject
        setText(textDisplayJson["text"].asString)
        setLineWidth(textDisplayJson["lineWidth"].asInt)
        setBackgroundColor(Color.fromARGB(textDisplayJson["backgroundColor"].asInt))
        setTextOpacity(textDisplayJson["textOpacity"].asByte)
        setShadowed(textDisplayJson["shadowed"].asBoolean)
        setSeeThrough(textDisplayJson["seeThrough"].asBoolean)
        setDefaultBackground(textDisplayJson["defaultBackground"].asBoolean)
        setAlignment(TextDisplay.Alignment.valueOf(textDisplayJson["alignmant"].asString))
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setText("Text Display")
        setLineWidth(Int.MAX_VALUE)
        setBackgroundColor(Color.GRAY)
        setTextOpacity(-1)
        setShadowed(false)
        setSeeThrough(true)
        setDefaultBackground(true)
        setAlignment(TextDisplay.Alignment.CENTER)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.Display.TextDisplay(net.minecraft.world.entity.EntityType.TEXT_DISPLAY, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
                val name = (1..10)
                    .map { allowedChars.random() }
                    .joinToString("")
                setText(name)
                getTestMessage(this@TextDisplay::class, "Set text", getText())
            },
            {
                setLineWidth(Random.nextInt(25))
                getTestMessage(this@TextDisplay::class, "Set line width", getLineWidth())
            },
            {
                setLineWidth(1)
                getTestMessage(this@TextDisplay::class, "Set line width", getLineWidth())
            },
            {
                setDefaultBackground(false)
                getTestMessage(this@TextDisplay::class, "Set default back ground", isDefaultBackground())
            },
            {
                val color = Color.fromRGB(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
                setBackgroundColor(color)
                getTestMessage(this@TextDisplay::class, "Set background color", getBackgroundColor().red, getBackgroundColor().green, getBackgroundColor().blue)
            },
            {

                setDefaultBackground(false)
                getTestMessage(this@TextDisplay::class, "Set default back ground", isDefaultBackground())
            },
            {
                setTextOpacity(Random.nextInt(10).toByte())
                getTestMessage(this@TextDisplay::class, "Set text opacity", getTextOpacity())
            },
            {
                setTextOpacity(Byte.MAX_VALUE)
                getTestMessage(this@TextDisplay::class, "Set text opacity", getTextOpacity())
            },
            {
                setShadowed(true)
                getTestMessage(this@TextDisplay::class, "Set shadowed", isShadowed())
            },
            {
                setShadowed(false)
                getTestMessage(this@TextDisplay::class, "Set shadowed", isShadowed())
            },
            {
                setSeeThrough(true)
                getTestMessage(this@TextDisplay::class, "Set see through", getSeeThrough())
            },
            {
                setSeeThrough(false)
                getTestMessage(this@TextDisplay::class, "Set see through", getSeeThrough())
            }
        ))
        addAll(TextDisplay.Alignment.entries.map {
            {
                setAlignment(it)
                getTestMessage(this@TextDisplay::class, "Set alignment", getAlignment().name.lowercase())
            }
        })}
}