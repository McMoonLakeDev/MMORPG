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

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.effect.AbstractEffect;
import com.minecraft.moonlake.mmorpg.effect.EffectType;
import com.minecraft.moonlake.mmorpg.manager.AccountManager;
import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/6/9.
 */
public class Bleed extends AbstractEffect {

    public Bleed(int time) {

        super(EffectType.BLEED, time, 20L);
    }

    /**
     * 更新效果在目标实体的作为
     */
    @Override
    public void update() {

        if(isPlayer()) {

            MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer((Player) getEntity());

            if(mmorpgPlayer != null) {

                mmorpgPlayer.damage(1);
                mmorpgPlayer.send("啊♂ 你出血了流了一地大姨妈.");
            }
        }
    }
}
