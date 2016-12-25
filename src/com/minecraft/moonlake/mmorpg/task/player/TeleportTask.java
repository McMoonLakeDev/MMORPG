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
 
  
package com.minecraft.moonlake.mmorpg.task.player;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import com.minecraft.moonlake.particle.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by MoonLake on 2016/6/15.
 */
public class TeleportTask extends AbstractPlayerTask implements Listener {

    private final Location targetLocation;
    private final boolean blindness;
    private boolean allowMove;

    public TeleportTask(MMORPGPlayer mmorpgPlayer, Location targetLocation, int delay) {

        this(mmorpgPlayer, targetLocation, delay, null);
    }

    public TeleportTask(MMORPGPlayer mmorpgPlayer, Location targetLocation, int delay, Runnable endExecute) {

        super(mmorpgPlayer, delay, endExecute);

        this.allowMove = false;
        this.targetLocation = targetLocation;
        this.blindness = ConfigManager.get("TpBook.Blindness").asBoolean();
    }

    public Location getTargetLocation() {

        return targetLocation;
    }

    public boolean isAllowMove() {

        return allowMove;
    }

    public void setAllowMove(boolean allowMove) {

        this.allowMove = allowMove;
    }

    public boolean isBlindness() {

        return blindness;
    }

    /**
     * 开始并执行任务
     */
    @Override
    public void start() {

        Bukkit.getServer().getPluginManager().registerEvents(this, getInstance().getMain());

        if (isBlindness()) {

            getPlayer().addPotionEffect(PotionEffectType.BLINDNESS, 1, getDelay() + 30);
            getPlayer().addPotionEffect(PotionEffectType.CONFUSION, 3, getDelay() + 60);
        }
        getPlayer().playSound(Sound.BLOCK_PORTAL_TRIGGER, 10f, 1f);

        runTaskTimer(getInstance().getMain(), 0L, 1L);
    }

    /**
     * 结束并调用执行后关闭 Task 占用
     */
    @Override
    public void close() {

        HandlerList.unregisterAll(this);

        super.close();
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        if(currentTime <= 0) {

            getPlayer().teleport(getTargetLocation());
            getPlayer().playSound(Sound.ENTITY_ENDERMEN_TELEPORT, 10f, 1f);
            close();
            cancel();
        }
        ParticleEffect.PORTAL.display(getPlayer().getLocation(), 32d, 1f, 1.6f, 1f, 0f, 30);

        currentTime--;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {

        if(isAllowMove()) return;
        if(!event.getPlayer().getName().equals(getPlayer().getName())) return;
        if(event.getFrom().getBlockX() == event.getTo().getBlockX() &&
                event.getFrom().getBlockY() == event.getTo().getBlockY() &&
                event.getFrom().getBlockZ() == event.getTo().getBlockZ()) return;

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {

        if(!event.getPlayer().getName().equals(getPlayer().getName())) return;

        event.setCancelled(true);
    }
}
