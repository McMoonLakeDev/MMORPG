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
 
  
package com.minecraft.moonlake.mmorpg.manager;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/8.
 */
public class ItemManager extends com.minecraft.moonlake.manager.ItemManager {

    public final static ItemStack AIR;
    public final static String POTION_LORE;
    public final static String TP_BOOK_LORE;
    public final static String RENAME_CARD_LORE;

    static {

        AIR = new ItemStack(Material.AIR);

        POTION_LORE =

                "&7药水消耗品 &a✔ \n" +
                " \n" +
                "&6需求等级: &b%reqLevel \n" +
                "&6使用后恢复 &b%value&6 点的%type \n" +
                " \n";

        TP_BOOK_LORE =

                "&7传送书消耗品 &a✔ \n" +
                " \n" +
                "&6使用后传送到 &b%target &6%type位置 \n" +
                " \n";

        RENAME_CARD_LORE =

                "&7改名卡消耗品 &a✔ \n" +
                " \n" +
                "&6方法: &2%method \n" +
                "&6使用后将修改 &b%type &6的自定义名称 \n" +
                " \n";
    }
}
