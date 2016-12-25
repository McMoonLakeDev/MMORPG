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
 
  
package com.minecraft.moonlake.mmorpg.util.player.bossbar;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.player.bossbar.HealthBar;
import com.minecraft.moonlake.mmorpg.api.adapter.bossbar.AbstractBossBar;
import com.minecraft.moonlake.mmorpg.api.adapter.bossbar.BarColor;
import com.minecraft.moonlake.mmorpg.api.adapter.bossbar.BarStyle;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import com.minecraft.moonlake.mmorpg.util.StringUtil;

/**
 * Created by MoonLake on 2016/5/18.
 */
public final class HealthBarUtil extends AbstractBossBar implements HealthBar {

    private final MMORPGPlayer mmorpgPlayer;
    private final String format;

    public HealthBarUtil(MMORPGPlayer mmorpgPlayer) {

        super("Health Bar", BarColor.RED, BarStyle.SOLID);

        this.mmorpgPlayer = mmorpgPlayer;
        this.format = ConfigManager.get("PlayerHealth.HealthBarTitle").asString();
        this.setTitle(toFormat(format, mmorpgPlayer));
        this.setProgress(1.0d);
        this.addPlayer(mmorpgPlayer);
    }

    /**
     * 更新此玩家的血量条的值
     */
    @Override
    public void update() {

        double result = mmorpgPlayer.getRPGHealth() / mmorpgPlayer.getRPGMaxHealth();

        setProgress(result);
        setTitle(toFormat(format, mmorpgPlayer));
    }

    private String toFormat(String format, MMORPGPlayer mmorpgPlayer) {

        String temp = StringUtil.stringClone(format)
                .replace("%health", String.valueOf(mmorpgPlayer.getRPGHealth()))
                .replace("%maxHealth", String.valueOf(mmorpgPlayer.getRPGMaxHealth()))
                .replace("%player", mmorpgPlayer.getName());

        return StringUtil.color(temp);
    }
}
