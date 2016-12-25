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
import com.minecraft.moonlake.mmorpg.api.gui.GUIManager;
import com.minecraft.moonlake.mmorpg.api.mount.type.MountDonkey;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.hotbar.HotBarItem;
import com.minecraft.moonlake.mmorpg.manager.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Created by MoonLake on 2016/5/14.
 */
public class PlayerBaseListener implements Listener {

    private final MMORPG main;

    public PlayerBaseListener(MMORPG main) {

        this.main = main;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if(!player.hasPlayedBefore()) {
            // 呐呐，欢迎新玩家哦 QWQ
            // 应该传送到重生点选择职业后再进行初始化操作

            //return;
            HotBarItem.initHotBarItem(player);
            DataManager.initPlayerDataAndDefaultData(player.getName());
            DataManager.initPlayerSkillData(player.getName());
            DataManager.initPlayerMountData(player.getName(), new MountDonkey());
        }

        if(!AccountManager.hasPlayer(player)) {

            MMORPGPlayer mmorpgPlayer = AccountManager.create(player);
            mmorpgPlayer.loadData();

            PlayerManager.initMMORPGPlayerState(mmorpgPlayer);
            PlayerManager.updatePlayerListName(mmorpgPlayer);
            SoulManager.updatePlayerHotBarSoul(mmorpgPlayer);
            GUIManager.createBaseGUI(mmorpgPlayer);
        }
        PlayerManager.checkFoodLevel(player);
        HotBarItem.checkHotBarItem(player);
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onQuit(PlayerQuitEvent event) {

        if(AccountManager.hasPlayer(event.getPlayer())) {

            MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(event.getPlayer());
            mmorpgPlayer.saveData();

            MMORPGManager.closeManager(mmorpgPlayer.getName());
        }
    }

    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event) {

        if(!(event.getEntity() instanceof Player)) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevel(FoodLevelChangeEvent event) {

        if(!(event.getEntity() instanceof Player)) return;

        event.setCancelled(true);
        PlayerManager.checkFoodLevel((Player)event.getEntity());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {

        if(!(event.getEntity() instanceof Player)) return;
        MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer((Player)event.getEntity());
        if(mmorpgPlayer == null) return;
        mmorpgPlayer.damage((int)event.getDamage());

        event.setDamage(0d);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(event.getEntity());
        if(mmorpgPlayer == null) return;

        event.setDroppedExp(0);
        event.setKeepLevel(true);

        if(mmorpgPlayer.getSoul() <= 0) {

            mmorpgPlayer.l18n("player.death.soul.notHave");
            HotBarItem.removeHotBarItemDrops(event.getDrops());

            event.setKeepInventory(false);

            return;
        }
        mmorpgPlayer.takeSoul(1);
        mmorpgPlayer.l18n("player.death.soul.onTake", 1);

        event.setKeepInventory(true);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {

        MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(event.getPlayer());
        if(mmorpgPlayer == null) return;

        mmorpgPlayer.respawn();

        HotBarItem.checkHotBarItem(mmorpgPlayer);
        SoulManager.updatePlayerHotBarSoul(mmorpgPlayer);
    }
}
