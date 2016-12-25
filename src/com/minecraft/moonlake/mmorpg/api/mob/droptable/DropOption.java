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
 
  
package com.minecraft.moonlake.mmorpg.api.mob.droptable;

/**
 * Created by MoonLake on 2016/8/4.
 */
public class DropOption {

    private int minAmount;
    private int maxAmount;
    private double chance;

    public DropOption(int minAmount, int maxAmount, double chance) {

        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.chance = chance;
    }

    public int getMinAmount() {

        return minAmount;
    }

    public void setMinAmount(int minAmount) {

        this.minAmount = minAmount;
    }

    public int getMaxAmount() {

        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {

        this.maxAmount = maxAmount;
    }

    public double getChance() {

        return chance;
    }

    public void setChance(double chance) {

        if(chance < 0d) {

            chance = 0d;
        }
        if(chance > 1d) {

            chance = 1d;
        }
        this.chance = chance;
    }

    @Override
    public String toString() {

        return minAmount + "-" + maxAmount + " " + chance;
    }
}
