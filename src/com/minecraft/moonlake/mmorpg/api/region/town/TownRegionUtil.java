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
 
  
package com.minecraft.moonlake.mmorpg.api.region.town;

import com.minecraft.moonlake.mmorpg.api.region.AbstractRegion;
import com.minecraft.moonlake.mmorpg.api.region.RegionType;

/**
 * Created by MoonLake on 2016/5/26.
 */
public class TownRegionUtil extends AbstractRegion implements TownRegion {

    public TownRegionUtil(String name) {

        this(name, null);
    }

    public TownRegionUtil(String name, com.sk89q.worldedit.regions.Region region) {

        super(name, RegionType.TOWN, region);
    }

    /**
     * 复制区域对象
     *
     * @return 区域对象
     */
    @Override
    public TownRegion clone() {

        return (TownRegion) super.clone();
    }
}
