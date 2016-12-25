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
 
  
package com.minecraft.moonlake.mmorpg.item.base.attackspeed;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.reflect.Reflect;

/**
 * Created by MoonLake on 2016/5/15.
 */
public enum ItemAttackSpeedType {

    NORMAL_SPEED("NormalSpeed", "&7普通攻击速度", 1.5d, NormalAttackSpeed.class),
    FAST_SPEED("FastSpeed", "&7快速攻击速度", 2.0d, FastAttackSpeed.class),
    SLOW_SPEED("SlowSpeed", "&7缓慢攻击速度", 0.8d, SlowAttackSpeed.class),
    ;

    private String type;
    private String name;
    private double count;
    private Class<? extends AbstractAttackSpeed> clazz;

    ItemAttackSpeedType(String type, String name, double count, Class<? extends AbstractAttackSpeed> clazz) {

        this.type = type;
        this.name = name;
        this.count = count;
        this.clazz = clazz;
    }

    /**
     * 获取攻击速度值类型名
     *
     * @return 类型名
     */
    public String getType() {

        return type;
    }

    /**
     * 获取攻击速度值中文名
     *
     * @return 中文名
     */
    public String getName() {

        return name;
    }

    /**
     * 获取攻击速度的数量值
     *
     * @return 数量值
     */
    public double getCount() {

        return -(4.0d - count);
    }

    /**
     * 获取攻击速度值的子类
     *
     * @return 子类
     */
    public Class<? extends AbstractAttackSpeed> getClazz() {

        return clazz;
    }

    /**
     * 获取类型的抽象类实例对象
     *
     * @param argsObject 参数
     * @return 实例对象
     */
    public <T extends ItemAttackSpeed> T newInstance(Object... argsObject) {

        T attackSpeed = null;

        try {

            attackSpeed = (T) Reflect.instantiateObject(getClazz(), argsObject);
    }
        catch (Exception e) {

            MMORPGPlugin.getInstances().log("实例化物品特殊攻击属性时异常: " + e.getMessage());

            if(MMORPGPlugin.getInstances().isDebug()) {

                e.printStackTrace();
            }
        }
        return attackSpeed;
    }
}
