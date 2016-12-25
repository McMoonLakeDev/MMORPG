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
 
  
package com.minecraft.moonlake.mmorpg.api.skill.data;

import com.minecraft.moonlake.mmorpg.api.skill.Skill;
import com.minecraft.moonlake.mmorpg.api.skill.passive.Passive;
import com.minecraft.moonlake.mmorpg.api.skill.talent.Talent;
import com.minecraft.moonlake.mmorpg.api.skill.ultimate.Ultimate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by MoonLake on 2016/6/1.
 */
public class MMORPGPlayerSkillData {

    private final String name;
    private Passive passive;
    private List<Skill> skills;
    private List<Talent> talents;
    private Ultimate ultimate;
    private int skillPoint;

    public MMORPGPlayerSkillData(String name) {

        this.name = name;
    }

    public List<Skill> getSkills() {

        return skills;
    }

    public void setSkills(List<Skill> skills) {

        this.skills = skills;
    }

    public void setSkills(Set<Skill> skills) {

        this.skills = new ArrayList<>(skills);
    }

    public List<Talent> getTalents() {

        return talents;
    }

    public void setTalents(List<Talent> talents) {

        this.talents = talents;
    }

    public void setTalents(Set<Talent> talents) {

        this.talents = new ArrayList<>(talents);
    }

    public int getSkillPoint() {

        return skillPoint;
    }

    public void setSkillPoint(int skillPoint) {

        this.skillPoint = skillPoint;
    }

    public String getName() {

        return name;
    }

    public Passive getPassive() {

        return passive;
    }

    public void setPassive(Passive passive) {

        this.passive = passive;
    }

    public Ultimate getUltimate() {

        return ultimate;
    }

    public void setUltimate(Ultimate ultimate) {

        this.ultimate = ultimate;
    }
}
