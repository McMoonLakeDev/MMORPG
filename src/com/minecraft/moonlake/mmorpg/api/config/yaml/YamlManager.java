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
 
  
package com.minecraft.moonlake.mmorpg.api.config.yaml;

import com.minecraft.moonlake.mmorpg.manager.MMORPGManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Created by MoonLake on 2016/6/28.
 */
public final class YamlManager extends MMORPGManager {

    /**
     * 从指定文件对象加载 Yaml 文件对象
     *
     * @param file 文件对象
     * @return Yaml 文件对象
     */
    public static YamlConfiguration loadYamlConfiguration(File file) {

        return YamlConfiguration.loadConfiguration(file);
    }

    /**
     * 从指定路径加载 Yaml 文件对象
     *
     * @param path 路径
     * @return Yaml 文件对象
     */
    public static YamlConfig loadYamlConfig(String path) {

        return new WrappedYamlConfig(path);
    }

    /**
     * 从指定路径加载 Yaml 文件对象
     *
     * @param path 路径
     * @param read 是否读取
     * @return Yaml 文件对象
     */
    public static YamlConfig loadYamlConfig(String path, boolean read) {

        return new WrappedYamlConfig(path, read);
    }

    /**
     * 从指定路径加载 Yaml 文件对象
     *
     * @param path 路径
     * @param create 是否没有则创建
     * @param read 是否读取
     * @return Yaml 文件对象
     */
    public static YamlConfig loadYamlConfig(String path, boolean create, boolean read) {

        return new WrappedYamlConfig(path, create, read);
    }

    /**
     * 从指定文件对象加载 Yaml 文件对象
     *
     * @param file 文件
     * @return Yaml 文件对象
     */
    public static YamlConfig loadYamlConfig(File file) {

        return new WrappedYamlConfig(file);
    }

    /**
     * 从指定文件对象加载 Yaml 文件对象
     *
     * @param file 文件
     * @param read 是否读取
     * @return Yaml 文件对象
     */
    public static YamlConfig loadYamlConfig(File file, boolean read) {

        return new WrappedYamlConfig(file, read);
    }

    /**
     * 从指定文件对象加载 Yaml 文件对象
     *
     * @param file 文件
     * @param create 是否没有则创建
     * @param read 是否读取
     * @return Yaml 文件对象
     */
    public static YamlConfig loadYamlConfig(File file, boolean create, boolean read) {

        return new WrappedYamlConfig(file, create, read);
    }
}
