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
 
  
package com.minecraft.moonlake.mmorpg.setting;

import com.minecraft.moonlake.manager.IoManager;
import com.minecraft.moonlake.mmorpg.api.config.yaml.YamlManager;
import com.minecraft.moonlake.mmorpg.manager.MMORPGManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * Created by MoonLake on 2016/6/28.
 */
public final class MMORPGSetting extends MMORPGManager {

    /**
     * 初始化服务器的设置文件
     */
    public static void initServerSetting() {

        File setting = new File(getMain().getDataFolder(), "setting.yml");
        if(!setting.exists()) {

            InputStream is = getMain().getResource("setting.yml");
            IoManager.outInputStream(setting, is);
        }
    }

    /**
     * 将服务器的设置应用到服务器
     */
    public static void applyServerSetting() {

        File file = new File(getMain().getDataFolder(), "setting.yml");
        YamlConfiguration setting = YamlManager.loadYamlConfiguration(file);

        appleServerRule(setting);
    }

    /**
     * 将服务器的规则设置应用到服务器
     */
    private static void appleServerRule(YamlConfiguration setting) {

        Map<String, String> ruleMap = new HashMap<>();
        List<World> worlds = getApplyWorls(setting.getBoolean("ServerRule.ApplyAllWorld"));
        Set<String> listSet = setting.getConfigurationSection("ServerRule.List").getKeys(false);

        if(worlds != null && worlds.size() > 0 && listSet != null && listSet.size() > 0) {

            getMain().log("&7##################################################");
            getMain().log("&7############### &e服务器世界规则设置&7 ###############");
            getMain().log("&7##################################################");

            for(String key : listSet) {

                String ruleValue = setting.getString("ServerRule.List." + key);
                ruleMap.put(key, ruleValue);

                getMain().log("&7##### &6规则: &2" + key + "&7 = &a" + ruleValue);
            }
            getMain().log("&7##################################################");

            if(ruleMap.size() > 0) {

                for(String rule : ruleMap.keySet()) {

                    for(World world : worlds) {

                        if(world != null) {

                            world.setGameRuleValue(rule, ruleMap.get(rule));
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取应用的世界对象集合
     *
     * @param flag 是否所有世界
     * @return 应用的世界对象集合
     */
    private static List<World> getApplyWorls(boolean flag) {

        List<World> worlds = new ArrayList<>();

        for(World world : Bukkit.getServer().getWorlds()) {

            if(!flag) {

                worlds.add(world);
                break;
            }
            worlds.add(world);
        }
        return worlds;
    }
}
