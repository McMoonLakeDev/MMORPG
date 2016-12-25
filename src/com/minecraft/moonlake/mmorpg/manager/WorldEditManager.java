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

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/5/24.
 */
public final class WorldEditManager extends MMORPGManager {

    /**
     * 获取创世神玩家选择的区域对象
     *
     * @param mmorpgPlayer 玩家
     * @return 区域对象 没有则返回 null
     */
    public static Selection getSelection(MMORPGPlayer mmorpgPlayer) {

        return mmorpgPlayer != null ? getSelection(mmorpgPlayer.getBukkitPlayer()) : null;
    }

    /**
     * 获取创世神玩家选择的区域对象
     *
     * @param player 玩家
     * @return 区域对象 没有则返回 null
     */
    public static Selection getSelection(Player player) {

        return getMain().getWorldEdit().getSelection(player);
    }
}
