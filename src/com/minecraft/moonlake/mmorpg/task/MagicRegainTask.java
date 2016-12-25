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
 
  
package com.minecraft.moonlake.mmorpg.task;

import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.AccountManager;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;

/**
 * Created by MoonLake on 2016/5/23.
 */
public class MagicRegainTask extends MMORPGTask {

    private final MMORPG main;
    private final long tick;
    private final int amount;

    public MagicRegainTask(MMORPG main) {

        this.main = main;
        this.tick = (long)(ConfigManager.get("PlayerMagic.Freq").asDouble() * 20);
        this.amount = ConfigManager.get("PlayerMagic.Amount").asInt();
        this.runTaskTimer(main.getMain(), 100L, tick);
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

        for(MMORPGPlayer mmorpgPlayer : AccountManager.getOnlinePlayers()) {

            if(!mmorpgPlayer.isDead()) {

                mmorpgPlayer.giveMagic(amount);
            }
        }
    }
}
