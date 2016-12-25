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
 
  
package com.minecraft.moonlake.mmorpg.manager;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.adapter.AdapterManager;
import com.minecraft.moonlake.mmorpg.api.pet.Pet;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Created by MoonLake on 2016/5/21.
 */
public final class EntityManager extends com.minecraft.moonlake.manager.EntityManager {

    private final static Map<UUID, Pet> petMap;

    static {

        petMap = new HashMap<>();
    }

    /**
     * 获取实体是否是宠物
     *
     * @param pet 实体
     * @return true 此实体是宠物 else 不是
     */
    public static boolean isPet(Entity pet) {

        return pet != null && pet.hasMetadata("petOwner");
    }

    /**
     * 获取实体是否是宠物
     *
     * @param pet 实体
     * @return true 此实体是宠物 else 不是
     */
    public static boolean isPet(com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity pet) {

        return isPet(pet.getBukkitEntity());
    }

    /**
     * 获取宠物对象从 Entity
     *
     * @param entity 实体对象
     * @return 宠物对象 没有则返回 null
     */
    public static Pet getPetFromEntity(Entity entity) {

        return isPet(entity) ? getPetFromUUID(entity.getUniqueId()) : null;
    }

    /**
     * 获取宠物对象从 UUID
     *
     * @param uuid 实体 UUID
     * @return 宠物对象 没有则返回 null
     */
    public static Pet getPetFromUUID(UUID uuid) {

        return petMap.containsKey(uuid) ? petMap.get(uuid) : null;
    }

    /**
     * 获取宠物的主人 MMORPG 玩家对象
     *
     * @param pet 宠物
     * @return MMORPG 玩家对象 没有则返回 null
     */
    public static MMORPGPlayer getPetOwner(Pet pet) {

        if(isPet(pet.getEntity())) {

            return getPetOwner(pet.getEntity());
        }
        return null;
    }

    /**
     * 获取宠物的主人 MMORPG 玩家对象
     *
     * @param entity 宠物
     * @return MMORPG 玩家对象 没有则返回 null
     */
    public static MMORPGPlayer getPetOwner(com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity) {

        if(isPet(entity)) {

            String name = entity.getMetadata("petOwner").get(0).asString();

            return AccountManager.getPlayer(name);
        }
        return null;
    }

    /**
     * 获取宠物的主人 MMORPG 玩家对象
     *
     * @param entity 宠物
     * @return MMORPG 玩家对象 没有则返回 null
     */
    public static MMORPGPlayer getPetOwner(Entity entity) {

        if(isPet(entity)) {

            String name = entity.getMetadata("petOwner").get(0).asString();

            return AccountManager.getPlayer(name);
        }
        return null;
    }

    /**
     * 获取指定实体是否为 MMORPG 玩家的坐骑
     *
     * @param entity 实体对象
     * @return true 则是坐骑 else 不是
     */
    public static boolean isMount(Entity entity) {

        return entity != null && entity.hasMetadata("mount");
    }

    /**
     * 设置实体的沉默属性
     *
     * @param entity 实体
     * @param flag 是否沉默
     */
    public static void setSilent(com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity, boolean flag) {

        if(entity == null) {

            return;
        }
        setSilent(entity.getBukkitEntity(), flag);
    }

    /**
     * 在指定位置掉落物品栈
     *
     * @param location 位置
     * @param item 物品栈
     * @return 实体物品 异常返回 null
     */
    public static Item dropItem(Location location, ItemStack item) {

        return dropItem(location, item, ItemManager.getItemDisplayName(item), false);
    }

    /**
     * 在指定位置掉落物品栈
     *
     * @param location 位置
     * @param item 物品栈
     * @param flag 是否显示物品栈自定义名称
     * @return 实体物品 异常返回 null
     */
    public static Item dropItem(Location location, ItemStack item, boolean flag) {

        return dropItem(location, item, ItemManager.getItemDisplayName(item), flag);
    }

    /**
     * 在指定位置掉落物品栈
     *
     * @param location 位置
     * @param item 物品栈
     * @param name 自定义名称
     * @return 实体物品 异常返回 null
     */
    public static Item dropItem(Location location, ItemStack item, String name) {

        return dropItem(location, item, name, false);
    }

    /**
     * 在指定位置掉落物品栈
     *
     * @param location 位置
     * @param item 物品栈
     * @param name 自定义名称
     * @param flag 是否显示自定义名称
     * @return 实体物品 异常返回 null
     */
    public static Item dropItem(Location location, ItemStack item, String name, boolean flag) {

        Item ei = null;

        try {

            Class<?> DropItem = com.minecraft.moonlake.mmorpg.nms.DropItem.class;

            Object instance = Reflect.instantiateObject(DropItem, location.getWorld(), location, item);
            Reflect.getMethod(DropItem, "setCustomName", String.class).invoke(instance, name);
            Reflect.getMethod(DropItem, "setCustomNameVisible", Boolean.class).invoke(instance, flag);

            ei = (Item) Reflect.getMethod(DropItem, "drop").invoke(instance);
        }
        catch (Exception e) {

            MMORPGPlugin.getInstances().log("掉落实体物品 '" + ItemManager.getItemDisplayName(item) + "' 时异常: " + e.getMessage());

            if(MMORPGPlugin.getInstances().isDebug()) {

                e.printStackTrace();
            }
        }
        return ei;
    }

