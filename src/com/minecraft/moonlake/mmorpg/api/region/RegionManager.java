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
import com.minecraft.moonlake.mmorpg.api.region.copy.CopyRegion;
import com.minecraft.moonlake.mmorpg.api.region.flag.RegionFlag;
import com.minecraft.moonlake.mmorpg.api.region.flag.RegionFlagType;
import com.minecraft.moonlake.mmorpg.api.region.mob.MobRegion;
import com.minecraft.moonlake.mmorpg.api.region.town.TownRegion;
import com.minecraft.moonlake.mmorpg.manager.MMORPGManager;
import com.minecraft.moonlake.mmorpg.manager.VectorManager;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.util.*;

/**
 * Created by MoonLake on 2016/5/27.
 */
public final class RegionManager extends MMORPGManager {

    private final static List<Region> regionList;
    private final static Map<String, TownRegion> townMap;
    private final static Map<String, CopyRegion> copyMap;
    private final static Map<String, MobRegion> mobMap;
    private final static Map<String, String> cacheCurrentMap;

    static {

    	mobMap = new HashMap<>();
        townMap = new HashMap<>();
        copyMap = new HashMap<>();
        regionList = new ArrayList<>();
        cacheCurrentMap = new HashMap<>();
    }

    /**
     * 加载所有的区域数据
     */
    public static void loadRegion() {

    	for(RegionType regionType : RegionType.values()) {
    		
    		File folder = new File(getMain().getDataFolder(), "/regions/" + regionType.getName().toLowerCase());
    		
    		if(folder != null && folder.exists()) {
    			
    			File[] regionDataArray = folder.listFiles();
    			
    			for(File regionData : regionDataArray) {
    				
    				if(regionData != null && regionData.exists() && regionData.isFile() && regionData.getName().endsWith(".yml")) {
    					
    					RegionConfig regionConfig = new RegionConfig(regionType, StringUtil.getFileNameNoSuffix(regionData));
    					regionConfig.loadData();
    					
    					putRegion(regionConfig.getRegion());
    				}
    			}
    		}
    	}
    	getMain().log("成功从本地数据里加载到 " + regionList.size() + " 个区域.");
    }

    /**
     * 获取所加载的区域数量大小
     *
     * @return 区域数量大小
     */
    public static int getRegionSize() {

        return regionList.size();
    }

    /**
     * 获取区域对象从缓存区域集合
     *
     * @param name 区域名
     * @return 区域对象 没有则返回 null
     */
    public static Region getRegionFromCache(String name) {

        if(townMap.containsKey(name)) {

            return getTownFromCache(name);
        }
        else if(copyMap.containsKey(name)) {

            return getCopyFromCache(name);
        }
        else if(mobMap.containsKey(name)) {
        	
        	return getMobFromCache(name);
        }
        return null;
    }

    /**
     * 获取城镇区域对象从缓存区域集合
     *
     * @param townName 城镇名
     * @return 城镇对象 没有则返回 null
     */
    public static TownRegion getTownFromCache(String townName) {

        return townMap.get(townName);
    }

    /**
     * 获取副本区域对象从缓存区域集合
     *
     * @param copyName 副本名
     * @return 副本对象 没有则返回 null
     */
    public static CopyRegion getCopyFromCache(String copyName) {

        return copyMap.get(copyName);
    }
    
    /**
     * 获取怪物区域对象从缓存区集合
     * 
     * @param mobName 怪物名
     * @return 怪物对象 没有则返回 null
     */
    public static MobRegion getMobFromCache(String mobName) {
    	
    	return mobMap.get(mobName);
    }

    /**
     * 获取指定位置是否拥有区域
     *
     * @param location 位置
     * @return 区域对象 没有则返回 null
     */
    public static Region hasRegion(Location location) {

        for(Region region : regionList) {

            if(region.contains(location)) {

                return region;
            }
        }
        return null;
    }

    /**
     * 获取数据是否存在指定类型的名称区域
     *
     * @param regionType 区域类型
     * @param name 区域名
     * @return true 区域存在 else 不存在
     */
    public static boolean hasRegionData(RegionType regionType, String name) {

        return new RegionConfig(regionType, name, false).exists();
    }

    /**
     * 将区域对象加入到数据 Map
     *
     * @param region 区域对象
     */
    public static void putRegion(Region region) {

        if(region != null) {

            regionList.add(region);
            
            getRegionTypeHashMap(region.getType()).put(region.getName(), region);
        }
    }

    /**
     * 获取玩家区域当前缓存 Map 对象
     *
     * @return 缓存对象
     */
    public static Map<String, String> getCacheCurrentMap() {

        return cacheCurrentMap;
    }

    /**
     * 获取区域对象的标示转换到字符串
     *
     * @param region 区域对象
     * @return 标示序列化字符串 没有则返回 "none"
     */
    public static String toSpecial(Region region) {

        if(region != null && region.hasFlag()) {

            Set<RegionFlag> flags = region.getFlags();

            return toSpecial(flags.toArray(new RegionFlag[flags.size()]));
        }
        return "none";
    }

