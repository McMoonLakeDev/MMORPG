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
 
  
package com.minecraft.moonlake.mmorpg.manager;

/**
 * Created by MoonLake on 2016/5/24.
 */
public final class l18n extends MMORPGManager {

    /**
     * 获取指定语言文件的指定 键 的值
     *
     * @param key 键
     * @return 值
     */
    public static String $(String key) {

        return getMain().l18n(key);
    }

    /**
     * 获取指定语言文件的指定 键 的值
     *
     * @param key 键
     * @param args 参数
     * @return 值
     */
    public static String $(String key, Object... args) {

        return getMain().l18n(key, args);
    }
}
