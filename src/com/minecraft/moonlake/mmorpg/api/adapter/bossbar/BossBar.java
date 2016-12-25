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

import java.util.List;

/**
 * Created by MoonLake on 2016/6/19.
 */
public interface BossBar {

    /**
     * 获取 Boss 血条的标题
     *
     * @return 标题
     */
    String getTitle();

    /**
     * 设置 Boss 血条的标题
     *
     * @param title 标题
     */
    void setTitle(String title);

    /**
     * 获取 Boss 血条的颜色
     *
     * @return 颜色
     */
    BarColor getColor();

    /**
     * 设置 Boss 血条的颜色
     *
     * @param barColor 颜色
     */
    void setColor(BarColor barColor);

    /**
     * 获取 Boss 血条的样式
     *
     * @return 样式
     */
    BarStyle getStyle();

    /**
     * 设置 Boss 血条的样式
     *
     * @param barStyle 样式
     */
    void setStyle(BarStyle barStyle);

    /**
     * 删除 Boss 血条的标示
     *
     * @param barFlag 标示
     */
    void removeFlag(BarFlag barFlag);

    /**
     * 添加 Boss 血条的标示
     *
     * @param barFlag 标示
     */
    void addFlag(BarFlag barFlag);

    /**
     * 获取 Boss 血条是否拥有指定标示
     *
     * @param barFlag 标示
     * @return true 则拥有 else 没有
     */
    boolean hasFlag(BarFlag barFlag);

    /**
     * 设置 Boss 血条的进度 (0 - 1.0)
     *
     * @param progress 进度
     */
    void setProgress(double progress);

    /**
     * 获取 Boss 血条的进度
     *
     * @return 进度 (0 - 1.0)
     */
    double getProgress();

    /**
     * 将 Boss 血条添加指定玩家对象
     *
     * @param mmorpgPlayer 玩家
     */
    void addPlayer(MMORPGPlayer mmorpgPlayer);

    /**
     * 将 Boss 血条添加指定玩家对象
     *
     * @param name 玩家名
     */
    @Deprecated
    void addPlayer(String name);

    /**
     * 将 Boss 血条删除指定玩家对象
     *
     * @param mmorpgPlayer 玩家
     */
    void removePlayer(MMORPGPlayer mmorpgPlayer);

    /**
     * 将 Boss 血条删除指定玩家对象
     *
     * @param name 玩家名
     */
    @Deprecated
    void removePlayer(String name);

    /**
     * 将 Boss 血条删除所有玩家对象
     */
    void removeAll();

    /**
     * 获取 Boss 血条的玩家对象集合
     *
     * @return 玩家集合
     */
    List<MMORPGPlayer> getPlayers();

    /**
     * 设置 Boss 血条是否可见
     *
     * @param visible 是否可见
     */
    void setVisible(boolean visible);

    /**
     * 获取 Boss 血条是否可见
     *
     * @return 是否可见
     */
    boolean isVisible();

    /**
     * 设置 Boss 血条是否为创建雾
     *
     * @param flag 是否
     */
    void setCreateFog(boolean flag);

    /**
     * 设置 Boss 血条是否为变暗的天空
     *
     * @param flag 是否
     */
    void setDarkenSky(boolean flag);

    /**
     * 设置 Boss 血条是否为播放 Boss 音乐
     *
     * @param flag 是否
     */
    void setPlayBossMusic(boolean flag);
}
