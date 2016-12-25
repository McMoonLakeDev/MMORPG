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

import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.MMORPGCore;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Created by MoonLake on 2016/07/04.
 */
public interface YamlConfig extends MMORPGCore {

	/**
     * 获取月色之湖 MMORPG 主类实例对象
     *
     * @return MMORPG
     */
    MMORPG getInstance();
    
    /**
     * 获取数据 Yaml 配置文件的路径
     * 
     * @return 路径
     */
    String getPath();
    
    /**
     * 获取数据 Yaml 配置文件的文件对象
     * 
     * @return 文件对象
     */
    File getFile();

    /**
     * 创建并初始化 Yaml 配置文件的文件对象
     */
    void createFile();

    /**
     * 读取数据 Yaml 配置文件的文件对象
     */
    void readConfig();
    
    /**
     * 将数据 Yaml 配置文件指定路径设置整数值
     * 
     * @param path 路径
     * @param value 值
     */
    void setInt(String path, int value);
    
    /**
     * 将数据 Yaml 配置文件指定路径设置双精度浮点值
     * 
     * @param path 路径
     * @param value 值
     */
    void setDouble(String path, double value);
    
    /**
     * 将数据 Yaml 配置文件指定路径设置单精度浮点值
     * 
     * @param path 路径
     * @param value 值
     */
    void setFloat(String path, float value);

    /**
     * 将数据 Yaml 配置文件指定路径设置布尔值
     *
     * @param path 路径
     * @param value 值
     */
    void setBoolean(String path, boolean value);
    
    /**
     * 将数据 Yaml 配置文件指定路径设置字符串值
     * 
     * @param path 路径
     * @param value 值
     */
    void setString(String path, String value);

    /**
     * 将数据 Yaml 配置文件指定路径设置字符串列表值
     *
     * @param path 路径
     * @param value 值
     */
    void setStringList(String path, List<String> value);

    /**
     * 获取数据 Yaml 配置文件指定路径的值
     *
     * @param path 路径
     * @return 路径的值
     */
    Object get(String path);

    /**
     * 获取数据 Yaml 配置文件指定路径的整数值
     * 
     * @param path 路径
     * @return 路径的整数值
     */
    int getInt(String path);
    
    /**
     * 获取数据 Yaml 配置文件指定路径的双精度浮点值
     * 
     * @param path 路径
     * @return 路径的双精度浮点数
     */
    double getDouble(String path);
    
    /**
     * 获取数据 Yaml 配置文件指定路径的单精度浮点值
     * 
     * @param path 路径
     * @return 路径的单精度浮点数
     */
    float getFloat(String path);

    /**
     * 获取数据 Yaml 配置文件指定路径的布尔值
     *
     * @param path 路径
     * @return 路径的布尔值
     */
    boolean getBoolean(String path);
    
    /**
     * 获取数据 Yaml 配置文件指定路径的字符串值
     * 
     * @param path 路径
     * @return 路径的字符串值
     */
    String getString(String path);

    /**
     * 获取数据 Yaml 配置文件指定路径的字符串列表值
     *
     * @param path 路径
     * @return 路径的字符串列表值
     */
    List<String> getStringList(String path);

    /**
     * 获取数据 Yaml 配置文件指定路径的键集合值
     *
     * @param path 路径
     * @return 键的集合值
     */
    Set<String> getKeys(String path);

    /**
     * 获取数据 Yaml 配置文件指定路径的键集合值
     *
     * @param path 路径
     * @param subSection 是否子节也获取
     * @return 键的集合值
     */
    Set<String> getKeys(String path, boolean subSection);
    
    /**
	 * 将数据 Yaml 配置文件的对象进行读取
	 */
    void loadData();
    
    /**
	 * 将数据 Yaml 配置文件的对象进行保存
	 */
    void saveData();
}
