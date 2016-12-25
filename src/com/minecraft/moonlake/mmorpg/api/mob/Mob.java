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

import com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity;
import com.minecraft.moonlake.mmorpg.api.adapter.entity.equipment.EquipmentType;
import com.minecraft.moonlake.mmorpg.api.mob.droptable.DropOption;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.effect.EffectType;
import com.minecraft.moonlake.mmorpg.manager.EntityManager;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by MoonLake on 2016/6/28.
 */
public interface Mob extends Entity {

    /**
     * 获取此怪物的类型
     * 
     * @return 类型
     */
    MobType getType();

    /**
     * 设置此怪物的等级
     *
     * @param mobLevel 等级
     */
    void setMobLevel(int mobLevel);

    /**
     * 获取此怪物的等级
     *
     * @return 等级
     */
    int getMobLevel();

    /**
     * 设置此怪物死亡的掉落经验
     *
     * @param dropExp 经验
     */
    void setDropExp(int dropExp);

    /**
     * 获取此怪物死亡的掉落经验
     *
     * @return 经验
     */
    int getDropExp();

    /**
     * 获取此怪物的显示名称
     *
     * @return 显示名称
     */
    String getDisplayName();

    /**
     * 设置此怪物死亡的掉落物品
     *
     * @param items 物品集合
     */
    void setDropItems(Map<ItemStack, DropOption> items);

    /**
     * 获取此怪物死亡的掉落物品集合
     *
     * @return 物品集合
     */
    Map<ItemStack, DropOption> getDropItems();

    /**
     * 清除此怪物死亡的掉落物品集合
     */
    void clearDropItems();

    /**
     * 将此怪物死亡的掉落物品集合添加物品
     *
     * @param item 物品
     * @param option 配置
     */
    void addDropItems(ItemStack item, DropOption option);

    /**
     * 设置此怪物指定装备栏的物品
     *
     * @param type 装备栏
     * @param item 物品
     */
    void setEquipmentItem(EquipmentType type, ItemStack item);

    /**
     * 设置此怪物指定装备栏的物品
     *
     * @param type 装备栏
     * @param item 物品
     * @param dropChange 装备掉落几率
     */
    void setEquipmentItem(EquipmentType type, ItemStack item, float dropChange);

    /**
     * 获取此实体指定装备栏的物品
     *
     * @param type 装备栏
     * @return 物品
     */
    ItemStack getEquipmentItem(EquipmentType type);

    /**
     * 清除此指定所有装备栏的物品
     */
    void clearEquipmentItems();

    /**
     * 获取此怪物的所在位置
     *
     * @return 位置
     */
    Location getLocation();

    /**
     * 获取此怪物的 Bukkit 对象
     *
     * @return Bukkit 怪物对象
     */
    org.bukkit.entity.Entity getBukkitEntity();

    /**
     * 获取此怪物是否是 LivingEntity 有生命怪物
     *
     * @return true 则是 else 不是
     */
    boolean isLiving();

    /**
     * 获取此怪物是否是 Creature 生物
     *
     * @return true 则是 else 不是
     */
    boolean isCreature();

    /**
     * 获取此怪物是否是 Tameable 可驯服生物
     *
     * @return true 则是 else 不是
     */
    boolean isTameable();

    /**
     * 获取此怪物是否是 Ageable 年龄生物
     *
     * @return true 则是 else 不是
     */
    boolean isAgeable();

    /**
     * 获取此怪物是否是 Player 玩家
     *
     * @return true 则是 else 不是
     */
    boolean isPlayer();

    /**
     * 获取此怪物的 LivingEntity 有生命怪物对象
     *
     * @return LivingEntity 对象
     */
    LivingEntity asLiving();

    /**
     * 获取此怪物的 Creature 生物对象
     *
     * @return Creature 对象
     */
    Creature asCreature();

    /**
     * 获取此怪物的 Tameable 可驯服生物对象
     *
     * @return Tameable 对象
     */
    Tameable asTameable();

    /**
     * 获取此怪物的 Ageable 年龄生物对象
     *
     * @return Ageable 对象
     */
    Ageable asAgeable();

    /**
     * 获取此怪物的 Player 玩家对象
     *
     * @return Player 对象
     */
    Player asPlayer();

