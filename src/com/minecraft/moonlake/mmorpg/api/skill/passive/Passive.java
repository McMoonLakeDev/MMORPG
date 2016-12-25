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
 
  
package com.minecraft.moonlake.mmorpg.api.skill.passive;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;

/**
 * Created by MoonLake on 2016/6/2.
 */
public interface Passive {

    /**
     * 获取被动技能的名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 获取被动技能的等级
     *
     * @return 等级
     */
    int getLevel();

    /**
     * 设置被动技能的等级
     *
     * @param level 等级
     */
    void setLevel(int level);

    /**
     * 获取被动技能对象实例从类名
     */
    class fromName {

        /**
         * 从被动技能类名获取到被动技能的实例对象
         *
         * @param name 类名
         * @return 被动技能对象实例 异常返回 null
         */
        public static Passive a(String name) {

            Passive obj = null;

            try {

                obj = (Passive) MMORPGPlugin.getInstances().getClassLoader$().loadClass("com.minecraft.moonlake.mmorpg.api.skill.passive.type." + name).newInstance();
            }
            catch (Exception e) {

                MMORPGPlugin.getInstances().log("实例化技能类对象时异常: " + e.getMessage());
            }
            return obj;
        }
    }
}
