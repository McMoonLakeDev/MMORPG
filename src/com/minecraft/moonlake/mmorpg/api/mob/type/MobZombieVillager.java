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

import com.minecraft.moonlake.mmorpg.api.mob.MobType;
import org.bukkit.entity.Villager;

/**
 * Created by MoonLake on 2016/7/1.
 */
public class MobZombieVillager extends MobZombie {

    private VillagerType villagerType;

    public MobZombieVillager(org.bukkit.entity.Entity entity) {

        this(MobType.ZOMBIE_VILLAGER, entity);
    }

    protected MobZombieVillager(MobType mobType, org.bukkit.entity.Entity entity) {

        super(mobType, entity);

        setVillagerType(VillagerType.FARMER);
    }

    /**
     * 获取此怪物僵尸是否为村民僵尸
     *
     * @return true 则是村民僵尸 else 不是
     */
    public boolean isVillager() {

        return true;
    }

    /**
     * 获取僵尸村民的村民类型
     *
     * @return 村民类型
     */
    public VillagerType getVillagerType() {

        return villagerType;
    }

    /**
     * 设置僵尸村民的村民类型
     *
     * @param villagerType 村民类型
     */
    public void setVillagerType(VillagerType villagerType) {

        this.villagerType = villagerType;
        this.asLiving().setVillagerProfession(Villager.Profession.valueOf(villagerType.name()));
    }
}
