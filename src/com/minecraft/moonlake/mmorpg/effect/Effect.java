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
 
  
package com.minecraft.moonlake.mmorpg.effect;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import org.bukkit.entity.LivingEntity;

/**
 * Created by MoonLake on 2016/6/9.
 */
public interface Effect {

    /**
     * 获取效果的时间
     *
     * @return 时间
     */
    int getTime();

    /**
     * 获取效果的间隔
     *
     * @return 间隔
     */
    long getInterval();

    /**
     * 获取效果的类型
     *
     * @return 类型
     */
    EffectType getEffectType();

    /**
     * 设置此效果的作为目标
     *
     * @param entity 目标
     */
    void setTarget(LivingEntity entity);

    /**
     * 设置此效果的作为玩家
     *
     * @param mmorpgPlayer 玩家
     */
    void setTarget(MMORPGPlayer mmorpgPlayer);

    /**
     * 执行并启动效果
     */
    void start();
}
