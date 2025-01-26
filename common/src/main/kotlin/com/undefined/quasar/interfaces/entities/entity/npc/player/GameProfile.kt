package com.undefined.quasar.interfaces.entities.entity.npc.player

import java.util.UUID

class GameProfile(
    private val skin: Skin,
    private val name: String,
) {

    fun getSkin(): Skin = skin
    fun getName(): String = name

}