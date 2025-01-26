package com.undefined.quasar.interfaces

import com.undefined.quasar.enums.EntityType

interface EntityFactory {
    fun createEntity(entityType: EntityType): Entity
}