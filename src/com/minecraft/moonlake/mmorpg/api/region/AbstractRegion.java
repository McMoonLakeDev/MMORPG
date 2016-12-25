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
 
  
package com.minecraft.moonlake.mmorpg.api.region;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.region.flag.RegionFlag;
import com.minecraft.moonlake.mmorpg.api.region.flag.RegionFlagType;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import com.minecraft.moonlake.mmorpg.manager.VectorManager;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by MoonLake on 2016/5/26.
 */
public abstract class AbstractRegion implements Region {

    private final String name;
    private final RegionType type;
    private String world;
    private String enter;
    private String leave;
    private List<RegionFlag> flags;
    private Location mainLocation;
    private Location backLocation;
    private com.sk89q.worldedit.regions.Region region;

    public AbstractRegion(String name, RegionType type) {

        this(name, type, null);
    }

    public AbstractRegion(String name, RegionType type, com.sk89q.worldedit.regions.Region region) {

        this.name = name;
        this.type = type;
        this.region = region;
        this.flags = new ArrayList<>();
        this.enter = ConfigManager.get("Region.EnterDefault").asString();
        this.leave = ConfigManager.get("Region.LeaveDefault").asString();
        this.world = region != null ? region.getWorld().getName() : null;
    }

    /**
     * 获取区域名称
     *
     * @return 名称
     */
    @Override
    public String getName() {

        return name;
    }

    /**
     * 获取区域类型
     *
     * @return 类型
     */
    public RegionType getType() {

        return type;
    }

    /**
     * 获取区域的世界
     *
     * @return 世界
     */
    public World getWorld() {

        return Bukkit.getServer().getWorld(world);
    }

    /**
     * 设置区域
     *
     * @param selection 选择区域
     */
    public void setRegion(Selection selection) {

        try {

            this.region = selection.getRegionSelector().getRegion();
            this.world = region != null ? region.getWorld().getName() : null;
        }
        catch (IncompleteRegionException e) {

            e.printStackTrace();
        }
    }

    /**
     * 设置区域
     *
     * @param region 选择区域
     */
    public void setRegion(com.sk89q.worldedit.regions.Region region) {

        this.region = region;
        this.world = region.getWorld().getName();
    }

    /**
     * 获取区域
     *
     * @return 区域
     */
    public com.sk89q.worldedit.regions.Region getRegion() {

        return region;
    }

    /**
     * 保存区域数据
     */
    @Override
    public void saveData() {

        //DataManager.saveRegionData(this);
    	RegionConfig regionConfig = new RegionConfig(type, name);
    	regionConfig.setRegion(this);
    	regionConfig.saveData();
    }

    /**
     * 复制区域对象
     *
     * @return 区域对象
     */
    public Region clone() {

        Region clone = type.newInstance(getName());
        clone.setRegion(region);
        clone.setEnter(enter);
        clone.setLeave(leave);
        clone.setMainLocation(mainLocation);
        return clone;
    }

    /**
     * 获取该区域是否包含位置
     *
     * @param location 位置
     * @return 是否包含
     */
    public boolean contains(Location location) {

        if(region != null && location != null) {

            return region.contains(VectorManager.fromXYZ(location.getX(), location.getY(), location.getZ()));
        }
        return false;
    }

    /**
     * 获取该区域是否包含实体
     *
     * @param entity 实体
     * @return 是否包含
     */
    public boolean contains(Entity entity) {

        return entity != null ? contains(entity.getLocation()) : false;
    }

    /**
     * 获取该区域是否包含玩家
     *
     * @param mmorpgPlayer 玩家
     * @return 是否包含
     */
    public boolean contains(MMORPGPlayer mmorpgPlayer) {

        return mmorpgPlayer != null ? contains(mmorpgPlayer.getLocation()) : false;
    }

    /**
     * 获取区域的标示集合
     *
     * @return 标示集合
     */
    public Set<RegionFlag> getFlags() {

        return new HashSet<>(flags);
    }

    /**
     * 获取区域指定标示类型的标示对象
     *
     * @param type 标示类型
     * @return 标示对象 没有则返回 null
     */
    public RegionFlag getFlag(RegionFlagType type) {

        if(hasFlag(type)) {

            for(int i = 0; i < flags.size(); i++) {

                if(flags.get(i).getType() == type) {

                    return flags.get(i);
                }
            }
        }
        return null;
    }

    /**
     * 给区域添加特殊标示
     *
     * @param flag 标示
     */
    public void addFlag(RegionFlag flag) {

        if(flag != null) {

            flags.add(flag);
        }
    }

    /**
     * 给区域添加特殊标示
     *
     * @param flag 标示
     */
    public void addFlags(RegionFlag... flags) {

        for(int i = 0; i < 0; i++) {

            addFlag(flags[i]);
        }
    }

    /**
     * 将区域指定类型标示删除
     *
     * @param type 标示类型
     */
    public void removeFlag(RegionFlagType type) {

        for(RegionFlag flag : flags) {

            if(flag.getType() == type) {

                flags.remove(flag);
                break;
            }
        }
    }

    /**
     * 获取区域是否拥有标示
     *
     * @return true 则拥有 else 没有
     */
    public boolean hasFlag() {

        return !flags.isEmpty();
    }

    /**
     * 获取区域是否拥有指定类型的标示
     *
     * @param type 标示类型
     * @return true 则拥有 else 没有
     */
    public boolean hasFlag(RegionFlagType type) {

        boolean result = false;

        for(RegionFlag flag : flags) {

            if(flag.getType() == type) {

                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 获取区域进入消息
     *
     * @return 进入消息
     */
    public String getEnter() {

        return enter;
    }

    /**
     * 设置区域进入消息
     *
     * @param enter 消息
     */
    public void setEnter(String enter) {

        this.enter = enter;
    }

    /**
     * 获取区域离开消息
     *
     * @return 离开消息
     */
    public String getLeave() {

        return leave;
    }

    /**
     * 设置区域离开消息
     *
     * @param leave 消息
     */
    public void setLeave(String leave) {

        this.leave = leave;
    }

    /**
     * 获取区域设置的主位置
     *
     * @return 主位置
     */
    public Location getMainLocation() {

        return mainLocation;
    }

    /**
     * 设置区域的主位置
     *
     * @param location 位置
     */
    public void setMainLocation(Location location) {

        this.mainLocation = location;
    }

    /**
     * 获取区域设置的返回位置
     *
     * @return 返回位置
     */
    public Location getBackLocation() {

        return backLocation;
    }

    /**
     * 设置区域的返回位置
     *
     * @param location
     */
    public void setBackLocation(Location location) {

        this.backLocation = location;
    }
}
