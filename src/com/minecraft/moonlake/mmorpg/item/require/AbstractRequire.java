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
 
  
package com.minecraft.moonlake.mmorpg.item.require;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/5/14.
 */
public abstract class AbstractRequire implements ItemRequire {

    public AbstractRequire() {

    }

    /**
     * 获取月色之湖 MMORPG 主类实例对象
     *
     * @return MMORPG
     */
    public MMORPG getInstance() {

        return MMORPGPlugin.getInstances();
    }

    /**
     * 获取物品特殊需求属性类型
     *
     * @return 属性类型
     */
    public abstract RequireType getType();

    /**
     * 获取物品特殊需求属性类型值
     *
     * @return 属性类型值
     */
    public abstract String getRequireValue();

    /**
     * 获取属性的文本标签
     *
     * @return 标签
     */
    public String getLore() {

        return StringUtil.format("{0}: &a{1}", getType().getName(), getRequireValue());
    }

    /**
     * 设置物品栈的特殊需求属性
     *
     * @param item 物品栈
     */
    public void setItemRequire(ItemStack item) {

        getInstance().getMoonLake().getLorelib().addLore(item, getLore());
    }
}
