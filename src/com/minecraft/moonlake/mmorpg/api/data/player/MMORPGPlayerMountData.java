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
 
  
package com.minecraft.moonlake.mmorpg.api.data.player;

import com.minecraft.moonlake.mmorpg.api.mount.Mount;

import java.util.List;

/**
 * Created by MoonLake on 2016/6/19.
 */
public class MMORPGPlayerMountData {

    private final String name;
    private List<Mount> mounts;

    public MMORPGPlayerMountData(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setMounts(List<Mount> mounts) {

        this.mounts = mounts;
    }

    public List<Mount> getMounts() {

        return mounts;
    }
}
