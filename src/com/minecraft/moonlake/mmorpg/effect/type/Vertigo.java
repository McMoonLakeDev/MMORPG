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
 
  
package com.minecraft.moonlake.mmorpg.effect.type;

import com.minecraft.moonlake.mmorpg.effect.AbstractEffect;
import com.minecraft.moonlake.mmorpg.effect.EffectType;
import com.minecraft.moonlake.mmorpg.manager.EntityManager;
import com.minecraft.moonlake.particle.ParticleEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by MoonLake on 2016/6/10.
 */
public class Vertigo extends AbstractEffect implements Listener {

    private double entityMovementSpeed;
    private double entityKnockBackResistance;

    public Vertigo(int time) {

        super(EffectType.VERTIGO, time, 1);

        setEffectTime(time * 20);
    }

    @Override
    protected void init() {

        super.init();

        if(isPlayer()) {

            getMain().getPluginManager().registerEvents(this, getMain().getMain());
        }
    }

    /**
     * 更新效果在目标实体的作为
     */
    @Override
    public void update() {

        if(isPlayer()) {

            if(!getEntity().hasPotionEffect(PotionEffectType.CONFUSION)) {

                getEntity().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (getTime() + 3) * 20, 0, false, false));
            }
        }
        else {

            if(!getEntity().hasMetadata(getEffectType().getType())) {

                getEntity().setCollidable(false);
                getEntity().setMetadata(getEffectType().getType(), new FixedMetadataValue(getMain().getMain(), "1"));

                entityMovementSpeed = EntityManager.getMovementSpeed(getEntity());
                entityKnockBackResistance = EntityManager.getKnockBackResistance(getEntity());

                EntityManager.setMovementSpeed(getEntity(), 0d);
                EntityManager.setKnockBackResistance(getEntity(), 1d);
            }
        }
        ParticleEffect.FIREWORKS_SPARK.display(0.5f, 0.1f, 0.5f, 0f, 3, getEntity().getLocation().add(0d, 3d, 0d), 32d);
    }

    @Override
    protected void dispose() {

        if(!isPlayer() && getEntity().hasMetadata(getEffectType().getType())) {

            getEntity().setCollidable(true);
            getEntity().removeMetadata(getEffectType().getType(), getMain().getMain());

            EntityManager.setMovementSpeed(getEntity(), entityMovementSpeed);
            EntityManager.setKnockBackResistance(getEntity(), entityKnockBackResistance);
        }
        HandlerList.unregisterAll(this);

        super.dispose();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {

        if(!isPlayer()) return;
        Player target = (Player)getEntity();
        if(!target.getName().equals(event.getPlayer().getName())) return;

        event.setCancelled(true);
    }
}
