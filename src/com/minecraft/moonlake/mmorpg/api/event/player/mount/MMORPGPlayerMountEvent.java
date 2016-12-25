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
 
  
package com.minecraft.moonlake.mmorpg.api.event.player.mount;

import com.minecraft.moonlake.mmorpg.api.event.player.MMORPGPlayerEvent;
import com.minecraft.moonlake.mmorpg.api.mount.Mount;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;

/**
 * Created by MoonLake on 2016/6/20.
 */
public abstract class MMORPGPlayerMountEvent extends MMORPGPlayerEvent {

    private Mount mount;

    public MMORPGPlayerMountEvent(MMORPGPlayer mmorpgPlayer, Mount mount) {

        super(mmorpgPlayer);

        this.mount = mount;
    }

    /**
     * 获取月色之湖 Mount 坐骑对象
     *
     * @return 坐骑
     */
    public final Mount getMount() {

        return mount;
    }
}
