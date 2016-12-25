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
 
  
package com.minecraft.moonlake.mmorpg.api.gui;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.MMORPGCore;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;

/**
 * Created by MoonLake on 2016/7/26.
 */
public abstract class MMORPGGUIS implements MMORPGCore {

    private final MMORPGGUI gui;
    private final static MMORPG MAIN;

    static {

        MAIN = MMORPGPlugin.getInstances();
    }

    public MMORPGGUIS(MMORPGGUI gui) {

        this.gui = gui;
    }

    /**
     * 获取月色之湖 MMORPG 主类实例对象
     *
     * @return MMORPG
     */
    public final MMORPG getInstance() {

        return MAIN;
    }

    /**
     * 获取月色之湖 MMORPG 主类实例对象
     *
     * @return MMORPG
     */
    protected final MMORPG getMain() {

        return MAIN;
    }

    /**
     * 获取此 MMORPG GUI 对象
     *
     * @return GUI 对象
     */
    public final MMORPGGUI getGUI() {

        return gui;
    }

    /**
     * 将此 MMORPG GUI 打开给指定玩家
     *
     * @param mmorpgPlayer 玩家
     */
    public void openGUI(MMORPGPlayer mmorpgPlayer) {

        getGUI().open(mmorpgPlayer);
    }

    /**
     * 将此 MMORPG GUI 进行注册
     */
    public final void register() {

        getMain().getMoonLakeGUIManager().registerGUI(gui);
    }

    /**
     * 将此 MMORPG GUI 进行卸载
     */
    public final void unregister() {

        getMain().getMoonLakeGUIManager().unregisterGUI(gui.getName());
    }
}
