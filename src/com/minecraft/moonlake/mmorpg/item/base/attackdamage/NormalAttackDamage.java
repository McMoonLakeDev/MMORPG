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

/**
 * 物品基础属性: 普通攻击伤害
 * Created by MoonLake on 2016/5/14.
 */
public class NormalAttackDamage extends AbstractAttackDamage {

    private int damage;

    /**
     * 物品基础属性: 普通攻击伤害
     */
    public NormalAttackDamage() {

        this(0);
    }

    /**
     * 物品基础属性: 普通攻击伤害
     *
     * @param damage 普通伤害值
     */
    public NormalAttackDamage(int damage) {

        super(ItemAttackDamageType.NORMAL_DAMAGE);

        this.damage = damage;
    }

    /**
     * 获取攻击伤害
     *
     * @return 伤害值
     */
    @Override
    public <T extends Number> T getDamage() {

        return (T)Integer.valueOf(damage);
    }

    /**
     * 设置攻击伤害
     *
     * @param damage 伤害值
     */
    @Override
    public <T extends Number> void setDamage(T damage) {

        this.damage = (Integer)damage;
    }
}
