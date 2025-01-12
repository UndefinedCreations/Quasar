package com.undefined.quasar.util

import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

fun ItemStack.serializer(): String {
    val outputStream = ByteArrayOutputStream()
    val dataOutput = BukkitObjectOutputStream(outputStream)

    dataOutput.writeObject(this)
    dataOutput.close()
    return Base64Coder.encodeLines(outputStream.toByteArray())
}

object ItemStackDeserializer {
    fun deserializer(string: String): ItemStack {
        val inputStream = ByteArrayInputStream(Base64Coder.decodeLines(string))
        val dataInput = BukkitObjectInputStream(inputStream)
        val itemStack = dataInput.readObject() as ItemStack
        dataInput.close()
        return itemStack
    }
}

