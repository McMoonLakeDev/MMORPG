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
import com.minecraft.moonlake.mmorpg.api.event.player.skill.PlayerSkillUpgradeEvent;
import com.minecraft.moonlake.mmorpg.api.gui.GUIManager;
import com.minecraft.moonlake.mmorpg.api.gui.MMORPGGUIButtonExecute;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.skill.Skill;
import com.minecraft.moonlake.mmorpg.api.skill.SkillManager;
import com.minecraft.moonlake.mmorpg.api.skill.reqatt.SkillReqAtt;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import com.minecraft.moonlake.mmorpg.manager.SkillComboManager;
import com.minecraft.moonlake.mmorpg.resources.GUIPoint;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by MoonLake on 2016/8/2.
 */
public class SkillGUI extends MMORPGPlayerGUI {

    public SkillGUI(MMORPGPlayer owner, String title, int size) {

        super(GUIManager.createGUI("SKILL:" + owner.getName(), title, size), owner);
    }

    public void readSkillData() {

        if(getOwner().getRole() != null) {

            Set<Skill> playerActiveSkill = getOwner().getSkill().getSkillList();

            if(playerActiveSkill != null && playerActiveSkill.size() > 0) {

                Iterator<Skill> activeSkillPlayer = playerActiveSkill.iterator();
                ItemStack[] activeSkillItems = new ItemStack[getOwner().getRole().getRoleSkills().length];
                List<String> activeSkillLore = ConfigManager.get("SkillGUI.SkillItemLoreAtt").asStringList();

                int index = 0;

                while(activeSkillPlayer.hasNext()) {

                    Skill active = activeSkillPlayer.next();
                    String lore = StringUtil.stringCopy(activeSkillLore, ',')
                            .replace("%skillName", active.getDisplayName())
                            .replace("%skillLevel", String.valueOf(active.getLevel()))
                            .replace("%skillMagic", String.valueOf(active.getMagic()))
                            .replace("%skillCombo", SkillComboManager.getComboString(active.getCombo()))
                            .replace("%skillDesc", active.getDescription())
                            .replace("%skillReqAtt", active.getFormatReqAtt());

                    int level = active.getLevel() > 64 ? 64 : active.getLevel();
                    activeSkillItems[index] = ItemManager.getLibrary().create(Material.WOOL, 3, level, active.getName(), lore.split(","));
                    index++;
                }
                getGUI().setSameButton(GUIManager.getSlotsFromArrayPoint(GUIPoint.SKILL_GUI_ACTIVE), activeSkillItems, new MMORPGGUIButtonExecute() {

                    @Override
                    public void execute(GUI gui, MMORPGPlayer clicked, GUIButton currentButton) {

                        if(ItemManager.compare(currentButton.getIcon(), Material.WOOL, 3)) {
                            // active skill icon
                            Skill active = SkillManager.getPlayerSkillFromName(clicked, ItemManager.getItemDisplayName(currentButton.getIcon()));

                            if(active != null) {

                                if(active.hasReqAtt()) {

                                    for(SkillReqAtt reqAtt : active.getReqAttList()) {

                                        if(!reqAtt.checkPlayer(clicked)) {

                                            clicked.closeInventory();
                                            SkillManager.onSkillReqAttGUINotHaveMessage(clicked, reqAtt);
                                            return;
                                        }
                                    }
                                }
                                if(clicked.getSkill().getSkillPoint() <= 0) {

                                    clicked.l18n("player.skillGUI.point.notHave");
                                    return;
                                }
                                if(active.getLevel() >= 40) {

                                    clicked.l18n("player.skillGUI.skill.maxLevel", active.getDisplayName());
                                    return;
                                }
                                clicked.getSkill().takeSkillPoint(1);
                                SkillManager.getPlayerSkillFromName(clicked, active.getName()).addLevel(1);
                                updateSkillPoint();
                                updateActiveSkill(active, currentButton);
                                clicked.l18n("player.skillGUI.skill.upgrade", active.getDisplayName(), active.getLevel());

                                // 触发玩家技能升级事件
                                PlayerSkillUpgradeEvent psue = new PlayerSkillUpgradeEvent(clicked, active);
                                Bukkit.getServer().getPluginManager().callEvent(psue);
                            }
                        }
                    }
                });
            }
            // player skill point icon
            int point = getOwner().getSkill().getSkillPoint();
            String name = ConfigManager.get("SkillGUI.SkillPointItemNameAtt").asString().replace("%skillPoint", String.valueOf(point));
            ItemStack icon = ItemManager.getLibrary().create(Material.EXP_BOTTLE, 0, point > 64 ? 64 : point, name);
            getGUI().setButton(GUIManager.getSlotFromArrayPoint(GUIPoint.SKILL_GUI_SKILL_POINT), icon);
        }
    }

    public void updateSkillPoint() {

        int point = getOwner().getSkill().getSkillPoint();
        String name = ConfigManager.get("SkillGUI.SkillPointItemNameAtt").asString().replace("%skillPoint", String.valueOf(point));
        ItemStack icon = ItemManager.getLibrary().create(Material.EXP_BOTTLE, 0, point > 64 ? 64 : point, name);
        getGUI().setButtonIcon(GUIManager.getSlotFromArrayPoint(GUIPoint.SKILL_GUI_SKILL_POINT), icon);
    }

    private void updateActiveSkill(Skill active, GUIButton button) {

        List<String> activeSkillLore = ConfigManager.get("SkillGUI.SkillItemLoreAtt").asStringList();
        String lore = StringUtil.stringCopy(activeSkillLore, ',')
                .replace("%skillName", active.getDisplayName())
                .replace("%skillLevel", String.valueOf(active.getLevel()))
                .replace("%skillMagic", String.valueOf(active.getMagic()))
                .replace("%skillCombo", SkillComboManager.getComboString(active.getCombo()))
                .replace("%skillDesc", active.getDescription())
                .replace("%skillReqAtt", active.getFormatReqAtt());

        int level = active.getLevel() > 64 ? 64 : active.getLevel();
        ItemStack item = ItemManager.getLibrary().create(Material.WOOL, 3, level, active.getName(), lore.split(","));

        button.updateIcon(item);
    }
}
