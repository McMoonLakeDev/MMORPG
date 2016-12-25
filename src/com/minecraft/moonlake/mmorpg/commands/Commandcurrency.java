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
import com.minecraft.moonlake.mmorpg.api.item.currency.Currency;
import com.minecraft.moonlake.mmorpg.api.item.currency.CurrencyType;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.l18n;

/**
 * Created by MoonLake on 2016/6/18.
 */
public class Commandcurrency extends MMORPGCommands {

    public Commandcurrency() {

        super("currency");
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
    @Command(usage = "/currency help", min = 1, max = 3, help = "command.currency.help", pre = "moonlake.mmorpg.currency")
    public void run(MMORPGPlayer mmorpgPlayer, CommandContent content) {

        if(content.argsLength() == 1) {

            if(content.equalsIgnoreCase(0, "help")) {

                currencyHelp(mmorpgPlayer);
            }
        }
        else if(content.argsLength() == 2) {

            if(content.equalsIgnoreCase(0, "give")) {

                giveCurrency(mmorpgPlayer, content.getArgFromIndex(1), null);
            }
        }
        else if(content.argsLength() == 3) {

            if(content.equalsIgnoreCase(0, "give")) {

                giveCurrency(mmorpgPlayer, content.getArgFromIndex(1), content.getArgFromIndex(2));
            }
        }
    }

    private void currencyHelp(MMORPGPlayer mmorpgPlayer) {

        String[] helps = {

                "/currency help - " + l18n.$("command.currency.help"),
                "/currency give <T> [A] - " + l18n.$("command.currency.help.give")
        };
        mmorpgPlayer.send(helps);
    }

    private void giveCurrency(MMORPGPlayer mmorpgPlayer, String type, String amount) {

        CurrencyType currencyType = CurrencyType.fromType(type);
        if(currencyType == null) {

            mmorpgPlayer.l18n("command.currency.give.notExists", type);
            return;
        }
        if(amount != null && !amount.matches("([0-9]+)")) {

            mmorpgPlayer.l18n("command.currency.give.notInteger");
            return;
        }
        int amountValue = amount == null ? 1 : Integer.parseInt(amount);

        Currency currency = currencyType.newInstance(amountValue);
        if(currency != null) {

            mmorpgPlayer.addItemStack(currency.getItem());
            mmorpgPlayer.l18n("command.currency.give", amountValue, currency.getType().getName());
        }
    }
}
