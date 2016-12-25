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

import com.minecraft.moonlake.mmorpg.api.config.MMORPGConfig;

/**
 * Created by MoonLake on 2016/5/23.
 */
public final class ConfigManager extends MMORPGManager {

    /**
     * 获取配置文件基础数据
     *
     * @param key 键
     * @return 基础数据
     */
    public static MMORPGConfig.BaseConfig get(String key) {

        return getMain().getMConfig().get(key);
    }
}
