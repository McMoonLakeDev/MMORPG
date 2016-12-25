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

import com.minecraft.moonlake.manager.LocationManager;
import com.minecraft.moonlake.mmorpg.api.item.expendable.AbstractExpendable;
import com.minecraft.moonlake.mmorpg.api.item.expendable.ExpendableType;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import com.minecraft.moonlake.mmorpg.task.player.PlayerTaskManager;
import com.minecraft.moonlake.mmorpg.task.player.TeleportTask;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/15.
 */
public abstract class AbstractTpBook extends AbstractExpendable implements TpBook {

    private final TpBookType type;
    private Location location;

    public AbstractTpBook(TpBookType type) {

        this(type, 1);
    }

    public AbstractTpBook(TpBookType type, int amount) {

        this(type, amount, null);
    }

    public AbstractTpBook(TpBookType type, int amount, Location location) {

        super(ExpendableType.TP_BOOK, amount, type.getName());

        this.type = type;
        this.location = location;
    }

    /**
     * 获取传送书的类型
     *
     * @return 传送书类型
     */
    public TpBookType getTpBookType() {

        return type;
    }

    /**
     * 复制 TpBook 物品栈对象
     *
     * @return 复制的对象
     */
    public TpBook clone() {

        return null;
    }

    /**
     * 获取传送书的目标位置
     *
     * @return 目标位置
     */
    @Override
    public Location getLocation() {

        return location;
    }

    /**
     * 获取 Bukkit 的物品栈对象
     *
     * @return 物品栈对象
     */
    public ItemStack getItem() {

        String lore = StringUtil.stringClone(TpBookManager.TP_BOOK_LORE)
                .replace("%target", LocationManager.toDataBit(getLocation(), 0, false))
                .replace("%type", getTpBookType().getValueType().getName());

        ItemStack tpBook = ItemManager.setTagValue(super.getItem(), "CanDestroy", getTpBookType().getType() + ":" + LocationManager.toDataBit(getLocation(), 3));
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

        int delay = ConfigManager.get("TpBook.Delay").asInt();

        TeleportTask tpEffect = new TeleportTask(mmorpgPlayer, getLocation(), delay, new Runnable() {

            @Override
            public void run() {

                mmorpgPlayer.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 10f, 1f);
                mmorpgPlayer.l18n("player.expendable.tpBook.used", getTpBookType().getName(), LocationManager.toDataBit(getLocation(), 0, false), getTpBookType().getValueType().getName());
            }
        });
        tpEffect.setAllowMove(false);
        tpEffect.start();
        tpEffect.getPlayer().l18n("player.expendable.tpBook.start");

        PlayerTaskManager.putTaskList(mmorpgPlayer, tpEffect);

        super.useItem(mmorpgPlayer);
    }
}
