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
 
  
package com.minecraft.moonlake.mmorpg.resources;

/**
 * Created by MoonLake on 2016/6/1.
 */
public final class GUIPoint {

    private GUIPoint() {}

    public final static int[][] SKILL_GUI_TALENTS = {

            {2, 2}, {3, 2}, {4, 2}, {5, 2}, {6, 2},
            {2, 3}, {3, 3}, {4, 3}, {5, 3}, {6, 3}
    };

    public final static int[][] SKILL_GUI_PASSIVE = {

            {2, 5}
    };

    public final static int[][] SKILL_GUI_ACTIVE = {

            {3, 5}, {4, 5}, {5, 5}, {6, 5}
    };

    public final static int[][] SKILL_GUI_SKILL_POINT = {

            {8, 2}
    };

    public final static int[][] SKILL_GUI_ULTIMATE = {

            {8, 5}
    };

    public final static int[][] PLAYER_GUI_SKILL_BUTTON = {

            {4, 2}
    };
}
