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
import com.minecraft.moonlake.mmorpg.api.region.AbstractRegion;
import com.minecraft.moonlake.mmorpg.api.region.RegionType;

/**
 * Created by MoonLake on 2016/7/1.
 */
public class MobRegionUtil extends AbstractRegion implements MobRegion {

    private String mob;
    private MobType mobType;

    public MobRegionUtil(String name) {

        this(name, null, null);
    }

    public MobRegionUtil(String name, MobType mobType) {

        this(name, null, mobType);
    }

    public MobRegionUtil(String name, com.sk89q.worldedit.regions.Region region, MobType mobType) {

        super(name, RegionType.MOB, region);

        this.mob = null;
        this.mobType = mobType;
    }

    /**
     * 复制区域对象
     *
     * @return 区域对象
     */
    @Override
    public MobRegion clone() {

        return (MobRegion) super.clone();
    }

    /**
     * 获取区域的怪物类型
     *
     * @return 怪物类型
     */
    @Override
    public MobType getMobType() {

        return mobType;
    }

    /**
     * 设置区域的怪物
     *
     * @param mobType 怪物
     */
    @Override
    public void setMob(String mob) {

        this.mob = mob;
    }
    
    /**
     * 获取区域的怪物
     * 
     * @return 怪物
     */
    public String getMob() {
    	
    	return mob;
    }
}
