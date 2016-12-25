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
import com.minecraft.moonlake.mmorpg.api.role.RoleType;
import com.minecraft.moonlake.mmorpg.api.skill.combo.SkillComboType;
import com.minecraft.moonlake.mmorpg.manager.AccountManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

/**
 * Created by MoonLake on 2016/5/17.
 */
public class ClickComboListener implements Listener {

    private final MMORPG main;

    public ClickComboListener(MMORPG main) {

        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {

        if(!event.hasItem() || event.getAction() == Action.PHYSICAL) return;

        MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(event.getPlayer());

        if(mmorpgPlayer == null || mmorpgPlayer.getRole() == null) return;
        if(event.getItem() == null || event.getItem().getType() != mmorpgPlayer.getRole().getOnlyWeapon()) return;
        if(mmorpgPlayer.hasParticleTask()) return;

        event.setCancelled(true);

        if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {

            if(mmorpgPlayer.getRoleType() == RoleType.WARRIOR) {

                if(mmorpgPlayer.getSkill().getComboIndex() == 0) {

                    return;
                }
            }
            mmorpgPlayer.getSkill().applyClick(SkillComboType.LEFT);
        }
        else if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            mmorpgPlayer.getSkill().applyClick(SkillComboType.RIGHT);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onShift(PlayerToggleSneakEvent event) {

        if(!event.isSneaking()) return;

        MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(event.getPlayer());

        if(mmorpgPlayer == null || mmorpgPlayer.getRole() == null) return;
        if(mmorpgPlayer.getItemInMainHand() == null) return;
        if(mmorpgPlayer.getItemInMainHand().getType() != mmorpgPlayer.getRole().getOnlyWeapon()) return;

        event.setCancelled(true);

        mmorpgPlayer.getSkill().applyClick(SkillComboType.SHIFT);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSwap(PlayerSwapHandItemsEvent event) {

        MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(event.getPlayer());

        if(mmorpgPlayer == null || mmorpgPlayer.getRole() == null) return;
        if(mmorpgPlayer.getItemInMainHand() == null) return;
        if(mmorpgPlayer.getItemInMainHand().getType() != mmorpgPlayer.getRole().getOnlyWeapon()) return;

        event.setCancelled(true);

        mmorpgPlayer.getSkill().applyClick(SkillComboType.F);
    }
}
