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
 
  
package com.minecraft.moonlake.mmorpg.api.item.expendable.renamecard.type;

import com.minecraft.moonlake.mmorpg.api.event.player.expendable.PlayerExpendableRenameCardEvent;
import com.minecraft.moonlake.mmorpg.api.gui.AnvilGUI;
import com.minecraft.moonlake.mmorpg.api.item.expendable.renamecard.AbstractRenameCard;
import com.minecraft.moonlake.mmorpg.api.item.expendable.renamecard.RenameCardManager;
import com.minecraft.moonlake.mmorpg.api.item.expendable.renamecard.RenameCardType;
import com.minecraft.moonlake.mmorpg.api.mount.Mount;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/20.
 */
public class RenameCardMount extends AbstractRenameCard {

    public RenameCardMount() {

        this(1);
    }

    public RenameCardMount(int amount) {

        super(RenameCardType.MOUNT_RENAME_CARD, amount);
    }

    /**
     * 将改名卡消耗品使用给指定玩家
     *
     * @param mmorpgPlayer 玩家
     */
    @Override
    public void useItem(MMORPGPlayer mmorpgPlayer) {

        PlayerExpendableRenameCardEvent perce = new PlayerExpendableRenameCardEvent(mmorpgPlayer, this);
        Bukkit.getServer().getPluginManager().callEvent(perce);

        if(!perce.isCancelled()) {

            Mount mount = mmorpgPlayer.getMount().getMain();

            if(mount == null) {

                mmorpgPlayer.l18n("player.mount.rename.notMain");
                return;
            }
            if(!mount.isLiving()) {

                mmorpgPlayer.l18n("player.mount.rename.notRide");
                return;
            }
            AnvilGUI rename = new AnvilGUI(mmorpgPlayer, new AnvilGUI.AnvilClickEventHandler() {

                @Override
                public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {

                    if (event.getSlot() != AnvilGUI.AnvilSlot.OUTPUT) {

                        event.setWillClose(false);
                        event.setWillDestroy(false);
                        return;
                    }
                    if (!ItemManager.isAir(event.getClickItem())) {

                        String displayName = ItemManager.getItemDisplayName(event.getClickItem());

                        if (displayName == null || displayName.equalsIgnoreCase("")) {

                            mmorpgPlayer.l18n("player.mount.rename.notNone");
                            return;
                        }
                        if (displayName.contains(" ")) {

                            mmorpgPlayer.l18n("player.mount.rename.hasNone");
                            return;
                        }
                        if (displayName.length() > RenameCardManager.MOUNT_DISPLAY_NAME_LENGTH) {

                            mmorpgPlayer.l18n("player.mount.rename.notLength", RenameCardManager.MOUNT_DISPLAY_NAME_LENGTH);
                            return;
                        }
                        mount.setDisplayName(displayName);

                        ItemStack item = ItemManager.getLibrary().create(Material.MONSTER_EGG, 0, 1, mount.getDisplayName());
                        item = ItemManager.getLibrary().addEnchantment(item, Enchantment.DURABILITY, 1);
                        item = ItemManager.getLibrary().addFlags(item, ItemFlag.HIDE_ENCHANTS);

                        mmorpgPlayer.getMount().updateMountFromSlot(mount, mmorpgPlayer.getMount().getMainSlot());
                        mmorpgPlayer.getMount().getMountGUI().setButtonIcon(mmorpgPlayer.getMount().getMainSlot(), item);
                        mmorpgPlayer.l18n("player.mount.rename.change", displayName);

                        event.setWillClose(true);
                        event.setWillDestroy(true);

                        ItemStack temp = getItem().clone();
                        temp.setAmount(1);

                        mmorpgPlayer.removeItemStack(temp);
                    }
                }
            }, new AnvilGUI.AnvilCloseEventHandler() {

                @Override
                public void onAnvilClose(AnvilGUI.AnvilCloseEvent event) {

                    event.getOwner().l18n("player.mount.rename.close");
                }
            });
            rename.setSlotItem(AnvilGUI.AnvilSlot.INPUT_LEFT, getItem());
            rename.open();

            super.useItem(mmorpgPlayer);
        }
    }
}
