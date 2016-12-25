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
 
  
package com.minecraft.moonlake.mmorpg.task.player;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;

/**
 * Created by MoonLake on 2016/6/19.
 */
public interface PlayerTask {

    /**
     * 获取作为的主玩家
     *
     * @return 玩家
     */
    MMORPGPlayer getPlayer();

    /**
     * 获取效果持续的延迟
     *
     * @return 延迟
     */
    int getDelay();

    /**
     * 开始并执行任务
     */
    void start();

    /**
     * 结束并调用执行后关闭 Task 占用
     */
    void close();
}
