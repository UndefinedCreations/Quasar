package com.undefined.quasar.interfaces.entities.entity.animal.water

import com.undefined.quasar.util.Color

interface TropicalFish : WaterAnimal {

    fun setPattern(pattern: Pattern)
    fun getPattern(): Pattern

    fun setBaseColor(color: Color)
    fun getBaseColor(): Color

    fun setPatternColor(color: Color)
    fun getPatternColor(): Color

    enum class Pattern(val id: String) {
        KOB("kob"),
        SUNSTREAK("sunstreak"),
        SNOOPER("snooper"),
        DASHER("dasher"),
        BRINELY("brinely"),
        SPOTTY("spotty"),
        FLOPPER("flopper"),
        STRIPEY("stripey"),
        GLITTER("glitter"),
        BLOCKFISH("blockfish"),
        BETTY("betty"),
        CLAYFISH("clayfish")
    }
}