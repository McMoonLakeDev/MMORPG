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

/**
 * Created by MoonLake on 2016/6/18.
 */
public class Commanddepot extends MMORPGCommands {

    public Commanddepot() {

        super("depot");
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
    @Command(usage = "/depot help", min = 0, max = 2, help = "command.depot.help", pre = "moonlake.mmorpg.depot")
    public void run(MMORPGPlayer mmorpgPlayer, CommandContent content) {

        if(content.argsLength() == 0) {

            mmorpgPlayer.getRepertory().open();
        }
        else if(content.argsLength() == 1) {

            if(content.equalsIgnoreCase(0, "help")) {

                depotHelp(mmorpgPlayer);
            }
        }
        else if(content.argsLength() == 2) {

            if(content.equalsIgnoreCase(0, "see")) {

                MMORPGPlayer target = AccountManager.getPlayer(content.getArgFromIndex(1));
                if(target == null) {

                    mmorpgPlayer.l18n("command.target.notOnline", content.getArgFromIndex(1));
                    return;
                }
                target.getRepertory().peep(mmorpgPlayer);
                mmorpgPlayer.l18n("command.depot.see", target.getName());
            }
        }
    }

    private void depotHelp(MMORPGPlayer mmorpgPlayer) {

        String[] helps = {

                "/depot - " + l18n.$("command.depot.help.main"),
                "/depot see <T> - " + l18n.$("command.depot.help.see")
        };
        mmorpgPlayer.send(helps);
    }
}
