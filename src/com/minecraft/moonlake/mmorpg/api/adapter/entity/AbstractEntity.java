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
 
  
package com.minecraft.moonlake.mmorpg.api.adapter.entity;

import com.minecraft.moonlake.mmorpg.api.adapter.AdapterManager;
import com.minecraft.moonlake.mmorpg.api.adapter.entity.equipment.EquipmentType;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.effect.EffectManager;
import com.minecraft.moonlake.mmorpg.effect.EffectType;
import com.minecraft.moonlake.mmorpg.manager.AccountManager;
import com.minecraft.moonlake.mmorpg.manager.EntityManager;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;

/**
 * Created by MoonLake on 2016/6/28.
 */
public abstract class AbstractEntity implements Entity {

    private final org.bukkit.entity.Entity entity;

    public AbstractEntity(org.bukkit.entity.Entity entity) {

        this.entity = entity;
    }

    @Override
    public int compareTo(Entity o) {

        return getUniqueId().compareTo(o.getUniqueId());
    }

    /**
     * 设置此怪物指定装备栏的物品
     *
     * @param type 装备栏
     * @param item 物品
     */
    @Override
    public void setEquipmentItem(EquipmentType type, ItemStack item) {

        setEquipmentItem(type, item, 0f);
    }

    /**
     * 设置此怪物指定装备栏的物品
     *
     * @param type       装备栏
     * @param item       物品
     * @param dropChange 装备掉落几率
     */
    @Override
    public void setEquipmentItem(EquipmentType type, ItemStack item, float dropChange) {

        switch (type) {

            case MAIN_HAND:

                asLiving().getEquipment().setItemInMainHand(item);
                asLiving().getEquipment().setItemInMainHandDropChance(dropChange);
                break;

            case OFF_HAND:

                asLiving().getEquipment().setItemInOffHand(item);
                asLiving().getEquipment().setItemInOffHandDropChance(dropChange);
                break;

            case HEAD:

                asLiving().getEquipment().setHelmet(item);
                asLiving().getEquipment().setHelmetDropChance(dropChange);
                break;

            case CHEST:

                asLiving().getEquipment().setChestplate(item);
                asLiving().getEquipment().setChestplateDropChance(dropChange);
                break;

            case LEGS:

                asLiving().getEquipment().setLeggings(item);
                asLiving().getEquipment().setLeggingsDropChance(dropChange);
                break;

            case FEET:

                asLiving().getEquipment().setBoots(item);
                asLiving().getEquipment().setBootsDropChance(dropChange);
                break;

            default:

                break;
        }
    }

    /**
     * 获取此实体指定装备栏的物品
     *
     * @param type 装备栏
     * @return 物品
     */
    @Override
    public ItemStack getEquipmentItem(EquipmentType type) {

        switch (type) {

            case MAIN_HAND:

                return asLiving().getEquipment().getItemInMainHand();

            case OFF_HAND:

                return asLiving().getEquipment().getItemInOffHand();

            case HEAD:

                return asLiving().getEquipment().getHelmet();

            case CHEST:

                return asLiving().getEquipment().getChestplate();

            case LEGS:

                return asLiving().getEquipment().getLeggings();

            case FEET:

                return asLiving().getEquipment().getBoots();

            default:

                return null;
        }
    }

    /**
     * 清除此指定所有装备栏的物品
     */
    @Override
    public void clearEquipmentItems() {

        asLiving().getEquipment().clear();
    }

    /**
     * 获取此实体的所在位置
     *
     * @return 位置
     */
    @Override
    public Location getLocation() {

        return entity.getLocation();
    }

    /**
     * 获取此实体的 Bukkit 对象
     *
     * @return Bukkit 实体对象
     */
    @Override
    public org.bukkit.entity.Entity getBukkitEntity() {

        return entity;
    }

    /**
     * 获取此实体是否是 LivingEntity 实体
     *
     * @return true 则是 else 不是
     */
    @Override
    public boolean isLiving() {

        return entity instanceof LivingEntity;
    }

    /**
     * 获取此实体是否是 Creature 生物
     *
     * @return true 则是 else 不是
     */
    @Override
    public boolean isCreature() {

        return entity instanceof Creature;
    }

    /**
     * 获取此实体是否是 Tameable 可驯服生物
     *
     * @return true 则是 else 不是
     */
    @Override
    public boolean isTameable() {

        return entity instanceof Tameable;
    }

    /**
     * 获取此实体是否是 Ageable 年龄生物
     *
     * @return true 则是 else 不是
     */
    @Override
    public boolean isAgeable() {

        return entity instanceof Ageable;
    }

    /**
     * 获取此实体是否是 Player 玩家
     *
     * @return true 则是 else 不是
     */
    @Override
    public boolean isPlayer() {

        return entity instanceof Player;
    }

