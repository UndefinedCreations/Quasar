package com.undefined.quasar.interfaces.entities.entity.npc.player

import com.google.gson.JsonParser
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import kotlin.collections.HashMap

object MojangUtil {

    private val uuidSkinCache: MutableMap<UUID, Pair<String, String>?> = HashMap()
    private val nameUuidCache: MutableMap<String, UUID?> = HashMap()

    fun isValidPlayer(uuid: UUID): Boolean {
        val sUrl = "https://sessionserver.mojang.com/session/minecraft/profile/$uuid?unsigned=false"
        var pair: Pair<String, String>? = null
        try {
            val req = URL(sUrl).openConnection()
            req.connect()

            val reader = InputStreamReader(req.content as InputStream)
            val json = JsonParser.parseReader(reader).asJsonObject
            val array = json.get("properties").asJsonArray

            for (element in array) {
                val obj = element.asJsonObject
                if (obj.get("name") != null && obj.get("name").asString == "textures") {
                    pair = Pair(obj.get("value").asString, obj.get("signature").asString)
                }
            }

            uuidSkinCache[uuid] = pair
            return true

        } catch (ex: Exception) {
            uuidSkinCache[uuid] = null
            return false
        }
    }

    fun isValidPlayer(name: String): Boolean {
        val nameUrl = "https://mc-account-type.pages.dev/api/$name"
        try {
            val req = URL(nameUrl).openConnection()
            req.connect()

            val reader = InputStreamReader(req.content as InputStream)
            val json = JsonParser.parseReader(reader).asJsonObject

            if (!json.get("success").asBoolean) {
                nameUuidCache[name.lowercase()] = null
                return false
            }

            val uuid = UUID.fromString(json.get("uuid").asString)
            nameUuidCache[name.lowercase()] = uuid
            return isValidPlayer(uuid)

        } catch (ex: Exception) {
            nameUuidCache[name.lowercase()] = null
            return false
        }
    }

    fun getSkinData(uuid: UUID): Pair<String, String>? {
        if (!uuidSkinCache.containsKey(uuid)) isValidPlayer(uuid)
        return uuidSkinCache[uuid]
    }

    fun getSkinData(name: String): Pair<String, String>? {
        if (!nameUuidCache.containsKey(name.lowercase())) isValidPlayer(name.lowercase())
        return uuidSkinCache[nameUuidCache[name.lowercase()] ?: return null]
    }

}