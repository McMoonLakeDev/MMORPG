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
import com.minecraft.moonlake.mmorpg.api.mob.MobConfig;
import com.minecraft.moonlake.mmorpg.api.mob.MobData;
import com.minecraft.moonlake.mmorpg.api.mob.MobManager;
import com.minecraft.moonlake.mmorpg.api.mob.MobType;
import com.minecraft.moonlake.mmorpg.api.mob.droptable.DropTable;
import com.minecraft.moonlake.mmorpg.api.mob.droptable.DropTableHandle;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import com.minecraft.moonlake.mmorpg.manager.l18n;
import com.minecraft.moonlake.mmorpg.util.StringUtil;

/**
 * Created by MoonLake on 2016/7/8.
 */
public class Commandmob extends MMORPGCommands {

    public Commandmob() {

        super("mob");
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
    @Command(usage = "/mob help", min = 1, max = 7, help = "command.mob.help", pre = "moonlake.mmorpg.mob")
    public void run(MMORPGPlayer mmorpgPlayer, CommandContent content) {

        if(content.argsLength() == 1) {

            if(content.equalsIgnoreCase(0, "help")) {

                mobHelp(mmorpgPlayer);
            }
        }
        else if(content.argsLength() == 2) {

        }
        else if(content.argsLength() == 3) {

            if(content.equalsIgnoreCase(0, "create")) {

                create(mmorpgPlayer,
                        content.getArgFromIndex(1),
                        content.getArgFromIndex(2),
                        null, null, null, null);
            }
            else if(content.equalsIgnoreCase(0, "summon")) {

                summon(mmorpgPlayer,
                        content.getArgFromIndex(1),
                        content.getArgFromIndex(2));
            }
            else if(content.equalsIgnoreCase(0, "droptable")) {

                if(content.equalsIgnoreCase(1, "add")) {

                    dropTableAdd(mmorpgPlayer, content.getArgFromIndex(2), null);
                }
                else if(content.equalsIgnoreCase(1, "del")) {

                    dropTableDel(mmorpgPlayer, content.getArgFromIndex(2), null);
                }
                else if(content.equalsIgnoreCase(1, "create")) {

                    dropTableCreate(mmorpgPlayer, content.getArgFromIndex(2), null);
                }
            }
        }
        else if(content.argsLength() == 4) {

            if(content.equalsIgnoreCase(0, "create")) {

                create(mmorpgPlayer,
                        content.getArgFromIndex(1),
                        content.getArgFromIndex(2),
                        content.getArgFromIndex(3),
                        null, null, null);
            }
            else if(content.equalsIgnoreCase(0, "setdroptable")) {


            }
            else if(content.equalsIgnoreCase(0, "droptable")) {

                if(content.equalsIgnoreCase(1, "add")) {

                    dropTableAdd(mmorpgPlayer, content.getArgFromIndex(2), content.getArgFromIndex(3));
                }
                else if(content.equalsIgnoreCase(1, "del")) {

                    dropTableDel(mmorpgPlayer, content.getArgFromIndex(2), content.getArgFromIndex(3));
                }
                else if(content.equalsIgnoreCase(1, "create")) {

                    dropTableCreate(mmorpgPlayer, content.getArgFromIndex(2), content.getArgFromIndex(3));
                }
            }
        }
        else if(content.argsLength() == 5) {

            if(content.equalsIgnoreCase(0, "create")) {

                create(mmorpgPlayer,
                        content.getArgFromIndex(1),
                        content.getArgFromIndex(2),
                        content.getArgFromIndex(3),
                        content.getArgFromIndex(4),
                        null, null);
            }
        }
        else if(content.argsLength() == 6) {

            if(content.equalsIgnoreCase(0, "create")) {

                create(mmorpgPlayer,
                        content.getArgFromIndex(1),
                        content.getArgFromIndex(2),
                        content.getArgFromIndex(3),
                        content.getArgFromIndex(4),
                        content.getArgFromIndex(5), null);
            }
        }
        else if(content.argsLength() == 7) {

            if(content.equalsIgnoreCase(0, "create")) {

                create(mmorpgPlayer,
                        content.getArgFromIndex(1),
                        content.getArgFromIndex(2),
                        content.getArgFromIndex(3),
                        content.getArgFromIndex(4),
                        content.getArgFromIndex(5),
                        content.getArgFromIndex(6));
            }
        }
    }

    private void mobHelp(MMORPGPlayer mmorpgPlayer) {

        String[] helps = {

                "/mob help - " + l18n.$("command.mob.help"),
                "/mob summon <T> <N> - " + l18n.$("command.mob.help.summon"),
                "/mob setdroptable <T> <N> <D> - " + l18n.$("command.mob.help.setDropTable"),
                "/mob create <T> <N> [D] [L] [E] [H:MH] - " + l18n.$("command.mob.help.create"),
                "/mob droptable add <N> [I:C,,,] - " + l18n.$("command.mob.help.dropTable.add"),
                "/mob droptable del <N> [I:C,,,] - " + l18n.$("command.mob.help.dropTable.del"),
                "/mob droptable remove <N> - " + l18n.$("command.mob.help.dropTable.remove"),
                "/mob droptable create <N> [I:C,,,] - " + l18n.$("command.mob.help.dropTable.create"),
        };
        mmorpgPlayer.send(helps);
    }

    private void create(MMORPGPlayer mmorpgPlayer, String type, String dataName, String displayName, String level, String dropExp, String realHealth) {

        MobType mobType = MobType.fromType(type);

        if(mobType == null) {

            mmorpgPlayer.l18n("command.mob.notType", type);
            return;
        }
        MobConfig mobConfig = new MobConfig(mobType, dataName, false, false);

        if(mobConfig.exists()) {

            mmorpgPlayer.l18n("command.mob.data.exists", dataName);
            return;
        }
        MobData mobData = new MobData(mobType, dataName);

        if(level != null && !StringUtil.isInteger(level)) {

            mmorpgPlayer.l18n("command.mob.create.level.notInteger");
            return;
        }
        if(dropExp != null && !StringUtil.isInteger(dropExp)) {

            mmorpgPlayer.l18n("command.mob.create.dropExp.notInteger");
            return;
        }
        if(realHealth != null && !StringUtil.isIntegerRatio(realHealth)) {

            mmorpgPlayer.l18n("command.mob.create.realHealth.notInteger");
            return;
        }
        int levelValue = level == null ? 0 : Integer.parseInt(level);
        int dropExpValue = dropExp == null ? 0 : Integer.parseInt(dropExp);
        int healthValue = realHealth == null ? 20 : Integer.parseInt(realHealth.split(":")[0]);
        int maxHealthValue = realHealth == null ? 20 : Integer.parseInt(realHealth.split(":")[1]);

        mobData.setDisplayName(displayName);
        mobData.setHealth(healthValue);
        mobData.setMaxHealth(maxHealthValue);
        mobData.setDropExp(dropExpValue);
        mobData.setLevel(levelValue);

        mobConfig.setMobData(mobData);
        mobConfig.createFile();
        mobConfig.readConfig();
        mobConfig.saveData();

        mmorpgPlayer.l18n("command.mob.create", dataName, mobType.getType());
    }

    private void summon(MMORPGPlayer mmorpgPlayer, String type, String dataName) {

        MobType mobType = MobType.fromType(type);

        if(mobType == null) {

            mmorpgPlayer.l18n("command.mob.notType", type);
            return;
        }
        MobConfig mobConfig = new MobConfig(mobType, dataName, false, false);

        if(!mobConfig.exists()) {

            mmorpgPlayer.l18n("command.mob.data.notExists", dataName);
            return;
        }
        mobConfig.readConfig();
        mobConfig.loadData();

        MobManager.spawn(mobType, mmorpgPlayer.getLocation(), mobConfig.getMobData());

        mmorpgPlayer.l18n("command.mob.summon", dataName, mobType.getType());
    }

    private void setDropTable(MMORPGPlayer mmorpgPlayer, String type, String dataName, String dropName) {

        MobType mobType = MobType.fromType(type);

        if(mobType == null) {

            mmorpgPlayer.l18n("command.mob.notType", type);
            return;
        }
        MobConfig mobConfig = new MobConfig(mobType, dataName, false, false);

        if(!mobConfig.exists()) {

            mmorpgPlayer.l18n("command.mob.data.notExists", dataName);
            return;
        }
        if(DropTableHandle.has(dropName) == null) {

            mmorpgPlayer.l18n("command.mob.dropTable.notExists", dropName);
            return;
        }
    }

    private void dropTableAdd(MMORPGPlayer mmorpgPlayer, String name, String rpgItem) {

    }

    private void dropTableDel(MMORPGPlayer mmorpgPlayer, String name, String rpgItem) {

    }

    private void dropTableCreate(MMORPGPlayer mmorpgPlayer, String name, String rpgItem) {

        if(DropTableHandle.has(name) != null) {

            mmorpgPlayer.l18n("command.mob.dropTable.notExists", name);
            return;
        }
        DropTable dropTable = new DropTable(name);

        if(rpgItem == null && !ItemManager.isAir(mmorpgPlayer.getItemInMainHand())) {

            dropTable.addDrop(mmorpgPlayer.getItemInMainHand());
        }
        else if(rpgItem != null) {
            // rpg item
        }
        DropTableHandle.save(dropTable, false);

        mmorpgPlayer.l18n("command.mob.dropTable.create", name);
    }
}
