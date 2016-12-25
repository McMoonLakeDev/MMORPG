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
 
  
package com.minecraft.moonlake.mmorpg.api;

import java.util.Set;

/**
 * Created by MoonLake on 2016/5/24.
 */
public interface MMORPGInfo {

    /**
     * 获取插件的称号
     *
     * @return Prefix
     */
    String getPluginPrefix();

    /**
     * 获取插件的名字
     *
     * @return Name
     */
    String getPluginName();

    /**
     * 获取插件的主类
     *
     * @return MainClass
     */
    String getPluginMain();

    /**
     * 获取插件的版本
     *
     * @return Version
     */
    String getPluginVersion();

    /**
     * 获取插件的网站
     *
     * @return Website
     */
    String getPluginWebsite();

    /**
     * 获取插件的简介
     *
     * @return Description
     */
    String getPluginDescription();

    /**
     * 获取插件的作者
     *
     * @return Auther
     */
    Set<String> getPluginAuthers();

    /**
     * 获取插件的依赖
     *
     * @return Depend
     */
    Set<String> getPluginDepends();

    /**
     * 获取插件的软依赖
     *
     * @return SoftDepend
     */
    Set<String> getPluginSoftDepends();
}
