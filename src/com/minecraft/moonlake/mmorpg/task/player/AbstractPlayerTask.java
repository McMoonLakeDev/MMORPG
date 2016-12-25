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
 
  
package com.minecraft.moonlake.mmorpg.task.player;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.task.MMORPGTask;

/**
 * Created by MoonLake on 2016/6/19.
 */
public abstract class AbstractPlayerTask extends MMORPGTask implements PlayerTask {

    private final MMORPGPlayer owner;
    private final Runnable endExecute;
    private final int delay;

    protected int currentTime;

    public AbstractPlayerTask(MMORPGPlayer mmorpgPlayer, int delay) {

        this(mmorpgPlayer, delay, null);
    }

    public AbstractPlayerTask(MMORPGPlayer mmorpgPlayer, int delay, Runnable endExecute) {

        this.owner = mmorpgPlayer;
        this.delay = delay;
        this.currentTime = delay;
        this.endExecute = endExecute;
    }

    /**
     * 获取作为的主玩家
     *
     * @return 玩家
     */
    public MMORPGPlayer getPlayer() {

        return owner;
    }

    /**
     * 获取效果持续的延迟
     *
     * @return 延迟
     */
    public int getDelay() {

        return delay;
    }

    /**
     * 结束并调用执行后关闭 Task 占用
     */
    @Override
    public void close() {

        if(endExecute != null) {

            endExecute.run();
        }
        PlayerTaskManager.closeTask(getPlayer().getName());
    }

    /**
     * 开始并执行任务
     */
    @Override
    public abstract void start();

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see     java.lang.Thread#run()
     */
    public abstract void run();
}
