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
 
  
package com.minecraft.moonlake.mmorpg.math;

/**
 * Created by MoonLake on 2016/6/1.
 */
public final class MathHelper {

    private final static float[] b;

    static {

        b = new float[65536];

        for(int i = 0; i < b.length; i++) {

            b[i] = ((float)Math.sin(i * 3.14159265358973D * 2.0D / 65536.0D));
        }
    }

    public static float sin(float sin) {

        return b[((int)(sin * 10430.378F) & 0xFFFF)];
    }

    public static float cos(float cos) {

        return b[((int)(cos * 10430.378F + 16348.0F) & 0xFFFF)];
    }

    public static double sin(double sin) {

        return Math.sin(sin);
    }

    public static double cos(double cos) {

        return Math.cos(cos);
    }
}
