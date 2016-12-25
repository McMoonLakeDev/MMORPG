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
 
  
package com.minecraft.moonlake.mmorpg.api.soul;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;

/**
 * Created by MoonLake on 2016/5/22.
 */
public interface Soul {

    /**
     * 获取玩家的当前灵魂点
     *
     * @param player 玩家
     * @return 灵魂数量
     */
    int getPlayerSoul(MMORPGPlayer player);

    /**
     * 获取玩家的最大灵魂点
     *
     * @param player 玩家
     * @return 最大灵魂数量
     */
    int getPlayerMaxSoul(MMORPGPlayer player);

    /**
     * 更新玩家的灵魂数量到快捷栏
     *
     * @param player 玩家
     */
    void updatePlayerHotBarSoul(MMORPGPlayer player);

    /**
     * 更新玩家的灵魂数量到快捷栏
     *
     * @param name 玩家名
     * @param amount 数量
     */
    void updatePlayerHotBarSoul(String name, int amount);
}