    /**
     * 获取此实体的 Tameable 可驯服生物对象
     *
     * @return Tameable 对象
     */
    @Override
    public Tameable asTameable() {

        return isTameable() ? ((Tameable)entity) : null;
    }

    /**
     * 获取此实体的 Ageable 年龄生物对象
     *
     * @return Ageable 对象
     */
    @Override
    public Ageable asAgeable() {

        return isAgeable() ? ((Ageable)entity) : null;
    }

    /**
     * 获取此实体的 LivingEntity 有生命实体对象
     *
     * @return LivingEntity 对象
     */
    @Override
    public LivingEntity asLiving() {

        return isLiving() ? (LivingEntity)entity : null;
    }

    /**
     * 获取此实体的 Creature 生物对象
     *
     * @return Creature 对象
     */
    @Override
    public Creature asCreature() {

        return isCreature() ? (Creature)entity : null;
    }

    /**
     * 获取此实体的 Player 玩家对象
     *
     * @return Player 对象
     */
    @Override
    public Player asPlayer() {

        return isPlayer() ? (Player)entity : null;
    }

    /**
     * 获取此实体的 MMORPGPlayer 玩家对象
     *
     * @return MMORPGPlayer 对象
     */
    @Override
    public MMORPGPlayer asMMORPGPlayer() {

        return isPlayer() ? AccountManager.getPlayer(asPlayer()) : null;
    }

    /**
     * 获取此实体的 UUID 对象
     *
     * @return UUID 对象
     */
    @Override
    public UUID getUniqueId() {

        return entity.getUniqueId();
    }

    /**
     * 获取此实体是否拥有指定实体的瞄准线
     *
     * @param entity 实体
     * @return true 则有 else 没有
     */
    @Override
    public boolean hasLineOfSight(Entity entity) {

        return ((LivingEntity)getBukkitEntity()).hasLineOfSight(entity.getBukkitEntity());
    }

    /**
     * 将此实体传送到指定目标位置
     *
     * @param location 位置
     */
    @Override
    public void teleport(Location location) {

        getBukkitEntity().teleport(location);
    }

    /**
     * 将此实体传送到指定目标位置
     *
     * @param world 世界
     * @param x     X 坐标
     * @param y     Y 坐标
     * @param z     Z 坐标
     */
    @Override
    public void teleport(World world, double x, double y, double z) {

        teleport(new Location(world, x, y, z));
    }

    /**
     * 获取此实体的当前生命值
     *
     * @return 生命值
     */
    @Override
    public double getHealth() {

        return asLiving().getHealth();
    }

    /**
     * 获取此实体的最大生命值
     *
     * @return 最大生命值
     */
    @Override
    public double getMaxHealth() {

        return asLiving().getMaxHealth();
    }

    /**
     * 获取此实体是否已经死亡
     *
     * @return true 则死亡 else 没有
     */
    @Override
    public boolean isDead() {

        return entity.isDead();
    }

    /**
     * 获取此实体是否是有效的
     *
     * @return true 则有效 else 不
     */
    @Override
    public boolean isValid() {

        return entity.isValid();
    }

    /**
     * 将此实体进行清除
     *
     * @return 是否死亡
     */
    @Override
    public boolean remove() {

        if(entity != null) {

            entity.remove();

            return isDead();
        }
        return true;
    }

    /**
     * 设置此实体的当前生命值
     *
     * @param health 生命值 (0.0 - maxHealth)
     */
    @Override
    public void setHealth(double health) {

        asLiving().setHealth(health);
    }

    /**
     * 设置此实体的最大生命值
     *
     * @param maxHealth 最大生命值
     */
    @Override
    public void setMaxHealth(double maxHealth) {

        asLiving().setMaxHealth(maxHealth);
    }

    /**
     * 设置此实体的燃烧时间 (Tick)
     *
     * @param ticks 时间
     */
    @Override
    public void setFireTicks(int ticks) {

        asLiving().setFireTicks(ticks);
    }

    /**
     * 获取此实体的自定义名称
     *
     * @return 自定义名称 没有则返回 null
     */
    @Override
    public String getCustomName() {

        return entity.getCustomName();
    }

    /**
     * 获取此怪物的自定义名称是否可见
     *
     * @return true 则可见 else 不可见
     */
    public boolean getCustomNameVisible() {

        return entity.isCustomNameVisible();
    }

    /**
     * 设置此实体的自定义名称
     *
     * @param customName 名称
     */
    @Override
    public void setCustomName(String customName) {

        entity.setCustomName(StringUtil.color(customName));
    }

    /**
     * 将此实体添加效果
     *
     * @param type 效果类型
     * @param time 效果时间
     */
    @Override
    public void addEffect(EffectType type, int time) {

        EffectManager.addEffect(asLiving(), type, time);
    }

