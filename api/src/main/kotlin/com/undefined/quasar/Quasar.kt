package com.undefined.quasar

import com.undefined.quasar.interfaces.EntityFactory
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.exception.EntityNotFoundException
import com.undefined.quasar.exception.UnsupportedVersionException
import com.undefined.quasar.interfaces.Entity
import com.undefined.quasar.util.NMSVersion
import com.undefined.quasar.util.Option
import com.undefined.quasar.v1_21_4.loader.QuasarNMS
import org.bukkit.plugin.java.JavaPlugin

class Quasar(
    private val plugin: JavaPlugin,
    val entityLoader: Option = Option.AUTOMATIC,
    val sendMetaPacket: Option = Option.AUTOMATIC) {

    companion object {
        lateinit var INSTANCE: Quasar
    }

    private val entityFactory: EntityFactory = getEntityFactory()

    init {
        INSTANCE = this
        QuasarCommon(plugin)
    }

    inline fun <reified T: Entity> createQuasarEntity(): T {
        val entityType = EntityType.entries.filter { it.clazz == T::class }.getOrNull(0) ?: throw EntityNotFoundException(T::class.java.simpleName)
        return createQuasarEntity(entityType) as T
    }

    fun createQuasarEntity(entityType: EntityType): Entity =
        this.entityFactory.createEntity(entityType)

    private fun getEntityFactory(): EntityFactory =
        when(NMSVersion.version) {
            "1.21.4" -> QuasarNMS(plugin, entityLoader.name, sendMetaPacket.name)
            else -> throw UnsupportedVersionException(NMSVersion.version)
        }

}