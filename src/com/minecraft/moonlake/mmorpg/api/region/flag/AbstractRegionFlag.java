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
 
  
package com.minecraft.moonlake.mmorpg.api.region.flag;

import com.minecraft.moonlake.mmorpg.api.config.MMORPGConfig;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;

/**
 * Created by MoonLake on 2016/5/28.
 */
public abstract class AbstractRegionFlag implements RegionFlag {

    private final RegionFlagType type;

    public AbstractRegionFlag(RegionFlagType type) {

        this.type = type;
    }

    /**
     * 获取区域标示的类型
     *
     * @return 类型
     */
    public RegionFlagType getType() {

        return type;
    }

    /**
     * 获取区域标示的值
     *
     * @return 基础数据
     */
    public abstract MMORPGConfig.BaseConfig getValue();

    /**
     * 设置区域标示的值
     *
     * @param object 数据
     */
    public abstract void setValue(Object object);
    
    /**
     * 检测指定玩家是否拥有此标示权
     * 
     * @param mmorpgPlayer 玩家
     * @return true 则拥有 else 没有
     */
    public abstract boolean checkPlayer(MMORPGPlayer mmorpgPlayer);
}
