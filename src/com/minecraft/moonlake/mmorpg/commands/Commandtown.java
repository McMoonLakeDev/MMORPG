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
import com.minecraft.moonlake.mmorpg.manager.l18n;

/**
 * Created by MoonLake on 2016/5/26.
 */
public class Commandtown extends MMORPGCommands {

    public Commandtown() {

        super("town");
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
    @Command(usage = "/town help", min = 1, max = 4, help = "command.town.help", pre = "moonlake.mmorpg.town")
    public void run(MMORPGPlayer mmorpgPlayer, CommandContent content) {

        if(content.argsLength() == 1) {

            if(content.equalsIgnoreCase(0, "help")) {

                townHelp(mmorpgPlayer);
            }
        }
    }

    private void townHelp(MMORPGPlayer mmorpgPlayer) {

        String[] helps = new String[] {

                "/town help - " + l18n.$("command.town.help"),
        };
        mmorpgPlayer.send(helps);
    }
}
