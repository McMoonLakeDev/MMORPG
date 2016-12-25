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
 
  
package com.minecraft.moonlake.mmorpg.api.mount;

import com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;

/**
 * Created by MoonLake on 2016/6/19.
 */
public interface Mount {

    /**
     * 获取坐骑的类型
     *
     * @return 类型
     */
    MountType getType();

    /**
     * 获取此坐骑的拥有者
     *
     * @return 拥有者
     */
    MMORPGPlayer getOwner();

    /**
     * 设置此坐骑的拥有者
     *
     * @param mmorpgPlayer 玩家
     */
    void setOwner(MMORPGPlayer mmorpgPlayer);

    /**
     * 获取此坐骑的显示名
     *
     * @return 显示名
     */
    String getDisplayName();

    /**
     * 设置此坐骑的显示名
     *
     * @param displayName 显示名
     */
    void setDisplayName(String displayName);

    /**
     * 获取此坐骑的实体对象
     *
     * @return 实体对象
     */
    Entity getMount();

    /**
     * 将坐骑召唤到拥有者位置
     */
    void onSummon();

    /**
     * 将此坐骑清除
     */
    void remove();

    /**
     * 将此坐骑传送到拥有者指定位置
     */
    void teleport();

    /**
     * 获取此坐骑是否活着
     *
     * @return 是否活着
     */
    boolean isLiving();

    /**
     * 将此拥有者骑乘此坐骑
     */
    void onRide();

    /**
     * 将此坐骑的骑乘者离开
     */
    void onEject();
}
