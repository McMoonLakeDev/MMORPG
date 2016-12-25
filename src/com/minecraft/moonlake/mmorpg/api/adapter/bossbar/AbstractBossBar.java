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
 
  
package com.minecraft.moonlake.mmorpg.api.adapter.bossbar;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.AccountManager;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoonLake on 2016/6/19.
 */
public abstract class AbstractBossBar implements BossBar {

    private final org.bukkit.boss.BossBar bossBar;
    private final List<MMORPGPlayer> players;

    public AbstractBossBar(String title, BarColor barColor, BarStyle barStyle) {

        this.players = new ArrayList<>();
        this.bossBar = Bukkit.getServer().createBossBar(title, org.bukkit.boss.BarColor.valueOf(barColor.name()), org.bukkit.boss.BarStyle.valueOf(barStyle.name()), new org.bukkit.boss.BarFlag[0]);
    }

    /**
     * 获取 Boss 血条的标题
     *
     * @return 标题
     */
    @Override
    public String getTitle() {

        return bossBar.getTitle();
    }

    /**
     * 设置 Boss 血条的标题
     *
     * @param title 标题
     */
    @Override
    public void setTitle(String title) {

        bossBar.setTitle(title);
    }

    /**
     * 获取 Boss 血条的颜色
     *
     * @return 颜色
     */
    @Override
    public BarColor getColor() {

        return BarColor.valueOf(bossBar.getColor().name());
    }

    /**
     * 设置 Boss 血条的颜色
     *
     * @param barColor 颜色
     */
    @Override
    public void setColor(BarColor barColor) {

        bossBar.setColor(org.bukkit.boss.BarColor.valueOf(barColor.name()));
    }

    /**
     * 获取 Boss 血条的样式
     *
     * @return 样式
     */
    @Override
    public BarStyle getStyle() {

        return BarStyle.valueOf(bossBar.getStyle().name());
    }

    /**
     * 设置 Boss 血条的样式
     *
     * @param barStyle 样式
     */
    @Override
    public void setStyle(BarStyle barStyle) {

        bossBar.setStyle(org.bukkit.boss.BarStyle.valueOf(barStyle.name()));
    }

    /**
     * 删除 Boss 血条的标示
     *
     * @param barFlag 标示
     */
    @Override
    public void removeFlag(BarFlag barFlag) {

        bossBar.removeFlag(org.bukkit.boss.BarFlag.valueOf(barFlag.name()));
    }

    /**
     * 添加 Boss 血条的标示
     *
     * @param barFlag 标示
     */
    @Override
    public void addFlag(BarFlag barFlag) {

        bossBar.addFlag(org.bukkit.boss.BarFlag.valueOf(barFlag.name()));
    }

    /**
     * 获取 Boss 血条是否拥有指定标示
     *
     * @param barFlag 标示
     * @return true 则拥有 else 没有
     */
    @Override
    public boolean hasFlag(BarFlag barFlag) {

        return bossBar.hasFlag(org.bukkit.boss.BarFlag.valueOf(barFlag.name()));
    }

    /**
     * 设置 Boss 血条的进度 (0 - 1.0)
     *
     * @param progress 进度
     */
    @Override
    public void setProgress(double progress) {

        bossBar.setProgress(progress);
    }

    /**
     * 获取 Boss 血条的进度
     *
     * @return 进度 (0 - 1.0)
     */
    @Override
    public double getProgress() {

        return bossBar.getProgress();
    }

    /**
     * 将 Boss 血条添加指定玩家对象
     *
     * @param mmorpgPlayer 玩家
     */
    @Override
    public void addPlayer(MMORPGPlayer mmorpgPlayer) {

        players.add(mmorpgPlayer);
        bossBar.addPlayer(mmorpgPlayer.getBukkitPlayer());
    }

    /**
     * 将 Boss 血条添加指定玩家对象
     *
     * @param name 玩家名
     */
    @Deprecated
    @Override
    public void addPlayer(String name) {

        MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(name);

        if(mmorpgPlayer != null) {

            addPlayer(mmorpgPlayer);
        }
    }

    /**
     * 将 Boss 血条删除指定玩家对象
     *
     * @param mmorpgPlayer
     */
    @Override
    public void removePlayer(MMORPGPlayer mmorpgPlayer) {

        players.remove(mmorpgPlayer);
        bossBar.removePlayer(mmorpgPlayer.getBukkitPlayer());
    }

    /**
     * 将 Boss 血条删除指定玩家对象
     *
     * @param name 玩家名
     */
    @Deprecated
    @Override
    public void removePlayer(String name) {

        MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(name);

        if(mmorpgPlayer != null) {

            removePlayer(mmorpgPlayer);
        }
    }

    /**
     * 将 Boss 血条删除所有玩家对象
     */
    @Override
    public void removeAll() {

        players.clear();
        bossBar.removeAll();
    }

    /**
     * 获取 Boss 血条的玩家对象集合
     *
     * @return 玩家集合
     */
    @Override
    public List<MMORPGPlayer> getPlayers() {

        return new ArrayList<>(players);
    }

    /**
     * 设置 Boss 血条是否可见
     *
     * @param visible 是否可见
     */
    @Override
    public void setVisible(boolean visible) {

        bossBar.setVisible(visible);
    }

    /**
     * 获取 Boss 血条是否可见
     *
     * @return 是否可见
     */
    @Override
    public boolean isVisible() {

        return bossBar.isVisible();
    }

    /**
     * 设置 Boss 血条是否为创建雾
     *
     * @param flag 是否
     */
    @Override
    public void setCreateFog(boolean flag) {

        if(flag) {

            addFlag(BarFlag.CREATE_FOG);
        }
        else {

            removeFlag(BarFlag.CREATE_FOG);
        }
    }

    /**
     * 设置 Boss 血条是否为变暗的天空
     *
     * @param flag 是否
     */
    @Override
    public void setDarkenSky(boolean flag) {

        if(flag) {

            addFlag(BarFlag.DARKEN_SKY);
        }
        else {

            removeFlag(BarFlag.DARKEN_SKY);
        }
    }

    /**
     * 设置 Boss 血条是否为播放 Boss 音乐
     *
     * @param flag 是否
     */
    @Override
    public void setPlayBossMusic(boolean flag) {

        if(flag) {

            addFlag(BarFlag.PLAY_BOSS_MUSIC);
        }
        else {

            removeFlag(BarFlag.PLAY_BOSS_MUSIC);
        }
    }
}
