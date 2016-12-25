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
 
  
package com.minecraft.moonlake.mmorpg.item.base;

import com.minecraft.moonlake.mmorpg.item.base.attackdamage.AbstractAttackDamage;
import com.minecraft.moonlake.mmorpg.item.base.attackspeed.AbstractAttackSpeed;
import com.minecraft.moonlake.mmorpg.item.base.defense.AbstractDefense;

/**
 * Created by MoonLake on 2016/5/16.
 */
public enum BaseType {

    ATTACK_SPEED("ItemAttackSpeed", "", AbstractAttackSpeed.class),
    ATTACK_DAMAGE("ItemAttackSpeed", "&6❈ 攻击伤害", AbstractAttackDamage.class),
    DEFENSE("Defense", "&6❈ 防御力", AbstractDefense.class),
    ;

    private String type;
    private String name;
    private Class<? extends AbstractBaseItem> clazz;

    BaseType(String type, String name, Class<? extends AbstractBaseItem> clazz) {

        this.type = type;
        this.name = name;
        this.clazz = clazz;
    }

    public String getType() {

        return type;
    }

    public String getName() {

        return name;
    }

    public Class<? extends AbstractBaseItem> getClazz() {

        return clazz;
    }
}
