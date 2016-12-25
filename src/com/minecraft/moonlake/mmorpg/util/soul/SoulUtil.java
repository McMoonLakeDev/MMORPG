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
 
  
package com.minecraft.moonlake.mmorpg.util.soul;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.soul.Soul;
import com.minecraft.moonlake.mmorpg.manager.AccountManager;
import com.minecraft.moonlake.mmorpg.hotbar.HotBarItem;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/5/22.
 */
public final class SoulUtil implements Soul {

    public SoulUtil() {

    }

    /**
     * 获取玩家的当前灵魂点
     *
     * @param player 玩家
     * @return 灵魂数量
     */
    @Override
    public int getPlayerSoul(MMORPGPlayer player) {

        return player.getSoul();
    }

    /**
     * 获取玩家的最大灵魂点
     *
     * @param player 玩家
     * @return 最大灵魂数量
     */
    @Override
    public int getPlayerMaxSoul(MMORPGPlayer player) {

        return player.getMaxSoul();
    }

    /**
     * 更新玩家的灵魂数量到快捷栏
     *
     * @param player 玩家
     */
    @Override
    public void updatePlayerHotBarSoul(MMORPGPlayer player) {

        updatePlayerHotBarSoul(player.getName(), player.getSoul());
    }

    /**
     * 更新玩家的灵魂数量到快捷栏
     *
     * @param name 玩家名
     * @param amount 数量
     */
    @Override
    public void updatePlayerHotBarSoul(String name, int amount) {

        MMORPGPlayer player = AccountManager.getPlayer(name);
        if(player == null) return;
        HotBarItem.HotBarType soul = HotBarItem.HotBarType.SOUL_SYSTEM;

        ItemStack item = player.getInventory().getItem(soul.getHotBarSlot());
        if(item == null || item.getType() != soul.getMaterial()) return;

        item.setAmount(amount <= 0 ? 1 : amount);

        player.getInventory().setItem(soul.getHotBarSlot(), item);
        player.updateInventory();
    }
}
