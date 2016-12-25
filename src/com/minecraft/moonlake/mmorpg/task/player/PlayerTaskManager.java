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
import com.minecraft.moonlake.mmorpg.manager.MMORPGManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/6/16.
 */
public final class PlayerTaskManager extends MMORPGManager {

    private final static Map<String, PlayerTask> playerTaskMap;

    static {

        playerTaskMap = new HashMap<>();
    }

    /**
     * 获取指定玩家是否拥有玩家任务
     *
     * @param mmorpgPlayer 玩家
     * @return true 则有效果任务 else 没有
     */
    public static boolean hasTask(MMORPGPlayer mmorpgPlayer) {

        return mmorpgPlayer != null && playerTaskMap.containsKey(mmorpgPlayer.getName());
    }

    /**
     * 将玩家和玩家任务加入到集合
     *
     * @param mmorpgPlayer 玩家
     * @param playerTask 玩家任务
     */
    public static void putTaskList(MMORPGPlayer mmorpgPlayer, PlayerTask playerTask) {

        if(playerTaskMap.containsKey(mmorpgPlayer.getName())) {

            closeTask(mmorpgPlayer.getName());
        }
        playerTaskMap.put(mmorpgPlayer.getName(), playerTask);
    }

    /**
     * 关闭并释放玩家任务
     *
     * @param name 玩家名
     */
    public static void closeTask(String name) {

        if(playerTaskMap.containsKey(name)) {

            playerTaskMap.remove(name);
        }
    }

    /**
     * 关闭对象类并释放内存占用
     */
    public static void close() {

        playerTaskMap.clear();
    }
}
