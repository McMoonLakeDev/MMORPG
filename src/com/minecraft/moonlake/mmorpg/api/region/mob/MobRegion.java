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
 
  
package com.minecraft.moonlake.mmorpg.api.region.mob;

import com.minecraft.moonlake.mmorpg.api.mob.MobType;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.region.Region;
import com.minecraft.moonlake.mmorpg.api.region.RegionType;
import com.minecraft.moonlake.mmorpg.api.region.flag.RegionFlag;
import com.minecraft.moonlake.mmorpg.api.region.flag.RegionFlagType;
import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.Set;

/**
 * Created by MoonLake on 2016/7/1.
 */
public interface MobRegion extends Region {

    /**
     * 获取区域名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 获取区域类型
     *
     * @return 类型
     */
    RegionType getType();

    /**
     * 获取区域的怪物类型
     *
     * @return 怪物类型
     */
    MobType getMobType();

    /**
     * 设置区域的怪物
     *
     * @param mob 怪物
     */
    void setMob(String mob);
    
    /**
     * 获取区域的怪物
     * 
     * @return 怪物
     */
    String getMob();

    /**
     * 获取区域的世界
     *
     * @return 世界
     */
    World getWorld();

    /**
     * 设置区域
     *
     * @param selection 选择区域
     */
    void setRegion(Selection selection);

    /**
     * 设置区域
     *
     * @param region 区域
     */
    void setRegion(com.sk89q.worldedit.regions.Region region);

    /**
     * 获取区域
     *
     * @return 区域
     */
    com.sk89q.worldedit.regions.Region getRegion();

    /**
     * 保存区域数据
     */
    void saveData();

    /**
     * 复制区域对象
     *
     * @return 区域对象
     */
    Region clone();

    /**
     * 获取该区域是否包含位置
     *
     * @param location 位置
     * @return 是否包含
     */
    boolean contains(Location location);

    /**
     * 获取该区域是否包含实体
     *
     * @param entity 实体
     * @return 是否包含
     */
    boolean contains(Entity entity);

    /**
     * 获取该区域是否包含玩家
     *
     * @param mmorpgPlayer 玩家
     * @return 是否包含
     */
    boolean contains(MMORPGPlayer mmorpgPlayer);

    /**
     * 获取区域是否拥有标示
     *
     * @return true 则拥有 else 没有
     */
    boolean hasFlag();

    /**
     * 获取区域是否拥有指定类型的标示
     *
     * @param type 标示类型
     * @return true 则拥有 else 没有
     */
    boolean hasFlag(RegionFlagType type);

    /**
     * 获取区域的标示集合
     *
     * @return 标示集合
     */
    Set<RegionFlag> getFlags();

    /**
     * 给区域添加特殊标示
     *
     * @param flag 标示
     */
    void addFlag(RegionFlag flag);

    /**
     * 给区域添加特殊标示
     *
     * @param flags 标示
     */
    void addFlags(RegionFlag... flags);

    /**
     * 将区域指定类型标示删除
     *
     * @param type 标示类型
     */
    void removeFlag(RegionFlagType type);

    /**
     * 获取区域指定标示类型的标示对象
     *
     * @param type 标示类型
     * @return 标示对象  没有则返回 null
     */
    RegionFlag getFlag(RegionFlagType type);

    /**
     * 获取区域进入消息
     *
     * @return 进入消息
     */
    String getEnter();

    /**
     * 设置区域进入消息
     *
     * @param enter 消息
     */
    void setEnter(String enter);

    /**
     * 获取区域离开消息
     *
     * @return 离开消息
     */
    String getLeave();

    /**
     * 设置区域离开消息
     *
     * @param leave 消息
     */
    void setLeave(String leave);

    /**
     * 获取区域设置的主位置
     *
     * @return 主位置
     */
    Location getMainLocation();

    /**
     * 设置区域的主位置
     *
     * @param location 位置
     */
    void setMainLocation(Location location);

    /**
     * 获取区域设置的返回位置
     *
     * @return 返回位置
     */
    Location getBackLocation();

    /**
     * 设置区域的返回位置
     *
     * @param location
     */
    void setBackLocation(Location location);
}
