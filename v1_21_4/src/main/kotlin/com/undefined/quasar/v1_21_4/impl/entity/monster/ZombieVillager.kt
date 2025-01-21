package com.undefined.quasar.v1_21_4.impl.entity.monster

import com.google.gson.JsonObject
import com.undefined.quasar.enums.EntityType
import com.undefined.quasar.interfaces.entities.entity.monster.ZombieVillager
import com.undefined.quasar.interfaces.entities.entity.npc.Profession
import com.undefined.quasar.interfaces.entities.entity.npc.Type
import com.undefined.quasar.v1_21_4.impl.entity.LivingEntity
import com.undefined.quasar.v1_21_4.mappings.FieldMappings
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.npc.VillagerData
import net.minecraft.world.entity.npc.VillagerProfession
import net.minecraft.world.entity.npc.VillagerType
import net.minecraft.world.level.Level
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftVillager.CraftProfession
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftVillager.CraftType


class ZombieVillager : LivingEntity(EntityType.ZOMBIE_VILLAGER), ZombieVillager {

    private var DATA_VILLAGER_DATA: EntityDataAccessor<VillagerData>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.ZombieVillager::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.ZombieVillager.DATA_VILLAGER_DATA
        )

    private var DATA_CONVERTING_ID: EntityDataAccessor<Boolean>? = null
        get() = getEntityDataAccessor(field,
            net.minecraft.world.entity.monster.ZombieVillager::class.java,
            FieldMappings.Entity.LivingEntity.Mob.Monster.ZombieVillager.DATA_CONVERTING_ID
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

    override fun setConverting(converting: Boolean) = setEntityDataAccessor(DATA_CONVERTING_ID, converting)

    override fun isConverting(): Boolean = getEntityDataValue(DATA_CONVERTING_ID) ?: false

    override fun getEntityData(): JsonObject {
        val zombieJson = super.getEntityData()
        val zombieVillagerJson = JsonObject()
        zombieVillagerJson.addProperty("type", getType().name)
        zombieVillagerJson.addProperty("profession", getProfession().name)
        zombieVillagerJson.addProperty("converting", isConverting())
        zombieJson.add("zombieVillage", zombieVillagerJson)
        return zombieJson
    }

    override fun setEntityData(jsonObject: JsonObject) {
        super<LivingEntity>.setEntityData(jsonObject)
        val zombieVillagerJson = jsonObject["zombieVillage"].asJsonObject
        setProfession(Profession.valueOf(zombieVillagerJson["profession"].asString))
        setType(Type.valueOf(zombieVillagerJson["type"].asString))
        setConverting(zombieVillagerJson["converting"].asBoolean)
    }

    override fun setDefaultValues() {
        super.setDefaultValues()
        setConverting(false)
        setProfession(Profession.NONE)
        setType(Type.PLAINS)
    }

    override fun getEntityClass(level: Level): Entity =
        net.minecraft.world.entity.monster.ZombieVillager(net.minecraft.world.entity.EntityType.ZOMBIE_VILLAGER, level)

    override fun getTests(): MutableList<() -> String> =
        super.getTests().apply { addAll(mutableListOf(
            {
                setConverting(true)
                getTestMessage(this@ZombieVillager::class, "Set converting", isConverting())
            },
            {
                setConverting(false)
                getTestMessage(this@ZombieVillager::class, "Set converting", isConverting())
            }
        ))
            addAll(Profession.entries.map {
                {
                    setProfession(it)
                    getTestMessage(this@ZombieVillager::class, "Set profession", getProfession().name.lowercase())
                }
            })

            addAll(Type.entries.map {
                {
                    setType(it)
                    getTestMessage(this@ZombieVillager::class, "Set type", getType().name.lowercase())
                }
            })
        }
}