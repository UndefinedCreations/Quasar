package com.undefined.quasar.interfaces.entities.entity.npc.player

import com.google.gson.JsonObject
import java.util.*

data class Skin(
    private val texture: String,
    private val signature: String
) {

    fun getTexture(): String = texture
    fun getSignature(): String = signature

    fun json(): JsonObject {
        val json = JsonObject()
        json.addProperty("texture", getTexture())
        json.addProperty("signature", getSignature())
        return json
    }

    companion object {
        fun of(name: String): Skin? {
            val data = MojangUtil.getSkinData(name)
            data?.let { return Skin(it.first, it.second) }
            return null
        }

        fun of(uuid: UUID): Skin? {
            val data = MojangUtil.getSkinData(uuid)
            data?.let { return Skin(it.first, it.second) }
            return null
        }

        fun of(jsonObject: JsonObject): Skin {
            return Skin(jsonObject["texture"].asString, jsonObject["signature"].asString)
        }

        val STEVE: Skin = Skin(
            "ewogICJ0aW1lc3RhbXAiIDogMTcyNDQ1OTY2NDU1MywKICAicHJvZmlsZUlkIiA6ICJjMDZmODkwNjRjOGE0OTExOWMyOWVhMWRiZDFhYWI4MiIsCiAgInByb2ZpbGVOYW1lIiA6ICJNSEZfU3RldmUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDVjNGVlNWNlMjBhZWQ5ZTMzZTg2NmM2NmNhYTM3MTc4NjA2MjM0YjM3MjEwODRiZjAxZDEzMzIwZmIyZWIzZiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9",
            "l85wVdzBMGxA2i5ua7HOXSONtytElEg7CsDFfYy/mTZWDEbBioELI8ZHGTzhnX0vVxtt7Gujoaev/XEcUHyKEaLHmxPr2prb6nV94SljibM4FG29/USkPNKRJUFsruSZZp6P6XUjcSfaBwGtVVmxzO3QP8My7wHesmiuhJhS2s9eXXIIOJBoPpfPrqQAOv0hD7Q/2oVpDd71ryEvyjjbkjAwSwSaOTJ4JNAgTjNnPKNdDrx+vVJgTeKMx7wmqMDPNmL8zhsdCbxvVlr3GYucPynu/abkYWLdNpjyQ2JcjTCmgzVtQ2MVOSCn3ZUw/+STZ0GnO31PwS8G7tNTZlLDSlrM8xqu55PjuhVSdsi8VpnCSb4ycNGq2aWcBsvYIIs50idPcyvgikf+BQf/XIDdNasxpTUkgNCrRldHodmx+mIwbN448NBzr4nCdl0IOQexaodTbLcEnvys+79Gmy4D2rLkZDiu4hOfHUEouxCEV/41k18lWU5Rhl5vBTyu8XkqFYI11QCKEr+uayn53QItqVgIVHrPypGoFHkHrccEQVE7ZwVRctQavkI9eOJoyPsd8J0V2W+J21XGmt6iY944jxBn32Fx0Mt4CPAkEDoYq+F1l2xH8CVENyei9+nG9c4HsCoZyUShMkablKcrMMuYbtvydENSzoMukb21brlgRY0="
        )
    }
}
