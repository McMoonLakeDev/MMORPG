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

import com.minecraft.moonlake.mmorpg.item.base.AbstractBaseItem;
import com.minecraft.moonlake.mmorpg.item.base.BaseType;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/5/14.
 */
public abstract class AbstractAttackDamage extends AbstractBaseItem implements ItemAttackDamage {

    private final ItemAttackDamageType type;
    private final static BaseType baseType = BaseType.ATTACK_DAMAGE;

    public AbstractAttackDamage() {

        this(ItemAttackDamageType.NORMAL_DAMAGE);
    }

    public AbstractAttackDamage(ItemAttackDamageType type) {

        super(baseType);

        this.type = type;
    }

    /**
     * 获取攻击伤害
     *
     * @param <T> 伤害值
     * @return 伤害值
     */
    public abstract <T extends Number> T getDamage();

    /**
     * 设置攻击伤害
     *
     * @param damage 伤害值
     * @param <T> 伤害值
     */
    public abstract <T extends Number> void setDamage(T damage);

    /**
     * 设置物品栈的攻击伤害属性
     *
     * @param item 物品栈
     */
    public void setItemAttackDamage(ItemStack item) {

        getInstance().getMoonLake().getLorelib().addLore(item, getLore());
    }

    /**
     * 获取攻击伤害标签
     *
     * @return 标签
     */
    public String getLore() {

        return StringUtil.format("{0}: &a{1}", baseType.getName(), format());
    }

    /**
     * 格式化获取攻击伤害值的字符串
     *
     * @return 字符串值
     */
    protected final String format() {

        String format = "";

        if(this instanceof NormalAttackDamage) {

            format = String.valueOf(getDamage()) + ItemAttackDamageType.NORMAL_DAMAGE.getSuffix();
        }
        else if(this instanceof ScopeAttackDamage) {

            ScopeAttackDamage damage = (ScopeAttackDamage)this;
            format = String.valueOf(damage.getMin()) + ItemAttackDamageType.SCOPE_DAMAGE.getSuffix() + String.valueOf(damage.getMax());
        }
        else {

            format = "0" + ItemAttackDamageType.NORMAL_DAMAGE.getSuffix();
        }
        return getDamage().doubleValue() > 0.0d ? "+" + format : "-" + format;
    }

    /**
     * 物品伤害值类型
     *
     * @return 伤害值类型
     */
    public ItemAttackDamageType getType() {

        return type;
    }
}
