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
 
  
package com.minecraft.moonlake.mmorpg.hotbar;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/6/20.
 */
public class HotBarItemData {

    private final HotBarItem.HotBarType type;
    private final Map<HotBarItem.HotBarInteractType, HotBarRunnable> mapUtil;

    public HotBarItemData(HotBarItem.HotBarType type) {

        this.type = type;
        this.mapUtil = new HashMap<>();
    }

    /**
     * 给此快捷栏物品类型添加交互执行
     *
     * @param type 交互类型
     * @param runnable 执行对象
     */
    public void addInteract(HotBarItem.HotBarInteractType type, HotBarRunnable runnable) {

        if(mapUtil.containsKey(type)) {

            mapUtil.remove(type);
        }
        mapUtil.put(type, runnable);
    }

    /**
     * 执行此快捷栏物品指定交互类型的
     *
     * @param type 交互类型
     * @param mmorpgPlayer 玩家
     */
    public void runInteract(HotBarItem.HotBarInteractType type, MMORPGPlayer mmorpgPlayer) {

        if(mapUtil.containsKey(type)) {

            HotBarRunnable runnable = mapUtil.get(type);

            if(runnable != null) {

                runnable.run(mmorpgPlayer);
            }
        }
    }
}
