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
 
  
package com.minecraft.moonlake.mmorpg.nms;

import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.ContainerAnvil;
import net.minecraft.server.v1_10_R1.EntityHuman;
import net.minecraft.server.v1_10_R1.EntityPlayer;

/**
 * Created by MoonLake on 2016/6/21.
 */
public class AnvilContainer extends ContainerAnvil {

    public AnvilContainer(Object entityPlayer) {

        this((EntityPlayer)entityPlayer);
    }

    public AnvilContainer(EntityPlayer entityPlayer) {

        super(entityPlayer.inventory, entityPlayer.world, BlockPosition.ZERO, entityPlayer);
    }

    @Override
    public boolean a(EntityHuman entityhuman) {

        return true;
    }
}
