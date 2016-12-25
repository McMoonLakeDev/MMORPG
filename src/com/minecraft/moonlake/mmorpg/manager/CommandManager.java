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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/5/24.
 */
public final class CommandManager extends MMORPGManager {

    /**
     * 获取命令执行对象是否为控制台对象
     *
     * @param sender 命令对象
     * @return true 为控制台执行 else 为玩家执行
     */
    public static boolean isConsole(CommandSender sender) {

        return sender != null && !(sender instanceof Player);
    }

    /**
     * 获取指定玩家名的玩家是否在线
     *
     * @param name 玩家名
     * @return true 为在线 else 没在线
     */
    public static boolean isOnline(String name) {

        return AccountManager.getPlayer(name) != null;
    }

    /**
     * 获取命令执行对象的 MMORPG 玩家对象
     *
     * @param sender 命令对象
     * @return 玩家对象 没有则返回 null
     */
    public static MMORPGPlayer getSender(CommandSender sender) {

        return !isConsole(sender) ? AccountManager.getPlayer(sender.getName()) : null;
    }
}
