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
 
  
package com.minecraft.moonlake.mmorpg.api.item.currency;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.minecraft.moonlake.mmorpg.api.item.ItemInnStack;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;

/**
 * Created by MoonLake on 2016/6/12.
 */
public abstract class AbstractCurrency extends ItemInnStack implements Currency {

    private final CurrencyType type;

    public AbstractCurrency(CurrencyType type) {

        this(type, 1);
    }

    public AbstractCurrency(CurrencyType type, int amount) {

        super(type.getMaterial(), type.getData(), amount, type.getName(), CurrencyManager.CURRENCY_LORE);

        this.type = type;
    }

    /**
     * 获取货币的类型
     *
     * @return 类型
     */
    public CurrencyType getType() {

        return type;
    }
    
    /**
     * 获取货币物品栈类型是否为方块
     * 
     * @return true 则是方块 else 不是
     */
    public boolean isBlock() {
    	
    	return getType().getMaterial().isBlock();
    }
    
    /**
     * 获取 Bukkit 的物品栈对象
     *
     * @return 物品栈对象
     */
    @Override
    public ItemStack getItem() {

        ItemStack currency = ItemManager.setTagValue(super.getItem(), "CanDestroy", "Currency:" + getType().getType());
        currency = getInstance().getMoonLake().getItemlib().addFlags(currency, ItemFlag.HIDE_DESTROYS);
        
        return currency;
    }

    /**
     * 复制 ItemInn 物品栈对象
     *
     * @return 复制的对象
     */
    @Override
    public Currency clone() {

        Currency currency = CurrencyManager.createCurrency(type, getAmount());

        return currency;
    }
}
