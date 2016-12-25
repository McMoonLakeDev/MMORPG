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
 
  
package com.minecraft.moonlake.mmorpg.commands;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.commands.CommandContent;
import com.minecraft.moonlake.mmorpg.api.commands.MMORPGCommand;
import com.minecraft.moonlake.mmorpg.api.commands.console.ConsoleHandle;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;

/**
 * Created by MoonLake on 2016/5/25.
 */
public abstract class MMORPGCommands implements MMORPGCommand {

    private final String name;

    public MMORPGCommands(String name) {

        this.name = name;
    }

    /**
     * 获取主类实例对象
     *
     * @return 主类
     */
    public final MMORPG getInstance() {

        return MMORPGPlugin.getInstances();
    }

    /**
     * 获取执行的命令名
     *
     * @return 命令名
     */
    @Override
    public String getCommand() {

        return name;
    }

    /**
     * 控制台执行命令
     *
     * @param console 控制台
     * @param content 命令内容
     */
    public abstract void run(ConsoleHandle console, CommandContent content);

    /**
     * 玩家执行命令
     *
     * @param mmorpgPlayer 玩家
     * @param content 命令内容
     */
    public abstract void run(MMORPGPlayer mmorpgPlayer, CommandContent content);
}
