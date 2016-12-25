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
 
  
package com.minecraft.moonlake.mmorpg.api.item.expendable.tpbook.type;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.item.expendable.tpbook.AbstractTpBook;
import com.minecraft.moonlake.mmorpg.api.item.expendable.tpbook.TpBookType;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/15.
 */
public class PointTpBook extends AbstractTpBook {

    public PointTpBook(Location location) {

        this(TpBookType.POINT_TP_BOOK, 1, location);
    }

    public PointTpBook(Location location, int amount) {

        this(TpBookType.POINT_TP_BOOK, amount, location);
    }

    public PointTpBook(TpBookType type, int amount, Location location) {

        super(type, amount, location);
    }

    /**
     * 获取 Bukkit 的物品栈对象
     *
     * @return 物品栈对象
     */
    public ItemStack getItem() {

        return super.getItem();
    }

    /**
     * 将传送书使用给指定玩家
     *
     * @param mmorpgPlayer 玩家
     */
    public void useItem(MMORPGPlayer mmorpgPlayer) {

        if(mmorpgPlayer != null) {

            mmorpgPlayer.removeItemStack(getItem());

            super.useItem(mmorpgPlayer);
        }
    }
}
