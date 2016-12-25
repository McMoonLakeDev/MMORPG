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

import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.commands.Command;
import com.minecraft.moonlake.mmorpg.api.commands.CommandContent;
import com.minecraft.moonlake.mmorpg.api.commands.MMORPGCommand;
import com.minecraft.moonlake.mmorpg.api.commands.console.ConsoleHandle;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.commands.console.ConsoleHandleUtil;
import com.minecraft.moonlake.mmorpg.commands.content.CommandContentUtil;
import com.minecraft.moonlake.mmorpg.manager.CommandManager;
import com.minecraft.moonlake.mmorpg.manager.l18n;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

/**
 * Created by MoonLake on 2016/5/24.
 */
public class MMORPGCommandManager {

    private final MMORPG main;

    public MMORPGCommandManager(MMORPG main) {

        this.main = main;
    }

    public void execute(Class<? extends MMORPGCommands> clazz, String comamnd, CommandSender sender, String[] args) {

        Command cmd = null;

        try {

            MMORPGCommand mmorpgCommand = clazz.newInstance();

            if(CommandManager.isConsole(sender)) {

                cmd = mmorpgCommand.getClass().getMethod("run", ConsoleHandle.class, CommandContent.class).getAnnotation(Command.class);

                if(!cmd.console()) {

                    sender.sendMessage(l18n.$("command.console.notUse"));
                    return;
                }
            }
            else {

                cmd = mmorpgCommand.getClass().getMethod("run", MMORPGPlayer.class, CommandContent.class).getAnnotation(Command.class);
            }
            if(!cmd.pre().isEmpty() && !hasPermission(sender, cmd.pre())) {

                sender.sendMessage(l18n.$("command.permission.notHave"));
                return;
            }
            if(args.length < cmd.min() || (cmd.max() != -1 && args.length > cmd.max())) {

                sender.sendMessage(l18n.$("command.args.noSuccess", cmd.usage()));

                if(!cmd.help().isEmpty()) {

                    sender.sendMessage(l18n.$("command.help.prefix", l18n.$(cmd.help())));
                }
                return;
            }
            if(CommandManager.isConsole(sender)) {

                mmorpgCommand.run(new ConsoleHandleUtil((ConsoleCommandSender)sender), new CommandContentUtil(mmorpgCommand.getCommand(), args));
            }
            else {

                mmorpgCommand.run(CommandManager.getSender(sender), new CommandContentUtil(mmorpgCommand.getCommand(), args));
            }
        }
        catch (Exception e) {

            main.log("执行 MMORPG 命令名为 '" + comamnd + "' 时异常: " + e.getMessage());
            
            if(main.isDebug()) {
            	
            	e.printStackTrace();
            }
        }
    }

    private boolean hasPermission(CommandSender sender, String pre) {

        if(pre.isEmpty() || sender.hasPermission(pre) || sender.hasPermission("admin")) {

            return true;
        }
        return false;
    }
}
