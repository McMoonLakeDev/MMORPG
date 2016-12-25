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

/**
 * Created by MoonLake on 2016/6/2.
 */
public abstract class AbstractPassive implements Passive {

    private final String name;
    private int level;

    public AbstractPassive(String name) {

        this.name = name;
    }

    /**
     * 获取被动技能的名称
     *
     * @return 名称
     */
    public String getName() {

        return name;
    }

    /**
     * 获取被动技能的等级
     *
     * @return 等级
     */
    public int getLevel() {

        return level;
    }

    /**
     * 设置被动技能的等级
     *
     * @param level 等级
     */
    public void setLevel(int level) {

        this.level = level;
    }
}
