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
import com.minecraft.moonlake.mmorpg.manager.CommandManager;

/**
 * Created by MoonLake on 2016/5/30.
 */
public class Commandhealth extends MMORPGCommands {

    public Commandhealth() {

        super("health");
    }

    /**
     * 控制台执行命令
     *
     * @param console 控制台
     * @param content 命令内容
     */
    @Override
    @Command(usage = "/health <Target>", min = 1, max = 1, help = "command.health.help")
    public void run(ConsoleHandle console, CommandContent content) {

        if(!CommandManager.isOnline(content.getArgFromIndex(0))) {

            console.l18n("command.target.notOnline", content.getArgFromIndex(0));
            return;
        }
        MMORPGPlayer target = AccountManager.getPlayer(content.getArgFromIndex(0));
        target.giveRPGHealth(target.getRPGMaxHealth());
        console.l18n("command.health.done", content.getArgFromIndex(0));
    }

    /**
     * 玩家执行命令
     *
     * @param mmorpgPlayer 玩家
     * @param content      命令内容
     */
    @Override
    @Command(usage = "/health [Target]", min = 0, max = 1, help = "command.health.help", pre = "moonlake.mmorpg.health")
    public void run(MMORPGPlayer mmorpgPlayer, CommandContent content) {

        if(content.argsLength() == 0) {

            mmorpgPlayer.setRPGHealth(mmorpgPlayer.getRPGMaxHealth());
            mmorpgPlayer.l18n("command.health.done", mmorpgPlayer.getName());
        }
        else if(content.argsLength() == 1) {

            if(!CommandManager.isOnline(content.getArgFromIndex(0))) {

                mmorpgPlayer.l18n("command.target.notOnline", content.getArgFromIndex(0));
                return;
            }
            MMORPGPlayer target = AccountManager.getPlayer(content.getArgFromIndex(0));
            target.giveRPGHealth(target.getRPGMaxHealth());
            mmorpgPlayer.l18n("command.health.done", content.getArgFromIndex(0));
        }
    }
}