    /**
     * 获取此怪物的 MMORPGPlayer 玩家对象
     *
     * @return MMORPGPlayer 对象
     */
    MMORPGPlayer asMMORPGPlayer();

    /**
     * 获取此怪物的 UUID 对象
     *
     * @return UUID 对象
     */
    UUID getUniqueId();

    /**
     * 获取此怪物是否拥有指定怪物的瞄准线
     *
     * @param entity 怪物
     * @return true 则有 else 没有
     */
    boolean hasLineOfSight(Entity entity);

    /**
     * 将此怪物传送到指定目标位置
     *
     * @param location 位置
     */
    void teleport(Location location);

    /**
     * 将此怪物传送到指定目标位置
     *
     * @param world 世界
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     */
    void teleport(World world, double x, double y, double z);

    /**
     * 获取此怪物的当前生命值
     *
     * @return 生命值
     */
    double getHealth();

    /**
     * 获取此怪物的最大生命值
     *
     * @return 最大生命值
     */
    double getMaxHealth();

    /**
     * 获取此怪物是否已经死亡
     *
     * @return true 则死亡 else 没有
     */
    boolean isDead();

    /**
     * 获取此怪物是否是有效的
     *
     * @return true 则有效 else 不
     */
    boolean isValid();

    /**
     * 将此怪物进行清除
     *
     * @return 是否死亡
     */
    boolean remove();

    /**
     * 设置此怪物的当前生命值
     *
     * @param health 生命值 (0.0 - maxHealth)
     */
    void setHealth(double health);

    /**
     * 设置此怪物的最大生命值
     *
     * @param maxHealth 最大生命值
     */
    void setMaxHealth(double maxHealth);

    /**
     * 设置此怪物的燃烧时间 (Tick)
     *
     * @param ticks 时间
     */
    void setFireTicks(int ticks);

    /**
     * 获取此怪物的自定义名称
     *
     * @return 自定义名称 没有则返回 null
     */
    String getCustomName();

    /**
     * 获取此怪物的自定义名称是否可见
     *
     * @return true 则可见 else 不可见
     */
    boolean getCustomNameVisible();

    /**
     * 设置此怪物的自定义名称
     *
     * @param customName 名称
     */
    void setCustomName(String customName);

    /**
     * 设置此实体的矢量
     *
     * @param vector 矢量
     */
    void setVector(Vector vector);

    /**
     * 将此怪物添加效果
     *
     * @param type 效果类型
     * @param time 效果时间
     */
    void addEffect(EffectType type, int time);

    /**
     * 获取此怪物是否拥有效果
     *
     * @param type 效果类型
     * @return true 拥有此效果类型 else 没有
     */
    boolean hasEffect(EffectType type);

    /**
     * 清除此怪物指定效果
     *
     * @param type 效果类型
     */
    void removeEffect(EffectType type);

    /**
     * 清除此怪物的所有效果
     */
    void removeEffect();

    /**
     * 将此怪物添加药水效果
     *
     * @param type 药水效果类型
     * @param level 等级
     * @param time 时间 (Tick)
     */
    void addPotionEffect(PotionEffectType type, int level, int time);

