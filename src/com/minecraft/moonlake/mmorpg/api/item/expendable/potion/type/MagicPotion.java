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
 
  
package com.minecraft.moonlake.mmorpg.api.item.expendable.potion.type;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.item.expendable.potion.AbstractPotion;
import com.minecraft.moonlake.mmorpg.api.item.expendable.potion.PotionType;

/**
 * Created by MoonLake on 2016/6/15.
 */
public class MagicPotion extends AbstractPotion {

    public MagicPotion() {

        this(1);
    }

    public MagicPotion(int amount) {

        this(amount, 0, 1);
    }

    public MagicPotion(int amount, int reqLevel, int value) {

        super(PotionType.MAGIC_POTION, amount, reqLevel, value);
    }

    /**
     * 将药水使用给指定玩家
     *
     * @param mmorpgPlayer 玩家
     */
    @Override
    public void useItem(MMORPGPlayer mmorpgPlayer) {

        if(mmorpgPlayer != null) {

            mmorpgPlayer.giveMagic(getValue());
            mmorpgPlayer.l18n("player.expendable.potion.use", getPotionType().getName(), getValue(), getPotionType().getValueType().getName());

            super.useItem(mmorpgPlayer);
        }
    }
}
