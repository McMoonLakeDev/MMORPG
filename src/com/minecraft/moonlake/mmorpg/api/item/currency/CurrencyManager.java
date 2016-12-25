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

import com.minecraft.moonlake.data.NBTTagData;
import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/12.
 */
public final class CurrencyManager extends ItemManager {

    public final static String[] CURRENCY_LORE;

    static {

        CURRENCY_LORE = new String[] {

                "&7月色大陆通用货币 &a✔ ",
                " ",
                "&6&o据说它由神秘的月神所造 ",
                "&6&o拥有不朽的奥秘, 坚不可摧 ",
                " "
        };
    }

    /**
     * 创建指定货币类型的货币对象
     *
     * @param type 货币类型
     * @return 货币对象 异常返回 null
     */
    public static Currency createCurrency(CurrencyType type) {

        return createCurrency(type, 1);
    }

    /**
     * 创建指定货币类型的货币对象
     *
     * @param type 货币类型
     * @param amount 货币数量 (1 - 64)
     * @return 货币对象 异常返回 null
     */
    public static Currency createCurrency(CurrencyType type, int amount) {

        Currency currency = null;

        try {

            Class<? extends Currency> clazz = type.getClazz();
            currency = (Currency) Reflect.instantiateObject(clazz, amount);
        }
        catch (Exception e) {

            MMORPGPlugin.getInstances().log("实例化货币类对象时异常: " + e.getMessage());
            
            if(MMORPGPlugin.getInstances().isDebug()) {
            	
            	e.printStackTrace();
            }
        }
        return currency;
    }
    
    /**
     * 获取指定物品栈是否为货币
     * 
     * @param item 物品栈
     * @return true 则是货币 else 不是
     */
    public static boolean isCurrency(ItemStack item) {
    	
    	if(item != null && CurrencyType.isCurrency(item.getType())) {

            return hasTagKey(item, "CanDestroy");
        }
        return false;
    }
    
    /**
     * 获取指定物品栈的货币对象
     * 
     * @param item 物品栈
     * @return 货币对象 没有则返回 null
     */
    public static Currency getCurrency(ItemStack item) {
    	
    	if(isCurrency(item)) {
    		
    		NBTTagData tagData = getTagValue(item, "CanDestroy");
    		
    		if(tagData != null) {
    			
    			String[] datas = tagData.asString().split(":");
    			
    			if(datas[0].equals("Currency")) {
    				
    				CurrencyType currencyType = CurrencyType.fromType(datas[1]);
        			
        			if(currencyType != null) {
        				
        				return createCurrency(currencyType, item.getAmount());
        			}
    			}
    		}
    	}
    	return null;
    }
}
