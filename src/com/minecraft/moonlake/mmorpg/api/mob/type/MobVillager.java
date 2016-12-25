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
 
  
package com.minecraft.moonlake.mmorpg.api.mob.type;

import com.minecraft.moonlake.mmorpg.api.mob.AbstractMob;
import com.minecraft.moonlake.mmorpg.api.mob.MobType;
import org.bukkit.entity.Villager;

/**
 * Created by MoonLake on 2016/7/3.
 */
public class MobVillager extends AbstractMob {

    private VillagerType villagerType;

    public MobVillager(org.bukkit.entity.Entity entity) {

        super(MobType.VILLAGER, entity);

        setVillagerType(VillagerType.FARMER);
    }

    @Override
    public Villager asLiving() {

        return (Villager) super.asLiving();
    }

    /**
     * 获取村民的村民类型
     *
     * @return 村民类型
     */
    public VillagerType getVillagerType() {

        return villagerType;
    }

    /**
     * 设置村民的村民类型
     *
     * @param villagerType 村民类型
     */
    public void setVillagerType(VillagerType villagerType) {

        this.villagerType = villagerType;
        this.asLiving().setProfession(Villager.Profession.valueOf(villagerType.name()));
    }
}
