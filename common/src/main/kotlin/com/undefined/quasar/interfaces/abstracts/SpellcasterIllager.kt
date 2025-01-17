package com.undefined.quasar.interfaces.abstracts

interface SpellcasterIllager : Raider {

    fun setSpellCasting(spell: IllagerSpell)
    fun getSpellCasting(): IllagerSpell

    enum class IllagerSpell(val id: Byte) {
        NONE(0),
        SUMMON_VEX(1),
        FANGS(2),
        WOLOLO(3),
        DISAPPEAR(4),
        BLINDNESS(5)
    }
}