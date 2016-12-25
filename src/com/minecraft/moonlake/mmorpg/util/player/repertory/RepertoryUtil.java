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
 
  
package com.minecraft.moonlake.mmorpg.util.player.repertory;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.player.repertory.Repertory;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import com.minecraft.moonlake.mmorpg.manager.DataManager;
import com.minecraft.moonlake.mmorpg.manager.PlayerManager;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

/**
 * Created by MoonLake on 2016/6/17.
 */
public class RepertoryUtil implements Repertory {

    private final MMORPGPlayer owner;
    private final Inventory repertory;
    private final String title;

    public RepertoryUtil(MMORPGPlayer mmorpgPlayer) {

        this.owner = mmorpgPlayer;
        this.title = StringUtil.stringClone(ConfigManager.get("PlayerRepertory.Title").asString()).replace("%player", mmorpgPlayer.getName());
        this.repertory = Bukkit.getServer().createInventory(mmorpgPlayer, PlayerManager.getRepertorySize(mmorpgPlayer), StringUtil.color(title));
    }

    /**
     * 获取此个人仓库的拥有者
     *
     * @return 拥有者
     */
    @Override
    public MMORPGPlayer getOwner() {

        return owner;
    }

    /**
     * 将此玩家的个人仓库进行打开
     */
    @Override
    public void open() {

        getOwner().openInventory(repertory);
    }

    /**
     * 偷窥此物品栏
     *
     * @param peeped 偷窥者
     */
    @Override
    public void peep(MMORPGPlayer peeped) {

        peeped.openInventory(repertory);
    }

    /**
     * 获取此玩家的个人仓库标题
     *
     * @return 标题
     */
    @Override
    public String getTitle() {

        return title;
    }

    /**
     * 获取玩家的个人仓库大小 (1-6)
     *
     * @return 仓库大小
     */
    @Override
    public int getSize() {

        return repertory.getSize() / 9;
    }

    /**
     * 加载此玩家的个人仓库数据
     */
    @Override
    public void loadData() {

        PlayerManager.readSerializeRepertory(repertory, DataManager.loadPlayerRepertoryData(getOwner().getName()));
    }

    /**
     * 保存此玩家的个人仓库数据
     */
    @Override
    public void saveData() {

        DataManager.savePlayerRepertoryData(getOwner().getName());
    }

    /**
     * 获取个人仓库的物品栏对象
     *
     * @return 物品栏对象
     */
    @Override
    public Inventory getInventory() {

        return repertory;
    }
}
