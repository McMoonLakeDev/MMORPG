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
 
  
package com.minecraft.moonlake.mmorpg.api.skill.reqatt;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.skill.reqatt.type.SkillReqAttDepend;
import com.minecraft.moonlake.mmorpg.api.skill.reqatt.type.SkillReqAttLevel;
import com.minecraft.moonlake.reflect.Reflect;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/7/7.
 */
public enum SkillReqAttType {

    /**
     * 技能需求属性类型: 依赖
     */
    DEPEND("Depend", SkillReqAttDepend.class),
    /**
     * 技能需求属性类型: 等级
     */
    LEVEL("Level", SkillReqAttLevel.class),
    ;

    private String type;
    private Class<? extends SkillReqAtt> clazz;
    private final static Map<String, SkillReqAttType> NAME_MAP;

    static {

        NAME_MAP = new HashMap<>();

        for(SkillReqAttType skillReqAttType : values()) {

            NAME_MAP.put(skillReqAttType.type.toLowerCase(), skillReqAttType);
        }
    }

    SkillReqAttType(String type, Class<? extends SkillReqAtt> clazz) {

        this.type = type;
        this.clazz = clazz;
    }

    public String getType() {

        return type;
    }

    public Class<? extends SkillReqAtt> getClazz() {

        return clazz;
    }

    public <T extends SkillReqAtt> T newInstance(Object... argsObject) {

        T t = null;

        try {

            t = (T) Reflect.instantiateObject(getClazz(), argsObject);
        }
        catch (Exception e) {

            MMORPGPlugin.getInstances().log("获取技能属性需求实例时异常: " + e.getMessage());

            if(MMORPGPlugin.getInstances().isDebug()) {

                e.printStackTrace();
            }
        }
        return t;
    }

    public static SkillReqAttType fromType(String type) {

        return NAME_MAP.get(type.toLowerCase());
    }
}
