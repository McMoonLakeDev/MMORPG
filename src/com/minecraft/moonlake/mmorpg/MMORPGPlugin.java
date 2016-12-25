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
 
  
package com.minecraft.moonlake.mmorpg;

import com.minecraft.moonlake.MoonLakePlugin;
import com.minecraft.moonlake.api.MoonLake;
import com.minecraft.moonlake.gui.GUIPlugin;
import com.minecraft.moonlake.gui.api.MoonLakeGUIManager;
import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.account.PlayerAccount;
import com.minecraft.moonlake.mmorpg.api.config.MMORPGConfig;
import com.minecraft.moonlake.mmorpg.api.gui.GUIManager;
import com.minecraft.moonlake.mmorpg.api.language.MMORPGLanguage;
import com.minecraft.moonlake.mmorpg.api.mob.MobManager;
import com.minecraft.moonlake.mmorpg.api.region.RegionManager;
import com.minecraft.moonlake.mmorpg.api.role.MMORPGRole;
import com.minecraft.moonlake.mmorpg.api.soul.Soul;
import com.minecraft.moonlake.mmorpg.api.sql.MMORPGSql;
import com.minecraft.moonlake.mmorpg.commands.MMORPGCommandManager;
import com.minecraft.moonlake.mmorpg.commands.MMORPGCommands;
import com.minecraft.moonlake.mmorpg.config.MMORPGConfigUtil;
import com.minecraft.moonlake.mmorpg.effect.EffectManager;
import com.minecraft.moonlake.mmorpg.hotbar.HotBarItem;
import com.minecraft.moonlake.mmorpg.item.Item;
import com.minecraft.moonlake.mmorpg.language.MMORPGLanguageUtil;
import com.minecraft.moonlake.mmorpg.listeners.block.FallingBlockListener;
import com.minecraft.moonlake.mmorpg.listeners.mob.MobClearListener;
import com.minecraft.moonlake.mmorpg.listeners.mob.MobDeathListener;
import com.minecraft.moonlake.mmorpg.listeners.player.*;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import com.minecraft.moonlake.mmorpg.manager.DataManager;
import com.minecraft.moonlake.mmorpg.manager.EntityManager;
import com.minecraft.moonlake.mmorpg.recipe.MMORPGRecipe;
import com.minecraft.moonlake.mmorpg.resources.PlayerLevel;
import com.minecraft.moonlake.mmorpg.setting.MMORPGSetting;
import com.minecraft.moonlake.mmorpg.sql.DataSource;
import com.minecraft.moonlake.mmorpg.task.MMORPGTaskManager;
import com.minecraft.moonlake.mmorpg.task.player.PlayerTaskManager;
import com.minecraft.moonlake.mmorpg.util.AccountUtil;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import com.minecraft.moonlake.mmorpg.util.item.ItemUtil;
import com.minecraft.moonlake.mmorpg.util.role.RoleUtil;
import com.minecraft.moonlake.mmorpg.util.soul.SoulUtil;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * 月色之湖大型角色扮演服务器 MMORPG 项目
 * Created by MoonLake on 2016/5/14.
 */
public class MMORPGPlugin extends JavaPlugin implements MMORPG {

    private Soul mmorpgSoul;
    private Item mmorpgitem;
    private MoonLake moonLake;
    private MMORPGSql mmorpgData;
    private MMORPGRole mmorpgRole;
    private MMORPGConfig mmorpgConfig;
    private PlayerAccount playerAccount;
    private MMORPGLanguage mmorpgLanguage;
    private MMORPGTaskManager mmorpgTaskManager;
    private MMORPGCommandManager mmorpgCommandManager;

    private FallingBlockListener fallingBlockListener;

    private ClassLoader classLoader;
    private PluginManager pluginManager;
    private WorldEditPlugin worldEditPlugin;
    private MoonLakeGUIManager moonLakeGUIManager;
    private final ConsoleCommandSender console;
    private final PluginDescriptionFile description;

    private static MMORPG MAIN;
    private static boolean DEBUG;

    public static void main(String[] args) {


    }

    static {

    }

    public MMORPGPlugin() {

        this.description = this.getDescription();
        this.console = this.getServer().getConsoleSender();
    }

