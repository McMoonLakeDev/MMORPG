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

import com.minecraft.moonlake.mmorpg.api.commands.Command;
import com.minecraft.moonlake.mmorpg.api.commands.CommandContent;
import com.minecraft.moonlake.mmorpg.api.commands.console.ConsoleHandle;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.AccountManager;
import com.minecraft.moonlake.mmorpg.manager.l18n;
import com.minecraft.moonlake.mmorpg.resources.PlayerLevel;

/**
 * Created by MoonLake on 2016/07/04.
 */
public class Commandexp extends MMORPGCommands {
	
	public Commandexp() {
		
		super("exp");
	}

    /**
     * 控制台执行命令
     *
     * @param console 控制台
     * @param content 命令内容
     */
	@Command(console = false)
    public void run(ConsoleHandle console, CommandContent content) {
    	
    }

    /**
     * 玩家执行命令
     *
     * @param mmorpgPlayer 玩家
     * @param content 命令内容
     */
	@Command(usage = "/exp help", min = 1, max = 3, help = "command.exp.help", pre = "moonlake.mmorpg.exp")
    public void run(MMORPGPlayer mmorpgPlayer, CommandContent content) {
    	
		if(content.argsLength() == 1) {
			
			if(content.equalsIgnoreCase(0, "help")) {
				
				expHelp(mmorpgPlayer);
			}
			else if(content.equalsIgnoreCase(0, "info")) {
				
				info(mmorpgPlayer, null);
			}
		}
		else if(content.argsLength() == 2) {
			
			if(content.equalsIgnoreCase(0, "info")) {
				
				info(mmorpgPlayer, content.getArgFromIndex(1));
			}
		}
    }
	
	private void expHelp(MMORPGPlayer mmorpgPlayer) {
		
		String[] helps = {
				
				"/exp help - " + l18n.$("command.exp.help"),
				"/exp info [T] - " + l18n.$("command.exp.help.info")
		};
		mmorpgPlayer.send(helps);
	}
	
	private void info(MMORPGPlayer mmorpgPlayer, String target) {
		
		MMORPGPlayer targetPlayer = mmorpgPlayer;
		
		if(target != null) {
			
			if(!mmorpgPlayer.hasPermission("moonlake.mmorpg.exp.info.target")) {
				
				mmorpgPlayer.l18n("command.permission.notHave");
				return;
			}
			targetPlayer = AccountManager.getPlayer(target);
			
			if(targetPlayer == null) {
				
				mmorpgPlayer.l18n("command.target.notOnline", target);
				return;
			}
		}
		targetPlayer.l18n("command.exp.info", targetPlayer.getName());
		targetPlayer.l18n("command.exp.info2", targetPlayer.getLevel(), targetPlayer.getExp());
		targetPlayer.l18n("command.exp.info3", PlayerLevel.getNextLevelNeedExp(targetPlayer));;
	}
}
