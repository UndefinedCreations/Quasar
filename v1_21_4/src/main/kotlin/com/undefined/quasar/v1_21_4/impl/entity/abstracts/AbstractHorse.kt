package com.undefined.quasar.v1_21_4.impl.entity.abstracts

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.abstracts.AbstractHorse

abstract class AbstractHorse(entityType: EntityType) : Animal(entityType), AbstractHorse {

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setSaddle(true)
                getTestMessage(this@AbstractHorse::class, "Set saddle", hasSaddle())
            },
            {
                setSaddle(false)
                getTestMessage(this@AbstractHorse::class, "Set saddle", hasSaddle())
            }
        )) }
}