    /**
     * 设置指定实体的击退抗性值
     *
     * @param entity 实体
     * @param value 值
     *
     * @see com.minecraft.moonlake.mmorpg.manager.EntityManager.Attribute.AttributeType#KNOCK_BACK_RESISTANCE
     */
    public static void setKnockBackResistance(com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity, double value) {

        setAttribute(entity, AttributeType.KNOCK_BACK_RESISTANCE, value);
    }

    /**
     * 获取指定实体的击退抗性值
     *
     * @param entity 实体
     * @return 实体的击退抗性值 异常返回 -1
     */
    public static double getKnockBackResistance(com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity) {

        return getAttribute(entity, AttributeType.KNOCK_BACK_RESISTANCE);
    }

    /**
     * 设置指定实体的移动速度值
     *
     * @param entity 实体
     * @param value 值
     *
     * @see com.minecraft.moonlake.mmorpg.manager.EntityManager.Attribute.AttributeType#MOVEMENT_SPEED
     */
    public static void setMovementSpeed(com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity, double value) {

        setAttribute(entity, AttributeType.MOVEMENT_SPEED, value);
    }

    /**
     * 设置指定实体的攻击伤害值
     *
     * @param entity 实体
     * @param value 值
     *
     * @see com.minecraft.moonlake.mmorpg.manager.EntityManager.Attribute.AttributeType#ATTACK_DAMAGE
     */
    public static void setDamage(com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity, double value) {

        setAttribute(entity, AttributeType.ATTACK_DAMAGE, value);
    }

    /**
     * 设置指定实体的血量上限值
     *
     * @param entity 实体
     * @param value 值
     *
     * @see com.minecraft.moonlake.mmorpg.manager.EntityManager.Attribute.AttributeType#MAX_HEALTH
     */
    public static void setMaxHealth(com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity, double value) {

        setMaxHealth(entity, value, true);
    }

    /**
     * 设置指定实体的血量上限值
     *
     * @param entity 实体
     * @param value 值
     * @param regain 是否恢复
     *
     * @see com.minecraft.moonlake.mmorpg.manager.EntityManager.Attribute.AttributeType#MAX_HEALTH
     */
    public static void setMaxHealth(com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity, double value, boolean regain) {

        setAttribute(entity, AttributeType.MAX_HEALTH, value);

        if(regain && entity instanceof LivingEntity) {

            ((LivingEntity)entity).setHealth(((LivingEntity)entity).getMaxHealth());
        }
    }

    /**
     * 设置指定实体的追踪范围
     *
     * @param entity 实体
     * @param value 值
     *
     * @see com.minecraft.moonlake.mmorpg.manager.EntityManager.Attribute.AttributeType#FOLLOW_RANGE
     */
    public static void setFollowRange(com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity, double value) {

        setAttribute(entity, AttributeType.FOLLOW_RANGE, value);
    }

    /**
     * 获取指定实体的移动速度值
     *
     * @param entity 实体
     * @return 实体的移动速度值 异常返回 -1
     */
    public static double getMovementSpeed(com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity) {

        return getAttribute(entity, AttributeType.MOVEMENT_SPEED);
    }

    /**
     * 获取指定实体的攻击伤害值
     *
     * @param entity 实体
     * @return 实体的攻击伤害值 异常返回 -1
     */
    public static double getDamage(com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity) {

        return getAttribute(entity, AttributeType.ATTACK_DAMAGE);
    }

    /**
     * 获取指定实体的追踪范围
     *
     * @param entity 实体
     * @return 实体的追踪范围 异常返回 -1
     */
    public static double getFollowRange(com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity) {

        return getAttribute(entity, AttributeType.FOLLOW_RANGE);
    }

    /**
     * 设置指定实体的指定属性类型的值
     *
     * @param entity 实体
     * @param type 属性类型
     * @param value 值
     */
    public static void setAttribute(com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity, AttributeType type, double value) {

        setAttribute(entity.getBukkitEntity(), type, value);
    }

    /**
     * 获取指定实体的指定属性类型的值
     *
     * @param entity 实体
     * @param type 属性类型
     * @return 属性类型的值 异常返回 -1
     */
    public static double getAttribute(com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity, AttributeType type) {

        return getAttribute(entity.getBukkitEntity(), type);
    }

    /**
     * 清除实体的路径发现者 AI
     *
     * @param entity 实体
     */
    public static void removePathFinders(com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity) {

        removePathFinders(entity.getBukkitEntity());
    }

