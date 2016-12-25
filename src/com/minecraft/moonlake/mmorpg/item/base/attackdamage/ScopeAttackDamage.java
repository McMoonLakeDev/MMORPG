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

import com.minecraft.moonlake.mmorpg.manager.RandomManager;

/**
 * 物品基础属性: 范围攻击伤害
 * Created by MoonLake on 2016/5/14.
 */
public class ScopeAttackDamage extends AbstractAttackDamage {

    private int min;
    private int max;

    /**
     * 物品基础属性: 范围攻击伤害
     */
    public ScopeAttackDamage() {

        this(0, 0);
    }

    /**
     * 物品基础属性: 范围攻击伤害
     * 
     * @param min 最低伤害
     * @param max 最高伤害
     */
    public ScopeAttackDamage(int min, int max) {

        super(ItemAttackDamageType.SCOPE_DAMAGE);

        this.min = min;
        this.max = max;
    }

    /**
     * 获取范围攻击伤害的最低伤害
     * 
     * @return 最低伤害
     */
    public int getMin() {

        return min;
    }

    /**
     * 获取范围攻击伤害的最高伤害
     *
     * @return 最高伤害
     */
    public int getMax() {

        return max;
    }

    /**
     * 设置范围攻击的最低伤害
     * 
     * @param min 最低伤害
     */
    public void setMin(int min) {

        this.min = min;
    }

    /**
     * 设置范围攻击的最高伤害
     *
     * @param max 最高伤害
     */
    public void setMax(int max) {

        this.max = max;
    }


    /**
     * 获取范围攻击的随机攻击伤害
     *
     * @return 伤害值
     */
    @Override
    public <T extends Number> T getDamage() {

        return (T)Integer.valueOf(RandomManager.getRandom().nextInt(getMax()) + getMin());
    }

    /**
     * ScopeAttackDamage cannot use this method. please use {@link ScopeAttackDamage} the setScopeDamage(int, int)
     *
     * @param damage null
     * @param <T> null
     */
    @Deprecated
    @Override
    public <T extends Number> void setDamage(T damage) {


    }

    /**
     * 设置范围攻击的伤害值
     *
     * @param min 最低伤害
     * @param max 最高伤害
     */
    public void setScopeDamage(int min, int max) {

        this.min = min;
        this.max = max;
    }
}
