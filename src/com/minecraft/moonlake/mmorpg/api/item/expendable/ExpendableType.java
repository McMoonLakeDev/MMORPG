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
 
  
package com.minecraft.moonlake.mmorpg.api.item.expendable;

import com.minecraft.moonlake.mmorpg.api.item.expendable.potion.AbstractPotion;
import com.minecraft.moonlake.mmorpg.api.item.expendable.renamecard.AbstractRenameCard;
import com.minecraft.moonlake.mmorpg.api.item.expendable.tpbook.AbstractTpBook;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/6/14.
 */
public enum ExpendableType {

    /**
     * 消耗品类型: 药水
     */
    POTION("Potion", "药水", Material.POTION, 0, AbstractPotion.class),
    /**
     * 消耗品类型: 传送书
     */
    TP_BOOK("TpBook", "传送书", Material.BOOK, 0, AbstractTpBook.class),
    /**
     * 消耗品类型: 改名卡
     */
    RENAME_CARD("RenameCard", "改名卡", Material.PAPER, 0, AbstractRenameCard.class),
    ;

    private String type;
    private String name;
    private Material material;
    private int data;
    private Class<? extends Expendable> clazz;
    private final static Map<String, ExpendableType> NAME_MAP;

    static {

        NAME_MAP = new HashMap<>();

        for(ExpendableType expendableType : values()) {

            NAME_MAP.put(expendableType.type.toLowerCase(), expendableType);
        }
    }

    ExpendableType(String type, String name, Material material, int data, Class<? extends Expendable> clazz) {

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

    public Class<? extends Expendable> getClazz() {

        return clazz;
    }
}
