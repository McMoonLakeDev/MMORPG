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
 
  
package com.minecraft.moonlake.mmorpg.api.commands.console;

/**
 * Created by MoonLake on 2016/5/24.
 */
public interface ConsoleHandle {

    /**
     * 给控制台发送消息文本
     *
     * @param msg 消息
     */
    void send(String msg);

    /**
     * 给控制台发送消息文本
     *
     * @param msg 消息
     */
    void send(String... msg);

    /**
     * 给此控制台发送语言文件指定 键 的值
     *
     * @param key 键
     */
    void l18n(String key);

    /**
     * 给此控制台发送语言文件指定 键 的值
     *
     * @param key 键
     * @param args 参数
     */
    void l18n(String key, Object... args);
}
