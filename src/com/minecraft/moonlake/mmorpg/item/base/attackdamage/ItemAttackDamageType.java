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
 
  
package com.minecraft.moonlake.mmorpg.item.base.attackdamage;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.reflect.Reflect;

/**
 * Created by MoonLake on 2016/5/14.
 */
public enum ItemAttackDamageType {

    /**
     * 伤害类型: 普通伤害 (物品的攻击伤害为固定值 例: 13)
     */
    NORMAL_DAMAGE("NormalAttackDamage", ' ', NormalAttackDamage.class),
    /**
     * 伤害类型: 范围伤害 (物品的攻击伤害为范围值 例: 10-100)
     */
    SCOPE_DAMAGE("ScopeAttackDamage", '~', ScopeAttackDamage.class),
    ;

    private String type;
    private char suffix;
    private Class<? extends AbstractAttackDamage> clazz;

    ItemAttackDamageType(String type, char suffix, Class<? extends AbstractAttackDamage> clazz) {

        this.type = type;
        this.suffix = suffix;
        this.clazz = clazz;
    }

    /**
     * 获取攻击伤害值类型名
     *
     * @return 类型名
     */
    public String getType() {

        return type;
    }

    /**
     * 获取攻击伤害值类型后缀符
     *
     * @return 后缀符
     */
    public char getSuffix() {

        return suffix;
    }

    /**
     * 获取攻击伤害值的子类
     *
     * @return 子类
     */
    public Class<? extends AbstractAttackDamage> getClazz() {

        return clazz;
    }

    /**
     * 获取类型的抽象类实例对象
     *
     * @param argsObject 参数
     * @return 实例对象
     */
    public <T extends ItemAttackDamage> T newInstance(Object... argsObject) {

        T attackDamage = null;

        try {

            attackDamage = (T) Reflect.instantiateObject(getClazz(), argsObject);
        }
        catch (Exception e) {

            MMORPGPlugin.getInstances().log("实例化物品特殊攻击属性时异常: " + e.getMessage());

            if(MMORPGPlugin.getInstances().isDebug()) {

                e.printStackTrace();
            }
        }
        return attackDamage;
    }
}
