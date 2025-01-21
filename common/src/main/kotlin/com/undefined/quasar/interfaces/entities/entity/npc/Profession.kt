package com.undefined.quasar.interfaces.entities.entity.npc

import org.bukkit.entity.Villager

enum class Profession {
    NONE,
    ARMORER,
    BUTCHER,
    CARTOGRAPHER,
    CLERIC,
    FARMER,
    FISHERMAN,
    FLETCHER,
    LEATHERWORKER,
    LIBRARIAN,
    MASON,
    NITWIT,
    SHEPHERD,
    TOOLSMITH,
    WEAPONSMITH;

    fun toBukkit(): Villager.Profession = Villager.Profession.valueOf(name)
}