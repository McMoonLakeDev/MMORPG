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
 
  
package com.minecraft.moonlake.mmorpg.item.require.excel;

import com.minecraft.moonlake.mmorpg.item.require.AbstractRequire;
import com.minecraft.moonlake.mmorpg.item.require.RequireType;
import com.minecraft.moonlake.mmorpg.util.StringUtil;

/**
 * 物品特殊需求属性: 是否不可交易
 * Created by MoonLake on 2016/5/14.
 */
public class NotTrade extends AbstractRequire {

    private boolean flag;

    /**
     * 物品特殊需求属性: 是否不可交易
     */
    public NotTrade() {

        this(true);
    }

    /**
     * 物品特殊需求属性: 是否不可交易
     *
     * @param flag true 不可交易 else 可交易
     */
    public NotTrade(boolean flag) {

        this.flag = flag;
    }

    /**
     * 获取是否不可交易属性的 Flag
     *
     * @return true 则不可交易 else 可交易
     */
    public boolean isFlag() {

        return flag;
    }

    /**
     * 设置是否不可交易属性的 Flag
     *
     * @param flag true 则不可交易 else 可交易
     */
    public void setFlag(boolean flag) {

        this.flag = flag;
    }

    /**
     * 获取物品特殊需求属性类型
     *
     * @return 属性类型
     */
    @Override
    public RequireType getType() {

        return RequireType.NOT_TRADE;
    }

    /**
     * 获取物品特殊需求属性类型值
     *
     * @return 属性类型值
     */
    @Override
    public String getRequireValue() {

        return StringUtil.yesOrNo(isFlag());
    }
}
