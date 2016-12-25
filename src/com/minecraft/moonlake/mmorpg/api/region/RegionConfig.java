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

import com.minecraft.moonlake.manager.LocationManager;
import com.minecraft.moonlake.mmorpg.api.data.DataConfig;
import com.minecraft.moonlake.mmorpg.api.region.mob.MobRegion;
import com.minecraft.moonlake.mmorpg.manager.VectorManager;

/**
 * Created by MoonLake on 2016/07/04.
 */
public class RegionConfig extends DataConfig {

	private final RegionType regionType;
	private Region region;
	
	public RegionConfig(RegionType regionType, String name) {
		
		this(regionType, name, true);
	}
	
	public RegionConfig(RegionType regionType, String name, boolean read) {
		
		super("/regions/" + regionType.getName().toLowerCase() + "/" + name + ".yml", read);
		
		this.regionType = regionType;
	}
	
	/**
	 * 获取区域数据 Yaml 配置文件的区域类型
	 * 
	 * @return 区域类型
	 */
	public RegionType getRegionType() {
		
		return regionType;
	}
	
	/**
	 * 设置此区域数据 Yaml 配置文件的区域对象
	 * 
	 * @param region 区域对象
	 */
	public void setRegion(Region region) {
		
		this.region = region;
	}
	
	/**
	 * 获取此区域数据 Yaml 配置文件的区域对象
	 * 
	 * @return 区域对象
	 */
	public Region getRegion() {
		
		return region;
	}
	
	/**
	 * 获取此区域数据 Yaml 配置文件的区域是否为城镇区域
	 * 
	 * @return true 则是城镇区域 else 不是
	 */
	public boolean isTown() {
		
		return regionType == RegionType.TOWN;
	}
	
	/**
	 * 获取此区域数据 Yaml 配置文件的区域是否为副本区域
	 * 
	 * @return true 则是副本区域 else 不是
	 */
	public boolean isCopy() {
		
		return regionType == RegionType.COPY;
	}
	
	/**
	 * 获取此区域数据 Yaml 配置文件的区域是否为怪物区域
	 * 
	 * @return true 则是怪物区域 else 不是
	 */
	public boolean isMob() {
		
		return regionType == RegionType.MOB;
	}
	
	/**
	 * 将区域数据 Yaml 配置文件进行读取
	 */
	public void loadData() {
		
		region = regionType.newInstance(getString("Region.Name"));
		region.setRegion(RegionManager.newCuboidRegion(
				getString("Region.World.Name"), 
				getString("Region.World.Pos1"), 
				getString("Region.World.Pos2")));
		region.setEnter(getString("Region.Options.EnterMessage"));
		region.setLeave(getString("Region.Options.LeaveMessage"));
		region.addFlags(RegionManager.fromSpecial(getString("Region.Options.Flags")));
		region.setMainLocation(LocationManager.fromData(getString("Region.Options.MainLocation")));
		region.setBackLocation(LocationManager.fromData(getString("Region.Options.BackLocation")));
		
		if(isMob()) {
			
			// mob region code
		}
		super.loadData();
	}
	
	/**
	 * 将区域数据 Yaml 配置文件的对象进行保存
	 */
	public void saveData() {
		
		if(region != null) {
			
			this.setString("Region.Name", region.getName());
			this.setString("Region.Type", region.getType().getName());
			this.setString("Region.World.Name", region.getWorld() != null ? region.getWorld().getName() : "none");
			this.setString("Region.World.Pos1", VectorManager.toXYZ(region.getRegion().getMinimumPoint(), true));
			this.setString("Region.World.Pos2", VectorManager.toXYZ(region.getRegion().getMaximumPoint(), true));
			this.setString("Region.Options.Flags", RegionManager.toSpecial(region));
			this.setString("Region.Options.MainLocation", LocationManager.toData(region.getMainLocation()));
			this.setString("Region.Options.BackLocation", LocationManager.toData(region.getBackLocation()));
			this.setString("Region.Options.EnterMessage", region.getEnter());
			this.setString("Region.Options.LeaveMessage", region.getLeave());
			
			if(isMob()) {
				
				this.setString("Region.Options.Mob", ((MobRegion)region).getMob());
			}
			super.saveData();
		}
	}
}
