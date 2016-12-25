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
 
  
package com.minecraft.moonlake.mmorpg.api.item.expendable.tpbook;

import com.minecraft.moonlake.data.NBTTagData;
import com.minecraft.moonlake.manager.LocationManager;
import com.minecraft.moonlake.mmorpg.api.item.expendable.ExpendableType;
import com.minecraft.moonlake.mmorpg.api.region.RegionManager;
import com.minecraft.moonlake.mmorpg.api.region.town.TownRegion;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/15.
 */
public final class TpBookManager extends ItemManager {

    /**
     * 获取指定物品栈是否是传送书消耗品
     *
     * @param item 物品栈
     * @return true 是传送书消耗品 else 不是
     */
    public static boolean isTpBook(ItemStack item) {

        if(item != null && item.getType() == ExpendableType.TP_BOOK.getMaterial()) {

            return hasTagKey(item, "CanDestroy");
        }
        return false;
    }

    /**
     * 获取指定物品栈的传送书消耗品对象
     *
     * @param item 物品栈
     * @return 传送书消耗品对象 异常或没有则返回 null
     */
    public static TpBook getTpBook(ItemStack item) {

        if(isTpBook(item)) {

            NBTTagData tagData = getTagValue(item, "CanDestroy");

            if(tagData != null) {

                String[] datas = tagData.asString().split(":");
                TpBookType type = TpBookType.fromType(datas[0]);

                if(type != null) {

                    if(type == TpBookType.POINT_TP_BOOK) {

                        return type.newInstance(LocationManager.fromData(datas[1]));
                    }
                    else if(type == TpBookType.TOWN_TP_BOOK) {

                        TownRegion town = RegionManager.getTownFromCache(datas[1]);

                        if(town != null) {

                            return type.newInstance(town.getMainLocation());
                        }
                    }
                }
            }
        }
        return null;
    }
}
