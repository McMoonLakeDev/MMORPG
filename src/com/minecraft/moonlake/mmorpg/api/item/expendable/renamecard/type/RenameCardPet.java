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
 
  
package com.minecraft.moonlake.mmorpg.api.item.expendable.renamecard.type;

import com.minecraft.moonlake.mmorpg.api.item.expendable.renamecard.AbstractRenameCard;
import com.minecraft.moonlake.mmorpg.api.item.expendable.renamecard.RenameCardType;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;

/**
 * Created by MoonLake on 2016/6/20.
 */
public class RenameCardPet extends AbstractRenameCard {

    public RenameCardPet() {

        this(1);
    }

    public RenameCardPet(int amount) {

        super(RenameCardType.PET_RENAME_CARD, amount);
    }

    /**
     * 将改名卡消耗品使用给指定玩家
     *
     * @param mmorpgPlayer 玩家
     */
    @Override
    public void useItem(MMORPGPlayer mmorpgPlayer) {

        super.useItem(mmorpgPlayer);
    }
}
