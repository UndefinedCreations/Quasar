package com.undefined.quasar.util

import com.undefined.quasar.QuasarCommon
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

fun delay(ticks: Int, runnable: BukkitRunnable.() -> Unit): BukkitTask =
    createRunnable(-1, runnable).runTaskLater(QuasarCommon.PLUGIN, ticks.toLong())

fun repeat(times: Int, ticks: Int, runnable: BukkitRunnable.() -> Unit): BukkitTask =
    createRunnable(times, runnable).runTaskTimer(QuasarCommon.PLUGIN, ticks.toLong(), ticks.toLong())


private fun createRunnable(times: Int = -1, runnable: BukkitRunnable.() -> Unit): BukkitRunnable {
    var amount = 0
    return object : BukkitRunnable() {
        override fun run() {
            runnable()
            if (times == -1) return
            amount++
            if (amount >= times) cancel()
        }
    }
}