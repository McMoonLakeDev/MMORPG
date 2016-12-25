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
import com.minecraft.moonlake.mmorpg.api.event.player.PlayerUpgradeEvent;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by MoonLake on 2016/6/8.
 */
public class PlayerSkillPointListener implements Listener {

    private final MMORPG main;
    private final int upgradePoint;
    private final int upgradeMultiple;

    public PlayerSkillPointListener(MMORPG main) {

        this.main = main;
        this.upgradePoint = ConfigManager.get("PlayerUpgrade.SkillPoint").asInt();
        this.upgradeMultiple = ConfigManager.get("PlayerUpgrade.Multiple").asInt();
    }

    @EventHandler
    public void onUpgrade(PlayerUpgradeEvent event) {

        MMORPGPlayer mmorpgPlayer = event.getPlayer();
        if(mmorpgPlayer == null || mmorpgPlayer.getRole() == null) return;

        if(event.getLevel() % 5 != 0) {
            // normal upgrade
            mmorpgPlayer.getSkill().giveSkillPoint(upgradePoint);
            mmorpgPlayer.l18n("player.upgrade.point.onGet", upgradePoint);
        }
        else {
            // multiple upgrade
            mmorpgPlayer.getSkill().giveSkillPoint(upgradeMultiple);
            mmorpgPlayer.l18n("player.upgrade.point.onGet", upgradeMultiple);
        }
        mmorpgPlayer.playSound(Sound.ENTITY_PLAYER_LEVELUP, 10f, 1f);
    }
}
