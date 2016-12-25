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

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.task.MMORPGTask;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by MoonLake on 2016/6/9.
 */
public abstract class AbstractEffect extends MMORPGTask implements Effect {

    private final EffectType type;
    private int time;
    private long interval;
    private int effectTime;
    private LivingEntity entity;
    private boolean isStart;
    private boolean isPlayer;
    private UUID targetUUID;

    public AbstractEffect(EffectType type, int time, long interval) {

        this.type = type;
        this.time = time;
        this.interval = interval;
        this.effectTime = time;
        this.entity = null;
        this.isStart = false;
        this.isPlayer = false;
        this.targetUUID = null;
    }

    @Override
    public void run() {

        effectTime--;

        if(effectTime <= 0 || entity == null || entity.isDead()) {

            dispose();
            cancel();
            return;
        }
        if(entity != null) {

            update();
        }
    }

    /**
     * 执行并启动效果
     */
    public void start() {

        if(!isStart) {

            init();
        }
    }

    /**
     * 更新效果在目标实体的作为
     */
    public abstract void update();

    /**
     * 初始化对象
     */
    protected void init() {

        isStart = true;

        runTaskTimer(MMORPGPlugin.getInstances().getMain(), 0L, interval);
    }

    /**
     * 释放对象并关闭内存占用
     */
    protected void dispose() {

        EffectManager.closeEffect(this);
    }

    /**
     * 获取 MMORPG 主类实例对象
     *
     * @return MMORPG 对象
     */
    protected final MMORPG getMain() {

        return MMORPGPlugin.getInstances();
    }

    /**
     * 获取效果作为的目标实体是否是玩家对象
     *
     * @return 是否是玩家
     */
    protected final boolean isPlayer() {

        return isPlayer;
    }

    /**
     * 获取效果作为的目标实体
     *
     * @return 目标实体
     */
    protected final LivingEntity getEntity() {

        return entity;
    }

    /**
     * 设置效果的持续时间
     *
     * @param time 时间
     */
    protected final void setEffectTime(int time) {

        this.effectTime = time;
    }

    /**
     * 设置此效果的作为目标
     *
     * @param entity 目标
     */
    public void setTarget(LivingEntity entity) {

        this.entity = entity;

        if(entity != null) {

            targetUUID = entity.getUniqueId();
            isPlayer = entity instanceof Player;
        }
    }

    /**
     * 设置此效果的作为玩家
     *
     * @param mmorpgPlayer 玩家
     */
    public void setTarget(MMORPGPlayer mmorpgPlayer) {

        setTarget(mmorpgPlayer != null ? mmorpgPlayer.getBukkitPlayer() : null);
    }

    /**
     * 获取效果的时间
     *
     * @return 时间
     */
    @Override
    public int getTime() {

        return time;
    }

    /**
     * 获取效果的间隔
     *
     * @return 间隔
     */
    @Override
    public long getInterval() {

        return interval;
    }

    /**
     * 获取效果的类型
     *
     * @return 类型
     */
    @Override
    public EffectType getEffectType() {

        return type;
    }
}
