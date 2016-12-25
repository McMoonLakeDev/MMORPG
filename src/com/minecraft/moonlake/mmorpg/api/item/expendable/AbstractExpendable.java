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
 
  
package com.minecraft.moonlake.mmorpg.api.item.expendable;

import com.minecraft.moonlake.mmorpg.api.item.ItemInnStack;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;

/**
 * Created by MoonLake on 2016/6/14.
 */
public abstract class AbstractExpendable extends ItemInnStack implements Expendable {

    private final ExpendableType type;

    public AbstractExpendable(ExpendableType type) {

        this(type, 1);
    }

    public AbstractExpendable(ExpendableType type, int amount) {

        this(type, amount, type.getName());
    }

    public AbstractExpendable(ExpendableType type, int amount, String name) {

        super(type.getMaterial(), type.getData(), amount, name);

        this.type = type;
    }

    /**
     * 获取消耗品的类型
     *
     * @return 消耗品类型
     */
    @Override
    public ExpendableType getType() {

        return type;
    }

    /**
     * 将消耗品使用给指定玩家
     *
     * @param mmorpgPlayer 玩家
     */
    public void useItem(MMORPGPlayer mmorpgPlayer) {


    }
}