    /**
     * 获取区域对象的标示转换到字符串
     *
     * @param flags 区域标示数组
     * @return 标示序列化字符串 没有则返回 "none"
     */
    public static String toSpecial(RegionFlag[] flags) {

        if(flags != null && flags.length > 0) {

            String data = "";

            for(RegionFlag flag : flags) {

                data += flag.getType().getName() + ":" + flag.getValue().asString() + ",";
            }
            return data.substring(0, data.lastIndexOf(","));
        }
        return "none";
    }

    /**
     * 将标示字符串反序列化为区域标示对象
     *
     * @param special 标示序列化字符串
     * @return 区域标示对象数组 没有则返回 null
     */
    public static RegionFlag[] fromSpecial(String special) {

        if(special != null && (special.contains(":") || special.contains(","))) {

            String[] datas = special.replaceAll(" ", "").split(",");
            RegionFlag[] flags = new RegionFlag[datas.length];

            for(int i = 0; i < flags.length; i++) {

                String[] subDatas = datas[i].split(":");
                RegionFlagType type = RegionFlagType.fromType(subDatas[0]);

                if(type != null) {

                    flags[i] = type.newInstance();

                    if(flags[i] != null) {

                        if(type == RegionFlagType.LEVEL) {

                            flags[i].setValue(Integer.parseInt(subDatas[1]));
                        }
                        else if(type == RegionFlagType.PERMISSION) {

                            flags[i].setValue(subDatas[1]);
                        }
                    }
                }
            }
            return flags;
        }
        return null;
    }

    /**
     * 更新区域对象到数据 Map
     *
     * @param newRegion 新区域对象
     */
    public static void updateRegion(Region newRegion) {

        if(newRegion != null) {

        	getRegionTypeHashMap(newRegion.getType()).put(newRegion.getName(), newRegion);
        }
    }
    
    /**
     * 检测指定玩家进入的区域的标示
     * 
     * @param mmorpgPlayer 玩家
     * @param enterRegion 进入的区域
     * @return true 则成功进入 else 无法进入
     */
    public static boolean checkPlayerRegionFlag(MMORPGPlayer mmorpgPlayer, Region enterRegion) {
    	
    	if(mmorpgPlayer != null && enterRegion != null && enterRegion.hasFlag()) {
    		
    		boolean result = true;
    		
    		for(RegionFlag regionFlag : enterRegion.getFlags()) {
    			
    			result = regionFlag.checkPlayer(mmorpgPlayer);
    			
    			if(!result) {
    				
    				break;
    			}
    		}
    		return result;
    	}
    	return true;
    }
    
    /**
     * 创建创世神 WorldEdit 的矩形区域对象
     * 
     * @param worldName 世界名
     * @param pos1 位置点1
     * @param pos2 位置点2
     * @return 矩形区域对象
     */
    public static com.sk89q.worldedit.regions.Region newCuboidRegion(String worldName, String pos1, String pos2) {
    	
    	com.sk89q.worldedit.world.World world = !worldName.equals("none") ? new BukkitWorld(Bukkit.getServer().getWorld(worldName)) : null;
    	com.sk89q.worldedit.Vector vector1 = VectorManager.getWVfromXYZ(pos1);
        com.sk89q.worldedit.Vector vector2 = VectorManager.getWVfromXYZ(pos2);
    	
    	return world != null ? new CuboidRegion(world, vector1, vector2) : new CuboidRegion(vector1, vector2);
    }
    
    /**
     * 删除指定类型的名称的区域数据
     * 
     * @param regionType 区域类型
     * @param name 区域名
     */
    public static void remove(RegionType regionType, String name) {
    	
    	if(regionType != null && name != null && !name.isEmpty()) {
    		
    		regionList.remove(getRegionTypeHashMap(regionType).remove(name));
			
			new RegionConfig(regionType, name, false).delete();
    	}
    }
    
    /**
     * 从指定区域类型获取 HashMap 对象
     * 
     * @param regionType 区域类型
     * @return 区域的 HashMap 对象 没有此类型则返回 null
     */
    private static <T extends Region> Map<String, T> getRegionTypeHashMap(RegionType regionType) {
    	
    	if(regionType == RegionType.TOWN) {
    		
    		return (Map<String, T>) townMap;
    	}
    	if(regionType == RegionType.COPY) {
    		
    		return (Map<String, T>) copyMap;
    	}
    	if(regionType == RegionType.MOB) {
    		
    		return (Map<String, T>) mobMap;
    	}
    	return null;
    }

    /**
     * 关闭对象类并释放内存占用
     */
    public static void close() {

        regionList.clear();
        townMap.clear();
        copyMap.clear();
        mobMap.clear();
        cacheCurrentMap.clear();
    }
}
