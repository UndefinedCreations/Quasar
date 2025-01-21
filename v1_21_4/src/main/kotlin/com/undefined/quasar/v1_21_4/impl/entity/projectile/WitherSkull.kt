package com.undefined.quasar.v1_21_4.impl.entity.projectile

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.projectile.WitherSkull
import com.undefined.quasar.v1_21_4.impl.entity.Entity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.level.Level

class WitherSkull : Entity(EntityType.WITHER_SKULL), WitherSkull {

    private var DATA_DANGEROUS: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.projectile.WitherSkull::class.java,
            FieldMappings.Entity.Projectile.WitherSkull.DATA_DANGEROUS
        )

    override fun setDangerous(dangerous: Boolean) = setEntityDataAccessor(DATA_DANGEROUS, dangerous)

    override fun isDangerous(): Boolean = getEntityDataValue(DATA_DANGEROUS) ?: false

    override fun getEntityData(): JsonObject {
        val projectileJson = super.getEntityData()
        val witherSkullJson = JsonObject()
        witherSkullJson.addProperty("dangerous", isDangerous())
        projectileJson.add("witherSkull", witherSkullJson)
        return projectileJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
        val witherSkullJson = jsonObject["witherSkull"].asJsonObject
        setDangerous(witherSkullJson["dangerous"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setDangerous(false)
    }

    override fun getEntityClass(level: Level): net.minecraft.world.entity.Entity =
        net.minecraft.world.entity.projectile.WitherSkull(net.minecraft.world.entity.EntityType.WITHER_SKULL, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setDangerous(true)
                getTestMessage(this@WitherSkull::class, "Set dangerous", isDangerous())
            }
        )) }
}