    @Override
    public void onEnable() {

        MAIN = this;

        setupDepend();

        classLoader = getClassLoader();
        pluginManager = getServer().getPluginManager();

        initFolder();

        MMORPGSetting.initServerSetting();

        mmorpgConfig = new MMORPGConfigUtil(getInstance());

        initDebugMode();

        mmorpgLanguage = new MMORPGLanguageUtil(getInstance());
        mmorpgLanguage.loadMMORPGLanguage();

        mmorpgSoul = new SoulUtil();
        mmorpgitem = new ItemUtil(getInstance());
        mmorpgData = new DataSource(getInstance());
        mmorpgRole = new RoleUtil(getInstance());
        playerAccount = new AccountUtil(getInstance());

        mmorpgTaskManager = new MMORPGTaskManager(getInstance());
        mmorpgCommandManager = new MMORPGCommandManager(getInstance());

        HotBarItem.initHotBar();
        RegionManager.loadRegion();
        MMORPGRecipe.addDefaultRecipe();
        MMORPGSetting.applyServerSetting();

        fallingBlockListener = new FallingBlockListener(getInstance());

        pluginManager.registerEvents(new ClickComboListener(getInstance()), this);
        pluginManager.registerEvents(new PlayerChatListener(getInstance()), this);
        pluginManager.registerEvents(new PlayerBaseListener(getInstance()), this);
        pluginManager.registerEvents(new PlayerMountListener(getInstance()), this);
        pluginManager.registerEvents(new PlayerPotionListener(getInstance()), this);
        pluginManager.registerEvents(new PlayerRegionListener(getInstance()), this);
        pluginManager.registerEvents(new PlayerHotbarListener(getInstance()), this);
        pluginManager.registerEvents(new PlayerTpBookListener(getInstance()), this);
        pluginManager.registerEvents(new PlayerCurrencyListener(getInstance()), this);
        pluginManager.registerEvents(new PlayerRepertoryListener(getInstance()), this);
        pluginManager.registerEvents(new PlayerSkillPointListener(getInstance()), this);
        pluginManager.registerEvents(new MobDeathListener(getInstance()), this);
        pluginManager.registerEvents(new MobClearListener(getInstance()), this);

        pluginManager.registerEvents(fallingBlockListener, this);

        this.log("月色之湖大型多人在线角色扮演 MLMMORPG 插件 v" + getPluginVersion() + " 成功加载.");
    }

    @Override
    public void onDisable() {

        mmorpgTaskManager.close();
        HotBarItem.close();
        MobManager.close();
        GUIManager.close();
        PlayerLevel.close();
        EffectManager.close();
        EntityManager.close();
        RegionManager.close();
        PlayerTaskManager.close();
        DataManager.savePlayersData();
    }

