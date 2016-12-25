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
 
  
package com.minecraft.moonlake.mmorpg.manager;

import com.minecraft.moonlake.mmorpg.api.skill.combo.SkillComboType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by MoonLake on 2016/5/17.
 */
public final class SkillComboManager extends MMORPGManager {

    public static int convertCombo(SkillComboType[] combos) {

        int id = 0;

        for (SkillComboType combo : combos) {

            id <<= 2;
            id |= combo.getId();
        }
        return id;
    }

    public static int convertCombo(SkillComboType[] combos, int amount) {

        int id = 0;

        for (int i = 0; (i < combos.length) && (i < amount); i++) {

            id <<= 2;
            id |= combos[i].getId();
        }
        return id;
    }

    public static String getComboString(int combo) {

        if (combo == -1) {

            return "";
        }
        return getComboString(convertId(combo));
    }

    public static String getComboString(List<SkillComboType> combos)  {

        if (combos == null) {

            return "";
        }

        String result = "";

        for (SkillComboType combo : combos) {

            if (result.length() > 0) {

                result = result + " + ";
            }
            result = result + combo.getType();
        }
        return result;
    }

    public static List<SkillComboType> convertId(int id) {

        ArrayList<SkillComboType> combos = new ArrayList<>();

        while (id > 0) {

            SkillComboType click = SkillComboType.getById(id & 0x3);

            if (click == null) {

                return null;
            }
            combos.add(click);
            id >>= 2;
        }
        Collections.reverse(combos);
        return combos;
    }
}