    /**
     * 将此怪物添加药水效果
     *
     * @param type 药水效果类型
     * @param level 等级
     * @param time 时间 (Tick)
     * @param ambient 是否产生更多的药水半透明粒子
     */
    void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient);

    /**
     * 将此怪物添加药水效果
     *
     * @param type 药水效果类型
     * @param level 等级
     * @param time 时间 (Tick)
     * @param ambient 是否产生更多的药水半透明粒子
     * @param particles 是否拥有粒子效果
     */
    void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient, boolean particles);

    /**
     * 将此怪物添加药水效果
     *
     * @param type  药水效果类型
     * @param level 等级
     * @param time  时间 (Tick)
     * @param ambient 是否产生更多的药水半透明粒子
     * @param particles 是否拥有粒子效果
     * @param color 药水粒子的颜色
     */
    void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient, boolean particles, Color color);

    /**
     * 设置此怪物的骑乘者怪物
     *
     * @param passenger 怪物
     */
    void setPassenger(Entity passenger);

    /**
     * 设置此怪物的骑乘者怪物
     *
     * @param passenger 怪物
     */
    void setPassenger(Player passenger);

    /**
     * 设置此怪物的骑乘者怪物
     *
     * @param passenger 怪物
     */
    void setPassenger(MMORPGPlayer passenger);

    /**
     * 获取此怪物的骑乘者
     *
     * @return 骑乘者怪物 没有则返回 null
     */
    Entity getPassenger();

    /**
     * 获取此怪物的眼部位置
     *
     * @return 眼部位置
     */
    Location getEyeLocation();

    /**
     * 获取此怪物的眼部高度
     *
     * @return 眼部高度
     */
    double getEyeHeight();

    /**
     * 获取此怪物所在的世界对象
     *
     * @return 世界
     */
    World getWorld();

    /**
     * 获取此怪物的目标怪物
     *
     * @return 目标 没有则返回 null
     */
    Entity getTarget();

    /**
     * 获取此怪物的交通工具怪物
     *
     * @return 交通工具
     */
    Entity getVehicle();

    /**
     * 设置此实体的交通工具实体
     *
     * @param vehicle 交通工具
     */
    void setVehicle(Entity vehicle);

    /**
     * 将此怪物从交通工具上下来
     */
    void onEject();

    /**
     * 获取此怪物的名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 强制将此实体受到伤害
     *
     * @param damage 伤害
     */
    void damage(double damage);

    /**
     * 强制将此实体受到伤害
     *
     * @param damage 伤害
     * @param damager 伤害源
     */
    void damage(double damage, Entity damager);

    /**
     * 强制将此实体受到伤害
     *
     * @param damage 伤害
     * @param damager 伤害源
     */
    void damage(double damage, MMORPGPlayer damager);
    /**
     * 设置此怪物无敌时间 (Tick)
     *
     * @param ticks 时间
     */
    void setNoDamageTicks(int ticks);

    /**
     * 设置此怪物是否显示自定义名称
     *
     * @param flag 是否显示
     */
    void setCustomNameVisible(boolean flag);

    /**
     * 设置此怪物的指定 键 的元数据
     *
     * @param key 键
     * @param value 元数据
     */
    void setMetadata(String key, MetadataValue value);

    /**
     * 获取此怪物是否拥有指定 键 的元数据
     *
     * @param key 键
     * @return true 则拥有 else 没有
     */
    boolean hasMetadata(String key);

    /**
     * 获取此怪物指定 键 的元数据集合
     *
     * @param key 键
     * @return 元数据集合 没有则返回空集合
     */
    List<MetadataValue> getMetadata(String key);

    /**
     * 设置此怪物指定属性的值
     *
     * @param type 属性
     * @param value 值
     */
    void setAttribute(EntityManager.AttributeType type, double value);

    /**
     * 设置此怪物的移动速度值
     *
     * @param value 值
     *
     * @see com.minecraft.moonlake.manager.EntityManager.AttributeType#MOVEMENT_SPEED
     */
    void setAttributeMovementSpeed(double value);

    /**
     * 设置此怪物的击退抗性值
     * 
     * @param value 值
     * 
     * @see com.minecraft.moonlake.manager.EntityManager.AttributeType#KNOCK_BACK_RESISTANCE
     */
    void setAttributeKnockbackResistance(double value);
    
    /**
     * 设置此怪物的攻击伤害值
     *
     * @param value 值
     *
     * @see com.minecraft.moonlake.manager.EntityManager.AttributeType#ATTACK_DAMAGE
     */
    void setAttributeDamage(double value);

    /**
     * 设置此怪物的血量上限值
     *
     * @param value 值
     *
     * @see com.minecraft.moonlake.manager.EntityManager.AttributeType#MAX_HEALTH
     */
    void setAttributeMaxHealth(double value);

    /**
     * 设置此怪物的血量上限值
     *
     * @param value 值
     * @param regain 是否恢复
     *
     * @see com.minecraft.moonlake.manager.EntityManager.AttributeType#MAX_HEALTH
     */
    void setAttributeMaxHealth(double value, boolean regain);

    /**
     * 设置此怪物的追踪范围
     *
     * @param value 值
     *
     * @see com.minecraft.moonlake.manager.EntityManager.AttributeType#FOLLOW_RANGE
     */
    void setAttributeFollowRange(double value);

    /**
     * 设置此怪物当远离时是否清除
     *
     * @param flag 是否远离时清除
     */
    void setRemoveWhenFarAway(boolean flag);

    @Override
    int compareTo(Entity o);


}
