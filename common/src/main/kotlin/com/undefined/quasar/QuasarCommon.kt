package com.undefined.quasar

import com.undefined.quasar.util.DEFAULTS
import com.undefined.quasar.util.Option
import org.bukkit.plugin.java.JavaPlugin

class QuasarCommon(plugin: JavaPlugin) {
    companion object {
        lateinit var PLUGIN: JavaPlugin
    }

    init {
        PLUGIN = plugin


    }
}