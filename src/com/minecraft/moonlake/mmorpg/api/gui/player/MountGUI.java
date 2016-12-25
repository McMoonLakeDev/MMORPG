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
 
  
package com.minecraft.moonlake.mmorpg.api.gui.player;

import com.minecraft.moonlake.api.itemlib.ItemBuilder;
import com.minecraft.moonlake.gui.api.GUI;
import com.minecraft.moonlake.gui.api.button.GUIButton;
import com.minecraft.moonlake.mmorpg.api.gui.MMORPGGUIButtonExecute;
import com.minecraft.moonlake.mmorpg.api.mount.Mount;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.gui.GUIManager;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by MoonLake on 2016/7/26.
 */
public class MountGUI extends MMORPGPlayerGUI {

    public MountGUI(MMORPGPlayer owner, String title, int size) {

        super(GUIManager.createGUI("MOUNT:" + owner.getName(), title, size), owner);
    }

    public void readMountData(List<Mount> mountList) {

        if(mountList != null && mountList.size() > 0) {

            getGUI().clearAll();

            for(Mount mount : mountList) {

                ItemStack icon = new ItemBuilder(Material.SADDLE, 0, mount.getDisplayName()).build();

                getGUI().addButton(icon, new MMORPGGUIButtonExecute() {

                    @Override
                    public void execute(GUI gui, MMORPGPlayer clicked, GUIButton currentButton) {

                        if(clicked.getMount().getMain() != null) {
                            // last main
                            int mainSlot = clicked.getMount().getMainSlot();
                            GUIButton mainButton = gui.getButton(mainSlot);
                            ItemStack mainButtonItem = mainButton.getIcon();

                            if(currentButton.getSlot() == clicked.getMount().getMainSlot()) {

                                if(!ItemManager.getLibrary().getEnchantments(mainButtonItem).containsKey(Enchantment.DURABILITY)) {
                                    // select main
                                    updateButton(currentButton);
                                    clicked.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 10f, 1f);
                                    clicked.l18n("player.mount.summon.onMain", ItemManager.getItemDisplayName(mainButtonItem));
                                    clicked.closeInventory();
                                }
                                return;
                            }
                            mainButtonItem = ItemManager.getLibrary().removeEnchantment(mainButtonItem, Enchantment.DURABILITY);
                            mainButton.updateIcon(mainButtonItem);
                        }
                        updateButton(currentButton);
                        clicked.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 10f, 1f);
                        clicked.l18n("player.mount.summon.onMain", ItemManager.getItemDisplayName(currentButton.getIcon()));
                        clicked.closeInventory();
                    }
                });
            }
        }
    }

    /**
     * 更新坐骑 GUI 指定按钮的物品栈
     *
     * @param button 按钮
     */
    public void updateButton(GUIButton button) {

        if(button == null) {

            return;
        }
        ItemStack item = button.getIcon();

        item = ItemManager.getLibrary().addEnchantment(item, Enchantment.DURABILITY, 1);
        item = ItemManager.getLibrary().addFlags(item, ItemFlag.HIDE_ENCHANTS);

        button.updateIcon(item);
        getOwner().getMount().setMain(button.getSlot());
    }

    /**
     * 设置坐骑 GUI 的默认坐骑
     */
    public void setDefaultToMain() {

        setDefaultToMain(0);
    }

    /**
     * 设置坐骑 GUI 的默认坐骑
     *
     * @param slot 索引
     */
    public void setDefaultToMain(int slot) {

        GUIButton main = getGUI().getButton(slot);

        if(main != null) {

            updateButton(main);
        }
    }

    /**
     * 设置坐骑 GUI 的默认坐骑
     *
     * @param slot 索引
     * @param icon 图标
     */
    public void setButtonIcon(int slot, ItemStack icon) {

        GUIButton main = getGUI().getButton(slot);

        if(main != null) {

            main.updateIcon(icon);
        }
    }
}
