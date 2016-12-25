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
 
  
package com.minecraft.moonlake.mmorpg.api.account;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

/**
 * Created by MoonLake on 2016/5/17.
 */
public interface PlayerAccount {

    /**
     * 获取月色之湖大型多人在线角色扮演服务器在线玩家对象集合
     *
     * @return 玩家对象集合
     */
    Set<MMORPGPlayer> getOnlinePlayers();

    /**
     * 创建月色之湖大型多人在线角色扮演玩家对象
     *
     * @param name 玩家名
     * @return MMORPG 玩家
     */
    MMORPGPlayer create(String name);

    /**
     * 创建月色之湖大型多人在线角色扮演玩家对象
     *
     * @param uuid 玩家 UUID
     * @return MMORPG 玩家
     */
    MMORPGPlayer create(UUID uuid);

    /**
     * 创建月色之湖大型多人在线角色扮演玩家对象
     *
     * @param player 玩家
     * @return MMORPG 玩家
     */
    MMORPGPlayer create(Player player);

    /**
     * 获取月色之湖大型多人在线角色扮演玩家对象
     *
     * @param name 玩家名
     * @return MMORPG 玩家
     */
    MMORPGPlayer getPlayer(String name);

    /**
     * 获取月色之湖大型多人在线角色扮演玩家对象
     *
     * @param uuid 玩家 UUID
     * @return MMORPG 玩家
     */
    MMORPGPlayer getPlayer(UUID uuid);

    /**
     * 获取月色之湖大型多人在线角色扮演玩家对象
     *
     * @param player 玩家
     * @return MMORPG 玩家
     */
    MMORPGPlayer getPlayer(Player player);

    /**
     * 获取玩家是否拥有 MMORPG 玩家对象
     *
     * @param name 玩家名
     * @return true 拥有对象 else 没有
     */
    boolean hasPlayer(String name);

    /**
     * 获取玩家是否拥有 MMORPG 玩家对象
     *
     * @param uuid 玩家 UUID
     * @return true 拥有对象 else 没有
     */
    boolean hasPlayer(UUID uuid);

    /**
     * 获取玩家是否拥有 MMORPG 玩家对象
     *
     * @param player 玩家
     * @return true 拥有对象 else 没有
     */
    boolean hasPlayer(Player player);

    /**
     * 关闭玩家的 MMORPG 数据并释放对象
     *
     * @param name 玩家名
     */
    void closePlayer(String name);

    /**
     * 关闭玩家的 MMORPG 数据并释放对象
     *
     * @param uuid 玩家 UUID
     */
    void closePlayer(UUID uuid);

    /**
     * 关闭玩家的 MMORPG 数据并释放对象
     *
     * @param player 玩家
     */
    void closePlayer(Player player);
}
