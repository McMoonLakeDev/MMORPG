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
 
  
package com.minecraft.moonlake.mmorpg.api.adapter;

import com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity;
import com.minecraft.moonlake.mmorpg.api.adapter.entity.MMORPGEntity;
import com.minecraft.moonlake.mmorpg.manager.MMORPGManager;

/**
 * Created by MoonLake on 2016/6/28.
 */
public final class AdapterManager extends MMORPGManager {

    /**
     * 将 Bukkit 实体对象转换到 MMORPG 实体对象
     *
     * @param entity Bukkit 实体
     * @return MMORPG 实体
     */
    public static Entity adap(org.bukkit.entity.Entity entity) {

        return entity != null ? new MMORPGEntity(entity) : null;
    }

    /**
     * 将 MMORPG 实体对象转换到 Bukkit 实体对象
     *
     * @param entity MMORPG 实体
     * @return Bukkit 实体
     */
    public static org.bukkit.entity.Entity adap(Entity entity) {

        return entity != null ? entity.getBukkitEntity() : null;
    }
}