    /**
     * 加载插件前置需求
     */
    private void setupDepend() {

        if(!this.setupMoonLake()) {

            this.log("前置月色之湖核心API插件加载失败.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if(!this.setupMoonLakeGUI()) {

            this.log("前置月色之湖 GUI 插件加载失败.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if(!this.setupWorldEdit()) {

            this.log("前置创世神 WorldEdit 插件加载失败.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    /**
     * 初始化插件的 Debug 调试模式
     */
    private void initDebugMode() {
    	
    	DEBUG = ConfigManager.get("Debug").asBoolean();
    	
    	if(DEBUG) {
    		
    		log("已启动插件 Debug 调试模式.");
    	}
    }
    
    /**
     * 初始化插件本地数据目录
     */
    private void initFolder() {

        if(!getDataFolder().exists())
            getDataFolder().mkdir();
        File config = new File(getDataFolder(), "config.yml");
        if(!config.exists())
            saveDefaultConfig();
    }

    /**
     * 加载月色之湖前置核心 API 插件
     *
     * @return 是否加载成功
     */
    private boolean setupMoonLake() {

        Plugin plugin = this.getServer().getPluginManager().getPlugin("MoonLake");
        return plugin != null && plugin instanceof MoonLakePlugin && (this.moonLake = ((MoonLakePlugin)plugin).getInstance()) != null;
    }

    /**
     * 加载月色之湖前置 GUI 插件
     *
     * @return 是否加载成功
     */
    private boolean setupMoonLakeGUI() {

        Plugin plugin = this.getServer().getPluginManager().getPlugin("MoonLakeGUI");
        return plugin != null && plugin instanceof GUIPlugin && (this.moonLakeGUIManager = ((GUIPlugin)plugin).getManager()) != null;
    }

    /**
     * 加载前置 WorldEdit 创世神插件
     *
     * @return 是否加载成功
     */
    private boolean setupWorldEdit() {

        Plugin plugin = this.getServer().getPluginManager().getPlugin("WorldEdit");
        return plugin != null && plugin instanceof WorldEditPlugin && (this.worldEditPlugin = (WorldEditPlugin)plugin) != null;
    }

    /**
     * 获取月色之湖 MMORPG 实例对象
     *
     * @return 实例
     */
    public MMORPG getInstance() {

        return MAIN;
    }

    /**
     * 获取月色之湖 MMORPG 实例对象
     *
     * @return 实例
     */
    @Deprecated
    public static MMORPG getInstances() {

        return MAIN;
    }

    /**
     * 获取插件的主类对象
     *
     * @return 主类
     */
    @Override
    public MMORPGPlugin getMain() {

        return this;
    }

    /**
     * 获取月色之湖前置核心 API 插件实例对象
     *
     * @return MoonLake
     */
    @Override
    public MoonLake getMoonLake() {

        return moonLake;
    }

    /**
     * 获取月色之湖前置 GUI 插件管理实例对象
     *
     * @return MoonLakeGUIManager
     */
    @Override
    public MoonLakeGUIManager getMoonLakeGUIManager() {

        return moonLakeGUIManager;
    }

    /**
     * 获取前置创世神 WorldEdit 插件实例对象
     *
     * @return WorldEdit
     */
    @Override
    public WorldEditPlugin getWorldEdit() {

        return worldEditPlugin;
    }

    /**
     * 获取月色之湖大型多人在线角色扮演游戏数据库对象
     *
     * @return MMORPGSql
     */
    @Override
    public MMORPGSql getData() {

        return mmorpgData;
    }

    /**
     * 获取月色之湖大型多人在线角色扮演角色对象
     *
     * @return MMORPGRole
     */
    @Override
    public MMORPGRole getRole() {

        return mmorpgRole;
    }

    /**
     * 获取月色之湖大型多人在线角色扮演游戏物品对象
     *
     * @return Item
     */
    @Override
    public Item getItem() {

        return mmorpgitem;
    }

    /**
     * 给控制台输出日志信息
     *
     * @param msg 日志
     */
    @Override
    public void log(String msg) {

        this.console.sendMessage("[MoonLakeMMORPG] " + StringUtil.toColor(msg));
    }

    /**
     * 获取月色之湖大型多人在线角色扮演玩家账户管理
     *
     * @return 玩家账户管理
     */
    @Override
    public PlayerAccount getAccount() {

        return playerAccount;
    }

    /**
     * 获取月色之湖大型多人在线角色扮演灵魂系统
     *
     * @return 灵魂系统
     */
    @Override
    public Soul getSoulSystem() {

        return mmorpgSoul;
    }

    /**
     * 获取月色之湖大型多人在线角色扮演配置文件对象
     *
     * @return 配置文件对象
     */
    @Override
    public MMORPGConfig getMConfig() {

        return mmorpgConfig;
    }

    /**
     * 获取指定语言文件的指定 键 的值
     *
     * @param key 键
     * @return 值
     */
    @Override
    public String l18n(String key) {

        return mmorpgLanguage.l18n(key);
    }

    /**
     * 获取指定语言文件的指定 键 的值
     *
     * @param key  键
     * @param args 参数
     * @return 值
     */
    @Override
    public String l18n(String key, Object... args) {

        return mmorpgLanguage.l18n(key, args);
    }

    /**
     * 获取插件的类加载器实例对象
     *
     * @return 类加载器
     */
    @Override
    public ClassLoader getClassLoader$() {

        return classLoader;
    }

    /**
     * 获取月色之湖大型多人在线角色扮演插件管理对象
     *
     * @return 插件管理对象
     */
    @Override
    public PluginManager getPluginManager() {

        return pluginManager;
    }

    /**
     * 获取掉落方块事件监听者对象
     *
     * @return 监听者对象
     */
    @Override
    public FallingBlockListener getFallingBlockListener() {

        return fallingBlockListener;
    }

    /**
     * 获取此插件是否为 Debug 调试模式
     * 
     * @return true 则是 else 不是
     */
	@Override
	public boolean isDebug() {
		
		return DEBUG;
	}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String cmd = command.getName().toLowerCase();

        try {

            Class<?> clazz = getClassLoader$().loadClass("com.minecraft.moonlake.mmorpg.commands.Command" + cmd);
            mmorpgCommandManager.execute((Class<? extends MMORPGCommands>)clazz, cmd, sender, args);
        }
        catch (Exception e) {

        	if(isDebug()) {
				
				e.printStackTrace();
			}
        }
        return true;
    }

    /**
     * 获取插件的称号
     *
     * @return Prefix
     */
    @Override
    public String getPluginPrefix() {

        return description.getPrefix();
    }

    /**
     * 获取插件的名字
     *
     * @return Name
     */
    @Override
    public String getPluginName() {

        return description.getName();
    }

    /**
     * 获取插件的主类
     *
     * @return MainClass
     */
    @Override
    public String getPluginMain() {

        return description.getMain();
    }

    /**
     * 获取插件的版本
     *
     * @return Version
     */
    @Override
    public String getPluginVersion() {

        return description.getVersion();
    }

    /**
     * 获取插件的网站
     *
     * @return Website
     */
    @Override
    public String getPluginWebsite() {

        return description.getWebsite();
    }

    /**
     * 获取插件的简介
     *
     * @return Description
     */
    @Override
    public String getPluginDescription() {

        return description.getDescription();
    }

    /**
     * 获取插件的作者
     *
     * @return Auther
     */
    @Override
    public Set<String> getPluginAuthers() {

        return new HashSet<String>(description.getAuthors());
    }

    /**
     * 获取插件的依赖
     *
     * @return Depend
     */
    @Override
    public Set<String> getPluginDepends() {

        return new HashSet<String>(description.getDepend());
    }

    /**
     * 获取插件的软依赖
     *
     * @return SoftDepend
     */
    @Override
    public Set<String> getPluginSoftDepends() {

        return new HashSet<String>(description.getSoftDepend());
    }
}
