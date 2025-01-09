package com.undefined.quasar.entity.factories

import com.undefined.quasar.entity.EntityFactory
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.exception.EntityNotFoundException
import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.v1_21_4.impl.entity.ArmorStand
import com.undefined.quasar.v1_21_4.impl.entity.vehicle.Minecart

class EntityFactory1_21_4: EntityFactory {

    override fun createEntity(entityType: EntityType): Entity =
        when(entityType) {
            EntityType.MINECART -> Minecart()
            EntityType.ARMORSTAND -> ArmorStand()
            else -> throw EntityNotFoundException(entityType.name)
        }

}