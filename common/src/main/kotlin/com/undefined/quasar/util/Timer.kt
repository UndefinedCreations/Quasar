package com.undefined.quasar.util

import com.undefined.quasar.QuasarCommon
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

fun delay(ticks: Int, runnable: BukkitRunnable.() -> Unit): BukkitTask =
    createRunnable(runnable).runTaskLater(QuasarCommon.PLUGIN, ticks.toLong())

private fun createRunnable(runnable: BukkitRunnable.() -> Unit): BukkitRunnable {
    return object : BukkitRunnable() {
        override fun run() {
            runnable()
        }
    }
}