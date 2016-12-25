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
 
  
package com.minecraft.moonlake.mmorpg.api.event.player.expendable;

import com.minecraft.moonlake.mmorpg.api.event.player.MMORPGPlayerEvent;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.item.expendable.Expendable;

/**
 * Created by MoonLake on 2016/6/15.
 */
public abstract class PlayerExpendableEvent extends MMORPGPlayerEvent {

    private final Expendable expendable;

    public PlayerExpendableEvent(MMORPGPlayer mmorpgPlayer, Expendable expendable) {

        super(mmorpgPlayer);

        this.expendable = expendable;
    }

    /**
     * 获取玩家的消耗品对象
     *
     * @return 消耗品
     */
    public Expendable getExpendable() {

        return expendable;
    }
}
