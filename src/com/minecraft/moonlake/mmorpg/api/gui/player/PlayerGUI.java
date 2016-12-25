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

import com.minecraft.moonlake.gui.api.GUI;
import com.minecraft.moonlake.gui.api.button.GUIButton;
import com.minecraft.moonlake.mmorpg.api.gui.GUIManager;
import com.minecraft.moonlake.mmorpg.api.gui.MMORPGGUIButtonExecute;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import com.minecraft.moonlake.mmorpg.resources.GUIPoint;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/8/3.
 */
public class PlayerGUI extends MMORPGPlayerGUI {

    public PlayerGUI(MMORPGPlayer mmorpgPlayer, String title, int size) {

        super(GUIManager.createGUI("PLAYER:" + mmorpgPlayer.getName(), title, size), mmorpgPlayer);
    }

    public void readPlayerData() {

        ItemStack skillIcon = ItemManager.getLibrary().create(Material.IRON_SWORD, 0, 1, "技能 GUI 面板");

        getGUI().setButton(GUIManager.getSlotFromArrayPoint(GUIPoint.PLAYER_GUI_SKILL_BUTTON), skillIcon, new MMORPGGUIButtonExecute() {

            @Override
            public void execute(GUI gui, MMORPGPlayer clicked, GUIButton currentButton) {

                clicked.closeInventory();
                clicked.getSkill().getSkillGUI().openGUI();
                clicked.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 10f, 1f);
            }
        });
    }
}
