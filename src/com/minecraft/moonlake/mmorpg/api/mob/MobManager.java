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

import com.minecraft.moonlake.mmorpg.api.mob.droptable.DropOption;
import com.minecraft.moonlake.mmorpg.exception.MMORPGIllegalArgumentException;
import com.minecraft.moonlake.mmorpg.manager.MMORPGManager;
import com.minecraft.moonlake.mmorpg.manager.RandomManager;
import com.minecraft.moonlake.mmorpg.task.TaskManager;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Created by MoonLake on 2016/6/29.
 */
public final class MobManager extends MMORPGManager {

    private final static Map<UUID, Mob> mobMap;

    static {

        mobMap = new HashMap<>();
    }

    /**
     * 在指定位置生成自定义怪物
     *
     * @param type 怪物类型
     * @param location 位置
     * @return Mob 怪物对象 异常则返回 null
     */
    public static <T extends Mob> T spawn(MobType type, Location location) {

        return spawn(type, location, null);
    }

    /**
     * 在指定位置生成自定义怪物
     *
     * @param type 怪物类型
     * @param location 位置
     * @param mobData 怪物数据
     * @param <T> 怪物类
     * @return Mob 怪物对象 异常则返回 null
     */
    public static <T extends Mob> T spawn(MobType type, Location location, MobData mobData) {

        if(type == null) {

            throw new MMORPGIllegalArgumentException("Mob spawn method type param not is null.");
        }
        org.bukkit.entity.Entity entity = location.getWorld().spawn(location, type.getEntityClass());
        Mob mob = type.newInstance(entity);

        if(mob != null) {

            if(mobData != null) {

                mob.setMaxHealth(mobData.getMaxHealth());
                mob.setMobLevel(mobData.getLevel());
                mob.setDropExp(mobData.getDropExp());
                mob.setCustomName(mobData.getDisplayName());
                mob.setDropItems(mobData.getDropItems());
                mob.setHealth(mobData.getHealth());
            }
            mobMap.put(mob.getUniqueId(), mob);
        }
        return (T) mob;
    }

    /**
     * 在指定位置生成自定义怪物
     *
     * @param clazz 怪物类
     * @param location 位置
     * @param <T> 怪物类
     * @return Mob 怪物对象 异常则返回 null
     */
    public static <T extends Mob> T spawn(Class<T> clazz, Location location) {

        return spawn(clazz, location, null);
    }

    /**
     * 在指定位置生成自定义怪物
     *
     * @param clazz 怪物类
     * @param location 位置
     * @param mobData 怪物数据
     * @param <T> 怪物类
     * @return Mob 怪物杜玺 异常则返回 null
     */
    public static <T extends Mob> T spawn(Class<T> clazz, Location location, MobData mobData) {

        return spawn(MobType.fromClass(clazz), location, mobData);
    }

    /**
     * 从指定怪物的 UUID 获取怪物对象
     *
     * @param uuid 怪物的 UUID
     * @return Mob 对象 没有则返回 null
     */
    public static Mob get(UUID uuid) {

        return mobMap.get(uuid);
    }

    /**
     * 获取指定 Bukkit 实体是否为 怪物对象
     *
     * @param entity 实体
     * @return true 则该实体是怪物 else 不是
     */
    public static boolean isMob(org.bukkit.entity.Entity entity) {

        return entity != null && get(entity.getUniqueId()) != null;
    }

    /**
     * 清除并关闭指定怪物的对象
     *
     * @param uuid 怪物的 UUID
     */
    public static void close(UUID uuid) {

        if(mobMap.containsKey(uuid)) {

            mobMap.remove(uuid);
        }
    }

    /**
     * 在指定位置生成击杀怪物的全息信息
     *
     * @param location 位置
     * @param info 显示的信息
     * @param time 显示的时间
     */
    public static void spawnKillMobHologramInfo(Location location, String info, int time) {

        final ArmorStand object = location.getWorld().spawn(location, ArmorStand.class);
        object.setSmall(true);
        object.setVisible(false);
        object.setBasePlate(false);
        object.setGravity(false);
        object.setCustomName(info);
        object.setCustomNameVisible(true);

        TaskManager.runTaskLater(new Runnable() {

            @Override
            public void run() {

                if(object != null) {

                    object.remove();
                }
            }
        }, time * 20L);
    }

    /**
     * 将指定掉落物品集合进行概率掉落
     *
     * @param dropItemMap 掉落物品集合
     * @return 概率掉落的物品集合
     */
    public static List<ItemStack> chanceDropTable(Map<ItemStack, DropOption> dropItemMap) {

        if(dropItemMap != null && dropItemMap.size() > 0) {

            List<ItemStack> dropItemList = new ArrayList<>();
            Iterator<Map.Entry<ItemStack, DropOption>> iterator = dropItemMap.entrySet().iterator();

            while (iterator.hasNext()) {

                Map.Entry<ItemStack, DropOption> entry = iterator.next();
                DropOption option = entry.getValue();
                ItemStack dropItem = entry.getKey();

                if(RandomManager.getRandom().nextDouble() <= option.getChance()) {

                    int finalAmount = RandomManager.getRandomNumber(option.getMinAmount(), option.getMaxAmount());
                    dropItem.setAmount(finalAmount);
                    dropItemList.add(dropItem);
                }
            }
            return dropItemList;
        }
        return new ArrayList<>();
    }

    /**
     * 关闭对象类并释放内存占用
     */
    public static void close() {

        for(Mob mob : mobMap.values()) {

            if(mob != null && !mob.isDead()) {

                mob.remove();
            }
        }
        mobMap.clear();
    }
}
