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
 
  
package com.minecraft.moonlake.mmorpg.api.skill.combo;

/**
 * Created by MoonLake on 2016/5/16.
 */
public enum SkillComboType {

    /**
     * 技能组合类型: 左
     */
    LEFT(1, "Left", "左"),
    /**
     * 技能组合类型: 右
     */
    RIGHT(2, "Right", "右"),
    /**
     * 技能组合类型: 潜行
     */
    SHIFT(3, "Shift", "潜行"),
    /**
     * 技能组合类型: F
     */
    F(4, "F", "F"),
    ;

    private int id;
    private String type;
    private String name;
    private final static SkillComboType[] COMBOS;

    static {

        COMBOS = new SkillComboType[] { null, LEFT, RIGHT, SHIFT, F };
    }

    SkillComboType(int id, String type, String name) {

        this.id = id;
        this.type = type;
        this.name = name;
    }

    /**
     * 获取技能组合 ID
     *
     * @return ID
     */
    public int getId() {

        return id;
    }

    /**
     * 获取技能组合类型名
     *
     * @return 类型名
     */
    public String getType() {

        return type;
    }

    /**
     * 获取技能组合类型中文名
     *
     * @return 中文名
     */
    public String getName() {

        return name;
    }

    public static SkillComboType getById(int id) {

        if (id < 0 || id >= COMBOS.length) {

            return null;
        }
        return COMBOS[id];
    }
}
