/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
  
package com.minecraft.moonlake.mmorpg.api.mob;

import com.minecraft.moonlake.mmorpg.api.mob.type.*;
import com.minecraft.moonlake.reflect.Reflect;

import org.bukkit.Location;
import org.bukkit.entity.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/6/29.
 */
public enum MobType {

    /**
     * 怪物类型: 僵尸
     */
    ZOMBIE("Zombie", "Mob Zombie", Zombie.class, MobZombie.class),
    /**
     * 怪物类型: 小僵尸
     */
    ZOMBIE_BABY("ZombieBaby", "Mob Zombie Baby", Zombie.class, MobZombieBaby.class),
    /**
     * 怪物类型: 猪人僵尸
     */
    ZOMBIE_PIG("ZombiePig", "Mob Zombie Pig", PigZombie.class, MobZombiePig.class),
    /**
     * 怪物类型: 小猪人僵尸
     */
    ZOMBIE_PIG_BABY("ZombiePigBaby", "Mob Zombie Pig Baby", PigZombie.class, MobZombiePigBaby.class),
    /**
     * 怪物类型: 尸壳僵尸
     */
    ZOMBIE_HUSK("ZombieHusk", "Mob Zombie Husk", Zombie.class, MobZombieHusk.class),
    /**
     * 怪物类型: 小尸壳僵尸
     */
    ZOMBIE_HUSK_BABY("ZombieHuskBaby", "Mob Zombie Husk Baby", Zombie.class, MobZombieHuskBaby.class),
    /**
     * 怪物类型: 村民僵尸
     */
    ZOMBIE_VILLAGER("ZombieVillager", "Mob Zombie Villager", Zombie.class, MobZombieVillager.class),
    /**
     * 怪物类型: 小村民僵尸
     */
    ZOMBIE_VILLAGER_BABY("ZombieVillagerBaby", "Mob Zombie Villager Baby", Zombie.class, MobZombieVillagerBaby.class),
    /**
     * 怪物类型: 骷髅
     */
    SKELETON("Skeleton", "Mob Skeleton", Skeleton.class, MobSkeleton.class),
    /**
     * 怪物类型: 流髑骷髅
     */
    SKELETON_STRAY("SkeletonStray", "Mob Skeleton Stray", Skeleton.class, MobSkeletonStray.class),
    /**
     * 怪物类型: 凋零骷髅
     */
    SKELETON_WITHER("SkeletonWither", "Mob Skeleton Wither", Skeleton.class, MobSkeletonWither.class),
    /**
     * 怪物类型: 苦力怕
     */
    CREEPER("Creeper", "Mob Creeper", Creeper.class, MobCreeper.class),
    /**
     * 怪物类型: 闪电苦力怕
     */
    CREEPER_BOLT("CreeperBolt", "Mob Creeper Bolt", Creeper.class, MobCreeperBolt.class),
    /**
     * 怪物类型: 蜘蛛
     */
    SPIDER("Spider", "Mob Spider", Spider.class, MobSpider.class),
    /**
     * 怪物类型: 蜘蛛骑士
     */
    SPIDER_KNIGHT("SpiderKnight", "Mob Spider Knight", Spider.class, MobSpiderKnight.class),
    /**
     * 怪物类型: 鸡
     */
    CHICKEN("Chicken", "Mob Chicken", Chicken.class, MobChicken.class),
    /**
     * 怪物类型: 女巫
     */
    WITCH("Witch", "Mob Witch", Witch.class, MobWitch.class),
    /**
     * 怪物类型: 史莱姆
     */
    SLIME("Slime", "Mob Slime", Slime.class, MobSlime.class),
    /**
     * 怪物类型: 牛
     */
    COW("Cow", "Mob Cow", Cow.class, MobCow.class),
    /**
     * 怪物类型: 狼
     */
    WOLF("Wolf", "Mob Wolf", Wolf.class, MobWolf.class),
    /**
     * 怪物类型: 末影人
     */
    ENDERMAN("Enderman", "Mob Enderman", Enderman.class, MobEnderman.class),
    /**
     * 怪物类型: 羊
     */
    SHEEP("Sheep", "Mob Sheep", Sheep.class, MobSheep.class),
    /**
     * 怪物类型: 蝙蝠
     */
    BAT("Bat", "Mob Bat", Bat.class, MobBat.class),
    /**
     * 怪物类型: 豹猫
     */
    OCELOT("Ocelot", "Mob Ocelot", Ocelot.class, MobOcelot.class),
    /**
     * 怪物类型: 马
     */
    HORSE("Horse", "Mob Horse", Horse.class, MobHorse.class),
    /**
     * 怪物类型: 鱿鱼
     */
    SQUID("Squid", "Mob Squid", Squid.class, MobSquid.class),
    /**
     * 怪物类型: 村民
     */
    VILLAGER("Villager", "Mob Villager", Villager.class, MobVillager.class),
    /**
     * 怪物类型: 哞菇牛
     */
    MUSHROOM_COW("MushroomCow", "Mob Mushroom Cow", MushroomCow.class, MobMushroomCow.class),
    /**
     * 怪物类型: 毒蜘蛛
     */
    CAVE_SPIDER("CaveSpider", "Mob Cave Spider", CaveSpider.class, MobCaveSpider.class),
    /**
     * 怪物类型: 毒蜘蛛骑士
     */
    CAVE_SPIDER_KNIGHT("CaveSpiderKnight", "Mob Cave Spider Knight", CaveSpider.class, MobCaveSpiderKnight.class),
    /**
     * 怪物类型: 兔子
     */
    RABBIT("Rabbit", "Mob Rabbit", Rabbit.class, MobRabbit.class),
    /**
     * 怪物类型: 杀手兔
     */
    RABBIT_KILLER("RabbitKiller", "Mob Rabbit Killer", Rabbit.class, MobRabbitKiller.class),
    /**
     * 怪物类型: 北极熊
     */
    POLAR_BEAR("PolarBear", "Mob PolarBear", PolarBear.class, MobPolarBear.class),
    /**
     * 怪物类型: 末影龙
     */
    ENDER_DRAGON("EnderDragon", "Mob Ender Dragon", EnderDragon.class, MobEnderDragon.class),
    /**
     * 怪物类型: 烈焰人
     */
    BLAZE("Blaze", "Mob Blaze", Blaze.class, MobBlaze.class),
    /**
     * 怪物类型: 恶魂
     */
    GHAST("Ghast", "Mob Ghast", Ghast.class, MobGhast.class),
    /**
     * 怪物类型: 岩浆怪
     */
    MAGMA_CUBE("MagmaCube", "Mob Magma Cube", MagmaCube.class, MobMagmaCube.class),
    /**
     * 怪物类型: 蠹虫
     */
    SILVERFISH("Silverfish", "Mob Silverfish", Silverfish.class, MobSilverfish.class),
    /**
     * 怪物类型: 末影螨
     */
    ENDER_MITE("Endermite", "Mob Endermite", Endermite.class, MobEndermite.class),
    /**
     * 怪物类型: 守卫者
     */
    GUARDIAN("Guardian", "Mob Guardian", Guardian.class, MobGuardian.class),
    /**
     * 怪物类型: 远古守护者
     */
    GUARDIAN_ELDER("Guardian Elder", "Mob Guardian Elder", Guardian.class, MobGuardianElder.class),
    /**
     * 怪物类型: 潜影贝
     */
    SHULKER("Shulker", "Mob Shulker", Shulker.class, MobShulker.class),
    /**
     * 怪物类型: 雪人
     */
    SNOWMAN("Snowman", "Mob Snowman", Snowman.class, MobSnowman.class),
    /**
     * 怪物类型: 巨人
     */
    GIANT("Giant", "Mob Giant", Giant.class, MobGiant.class),
    /**
     * 怪物类型: 凋零
     */
    WITHER("Wither", "Mob Wither", Wither.class, MobWither.class),
    ;

