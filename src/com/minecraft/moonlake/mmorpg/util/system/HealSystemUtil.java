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
 
  
package com.minecraft.moonlake.mmorpg.util.system;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.player.bossbar.HealthBar;
import com.minecraft.moonlake.mmorpg.api.system.HealthSystem;
import com.minecraft.moonlake.mmorpg.util.player.bossbar.HealthBarUtil;

/**
 * Created by MoonLake on 2016/5/23.
 */
public class HealSystemUtil implements HealthSystem {

    private final MMORPGPlayer player;
    private HealthBar healthBar;

    public HealSystemUtil(MMORPGPlayer player) {

        this.player = player;
        this.healthBar = new HealthBarUtil(player);
    }

    /**
     * 获取此玩家的血量条对象
     *
     * @return 血量条对象
     */
    @Override
    public HealthBar getHealthBar() {

        return healthBar;
    }

    /**
     * 更新此玩家的血量条的值
     */
    @Override
    public void update() {

        healthBar.update();
    }
}
