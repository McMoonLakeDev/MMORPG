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
 
  
package com.minecraft.moonlake.mmorpg.util.system;

import com.minecraft.moonlake.mmorpg.api.gui.GUIManager;
import com.minecraft.moonlake.mmorpg.api.gui.player.MountGUI;
import com.minecraft.moonlake.mmorpg.api.mount.Mount;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.system.MountSystem;
import com.minecraft.moonlake.mmorpg.manager.DataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MoonLake on 2016/5/23.
 */
public class MountSystemUtil implements MountSystem {

    private final MMORPGPlayer owner;
    private final Map<Integer, Mount> mountMap;
    private Mount main;
    private int mainSlot;

    public MountSystemUtil(MMORPGPlayer mmorpgPlayer) {

        this.owner = mmorpgPlayer;
        this.mountMap = new HashMap<>();
    }

    /**
     * 获取拥有者玩家对象
     *
     * @return 玩家
     */
    @Override
    public MMORPGPlayer getOwner() {

        return owner;
    }

    /**
     * 获取此玩家的坐骑 GUI 对象
     *
     * @return 坐骑 GUI
     */
    @Override
    public MountGUI getMountGUI() {

        return GUIManager.getMountGUI(getOwner());
    }

    /**
     * 将此玩家打开坐骑系统 GUI
     */
    @Override
    public void open() {

        GUIManager.getMountGUI(getOwner()).openGUI();
    }

    /**
     * 获取此玩家的坐骑集合
     *
     * @return 坐骑集合
     */
    @Override
    public List<Mount> getMountList() {

        return new ArrayList<>(mountMap.values());
    }

    /**
     * 获取此玩家的坐骑数量
     *
     * @return 数量
     */
    @Override
    public int getMountSize() {

        return mountMap.size();
    }

    /**
     * 获取此玩家的出站坐骑
     *
     * @return 出站坐骑 没有返回 null
     */
    @Override
    public Mount getMain() {

        return main;
    }

    /**
     * 设置此玩家的出战坐骑
     *
     * @param slot 坐骑 GUI 索引
     */
    @Override
    public void setMain(int slot) {

        this.mainSlot = slot;
        this.main = mountMap.get(slot);
    }

    /**
     * 获取此玩家的出战坐骑索引
     *
     * @return 索引
     */
    @Override
    public int getMainSlot() {

        return mainSlot;
    }

    /**
     * 给予此玩家新的坐骑
     *
     * @param mount 坐骑
     */
    @Override
    public void giveMount(Mount mount) {

        if(mount != null) {

            if(mount.getOwner() == null || !mount.getOwner().equals(getOwner())) {

                mount.setOwner(getOwner());
            }
            mountMap.put(getMountSize() + 1, mount);
            saveData();
        }
    }

    /**
     * 更新此玩家的坐骑从索引
     *
     * @param newMount 坐骑
     * @param slot     索引
     */
    @Override
    public void updateMountFromSlot(Mount newMount, int slot) {

        if(mountMap.containsKey(slot)) {

            mountMap.remove(slot);
        }
        mountMap.put(slot, newMount);
    }

    /**
     * 读取此玩家的坐骑数据
     */
    @Override
    public void loadData() {

        List<Mount> temp = DataManager.loadPlayerMountData(getOwner().getName());
        int index = 0;

        for(Mount mount : temp) {

            if(mount != null) {

                if(mount.getOwner() == null || !mount.getOwner().equals(getOwner())) {

                    mount.setOwner(getOwner());
                }
                mountMap.put(index, mount);
                index++;
            }
        }
    }

    /**
     * 保存此玩家的坐骑数据
     */
    @Override
    public void saveData() {

        DataManager.savePlayerMountData(getOwner().getName());
    }
}