    private String type;
    private String name;
    private Class<? extends Entity> entityClass;
    private Class<? extends Mob> clazz;
    private final static Map<String, MobType> NAME_MAP;
    private final static Map<Class<? extends Mob>, MobType> IKEY_MAP;

    static {

        NAME_MAP = new HashMap<>();
        IKEY_MAP = new HashMap<>();

        for(MobType mobType : values()) {

            NAME_MAP.put(mobType.type.toLowerCase(), mobType);
            IKEY_MAP.put(mobType.clazz, mobType);
        }
    }

    MobType(String type, String name, Class<? extends Entity> entityClass, Class<? extends Mob> clazz) {

        this.type = type;
        this.name = name;
        this.entityClass = entityClass;
        this.clazz = clazz;
    }

    public String getType() {

        return type;
    }

    public String getName() {

        return name;
    }

    public Class<? extends Entity> getEntityClass() {

        return entityClass;
    }

    public Class<? extends Mob> getClazz() {

        return clazz;
    }

    public <T extends Mob> T newInstance(Object... argsObject) {

        T t = null;

        try {

            t = (T) Reflect.instantiateObject(getClazz(), argsObject);
        }
        catch (Exception e) {

        }
        return t;
    }
    
    /**
     * 在指定位置生成自定义怪物
     * 
     * @param location 位置
     * @see com.minecraft.moonlake.mmorpg.api.mob.MobManager#spawn(MobType, Location)
     * @return Mob 怪物对象 异常则返回 null
     */
    public Mob spawn(Location location) {
    	
    	return MobManager.spawn(this, location);
    }

    public static MobType fromType(String type) {

        return NAME_MAP.get(type.toLowerCase());
    }

    public static MobType fromClass(Class<? extends Mob> clazz) {

        return IKEY_MAP.get(clazz);
    }
}
