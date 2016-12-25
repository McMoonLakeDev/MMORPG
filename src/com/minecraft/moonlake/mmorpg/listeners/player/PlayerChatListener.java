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
 
  
package com.minecraft.moonlake.mmorpg.listeners.player;

import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.AccountManager;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import com.minecraft.moonlake.mmorpg.manager.l18n;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by MoonLake on 2016/5/25.
 */
public class PlayerChatListener implements Listener {

    private final MMORPG main;

    private final boolean globalChat;
    private final String chatFormat;
    private final boolean notHaveRolePlayerChat;
    private final int nearbyChatRadius;

    public PlayerChatListener(MMORPG main) {

        this.main = main;
        this.globalChat = ConfigManager.get("PlayerChat.GlobalChat").asBoolean();
        this.chatFormat = ConfigManager.get("PlayerChat.ChatFormat").asString();
        this.notHaveRolePlayerChat = ConfigManager.get("PlayerChat.NotHaveRolePlayerChat").asBoolean();
        this.nearbyChatRadius = ConfigManager.get("PlayerChat.NearbyChatRadius").asInt();
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {

        if(event.isCancelled()) return;

        MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(event.getPlayer());
        if((mmorpgPlayer == null || mmorpgPlayer.getRole() == null) && !notHaveRolePlayerChat) {

            event.getPlayer().sendMessage(l18n.$("player.chat.role.none"));
            event.setCancelled(true); return;
        }

        if(globalChat) {

            event.setFormat(toFormat(mmorpgPlayer, event.getMessage()));
        }
        else {

            String format = toFormat(mmorpgPlayer, event.getMessage());
            Location center = mmorpgPlayer.getLocation();

            for(MMORPGPlayer player : AccountManager.getOnlinePlayers()) {

                if(player.distance(center) <= nearbyChatRadius) {

                    player.send(format);
                }
            }
            System.out.println(format);
            event.setCancelled(true);
        }
    }

    private String toFormat(MMORPGPlayer mmorpgPlayer, String message) {

        String format = StringUtil.stringClone(chatFormat)
                       .replace("%role", mmorpgPlayer.getRoleType().getPrefix())
                       .replace("%level", String.valueOf(mmorpgPlayer.getLevel()))
                       .replace("%player", mmorpgPlayer.getName())
                       .replace("%message", message);

        return StringUtil.color(format);
    }
}
