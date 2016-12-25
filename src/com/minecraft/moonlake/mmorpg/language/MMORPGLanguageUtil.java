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
 
  
package com.minecraft.moonlake.mmorpg.language;

import com.minecraft.moonlake.manager.IoManager;
import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.language.MMORPGLanguage;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import com.minecraft.moonlake.mmorpg.util.StringUtil;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/5/24.
 */
public class MMORPGLanguageUtil implements MMORPGLanguage {

    private final MMORPG main;
    private final String folder = "/language";

    private Map<String, String> kvMap;

    public MMORPGLanguageUtil(MMORPG main) {

        this.main = main;
        this.initFolder();
        this.kvMap = new HashMap<>();
    }

    private void initFolder() {

        File languange = new File(main.getDataFolder(), folder);
        if(!languange.exists())
            languange.mkdir();

        File defaultLocal = new File(main.getDataFolder(), folder + "/zh_CN.lang");
        if(!defaultLocal.exists()) {

            InputStream is = main.getResource("zh_CN.lang");
            IoManager.outInputStream(defaultLocal, is);
        }
    }

    /**
     * 加载语言文件消息文本
     */
    public void loadLanguage() {

        kvMap.clear();

        String setting = ConfigManager.get("Language").asString();
        File language = new File(main.getDataFolder(), folder + "/" + setting + ".lang");

        if(!language.exists()) {

            main.log("加载语言文件 '" + setting + "' 时异常: 未存在此文件.");
            language = new File(main.getDataFolder(), folder + "/zh_CN.lang");
            setting = "zh_CN";
        }
        kvMap = IoManager.readLangFile(ConfigManager.get("Prefix").asString(), language);
        main.log("成功载入 '" + setting + "' 语言文件文本消息.");
    }


    /**
     * 加载月色之湖大型多人在线角色扮演语言文件
     */
    @Override
    @Deprecated
    public void loadMMORPGLanguage() {

        loadLanguage();
    }

    /**
     * 获取指定语言文件的指定 键 的值
     *
     * @param key 键
     * @return 值
     */
    @Override
    public String l18n(String key) {

        return kvMap.containsKey(key) ? kvMap.get(key) : "";
    }

    /**
     * 给此玩家发送语言文件指定 键 的值
     *
     * @param key  键
     * @param args 参数
     */
    @Override
    public String l18n(String key, Object... args) {

        return StringUtil.format(l18n(key), args);
    }

    private void checkVersion(String version) {
    }
}
