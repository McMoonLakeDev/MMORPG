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

import com.minecraft.moonlake.mmorpg.api.role.RoleType;

/**
 * Created by MoonLake on 2016/5/20.
 */
public class MMORPGPlayerData {

    private final String name;

    private RoleType roleType;
    private int exp;
    private int level;
    private double health;
    private double maxHealth;
    private int magic;
    private int maxMagic;
    private int soul;
    private int maxSoul;

    public MMORPGPlayerData(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }

    public RoleType getRoleType() {

        return roleType;
    }

    public void setRoleType(RoleType roleType) {

        this.roleType = roleType;
    }

    public int getExp() {

        return exp;
    }

    public void setExp(int exp) {

        this.exp = exp;
    }

    public int getLevel() {

        return level;
    }

    public void setLevel(int level) {

        this.level = level;
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

    public int getMagic() {

        return magic;
    }

    public void setMagic(int magic) {

        this.magic = magic;
    }

    public int getMaxMagic() {

        return maxMagic;
    }

    public void setMaxMagic(int maxMagic) {

        this.maxMagic = maxMagic;
    }

    public int getSoul() {

        return soul;
    }

    public void setSoul(int soul) {

        this.soul = soul;
    }

    public int getMaxSoul() {

        return maxSoul;
    }

    public void setMaxSoul(int maxSoul) {

        this.maxSoul = maxSoul;
    }
}
