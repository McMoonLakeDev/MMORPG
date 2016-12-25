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
 
  
package com.minecraft.moonlake.mmorpg.api.region.flag.type;

import com.minecraft.moonlake.data.Conversions;
import com.minecraft.moonlake.mmorpg.api.config.MMORPGConfig;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.region.flag.AbstractRegionFlag;
import com.minecraft.moonlake.mmorpg.api.region.flag.RegionFlagType;
import com.minecraft.moonlake.mmorpg.config.MMORPGConfigUtil;

/**
 * Created by MoonLake on 2016/5/28.
 */
public class RegionFlagLevel extends AbstractRegionFlag {

    private int level;

    public RegionFlagLevel() {

        this(0);
    }

    public RegionFlagLevel(int level) {

        super(RegionFlagType.LEVEL);

        this.level = level;
    }

    public void setLevel(int level) {

        this.level = level;
    }

    public int getLevel() {

        return level;
    }

    /**
     * 获取区域标示的值
     *
     * @return 基础数据
     */
    @Override
    public MMORPGConfig.BaseConfig getValue() {

        return new MMORPGConfigUtil.BaseConfigUtil(level);
    }

    /**
     * 设置区域标示的值
     *
     * @param object 数据
     */
    @Override
    public void setValue(Object object) {

        if(object != null && object instanceof Number) {

            this.level = Conversions.toInt(object);
        }
    }
    
    /**
     * 检测指定玩家是否拥有此标示权
     * 
     * @param mmorpgPlayer 玩家
     * @return true 则拥有 else 没有
     */
    public boolean checkPlayer(MMORPGPlayer mmorpgPlayer) {
    	
    	boolean result = mmorpgPlayer.getLevel() >= level;
    	
    	if(!result) {
    		
    		mmorpgPlayer.l18n("player.region.flag.check.notHave.level", level);
    	}
    	return result;
    }
}
