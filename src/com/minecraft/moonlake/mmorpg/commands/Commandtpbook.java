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
import com.minecraft.moonlake.mmorpg.api.item.expendable.tpbook.TpBook;
import com.minecraft.moonlake.mmorpg.api.item.expendable.tpbook.TpBookType;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.region.RegionManager;
import com.minecraft.moonlake.mmorpg.api.region.town.TownRegion;
import com.minecraft.moonlake.mmorpg.manager.l18n;

/**
 * Created by MoonLake on 2016/6/17.
 */
public class Commandtpbook extends MMORPGCommands {

    public Commandtpbook() {

        super("tpbook");
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
    @Command(usage = "/tpbook help", min = 1, max = 4, help = "command.tpBook.help", pre = "moonlake.mmorpg.tpbook")
    public void run(MMORPGPlayer mmorpgPlayer, CommandContent content) {

        if(content.argsLength() == 1) {

            if(content.equalsIgnoreCase(0, "help")) {

                tpBookHelp(mmorpgPlayer);
            }
        }
        else if(content.argsLength() == 2) {

            if(content.equalsIgnoreCase(0, "give")) {

                giveTpBook(mmorpgPlayer, content.getArgFromIndex(1), null, null);
            }
        }
        else if(content.argsLength() == 3) {

            if(content.equalsIgnoreCase(0, "give")) {

                giveTpBook(mmorpgPlayer, content.getArgFromIndex(1), content.getArgFromIndex(2), null);
            }
        }
        else if(content.argsLength() == 4) {

            if(content.equalsIgnoreCase(0, "give")) {

                giveTpBook(mmorpgPlayer, content.getArgFromIndex(1), content.getArgFromIndex(2), content.getArgFromIndex(3));
            }
        }
    }

    private void tpBookHelp(MMORPGPlayer mmorpgPlayer) {

        String[] helps = {

                "/tpbook help - " + l18n.$("command.tpBook.help"),
                "/tpbook give <T> [S] [A] - " + l18n.$("command.tpBook.help.give")
        };
        mmorpgPlayer.send(helps);
    }

    private void giveTpBook(MMORPGPlayer mmorpgPlayer, String type, String special, String amount) {

        TpBookType tpBookType = TpBookType.fromType(type);

        if(tpBookType == null) {

            mmorpgPlayer.l18n("command.tpBook.give.notExists", type);
            return;
        }
        if(amount != null && !amount.matches("([0-9]+)")) {

            mmorpgPlayer.l18n("command.tpBook.give.notInteger");
            return;
        }
        TpBook tpBook = null;
        int amountValue = amount == null ? 1 : Integer.parseInt(amount);

        if(tpBookType == TpBookType.POINT_TP_BOOK) {

            tpBook = tpBookType.newInstance(mmorpgPlayer.getLocation(), amountValue);
        }
        else if(tpBookType == TpBookType.TOWN_TP_BOOK) {

            if(special == null) {

                mmorpgPlayer.l18n("command.tpBook.give.needSpecial", type);
                return;
            }
            TownRegion town = RegionManager.getTownFromCache(special);

            if(town == null) {

                mmorpgPlayer.l18n("command.region.notExists", special);
                return;
            }
            if(town.getMainLocation() == null) {

                mmorpgPlayer.l18n("command.tpBook.give.notMainLocation");
                return;
            }
            tpBook = tpBookType.newInstance(town.getMainLocation(), amountValue);
        }
        if(tpBook != null) {

            mmorpgPlayer.addItemStack(tpBook.getItem());
            mmorpgPlayer.l18n("command.tpBook.give", amountValue, tpBook.getTpBookType().getName());
        }
    }
}
