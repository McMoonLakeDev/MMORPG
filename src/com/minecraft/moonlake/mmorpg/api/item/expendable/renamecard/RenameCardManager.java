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
 
  
package com.minecraft.moonlake.mmorpg.api.item.expendable.renamecard;

import com.minecraft.moonlake.data.NBTTagData;
import com.minecraft.moonlake.mmorpg.api.item.expendable.ExpendableType;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/21.
 */
public class RenameCardManager extends ItemManager {

    public final static int MOUNT_DISPLAY_NAME_LENGTH;

    static {

        MOUNT_DISPLAY_NAME_LENGTH = ConfigManager.get("PlayerMount.DisplayNameLength").asInt();
    }

    /**
     * 获取指定物品栈是否是改名卡消耗品
     *
     * @param item 物品栈
     * @return true 是改名卡消耗品 else 不是
     */
    public static boolean isRenameCard(ItemStack item) {

        if(item != null && item.getType() == ExpendableType.RENAME_CARD.getMaterial()) {

            return ItemManager.hasTagKey(item, "CanDestroy");
        }
        return false;
    }

    /**
     * 获取指定物品栈的改名卡消耗品对象
     *
     * @param item 物品栈
     * @return 改名卡消耗品对象 异常或没有则返回 null
     */
    public static RenameCard getRenameCard(ItemStack item) {

        if(isRenameCard(item)) {

            NBTTagData tagData = getTagValue(item, "CanDestroy");

            if(tagData != null) {

                RenameCardType renameCardType = RenameCardType.fromType(tagData.asString());

                if(renameCardType != null) {

                    return renameCardType.newInstance(1);
                }
            }
        }
        return null;
    }
}