    /**
     * 获取指定位置的半径内的实体
     *
     * @param location 位置
     * @param radius 半径
     * @return 实体集合
     */
    public static List<com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity> getRPGEntityInRadius(Location location, double radius) {

        return getRPGEntityInRadius(location, radius, new HashSet<Class<? extends LivingEntity>>());
    }

    /**
     * 获取指定位置的半径内的实体
     *
     * @param location 位置
     * @param radius 半径
     * @param owner 无视主人
     * @return 实体集合
     */
    public static List<com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity> getRPGEntityInRadius(Location location, double radius, Player owner) {

        List<com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity> entityList = getRPGEntityInRadius(location, radius);

        for(int i = 0; i < entityList.size(); i++) {

            com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity = entityList.get(i);

            if(entity.isPlayer() && entity.asPlayer().equals(owner)) {

                entityList.remove(entity);

                i--;
            }
        }
        return entityList;
    }

    /**
     * 获取指定位置的半径内的实体
     *
     * @param location 位置
     * @param radius 半径
     * @param owner 无视主人
     * @param ignoreEntity 无视的实体
     * @return 实体集合
     */
    public static List<com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity> getRPGEntityInRadius(Location location, double radius, Player owner, Set<Class<? extends LivingEntity>> ignoreEntity) {

        List<com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity> entityList = getRPGEntityInRadius(location, radius, ignoreEntity);

        for(int i = 0; i < entityList.size(); i++) {

            com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity = entityList.get(i);

            if(entity.isPlayer() && entity.asPlayer().equals(owner)) {

                entityList.remove(entity);

                i--;
            }
        }
        return entityList;
    }

    /**
     * 获取指定位置的半径内的实体
     *
     * @param location 位置
     * @param radius 半径
     * @param owner 无视主人
     * @return 实体集合
     */
    public static List<com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity> getEntityInRadius(Location location, double radius, MMORPGPlayer owner) {

        List<com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity> entityList = getRPGEntityInRadius(location, radius);

        for(int i = 0; i < entityList.size(); i++) {

            com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity = entityList.get(i);

            if(entity.isPlayer() && entity.asMMORPGPlayer().equals(owner)) {

                entityList.remove(entity);

                i--;
            }
        }
        return entityList;
    }

    /**
     * 获取指定位置的半径内的实体
     *
     * @param location 位置
     * @param radius 半径
     * @param owner 无视主人
     * @param ignoreEntity 无视的实体
     * @return 实体集合
     */
    public static List<com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity> getRPGEntityInRadius(Location location, double radius, MMORPGPlayer owner, Set<Class<? extends LivingEntity>> ignoreEntity) {

        List<com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity> entityList = getRPGEntityInRadius(location, radius, ignoreEntity);

        for(int i = 0; i < entityList.size(); i++) {

            com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity entity = entityList.get(i);

            if(entity.isPlayer() && entity.asMMORPGPlayer().equals(owner)) {

                entityList.remove(entity);

                i--;
            }
        }
        return entityList;
    }

    /**
     * 获取指定位置的半径内的实体
     *
     * @param location 位置
     * @param radius 半径
     * @param ignoreEntity 无视的实体
     * @return 实体集合
     */
    public static List<com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity> getRPGEntityInRadius(Location location, double radius, Set<Class<? extends LivingEntity>> ignoreEntity) {

        List<com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity> entityList = new ArrayList<>();

        for(Entity entity : location.getWorld().getNearbyEntities(location, radius, radius, radius)) {

            if(entity != null && !entity.isDead() && entity instanceof LivingEntity && !ignoreEntity.contains(entity.getClass())) {

                entityList.add(AdapterManager.adap(entity));
            }
        }
        return entityList;
    }

    /**
     * 将宠物对象存储到宠物列表
     *
     * @param pet 宠物对象
     * @return 宠物对象
     */
    public static Pet putPetList(Pet pet) {

        if(pet.getEntity() != null) {

            petMap.put(pet.getEntity().getUniqueId(), pet);
        }
        return pet;
    }

    /**
     * 将宠物对象从宠物列表删除
     *
     * @param pet 宠物对象
     * @return 宠物对象
     */
    public static Pet delPetFromList(Pet pet) {

        if(petMap.containsKey(pet.getEntity().getUniqueId())) {

            delPetFromList(pet.getEntity().getUniqueId());
        }
        return pet;
    }

    /**
     * 将宠物对象从宠物列表删除
     *
     * @param uuid 宠物 UUID
     * @return 宠物对象
     */
    public static Pet delPetFromList(UUID uuid) {

        Pet pet = null;

        if(petMap.containsKey(uuid)) {

            pet = getPetFromUUID(uuid);

            petMap.remove(uuid);
        }
        return pet;
    }

    /**
     * 关闭对象类并释放内存占用
     */
    public static void close() {

        for(Pet pet : petMap.values()) {

            if(pet.getEntity() != null) {

                pet.getEntity().remove();
            }
        }
        petMap.clear();
    }
}
