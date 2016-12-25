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
 
  
package com.minecraft.moonlake.mmorpg.api.pet.excel;

import com.minecraft.moonlake.mmorpg.api.pet.AbstractPet;
import com.minecraft.moonlake.mmorpg.api.pet.PetType;
import org.bukkit.DyeColor;
import org.bukkit.entity.Wolf;

/**
 * Created by MoonLake on 2016/5/21.
 */
public class PetDog extends AbstractPet {

    public PetDog() {

        super(PetType.DOG);
    }

    public PetDog(String name) {

        super(PetType.DOG, name);
    }

    @Override
    protected void init() {

        super.init();

        Wolf dog = (Wolf) getEntity();
        dog.setOwner(getOwner().getBukkitPlayer());
        dog.setCollarColor(DyeColor.RED);
    }

    /**
     * 更新此宠物的状态
     */
    @Override
    public void update() {

    }
}
