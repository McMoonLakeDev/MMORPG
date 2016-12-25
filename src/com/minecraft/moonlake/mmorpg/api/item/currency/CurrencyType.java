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

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.item.currency.type.GoldBlock;
import com.minecraft.moonlake.mmorpg.api.item.currency.type.GoldBroken;
import com.minecraft.moonlake.mmorpg.api.item.currency.type.GoldCoin;
import com.minecraft.moonlake.mmorpg.api.item.currency.type.GoldIngot;
import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/6/12.
 */
public enum CurrencyType {

    /**
     * 货币类型: 碎金子
     */
    GOLD_BROKEN("GoldBroken", "&e碎金子", Material.GOLD_NUGGET, 0, GoldBroken.class),
    /**
     * 货币类型: 金币
     */
    GOLD_COIN("GoldCoin", "&e金币", Material.DOUBLE_PLANT, 0, GoldCoin.class),
    /**
     * 货币类型: 金锭
     */
    GOLD_INGOT("GoldIngot", "&e金锭", Material.GOLD_INGOT, 0, GoldIngot.class),
    /**
     * 货币类型: 金块
     */
    GOLD_BLOCK("GoldBlock", "&e金块", Material.GOLD_BLOCK, 0, GoldBlock.class),;

    private String type;
    private String name;
    private Material material;
    private int data;
    private Class<? extends Currency> clazz;
    private final static Map<String, CurrencyType> NAME_MAP;
    private final static Map<Material, CurrencyType> MATERIAL_MAP;

    static {

        NAME_MAP = new HashMap<>();
        MATERIAL_MAP = new HashMap<>();

        for(CurrencyType currencyType : values()) {

            NAME_MAP.put(currencyType.type.toLowerCase(), currencyType);
            MATERIAL_MAP.put(currencyType.material, currencyType);
        }
    }

    CurrencyType(String type, String name, Material material, int data, Class<? extends Currency> clazz) {

        this.type = type;
        this.name = name;
        this.material = material;
        this.data = data;
        this.clazz = clazz;
    }

    public String getType() {

        return type;
    }

    public String getName() {

        return name;
    }

    public Material getMaterial() {

        return material;
    }

    public int getData() {

        return data;
    }

    public Class<? extends Currency> getClazz() {

        return clazz;
    }

    public <T extends Currency> T newInstance(Object... argsObject) {

        T t = null;

        try {

            t = (T) Reflect.instantiateObject(getClazz(), argsObject);
        }
        catch (Exception e) {

        	if(MMORPGPlugin.getInstances().isDebug()) {
        		
        		e.printStackTrace();
        	}
        }
        return t;
    }

    public static CurrencyType fromType(String type) {

        return NAME_MAP.get(type.toLowerCase());
    }
    
    public static boolean isCurrency(Material material) {
    	
    	return MATERIAL_MAP.containsKey(material);
    }
}
