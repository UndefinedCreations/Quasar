package com.undefined.quasar.interfaces.entities.entity.npc.player

import com.google.gson.JsonParser
import com.undefined.quasar.interfaces.LivingEntity
import org.bukkit.entity.Entity
import java.util.*

interface Player : LivingEntity {

    fun setSkin(skin: Skin?) = skin?.let { setGameProfile(GameProfile(it, getName())) }
    fun getSkin(): Skin

    fun setName(name: String) = setGameProfile(GameProfile(getSkin(), name))
    fun getName(): String

    fun getCape(): Cape {
        val decodeTexture = String(Base64.getDecoder().decode(getSkin().getTexture()))
        val json = JsonParser.parseString(decodeTexture).asJsonObject
        val textureJson = json["textures"].asJsonObject
        val capeTexture = if (textureJson.has("CAPE")) json["CAPE"].asJsonObject["url"].asString.split("/").let { it[it.size -1] } else ""
        return Cape.of(capeTexture)
    }

    fun hideName(hide: Boolean)
    fun isNameHidden(): Boolean

    fun getTrueProfile(): GameProfile?
    fun resetProfile()

    fun setGameProfile(gameProfile: GameProfile)
    fun getGameProfile(): GameProfile

    fun setCrouching(crouching: Boolean)
    fun isCrouching(): Boolean

    fun setSwimming(swimming: Boolean)
    fun isSwimming(): Boolean

    fun setRiptideAttack(riptide: Boolean)
    fun isInRiptideAttack(): Boolean

    fun moveMainHand()
    fun moveOffHand()

    fun setCamara(entity: Entity?)
    fun setCamara(entity: com.undefined.quasar.interfaces.Entity)

    fun setReduceDebug(reduceDebug: Boolean)
    fun isReduceDebug(): Boolean

    fun sendDisconnectedPacket()
    fun sendReconnectPacket()
}