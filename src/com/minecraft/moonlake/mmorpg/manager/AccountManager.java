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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

/**
 * Created by MoonLake on 2016/5/20.
 */
public final class AccountManager extends MMORPGManager {

    /**
     * 获取月色之湖大型多人在线角色扮演服务器在线玩家对象集合
     *
     * @return 玩家对象集合
     */
    public static Set<MMORPGPlayer> getOnlinePlayers() {

        return getMain().getAccount().getOnlinePlayers();
    }

    /**
     * 创建月色之湖大型多人在线角色扮演玩家对象
     *
     * @param name 玩家名
     * @return MMORPG 玩家
     */
    public static MMORPGPlayer create(String name) {

        return getMain().getAccount().create(name);
    }

    /**
     * 创建月色之湖大型多人在线角色扮演玩家对象
     *
     * @param uuid 玩家 UUID
     * @return MMORPG 玩家
     */
    @Deprecated
    public static MMORPGPlayer create(UUID uuid) {

        return create(getMain().getMoonLake().getPlayerlib().getPlayer(uuid));
    }

    /**
     * 创建月色之湖大型多人在线角色扮演玩家对象
     *
     * @param player 玩家
     * @return MMORPG 玩家
     */
    public static MMORPGPlayer create(Player player) {

        return player != null ? create(player.getName()) : null;
    }

    /**
     * 获取月色之湖大型多人在线角色扮演玩家对象
     *
     * @param name 玩家名
     * @return MMORPG 玩家
     */
    public static MMORPGPlayer getPlayer(String name) {

        return getMain().getAccount().getPlayer(name);
    }

    /**
     * 获取月色之湖大型多人在线角色扮演玩家对象
     *
     * @param uuid 玩家 UUID
     * @return MMORPG 玩家
     */
    @Deprecated
    public static MMORPGPlayer getPlayer(UUID uuid) {

        return getPlayer(getMain().getMoonLake().getPlayerlib().getPlayer(uuid));
    }

    /**
     * 获取月色之湖大型多人在线角色扮演玩家对象
     *
     * @param player 玩家
     * @return MMORPG 玩家
     */
    public static MMORPGPlayer getPlayer(Player player) {

        return player != null ? getPlayer(player.getName()) : null;
    }

    /**
     * 获取玩家是否拥有 MMORPG 玩家对象
     *
     * @param name 玩家名
     * @return true 拥有对象 else 没有
     */
    public static boolean hasPlayer(String name) {

        return getMain().getAccount().hasPlayer(name);
    }

    /**
     * 获取玩家是否拥有 MMORPG 玩家对象
     *
     * @param uuid 玩家 UUID
     * @return true 拥有对象 else 没有
     */
    @Deprecated
    public static boolean hasPlayer(UUID uuid) {

        return hasPlayer(getMain().getMoonLake().getPlayerlib().getPlayer(uuid));
    }

    /**
     * 获取玩家是否拥有 MMORPG 玩家对象
     *
     * @param player 玩家
     * @return true 拥有对象 else 没有
     */
    public static boolean hasPlayer(Player player) {

        return player != null && hasPlayer(player.getName());
    }

    /**
     * 关闭玩家的 MMORPG 数据并释放对象
     *
     * @param name 玩家名
     */
    public static void closePlayer(String name) {

        if(hasPlayer(name)) {

            getMain().getAccount().closePlayer(name);
        }
    }

    /**
     * 关闭玩家的 MMORPG 数据并释放对象
     *
     * @param uuid 玩家 UUID
     */
    @Deprecated
    public static void closePlayer(UUID uuid) {

        closePlayer(getMain().getMoonLake().getPlayerlib().getPlayer(uuid));
    }

    /**
     * 关闭玩家的 MMORPG 数据并释放对象
     *
     * @param player 玩家
     */
    public static void closePlayer(Player player) {

        if(player != null && hasPlayer(player)) {

            closePlayer(player.getName());
        }
    }

    /**
     * 获取 Bukkit 实体玩家对象
     *
     * @param name 玩家名
     * @return Bukkit 玩家 没有或不在线则返回 null
     */
    public static Player getBukkitPlayer(String name) {

        Player temp = Bukkit.getServer().getPlayer(name);
        return temp != null && temp.isOnline() ? temp : null;
    }
}
