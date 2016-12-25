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
 
  
package com.minecraft.moonlake.mmorpg.task;

import com.minecraft.moonlake.mmorpg.manager.MMORPGManager;

/**
 * Created by MoonLake on 2016/6/27.
 */
public final class TaskManager extends MMORPGManager {

    /**
     * 执行服务器任务
     *
     * @param runnable 执行
     * @return 任务对象
     */
    public static MMORPGTask runTask(Runnable runnable) {

        MMORPGTask task = new MMORPGTask() {

            @Override
            public void run() {

                runnable.run();
            }
        };
        task.runTask(getMain().getMain());

        return task;
    }

    /**
     * 执行服务器任务
     *
     * @param runnable 执行
     */
    public static void runTask(MMORPGRunnable runnable) {

        runnable.runTask(getMain().getMain());
    }

    /**
     * 异步执行服务器任务
     *
     * @param runnable 执行
     * @return 任务对象
     */
    public static MMORPGTask runTaskAsynchronously(Runnable runnable) {

        MMORPGTask task = new MMORPGTask() {

            @Override
            public void run() {

                runnable.run();
            }
        };
        task.runTaskAsynchronously(getMain().getMain());

        return task;
    }

    /**
     * 异步执行服务器任务
     *
     * @param runnable 执行
     */
    public static void runTaskAsynchronously(MMORPGRunnable runnable) {

        runnable.runTaskAsynchronously(getMain().getMain());
    }

    /**
     * 执行服务器任务在多少 Tick 后
     *
     * @param runnable 执行
     * @param delay 延迟
     * @return 任务对象
     */
    public static MMORPGTask runTaskLater(Runnable runnable, long delay) {

        MMORPGTask task = new MMORPGTask() {

            @Override
            public void run() {

                runnable.run();
            }
        };
        task.runTaskLater(getMain().getMain(), delay);

        return task;
    }

    /**
     * 执行服务器任务在多少 Tick 后
     *
     * @param runnable 执行
     * @param delay 延迟
     */
    public static void runTaskLater(MMORPGRunnable runnable, long delay) {

        runnable.runTaskLater(getMain().getMain(), delay);
    }

    /**
     * 异步执行服务器任务在多少 Tick 后
     *
     * @param runnable 执行
     * @param delay 延迟
     * @return 任务对象
     */
    public static MMORPGTask runTaskLaterAsynchronously(Runnable runnable, long delay) {

        MMORPGTask task = new MMORPGTask() {

            @Override
            public void run() {

                runnable.run();
            }
        };
        task.runTaskLaterAsynchronously(getMain().getMain(), delay);

        return task;
    }

    /**
     * 异步执行服务器任务在多少 Tick 后
     *
     * @param runnable 执行
     * @param delay 延迟
     */
    public static void runTaskLaterAsynchronously(MMORPGRunnable runnable, long delay) {

        runnable.runTaskLaterAsynchronously(getMain().getMain(), delay);
    }

    /**
     * 执行服务器任务在多少 Tick 后开始定时器
     *
     * @param runnable 执行
     * @param delay 延迟
     * @param period 周期
     * @return 任务对象
     */
    public static MMORPGTask runTimer(Runnable runnable, long delay, long period) {

        MMORPGTask task = new MMORPGTask() {

            @Override
            public void run() {

                runnable.run();
            }
        };
        task.runTaskTimer(getMain().getMain(), delay, period);

        return task;
    }

    /**
     * 执行服务器任务在多少 Tick 后开始定时器
     *
     * @param runnable 执行
     * @param delay 延迟
     * @param period 周期
     */
    public static void runTimer(MMORPGRunnable runnable, long delay, long period) {

        runnable.runTaskTimer(getMain().getMain(), delay, period);
    }

    /**
     * 异步执行服务器任务在多少 Tick 后开始定时器
     *
     * @param runnable 执行
     * @param delay 延迟
     * @param period 周期
     * @return 任务对象
     */
    public static MMORPGTask runTimerAsynchronously(Runnable runnable, long delay, long period) {

        MMORPGTask task = new MMORPGTask() {

            @Override
            public void run() {

                runnable.run();
            }
        };
        task.runTaskTimerAsynchronously(getMain().getMain(), delay, period);

        return task;
    }

    /**
     * 异步执行服务器任务在多少 Tick 后开始定时器
     *
     * @param runnable 执行
     * @param delay 延迟
     * @param period 周期
     */
    public static void runTimerAsynchronously(MMORPGRunnable runnable, long delay, long period) {

        runnable.runTaskTimerAsynchronously(getMain().getMain(), delay, period);
    }
}
