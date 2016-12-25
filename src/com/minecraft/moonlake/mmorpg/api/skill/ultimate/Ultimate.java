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
 
  
package com.minecraft.moonlake.mmorpg.api.skill.ultimate;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.skill.Skill;

/**
 * Created by MoonLake on 2016/6/6.
 */
public interface Ultimate extends Skill {

    /**
     * 获取技能对象实例从类名
     */
    class fromName {

        /**
         * 从技能类名获取到技能大招的实例对象
         *
         * @param name 类名
         * @return 技能大招对象实例 异常返回 null
         */
        public static Ultimate a(String name) {

            Ultimate obj = null;

            try {

                obj = (Ultimate) MMORPGPlugin.getInstances().getClassLoader$().loadClass("com.minecraft.moonlake.mmorpg.api.skill.ultimate.type." + name).newInstance();
            }
            catch (Exception e) {

                MMORPGPlugin.getInstances().log("实例化技能大招类对象时异常: " + e.getMessage());
            }
            return obj;
        }
    }
}
