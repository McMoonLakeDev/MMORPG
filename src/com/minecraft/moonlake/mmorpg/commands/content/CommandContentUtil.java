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
 
  
package com.minecraft.moonlake.mmorpg.commands.content;

import com.minecraft.moonlake.mmorpg.api.commands.CommandContent;

/**
 * Created by MoonLake on 2016/5/25.
 */
public class CommandContentUtil implements CommandContent {

    private String command;
    private String[] args;

    public CommandContentUtil(String command, String[] args) {

        this.command = command;
        this.args = args;
    }

    /**
     * 主命令
     *
     * @return 主命令
     */
    @Override
    public String command() {

        return command;
    }

    /**
     * 命令参数
     *
     * @return 参数数组
     */
    @Override
    public String[] args() {

        return args;
    }

    /**
     * 命令长度
     *
     * @return 长度
     */
    @Override
    public int argsLength() {

        return args.length;
    }

    /**
     * 获取命令参数指定索引是否等于指定目标字符串
     *
     * @param index  参数索引 (0 ->)
     * @param target 目标
     * @return 是否符合
     */
    @Override
    public boolean equalsIgnoreCase(int index, String target) {

        return args.length - 1 >= index && args[index].equalsIgnoreCase(target);
    }

    /**
     * 获取命令参数指定索引的字符串值
     *
     * @param index 参数索引 (0 ->)
     * @return 索引的字符串值
     */
    @Override
    public String getArgFromIndex(int index) {

        return args.length - 1 >= index ? args[index] : "";
    }
}
