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
 
  
package com.minecraft.moonlake.mmorpg.api.event.pet;

import com.minecraft.moonlake.mmorpg.api.event.MMORPGEvent;
import com.minecraft.moonlake.mmorpg.api.pet.Pet;

/**
 * Created by MoonLake on 2016/5/22.
 */
public abstract class MMORPGPetEvent extends MMORPGEvent {

    private Pet pet;

    public MMORPGPetEvent(Pet pet) {

        this.pet = pet;
    }

    /**
     * 获取月色之湖 Pet 宠物对象
     *
     * @return 宠物
     */
    public final Pet getPet() {

        return pet;
    }
}
