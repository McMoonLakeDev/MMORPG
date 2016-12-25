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
 
  
package com.minecraft.moonlake.mmorpg.manager;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.MMORPGCore;
import com.minecraft.moonlake.mmorpg.api.gui.GUIManager;
import com.minecraft.moonlake.mmorpg.task.player.PlayerTaskManager;

/**
 * Created by MoonLake on 2016/6/9.
 */
public abstract class MMORPGManager implements MMORPGCore {

    private final static MMORPG main;

    static {

        main = MMORPGPlugin.getInstances();
    }

    /**
     * 获取月色之湖 MMORPG 主类实例对象
     *
     * @return MMORPG
     */
    @Override
    public MMORPG getInstance() {

        return main;
    }

    protected static MMORPG getMain() {

        return main;
    }

    /**
     * 关闭玩家在其他管理内的数据对象
     *
     * @param name 玩家名
     */
    public static void closeManager(String name) {

        AccountManager.closePlayer(name);
        GUIManager.closeGUI(name);
        PlayerTaskManager.closeTask(name);
    }
}
