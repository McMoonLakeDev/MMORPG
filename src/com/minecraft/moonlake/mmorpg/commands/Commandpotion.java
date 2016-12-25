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
import com.minecraft.moonlake.mmorpg.api.item.expendable.potion.Potion;
import com.minecraft.moonlake.mmorpg.api.item.expendable.potion.PotionType;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.l18n;

/**
 * Created by MoonLake on 2016/6/17.
 */
public class Commandpotion extends MMORPGCommands {

    public Commandpotion() {

        super("potion");
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
    @Command(usage = "/potion help", min = 1, max = 4, help = "command.potion.help", pre = "moonlake.mmorpg.potion")
    public void run(MMORPGPlayer mmorpgPlayer, CommandContent content) {

        if(content.argsLength() == 1) {

            if(content.equalsIgnoreCase(0, "help")) {

                potionHelp(mmorpgPlayer);
            }
        }
        else if(content.argsLength() == 2) {

            if(content.equalsIgnoreCase(0, "give")) {

                givePotion(mmorpgPlayer, content.getArgFromIndex(1), null, null);
            }
        }
        else if(content.argsLength() == 3) {

            if(content.equalsIgnoreCase(0, "give")) {

                givePotion(mmorpgPlayer, content.getArgFromIndex(1), content.getArgFromIndex(2), null);
            }
        }
        else if(content.argsLength() == 4) {

            if(content.equalsIgnoreCase(0, "give")) {

                givePotion(mmorpgPlayer, content.getArgFromIndex(1), content.getArgFromIndex(2), content.getArgFromIndex(3));
            }
        }
    }

    private void potionHelp(MMORPGPlayer mmorpgPlayer) {

        String[] helps = {

                "/potion help - " + l18n.$("command.potion.help"),
                "/potion give <T> [S] [A] - " + l18n.$("command.potion.help.give")
        };
        mmorpgPlayer.send(helps);
    }

    private void givePotion(MMORPGPlayer mmorpgPlayer, String type, String special, String amount) {

        PotionType potionType = PotionType.fromType(type);
        if(potionType == null) {

            mmorpgPlayer.l18n("command.potion.give.notExists", type);
            return;
        }
        if(amount != null && !amount.matches("([0-9]+)")) {

            mmorpgPlayer.l18n("command.potion.give.notInteger");
            return;
        }
        int amountValue = amount == null ? 1 : Integer.parseInt(amount);

        if(special != null && !special.matches("([0-9]+):([0-9]+)")) {

            mmorpgPlayer.l18n("command.potion.give.notSpecial");
            return;
        }
        int reqLevel = special == null ? 0 : Integer.parseInt(special.split(":")[0]);
        int value = special == null ? 1 : Integer.parseInt(special.split(":")[1]);

        Potion potion = potionType.newInstance(amountValue, reqLevel, value);

        if(potion != null) {

            mmorpgPlayer.addItemStack(potion.getItem());
            mmorpgPlayer.l18n("command.potion.give", amountValue, potion.getPotionType().getName());
        }
    }
}
