package com.undefined.quasar.entity

import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.Entity

interface EntityFactory {

    fun createEntity(entityType: EntityType): Entity

}