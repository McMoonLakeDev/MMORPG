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
import com.minecraft.moonlake.mmorpg.api.region.Region;
import com.minecraft.moonlake.mmorpg.api.region.RegionManager;
import com.minecraft.moonlake.mmorpg.api.region.RegionType;
import com.minecraft.moonlake.mmorpg.api.region.flag.RegionFlag;
import com.minecraft.moonlake.mmorpg.api.region.flag.RegionFlagType;
import com.minecraft.moonlake.mmorpg.manager.WorldEditManager;
import com.minecraft.moonlake.mmorpg.manager.l18n;
import com.sk89q.worldedit.bukkit.selections.Selection;

/**
 * Created by MoonLake on 2016/6/15.
 */
public class Commandregion extends MMORPGCommands {

    public Commandregion() {

        super("region");
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
    @Command(usage = "/region help", min = 1, max = 4, help = "command.region.help", pre = "moonlake.mmorpg.region")
    public void run(MMORPGPlayer mmorpgPlayer, CommandContent content) {

        if(content.argsLength() == 1) {

            if(content.equalsIgnoreCase(0, "help")) {

                regionHelp(mmorpgPlayer);
            }
        }
        else if(content.argsLength() == 2) {


            if(content.equalsIgnoreCase(0, "remove")) {

                removeRegion(mmorpgPlayer, content.getArgFromIndex(1));
            }
            else if(content.equalsIgnoreCase(0, "main")) {

                setMain(mmorpgPlayer, content.getArgFromIndex(1));
            }
            else if(content.equalsIgnoreCase(0, "back")) {

                setBack(mmorpgPlayer, content.getArgFromIndex(1));
            }
        }
        else if(content.argsLength() == 3) {

            if(content.equalsIgnoreCase(0, "create")) {

                createRegion(mmorpgPlayer, content.getArgFromIndex(1), content.getArgFromIndex(2));
            }
            else if(content.equalsIgnoreCase(0, "enter") || content.equalsIgnoreCase(0, "leave")) {

                setEnter$Leave(mmorpgPlayer, content.getArgFromIndex(1), content.getArgFromIndex(0), content.getArgFromIndex(2));
            }
            else if(content.equalsIgnoreCase(0, "delflag")) {

                delFlag(mmorpgPlayer, content.getArgFromIndex(1), content.getArgFromIndex(2));
            }
        }
        else if(content.argsLength() == 4) {

            if(content.equalsIgnoreCase(0, "addflag")) {

                addFlag(mmorpgPlayer, content.getArgFromIndex(1), content.getArgFromIndex(2), content.getArgFromIndex(3));
            }
            else if(content.equalsIgnoreCase(0, "setflag")) {

                setFlag(mmorpgPlayer, content.getArgFromIndex(1), content.getArgFromIndex(2), content.getArgFromIndex(3));
            }
        }
    }

    private void regionHelp(MMORPGPlayer mmorpgPlayer) {

        String[] helps = new String[] {

                "/region help - " + l18n.$("command.region.help"),
                "/region main <N> - " + l18n.$("command.region.help.setMain"),
                "/region back <N> - " + l18n.$("command.region.help.setBack"),
                "/region remove <N> - " + l18n.$("command.region.help.remove"),
                "/region enter <N> <V> - " + l18n.$("command.region.help.setEnter"),
                "/region leave <N> <V> - " + l18n.$("command.region.help.setLeave"),
                "/region create <N> <T> - " + l18n.$("command.region.help.create"),
                "/region delflag <N> <F> - " + l18n.$("command.region.help.delFlag"),
                "/region addflag <N> <F> <V> - " + l18n.$("command.region.help.addFlag"),
                "/region setflag <N> <F> <V> - " + l18n.$("command.region.help.setFlag"),
        };
        mmorpgPlayer.send(helps);
    }

    private void createRegion(MMORPGPlayer mmorpgPlayer, String name, String type) {

        RegionType regionType = RegionType.fromType(type);
        if(regionType == null) {

            mmorpgPlayer.l18n("command.region.create.notType", type);
            return;
        }
        if(RegionManager.getRegionFromCache(name) != null) {

            mmorpgPlayer.l18n("command.region.exists", name);
            return;
        }
        Selection selection = WorldEditManager.getSelection(mmorpgPlayer);
        if(selection == null) {

            mmorpgPlayer.l18n("worldEdit.selection.none");
            return;
        }
        Region town = regionType.newInstance(name);
        town.setRegion(selection);
        town.saveData();

        RegionManager.putRegion(town);
        mmorpgPlayer.l18n("command.region.create", name);
    }

    private void removeRegion(MMORPGPlayer mmorpgPlayer, String name) {

    	Region cache = RegionManager.getRegionFromCache(name);
    	
        if(cache == null) {

            mmorpgPlayer.l18n("command.region.notExists", name);
            return;
        }
        RegionManager.remove(cache.getType(), name);
        mmorpgPlayer.l18n("command.region.remove", name);
    }

    private void setMain(MMORPGPlayer mmorpgPlayer, String name) {

        if(RegionManager.hasRegion(mmorpgPlayer.getLocation()) == null) {

            mmorpgPlayer.l18n("command.region.setMain.notLocation", name);
            return;
        }
        Region region = RegionManager.getRegionFromCache(name);
        if(region == null) {

            mmorpgPlayer.l18n("command.region.notExists", name);
            return;
        }
        region.setMainLocation(mmorpgPlayer.getLocation());
        region.saveData();
        RegionManager.updateRegion(region);

        mmorpgPlayer.l18n("command.region.setMain", name);
    }

    private void setBack(MMORPGPlayer mmorpgPlayer, String name) {

        Region region = RegionManager.getRegionFromCache(name);
        if(region == null) {

            mmorpgPlayer.l18n("command.region.notExists", name);
            return;
        }
        region.setBackLocation(mmorpgPlayer.getLocation());
        region.saveData();
        RegionManager.updateRegion(region);

        mmorpgPlayer.l18n("command.region.setBack", name);
    }

    private void setEnter$Leave(MMORPGPlayer mmorpgPlayer, String name, String type, String value) {

        Region cache = RegionManager.getRegionFromCache(name);
        if(cache == null) {

            mmorpgPlayer.l18n("command.region.notExists", name);
            return;
        }
        if(type.equalsIgnoreCase("enter")) {

            cache.setEnter(value);
        }
        else {

            cache.setLeave(value);
        }
        type = type.toLowerCase().replace(String.valueOf(type.charAt(0)), String.valueOf(type.charAt(0)).toUpperCase());

        cache.saveData();
        RegionManager.updateRegion(cache);
        mmorpgPlayer.l18n("command.region.set" + type, name);
    }

    private void delFlag(MMORPGPlayer mmorpgPlayer, String name, String flag) {

        RegionFlagType flagType = RegionFlagType.fromType(flag);
        if(flagType == null) {

            mmorpgPlayer.l18n("command.region.flag.none", flag);
            return;
        }
        Region cache = RegionManager.getRegionFromCache(name);
        if(cache == null) {

            mmorpgPlayer.l18n("command.region.notExists", name);
            return;
        }
        if(!cache.hasFlag(flagType)) {

            mmorpgPlayer.l18n("command.region.flag.notExists", flag);
            return;
        }
        cache.removeFlag(flagType);
        cache.saveData();

        RegionManager.updateRegion(cache);
        mmorpgPlayer.l18n("command.region.flag.del", flag, name);
    }

    private void addFlag(MMORPGPlayer mmorpgPlayer, String name, String flag, String value) {

        RegionFlagType flagType = RegionFlagType.fromType(flag);
        if(flagType == null) {

            mmorpgPlayer.l18n("command.region.flag.none", flag);
            return;
        }
        Region cache = RegionManager.getRegionFromCache(name);
        if(cache == null) {

            mmorpgPlayer.l18n("command.region.notExists", name);
            return;
        }
        if(cache.hasFlag(flagType)) {

            mmorpgPlayer.l18n("command.region.flag.exists", flag);
            return;
        }
        RegionFlag addFlag = flagType.newInstance();
        Object obj = checkFlagType(flagType, value);
        if(obj == null) {

            mmorpgPlayer.l18n("command.region.flag.valueType", flag);
            return;
        }
        addFlag.setValue(obj);
        cache.addFlag(addFlag);
        cache.saveData();

        RegionManager.updateRegion(cache);
        mmorpgPlayer.l18n("command.region.flag.add", flag, value, name);
    }

    private void setFlag(MMORPGPlayer mmorpgPlayer, String name, String flag, String newValue) {

        RegionFlagType flagType = RegionFlagType.fromType(flag);
        if(flagType == null) {

            mmorpgPlayer.l18n("command.region.flag.none", flag);
            return;
        }
        Region cache = RegionManager.getRegionFromCache(name);
        if(cache == null) {

            mmorpgPlayer.l18n("command.region.notExists", name);
            return;
        }
        if(!cache.hasFlag(flagType)) {

            mmorpgPlayer.l18n("command.region.flag.notExists", flag);
            return;
        }
        RegionFlag setFlag = cache.getFlag(flagType);
        Object obj = checkFlagType(flagType, newValue);
        if(obj == null) {

            mmorpgPlayer.l18n("command.region.flag.valueType", flag);
            return;
        }
        setFlag.setValue(obj);
        cache.removeFlag(flagType);
        cache.addFlag(setFlag);
        cache.saveData();

        RegionManager.updateRegion(cache);
        mmorpgPlayer.l18n("command.region.flag.set", flag, newValue, name);
    }

    private Object checkFlagType(RegionFlagType flagType, String value) {

        Object obj = null;

        if(flagType == RegionFlagType.LEVEL) {

            if(value.matches("([0-9]+)")) {

                obj = Integer.parseInt(value);
            }
        }
        else if(flagType == RegionFlagType.PERMISSION) {

            obj = value;
        }
        return obj;
    }
}
