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

import com.minecraft.moonlake.api.MoonLake;
import com.minecraft.moonlake.gui.api.MoonLakeGUIManager;
import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.account.PlayerAccount;
import com.minecraft.moonlake.mmorpg.api.config.MMORPGConfig;
import com.minecraft.moonlake.mmorpg.api.role.MMORPGRole;
import com.minecraft.moonlake.mmorpg.api.soul.Soul;
import com.minecraft.moonlake.mmorpg.api.sql.MMORPGSql;
import com.minecraft.moonlake.mmorpg.item.Item;
import com.minecraft.moonlake.mmorpg.listeners.block.FallingBlockListener;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.io.InputStream;

/**
 * Created by MoonLake on 2016/5/14.
 */
public interface MMORPG extends MMORPGInfo {

    /**
     * 获取插件的主类对象
     *
     * @return 主类
     */
    MMORPGPlugin getMain();

    /**
     * 获取月色之湖前置核心 API 插件实例对象
     *
     * @return MoonLake
     */
    MoonLake getMoonLake();

    /**
     * 获取月色之湖前置 GUI 插件管理实例对象
     *
     * @return MoonLakeGUIManager
     */
    MoonLakeGUIManager getMoonLakeGUIManager();

    /**
     * 获取前置创世神 WorldEdit 插件实例对象
     *
     * @return WorldEdit
     */
    WorldEditPlugin getWorldEdit();

    /**
     * 获取月色之湖大型多人在线角色扮演游戏数据库对象
     *
     * @return MMORPGSql
     */
    MMORPGSql getData();

    /**
     * 获取月色之湖大型多人在线角色扮演角色对象
     *
     * @return MMORPGRole
     */
    MMORPGRole getRole();

    /**
     * 获取月色之湖大型多人在线角色扮演游戏物品对象
     *
     * @return Item
     */
    Item getItem();

    /**
     * 给控制台输出日志信息
     *
     * @param msg 日志
     */
    void log(String msg);

    /**
     * 获取月色之湖大型多人在线角色扮演服务器
     *
     * @return Server
     */
    Server getServer();

    /**
     * 获取插件数据目录
     *
     * @return 目录
     */
    File getDataFolder();

    /**
     * 获取月色之湖大型多人在线角色扮演玩家账户管理
     *
     * @return 玩家账户管理
     */
    PlayerAccount getAccount();

    /**
     * 获取月色之湖大型多人在线角色扮演灵魂系统
     *
     * @return 灵魂系统
     */
    Soul getSoulSystem();

    /**
     * 获取月色之湖大型多人在线角色扮演配置文件对象
     *
     * @return 配置文件对象
     */
    FileConfiguration getConfig();

    /**
     * 获取月色之湖大型多人在线角色扮演配置文件对象
     *
     * @return 配置文件对象
     */
    MMORPGConfig getMConfig();

    /**
     * 获取指定语言文件的指定 键 的值
     *
     * @param key 键
     * @return 值
     */
    String l18n(String key);

    /**
     * 获取指定语言文件的指定 键 的值
     *
     * @param key 键
     * @param args 参数
     * @return 值
     */
    String l18n(String key, Object... args);

    /**
     * 将插件内的资源文件保存到外部路径
     *
     * @param url 路径
     * @param replace 是否替换
     */
    void saveResource(String url, boolean replace);

    /**
     * 获取插件内的资源文件并转换为输入流
     *
     * @param url 路径
     * @return 输入流
     */
    InputStream getResource(String url);

    /**
     * 获取插件的类加载器实例对象
     *
     * @return 类加载器
     */
    ClassLoader getClassLoader$();

    /**
     * 获取月色之湖大型多人在线角色扮演插件管理对象
     *
     * @return 插件管理对象
     */
    PluginManager getPluginManager();

    /**
     * 获取掉落方块事件监听者对象
     *
     * @return 监听者对象
     */
    FallingBlockListener getFallingBlockListener();
    
    /**
     * 获取此插件是否为 Debug 调试模式
     * 
     * @return true 则是 else 不是
     */
    boolean isDebug();
}
