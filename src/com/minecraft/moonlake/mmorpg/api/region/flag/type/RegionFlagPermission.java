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

import com.minecraft.moonlake.mmorpg.api.config.MMORPGConfig;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.region.flag.AbstractRegionFlag;
import com.minecraft.moonlake.mmorpg.api.region.flag.RegionFlagType;
import com.minecraft.moonlake.mmorpg.config.MMORPGConfigUtil;

/**
 * Created by MoonLake on 2016/5/28.
 */
public class RegionFlagPermission extends AbstractRegionFlag {

    private String permission;

    public RegionFlagPermission() {

        this("");
    }

    public RegionFlagPermission(String permission) {

        super(RegionFlagType.PERMISSION);

        this.permission = permission;
    }

    public void setPremission(String permission) {

        this.permission = permission;
    }

    public String getPremission() {

        return permission;
    }

    /**
     * 获取区域标示的值
     *
     * @return 基础数据
     */
    @Override
    public MMORPGConfig.BaseConfig getValue() {

        return new MMORPGConfigUtil.BaseConfigUtil(permission);
    }

    /**
     * 设置区域标示的值
     *
     * @param object 数据
     */
    @Override
    public void setValue(Object object) {

        if(object != null && object instanceof String) {

            this.permission = object.toString();
        }
    }
    
    /**
     * 检测指定玩家是否拥有此标示权
     * 
     * @param mmorpgPlayer 玩家
     * @return true 则拥有 else 没有
     */
    public boolean checkPlayer(MMORPGPlayer mmorpgPlayer) {
    	
    	boolean result = mmorpgPlayer.hasPermission(permission);
    	
    	if(!result) {
    		
    		mmorpgPlayer.l18n("player.region.flag.check.notHave.permission");
    	}
    	return result;
    }
}
