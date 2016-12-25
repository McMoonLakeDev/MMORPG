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
 
  
package com.minecraft.moonlake.mmorpg.api.item.currency.type;

import com.minecraft.moonlake.mmorpg.api.item.currency.AbstractCurrency;
import com.minecraft.moonlake.mmorpg.api.item.currency.CurrencyType;

/**
 * Created by MoonLake on 2016/6/12.
 */
public class GoldBroken extends AbstractCurrency {

    public GoldBroken() {

        this(1);
    }

    public GoldBroken(int amount) {

        super(CurrencyType.GOLD_BROKEN, amount);
    }
}
