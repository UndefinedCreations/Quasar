package com.undefined.quasar.v1_21_4.impl.entity.npc

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.npc.Profession
import com.undefined.quasar.interfaces.entities.entity.npc.Type
import com.undefined.quasar.interfaces.entities.entity.npc.Villager
import com.undefined.quasar.v1_21_4.impl.entity.abstracts.Animal
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.npc.VillagerData
import net.minecraft.world.entity.npc.VillagerProfession
import net.minecraft.world.entity.npc.VillagerType
import net.minecraft.world.level.Level
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftVillager.CraftProfession
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftVillager.CraftType

class Villager : Animal(EntityType.VILLAGER), Villager {

    private var DATA_VILLAGER_DATA: EntityDataAccessor<VillagerData>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.npc.Villager::class.java,
            FieldMappings.Entity.LivingEntity.Npc.Villager.DATA_VILLAGER_DATA
        )

    override fun setProfession(profession: Profession) {
        entity ?: return
        val data = getVillagerData()
        setVillagerData(data.setProfession(CraftProfession.bukkitToMinecraft(profession.toBukkit())))
    }

    override fun getProfession(): Profession = Profession.valueOf(CraftProfession.minecraftToBukkit(getVillagerData().profession).name())

    override fun setType(type: Type) {
        entity ?: return
        val data = getVillagerData()
        setVillagerData(data.setType(CraftType.bukkitToMinecraft(org.bukkit.entity.Villager.Type.valueOf(type.name))))
    }

    override fun getType(): Type = Type.valueOf(CraftType.minecraftToBukkit(getVillagerData().type).name())

    private fun setVillagerData(data: VillagerData) = setEntityDataAccessor(DATA_VILLAGER_DATA, data)
    private fun getVillagerData(): VillagerData = getEntityDataValue(DATA_VILLAGER_DATA) ?: VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1)

    override fun getEntityData(): JsonObject {
        val mobJson = super.getEntityData()
        val villagerJson = JsonObject()
        villagerJson.addProperty("type", getType().name)
        villagerJson.addProperty("profession", getProfession().name)
        mobJson.add("villager", villagerJson)
        return mobJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<Animal>.setEntityData(jsonObject)
        val villagerJson = jsonObject["villager"].asJsonObject
        setProfession(Profession.valueOf(villagerJson["profession"].asString))
        setType(Type.valueOf(villagerJson["type"].asString))
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setProfession(Profession.NONE)
        setType(Type.PLAINS)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.npc.Villager(net.minecraft.world.entity.EntityType.VILLAGER, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply {
            addAll(Profession.entries.map {
                {
                    setProfession(it)
                    getTestMessage(this@Villager::class, "Set profession", getProfession().name.lowercase())
                }
            })

            addAll(Type.entries.map {
                {
                    setType(it)
                    getTestMessage(this@Villager::class, "Set type", getType().name.lowercase())
                }
            })
        }
}