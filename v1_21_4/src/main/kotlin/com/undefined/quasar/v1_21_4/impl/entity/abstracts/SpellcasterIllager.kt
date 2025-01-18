package com.undefined.quasar.v1_21_4.impl.entity.abstracts

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.abstracts.SpellcasterIllager
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor


abstract class SpellcasterIllager(entityType: EntityType) : Raider(entityType), SpellcasterIllager {

    private var DATA_SPELL_CASTING_ID: EntityDataAccessor<Byte>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.SpellcasterIllager::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.Raider.SpellcasterIllager.DATA_SPELL_CASTING_ID
        )

    override fun setSpellCasting(spell: SpellcasterIllager.IllagerSpell) = setEntityDataAccessor(DATA_SPELL_CASTING_ID, spell.id)

    override fun getSpellCasting(): SpellcasterIllager.IllagerSpell = getEntityDataValue(DATA_SPELL_CASTING_ID)?.let {
        data -> SpellcasterIllager.IllagerSpell.entries.first { it.id == data }
    } ?: SpellcasterIllager.IllagerSpell.NONE

    override fun getEntityData(): JsonObject {
        val raiderJson = super.getEntityData()
        val spellCasterIllagerJson = JsonObject()
        spellCasterIllagerJson.addProperty("spell", getSpellCasting().id)
        raiderJson.add("spellCasterIllager", spellCasterIllagerJson)
        return raiderJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Raider>.setEntityData(jsonObject)
        val spellCasterIllagerJson = jsonObject["spellCasterIllager"].asJsonObject
        setSpellCasting(SpellcasterIllager.IllagerSpell.entries.first { it.id == spellCasterIllagerJson["spell"].asByte })
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setSpellCasting(SpellcasterIllager.IllagerSpell.NONE)
    }

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply {
            addAll(
                SpellcasterIllager.IllagerSpell.entries.map {
                    {
                        setSpellCasting(it)
                        getTestMessage(this@SpellcasterIllager::class, "Set spell casting", getSpellCasting().name.lowercase())

                    }
                }
            )
        }
}