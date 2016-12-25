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

import com.minecraft.moonlake.mmorpg.item.base.AbstractBaseItem;
import com.minecraft.moonlake.mmorpg.item.base.BaseType;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/5/15.
 */
public abstract class AbstractAttackSpeed extends AbstractBaseItem implements ItemAttackSpeed {

    private final ItemAttackSpeedType type;
    private final static BaseType baseType = BaseType.ATTACK_SPEED;

    public AbstractAttackSpeed() {

        this(ItemAttackSpeedType.NORMAL_SPEED);
    }

    public AbstractAttackSpeed(ItemAttackSpeedType type) {

        super(baseType);

        this.type = type;
    }

    /**
     * 获取攻击速度属性的类型
     *
     * @return 类型
     */
    public ItemAttackSpeedType getType() {

        return type;
    }

    /**
     * 设置物品栈的攻击速度属性
     *
     * @param item 物品栈
     */
    public void setItemAttackSpeed(ItemStack item) {

        getInstance().getMoonLake().getLorelib().addLore(item, StringUtil.format("{0}", format()));
    }

    /**
     * 格式化获取攻击速度值的字符串
     *
     * @return 字符串值
     */
    protected final String format() {

        String format = "";

        if(this instanceof NormalAttackSpeed) {

            format = ItemAttackSpeedType.NORMAL_SPEED.getName();
        }
        else if(this instanceof FastAttackSpeed) {

            format = ItemAttackSpeedType.FAST_SPEED.getName();
        }
        else if(this instanceof SlowAttackSpeed) {

            format = ItemAttackSpeedType.SLOW_SPEED.getName();
        }
        else {

            format = ItemAttackSpeedType.NORMAL_SPEED.getName();
        }
        return format;
    }
}
