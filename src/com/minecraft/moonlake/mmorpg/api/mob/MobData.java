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
 
  
package com.minecraft.moonlake.mmorpg.api.mob;

import com.minecraft.moonlake.mmorpg.api.mob.droptable.DropOption;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/7/8.
 */
public class MobData {

    private final MobType mobType;
    private final String dataName;
    private double health;
    private double maxHealth;
    private int level;
    private int dropExp;
    private Map<ItemStack, DropOption> dropItemMap;
    private String displayName;

    public MobData(MobType mobType, String dataName) {

        this.mobType = mobType;
        this.dataName = dataName;
        this.health = 20d;
        this.maxHealth = 20d;
        this.level = 0;
        this.dropExp = 0;
        this.dropItemMap = new HashMap<>();
        this.displayName = mobType.getName();
    }

    public MobType getMobType() {

        return mobType;
    }

    public String getDataName() {

        return dataName;
    }

    public double getHealth() {

        return health;
    }

    public void setHealth(double health) {

        this.health = health;
    }

    public double getMaxHealth() {

        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {

        this.maxHealth = maxHealth;
    }

    public int getLevel() {

        return level;
    }

    public void setLevel(int level) {

        this.level = level;
    }

    public int getDropExp() {

        return dropExp;
    }

    public void setDropExp(int dropExp) {

        this.dropExp = dropExp;
    }

    public Map<ItemStack, DropOption> getDropItems() {

        return dropItemMap;
    }

    public void setDropItems(Map<ItemStack, DropOption> dropItemMap) {

        this.dropItemMap = dropItemMap;
    }

    public void addDropItems(Map<ItemStack, DropOption> dropItemMap) {

        this.dropItemMap.putAll(dropItemMap);
    }

    public String getDisplayName() {

        return displayName;
    }

    public void setDisplayName(String displayName) {

        this.displayName = displayName;
    }
}
