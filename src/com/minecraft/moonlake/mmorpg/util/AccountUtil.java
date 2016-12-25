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
 
  
package com.minecraft.moonlake.mmorpg.util;

import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.account.PlayerAccount;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.util.player.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by MoonLake on 2016/5/17.
 */
public final class AccountUtil implements PlayerAccount {

    private final MMORPG main;
    private final Map<String, MMORPGPlayer> playerAccount;

    public AccountUtil(MMORPG main) {

        this.main = main;
        this.playerAccount = new HashMap<>();
    }

    /**
     * 获取月色之湖大型多人在线角色扮演服务器在线玩家对象集合
     *
     * @return 玩家对象集合
     */
    public Set<MMORPGPlayer> getOnlinePlayers() {

        return new HashSet<>(playerAccount.values());
    }

    /**
     * 创建月色之湖大型多人在线角色扮演玩家对象
     *
     * @param name 玩家名
     * @return MMORPG 玩家
     */
    @Override
    public MMORPGPlayer create(String name) {

        return _create(name);
    }

    /**
     * 创建月色之湖大型多人在线角色扮演玩家对象
     *
     * @param uuid 玩家 UUID
     * @return MMORPG 玩家
     */
    @Override
    public MMORPGPlayer create(UUID uuid) {

        return create(main.getMoonLake().getPlayerlib().getPlayer(uuid));
    }

    /**
     * 创建月色之湖大型多人在线角色扮演玩家对象
     *
     * @param player 玩家
     * @return MMORPG 玩家
     */
    @Override
    public MMORPGPlayer create(Player player) {

        return player != null ? create(player.getName()) : null;
    }

    /**
     * 获取月色之湖大型多人在线角色扮演玩家对象
     *
     * @param name 玩家名
     * @return MMORPG 玩家
     */
    @Override
    public MMORPGPlayer getPlayer(String name) {

        return get(name);
    }

    /**
     * 获取月色之湖大型多人在线角色扮演玩家对象
     *
     * @param uuid 玩家 UUID
     * @return MMORPG 玩家
     */
    @Override
    public MMORPGPlayer getPlayer(UUID uuid) {

        return getPlayer(main.getMoonLake().getPlayerlib().getPlayer(uuid));
    }

    /**
     * 获取月色之湖大型多人在线角色扮演玩家对象
     *
     * @param player 玩家
     * @return MMORPG 玩家
     */
    @Override
    public MMORPGPlayer getPlayer(Player player) {

        return player != null ? getPlayer(player.getName()) : null;
    }

    /**
     * 获取玩家是否拥有 MMORPG 玩家对象
     *
     * @param name 玩家名
     * @return true 拥有对象 else 没有
     */
    @Override
    public boolean hasPlayer(String name) {

        return has(name);
    }

    /**
     * 获取玩家是否拥有 MMORPG 玩家对象
     *
     * @param uuid 玩家 UUID
     * @return true 拥有对象 else 没有
     */
    @Override
    public boolean hasPlayer(UUID uuid) {

        return hasPlayer(main.getMoonLake().getPlayerlib().getPlayer(uuid));
    }

    /**
     * 获取玩家是否拥有 MMORPG 玩家对象
     *
     * @param player 玩家
     * @return true 拥有对象 else 没有
     */
    @Override
    public boolean hasPlayer(Player player) {

        return player != null && has(player.getName());
    }

    /**
     * 关闭玩家的 MMORPG 数据并释放对象
     *
     * @param name 玩家名
     */
    @Override
    public void closePlayer(String name) {

        if(has(name)) {

            close(name);
        }
    }

    /**
     * 关闭玩家的 MMORPG 数据并释放对象
     *
     * @param uuid 玩家 UUID
     */
    @Override
    public void closePlayer(UUID uuid) {

        closePlayer(main.getMoonLake().getPlayerlib().getPlayer(uuid));
    }

    /**
     * 关闭玩家的 MMORPG 数据并释放对象
     *
     * @param player 玩家
     */
    @Override
    public void closePlayer(Player player) {

        if(player != null && hasPlayer(player)) {

            closePlayer(player.getName());
        }
    }

    private MMORPGPlayer _create(String name) {

        if(!has(name)) {

            playerAccount.put(name, new PlayerUtil(name));
        }
        return get(name);
    }

    private boolean has(String name) {

        return playerAccount.containsKey(name);
    }

    private MMORPGPlayer get(String name) {

        return has(name) ? playerAccount.get(name) : null;
    }

    private void close(String name) {

        if(has(name)) {

            playerAccount.remove(name);
        }
    }
}
