package com.unedfined.quasar.v1_21_4

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.LivingEntity
import org.bukkit.entity.Player

abstract class LivingEntity(entityType: EntityType): LivingEntity, Entity(entityType) {

    override fun setEntityData(jsonObject: JsonObject) {
        super<Entity>.setEntityData(jsonObject)
    }

    override fun runTest(
        logger: Player,
        delayTime: Int,
        stageOneTest: (Exception?) -> Unit,
        stageTwoTest: (Exception?) -> Unit,
        stageThreeTest: (Exception?) -> Unit
    ): Int {
        super.runTest(logger, delayTime, { e ->
            trycatch({
                stageOneTest(e)
                if (e != null) return@trycatch


                stageTwoTest(null)
            }, stageTwoTest)
        }, stageTwoTest, stageThreeTest)

        return 2
    }
}