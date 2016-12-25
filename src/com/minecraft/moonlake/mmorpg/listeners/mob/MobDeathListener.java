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
 
  
package com.minecraft.moonlake.mmorpg.listeners.mob;

import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.event.mob.MobDeathEvent;
import com.minecraft.moonlake.mmorpg.api.mob.Mob;
import com.minecraft.moonlake.mmorpg.api.mob.MobManager;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.AccountManager;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Created by MoonLake on 2016/7/3.
 */
public class MobDeathListener implements Listener {

    private final MMORPG main;
    private final boolean deathShowHologramInfoState;
    private final String deathShowHologramInfo;
    private final int deathShowHologramInfoTime;

    public MobDeathListener(MMORPG main) {

        this.main = main;
        this.deathShowHologramInfo = ConfigManager.get("Mob.DeathShowHologramInfo").asString();
        this.deathShowHologramInfoTime = ConfigManager.get("Mob.DeathShowHologramInfoTime").asInt();
        this.deathShowHologramInfoState = ConfigManager.get("Mob.DeathShowHologramInfoState").asBoolean();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDeath(EntityDeathEvent event) {

        Entity entity = event.getEntity();

        if(entity != null && !(entity instanceof Player)) {

            Mob mob = MobManager.get(entity.getUniqueId());

            if (mob != null) {

                MobDeathEvent mde = new MobDeathEvent(mob);
                Bukkit.getServer().getPluginManager().callEvent(mde);

                event.setDroppedExp(0);
                event.getDrops().clear();

                EntityDamageEvent ede = entity.getLastDamageCause();

                if(ede instanceof EntityDamageByEntityEvent) {

                    EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent)ede;
                    Entity damager = edbee.getDamager();

                    if(damager != null && damager instanceof Player) {

                        MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer((Player)damager);
                        if(mmorpgPlayer == null) return;

                        if(deathShowHologramInfoState) {

                            spawnMobHologramInfo(mob, mmorpgPlayer);
                        }
                        mmorpgPlayer.giveExp(mob.getDropExp());
                        mmorpgPlayer.l18n("player.mob.kill.message", mob.getCustomName(), mob.getDropExp());
                    }
                }
                event.getDrops().addAll(MobManager.chanceDropTable(mob.getDropItems()));
            }
        }
    }

    /**
     * 在指定位置生成击杀怪物的全息信息
     *
     * @param mob 怪物
     * @param killer 击杀的玩家
     */
    private void spawnMobHologramInfo(Mob mob, MMORPGPlayer killer) {

        String info = StringUtil.stringClone(deathShowHologramInfo)
                .replace("%exp", String.valueOf(mob.getDropExp()))
                .replace("%player", killer.getName());

        MobManager.spawnKillMobHologramInfo(mob.getLocation(), StringUtil.color(info), deathShowHologramInfoTime);
    }
}