    /**
     * 获取此实体是否拥有效果
     *
     * @param type 效果类型
     * @return true 拥有此效果类型 else 没有
     */
    @Override
    public boolean hasEffect(EffectType type) {

        return EffectManager.hasEffect(asLiving(), type);
    }

    /**
     * 清除此实体指定效果
     *
     * @param type 效果类型
     */
    @Override
    public void removeEffect(EffectType type) {

        EffectManager.removeEffect(asLiving(), type);
    }

    /**
     * 清除此实体的所有效果
     */
    @Override
    public void removeEffect() {

        EffectManager.removeEffect(asLiving());
    }

    /**
     * 将此实体添加药水效果
     *
     * @param type  药水效果类型
     * @param level 等级
     * @param time  时间 (Tick)
     */
    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time) {

        asLiving().addPotionEffect(new PotionEffect(type, time, level - 1));
    }

    /**
     * 将此实体添加药水效果
     *
     * @param type    药水效果类型
     * @param level   等级
     * @param time    时间 (Tick)
     * @param ambient 是否产生更多的药水半透明粒子
     */
    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient) {

        asLiving().addPotionEffect(new PotionEffect(type, time, level - 1, ambient));
    }

    /**
     * 将此实体添加药水效果
     *
     * @param type      药水效果类型
     * @param level     等级
     * @param time      时间 (Tick)
     * @param ambient   是否产生更多的药水半透明粒子
     * @param particles 是否拥有粒子效果
     */
    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient, boolean particles) {

        asLiving().addPotionEffect(new PotionEffect(type, time, level - 1, ambient, particles));
    }

    /**
     * 将此实体添加药水效果
     *
     * @param type      药水效果类型
     * @param level     等级
     * @param time      时间 (Tick)
     * @param ambient   是否产生更多的药水半透明粒子
     * @param particles 是否拥有粒子效果
     * @param color     药水粒子的颜色
     */
    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient, boolean particles, Color color) {

        asLiving().addPotionEffect(new PotionEffect(type, time, level - 1, ambient, particles, color));
    }

    /**
     * 设置此实体的骑乘者实体
     *
     * @param passenger 实体
     */
    @Override
    public void setPassenger(Entity passenger) {

        entity.setPassenger(passenger.getBukkitEntity());
    }

    /**
     * 设置此实体的骑乘者实体
     *
     * @param passenger 实体
     */
    @Override
    public void setPassenger(Player passenger) {

        entity.setPassenger(passenger);
    }

    /**
     * 设置此实体的骑乘者实体
     *
     * @param passenger 实体
     */
    @Override
    public void setPassenger(MMORPGPlayer passenger) {

        setPassenger(passenger.getBukkitPlayer());
    }

    /**
     * 获取此实体的骑乘者
     *
     * @return 骑乘者实体 没有则返回 null
     */
    @Override
    public Entity getPassenger() {

        return entity.getPassenger() != null ? AdapterManager.adap(entity.getPassenger()) : null;
    }

    /**
     * 获取此实体的眼部位置
     *
     * @return 眼部位置
     */
    @Override
    public Location getEyeLocation() {

        return asLiving().getEyeLocation();
    }

    /**
     * 获取此实体的眼部高度
     *
     * @return 眼部高度
     */
    @Override
    public double getEyeHeight() {

        return asLiving().getEyeHeight();
    }

    /**
     * 获取此实体所在的世界对象
     *
     * @return 世界
     */
    @Override
    public World getWorld() {

        return entity.getWorld();
    }

    /**
     * 获取此实体的目标实体
     *
     * @return 目标 没有则返回 null
     */
    @Override
    public Entity getTarget() {

        if(isCreature()) {

            return AdapterManager.adap(asCreature().getTarget());
        }
        if(getBukkitEntity().getLastDamageCause() != null) {

            return AdapterManager.adap(getBukkitEntity().getLastDamageCause().getEntity());
        }
        return null;
    }

    /**
     * 获取此实体的交通工具实体
     *
     * @return 交通工具
     */
    @Override
    public Entity getVehicle() {

        return AdapterManager.adap(entity.getVehicle());
    }

    /**
     * 设置此实体的交通工具实体
     *
     * @param vehicle 交通工具
     */
    @Override
    public void setVehicle(Entity vehicle) {

        vehicle.setPassenger(this);
    }

    /**
     * 将此实体的骑乘者驱逐下
     */
    @Override
    public void onEject() {

        entity.eject();
    }

    /**
     * 获取此实体的名称
     *
     * @return 名称
     */
    @Override
    public String getName() {

        return entity.getName();
    }

    /**
     * 强制将此实体收到伤害
     *
     * @param damage 伤害
     */
    @Override
    public void damage(double damage) {

        asLiving().damage(damage);
    }

    /**
     * 强制将此实体受到伤害
     *
     * @param damage 伤害
     * @param damager 伤害源
     */
    public void damage(double damage, Entity damager) {

        asLiving().damage(damage, damager.getBukkitEntity());
    }

    /**
     * 强制将此实体受到伤害
     *
     * @param damage 伤害
     * @param damager 伤害源
     */
    public void damage(double damage, MMORPGPlayer damager) {

        asLiving().damage(damage, damager.getBukkitPlayer());
    }

    /**
     * 设置此实体无敌时间 (Tick)
     *
     * @param ticks 时间
     */
    @Override
    public void setNoDamageTicks(int ticks) {

        if(isLiving()) {

            asLiving().setNoDamageTicks(ticks);
        }
    }

    /**
     * 设置此实体是否显示自定义名称
     *
     * @param flag 是否显示
     */
    @Override
    public void setCustomNameVisible(boolean flag) {

        entity.setCustomNameVisible(flag);
    }

    /**
     * 设置此实体的矢量
     *
     * @param vector 矢量
     */
    @Override
    public void setVector(Vector vector) {

        entity.setVelocity(vector);
    }

    /**
     * 设置此实体的指定 键 的元数据
     *
     * @param key   键
     * @param value 元数据
     */
    @Override
    public void setMetadata(String key, MetadataValue value) {

        entity.setMetadata(key, value);
    }

    /**
     * 获取此实体是否拥有指定 键 的元数据
     *
     * @param key 键
     * @return true 则拥有 else 没有
     */
    @Override
    public boolean hasMetadata(String key) {

        return entity.hasMetadata(key);
    }

    /**
     * 获取此实体指定 键 的元数据集合
     *
     * @param key 键
     * @return 元数据集合 没有则返回空集合
     */
    @Override
    public List<MetadataValue> getMetadata(String key) {

        return entity.getMetadata(key);
    }

    /**
     * 设置此实体指定属性的值
     *
     * @param type  属性
     * @param value 值
     */
    public void setAttribute(EntityManager.AttributeType type, double value) {

        EntityManager.setAttribute(this, type, value);
    }

    /**
     * 设置指定实体的移动速度值
     *
     * @param value 值
     *
     * @see com.minecraft.moonlake.manager.EntityManager.AttributeType#MOVEMENT_SPEED
     */
    public void setAttributeMovementSpeed(double value) {

        setAttribute(EntityManager.AttributeType.MOVEMENT_SPEED, value);
    }

    /**
     * 设置指定实体的击退抗性值
     *
     * @param value 值
     *
     * @see com.minecraft.moonlake.manager.EntityManager.AttributeType#KNOCK_BACK_RESISTANCE
     */
    public void setAttributeKnockbackResistance(double value) {

    	setAttribute(EntityManager.AttributeType.KNOCK_BACK_RESISTANCE, value);
    }

    /**
     * 设置指定实体的攻击伤害值
     *
     * @param value 值
     *
     * @see com.minecraft.moonlake.manager.EntityManager.AttributeType#ATTACK_DAMAGE
     */
    public void setAttributeDamage(double value) {

        setAttribute(EntityManager.AttributeType.ATTACK_DAMAGE, value);
    }

    /**
     * 设置指定实体的血量上限值
     *
     * @param value 值
     *
     * @see com.minecraft.moonlake.manager.EntityManager.AttributeType#MAX_HEALTH
     */
    public void setAttributeMaxHealth(double value) {

        setAttributeMaxHealth(value, true);
    }

    /**
     * 设置指定实体的血量上限值
     *
     * @param value 值
     * @param regain 是否恢复
     *
     * @see com.minecraft.moonlake.manager.EntityManager.AttributeType#MAX_HEALTH
     */
    public void setAttributeMaxHealth(double value, boolean regain) {

        setAttribute(EntityManager.AttributeType.MAX_HEALTH, value);

        if(regain && entity instanceof LivingEntity) {

            setHealth(getMaxHealth());
        }
    }

    /**
     * 设置指定实体的追踪范围
     *
     * @param value 值
     *
     * @see com.minecraft.moonlake.manager.EntityManager.AttributeType#FOLLOW_RANGE
     */
    public void setAttributeFollowRange(double value) {

        setAttribute(EntityManager.AttributeType.FOLLOW_RANGE, value);
    }

    /**
     * 设置此实体当原理时是否清除
     *
     * @param flag 是否远离时清除
     */
    public void setRemoveWhenFarAway(boolean flag) {

        asLiving().setRemoveWhenFarAway(flag);
    }

    @Override
    public boolean equals(Object obj) {

        if(obj != null) {

            if(obj instanceof Entity) {

                return getUniqueId().equals(((Entity)obj).getUniqueId());
            }
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {

        return getUniqueId().hashCode();
    }
}
