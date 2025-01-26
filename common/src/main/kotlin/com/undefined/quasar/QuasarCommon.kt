package com.undefined.quasar

import org.bukkit.plugin.java.JavaPlugin

class QuasarCommon(plugin: JavaPlugin) {
    companion object {
        lateinit var PLUGIN: JavaPlugin
    }

    init {
        PLUGIN = plugin


    }
}