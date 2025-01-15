package com.undefined.quasar.interfaces.entities.entity.monster

import com.undefined.quasar.interfaces.abstracts.Monster

interface Blaze : Monster {
    fun isCharged(): Boolean
    fun setCharged(charged: Boolean)
}