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
 
  
package com.minecraft.moonlake.mmorpg.item.base.defense;

import com.minecraft.moonlake.api.itemlib.Itemlib;
import com.minecraft.moonlake.mmorpg.item.base.AbstractBaseItem;
import com.minecraft.moonlake.mmorpg.item.base.BaseType;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/5/14.
 */
public abstract class AbstractDefense extends AbstractBaseItem implements ItemDefense {

    private final ItemDefenseType type;
    private final static BaseType baseType = BaseType.DEFENSE;
    private int defense;
    private Itemlib.AttributeType.Slot slot;

    public AbstractDefense(int defense, ItemDefenseType type) {

        this(defense, type, null);
    }

    public AbstractDefense(int defense, ItemDefenseType type, Itemlib.AttributeType.Slot slot) {

        super(baseType);

        this.type = type;
        this.slot = slot;
        this.defense = defense;
    }

    /**
     * 获取防御力
     *
     * @return 防御力
     */
    public int getDefense() {

        return defense;
    }

    /**
     * 设置防御力
     *
     * @param defense 防御力
     */
    public void setDefense(int defense) {

        this.defense = defense;
    }

    /**
     * 设置物品栈的攻击伤害属性
     *
     * @param item 物品栈
     */
    public void setItemDefense(ItemStack item) {

        getInstance().getMoonLake().getLorelib().addLore(item, getLore());
    }

    /**
     * 获取属性的文本标签
     *
     * @return 标签
     */
    public String getLore() {

        return StringUtil.format("{0}: &a{1}", baseType.getName(), format());
    }

    /**
     * 格式化获取攻击防御值的字符串
     *
     * @return 字符串值
     */
    protected final String format() {

        String format = "";

        if(this instanceof NormalDefense) {

            format = String.valueOf(getDefense()) + getType().getSuffix();
        }
        else {

            format = "0" + getType().getSuffix();
        }
        return getDefense() > 0 ? "+" + format : "-" + format;
    }

    /**
     * 物品防御值类型
     *
     * @return 防御值类型
     */
    public ItemDefenseType getType() {

        return type;
    }

    /**
     * 获取物品防御对应的槽位
     *
     * @return 槽位
     */
    public Itemlib.AttributeType.Slot getSlot() {

        return slot;
    }

    /**
     * 设置物品防御对应的槽位
     *
     * @param slot 槽位
     */
    public void setSlot(Itemlib.AttributeType.Slot slot) {

        this.slot = slot;
    }
}
