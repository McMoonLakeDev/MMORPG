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
import com.minecraft.moonlake.mmorpg.api.region.Region;
import com.minecraft.moonlake.mmorpg.api.region.RegionManager;
import com.minecraft.moonlake.mmorpg.api.region.town.TownRegionUtil;
import com.minecraft.moonlake.mmorpg.api.item.expendable.tpbook.AbstractTpBook;
import com.minecraft.moonlake.mmorpg.api.item.expendable.tpbook.TpBookManager;
import com.minecraft.moonlake.mmorpg.api.item.expendable.tpbook.TpBookType;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/15.
 */
public class TownTpBook extends AbstractTpBook {

    private final Region town;

    public TownTpBook(Location location) {

        this(location, 1);
    }

    public TownTpBook(Location location, int amount) {

        this(RegionManager.hasRegion(location), amount);
    }

    public TownTpBook(Region town) {

        this(town, 1);
    }

    public TownTpBook(Region town, int amount) {

        super(TpBookType.TOWN_TP_BOOK, amount, town.getMainLocation());

        this.town = town;
    }

    /**
     * 获取传送书的城镇对象
     *
     * @return 城镇
     */
    public TownRegionUtil getTown() {

        return (TownRegionUtil) town;
    }

    /**
     * 获取 Bukkit 的物品栈对象
     *
     * @return 物品栈对象
     */
    public ItemStack getItem() {

        String targetData = getTown().getName();
        String lore = StringUtil.stringClone(TpBookManager.TP_BOOK_LORE)
                .replace("%target", targetData)
                .replace("%type", getTpBookType().getValueType().getName());

        ItemStack tpBook = ItemManager.setTagValue(super.getItem(), "CanDestroy", getTpBookType().getType() + ":" + targetData);
        tpBook = getInstance().getMoonLake().getLorelib().setLore(tpBook, lore.split("\n"));
        tpBook = getInstance().getMoonLake().getItemlib().addEnchantment(tpBook, Enchantment.DURABILITY, 1);

        return getInstance().getMoonLake().getItemlib().addFlags(tpBook, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS);
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
