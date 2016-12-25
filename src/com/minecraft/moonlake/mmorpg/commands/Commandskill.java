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
 
  
package com.minecraft.moonlake.mmorpg.commands;

import com.minecraft.moonlake.api.itemlib.ItemBuilder;
import com.minecraft.moonlake.mmorpg.api.adapter.entity.equipment.EquipmentType;
import com.minecraft.moonlake.mmorpg.api.commands.Command;
import com.minecraft.moonlake.mmorpg.api.commands.CommandContent;
import com.minecraft.moonlake.mmorpg.api.commands.console.ConsoleHandle;
import com.minecraft.moonlake.mmorpg.api.mob.MobManager;
import com.minecraft.moonlake.mmorpg.api.mob.type.MobCaveSpiderKnight;
import com.minecraft.moonlake.mmorpg.api.mob.type.MobSkeletonWither;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.gui.GUIManager;
import com.minecraft.moonlake.mmorpg.manager.l18n;
import org.bukkit.Material;

/**
 * Created by MoonLake on 2016/6/12.
 */
public class Commandskill extends MMORPGCommands {

    public Commandskill() {

        super("skill");
    }

    /**
     * 控制台执行命令
     *
     * @param console 控制台
     * @param content 命令内容
     */
    @Override
    @Command(console = false)
    public void run(ConsoleHandle console, CommandContent content) {

    }

    /**
     * 玩家执行命令
     *
     * @param mmorpgPlayer 玩家
     * @param content      命令内容
     */
    @Override
    @Command(usage = "/skill help", min = 1, max = 2, help = "command.skill.help", pre = "command.mmorpg.skill")
    public void run(MMORPGPlayer mmorpgPlayer, CommandContent content) {

        if(content.argsLength() == 1) {

            if(content.equalsIgnoreCase(0, "help")) {
            	
            	skillHelp(mmorpgPlayer);
            }
            else if(content.equalsIgnoreCase(0, "gui")) {
            	
            	GUIManager.getSkillGUI(mmorpgPlayer).openGUI();
            }
            else if(content.equalsIgnoreCase(0, "mob")) {

                MobCaveSpiderKnight mobCaveSpiderKnight = MobManager.spawn(MobCaveSpiderKnight.class, mmorpgPlayer.getLocation());
                mobCaveSpiderKnight.setKnight(MobSkeletonWither.class);
                mobCaveSpiderKnight.getKnight().setEquipmentItem(EquipmentType.HEAD, new ItemBuilder(Material.IRON_HELMET, 0).build());
                mobCaveSpiderKnight.getKnight().setEquipmentItem(EquipmentType.MAIN_HAND, new ItemBuilder(Material.DIAMOND_SWORD, 0).build());
                mobCaveSpiderKnight.getKnight().setCustomName("&c凋零骑士");
                mobCaveSpiderKnight.getKnight().setDropExp(100);

                mmorpgPlayer.send("Mob Spawn Debug: True.");
            }
        }
    }
    
    private void skillHelp(MMORPGPlayer mmorpgPlayer) {
    	
    	String[] helps = {
    			
    			"/skill help - " + l18n.$("command.skill.help"),
    			"/skill gui - " + l18n.$("command.skill.help.gui"),
    	};
    	mmorpgPlayer.send(helps);
    }
}
