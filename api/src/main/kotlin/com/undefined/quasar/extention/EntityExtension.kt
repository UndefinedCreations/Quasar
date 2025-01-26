package com.undefined.quasar.extention

import com.undefined.quasar.Quasar
import com.undefined.quasar.enums.EntityType
import org.bukkit.entity.Entity

fun Entity.toQuasar(): com.undefined.quasar.interfaces.Entity {
    return Quasar.INSTANCE.createQuasarEntity(EntityType.valueOf(type.name)).apply { setEntity(this@toQuasar) }
}