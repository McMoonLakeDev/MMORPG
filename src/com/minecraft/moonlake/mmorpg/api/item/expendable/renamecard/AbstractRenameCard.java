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
 
  
package com.minecraft.moonlake.mmorpg.api.item.expendable.renamecard;

import com.minecraft.moonlake.mmorpg.api.item.expendable.AbstractExpendable;
import com.minecraft.moonlake.mmorpg.api.item.expendable.ExpendableType;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/20.
 */
public abstract class AbstractRenameCard extends AbstractExpendable implements RenameCard {

    private final RenameCardType type;

    public AbstractRenameCard(RenameCardType type) {

        this(type, 1);
    }

    public AbstractRenameCard(RenameCardType type, int amount) {

        super(ExpendableType.RENAME_CARD, amount, type.getName());

        this.type = type;
    }

    /**
     * 获取改名卡的类型
     *
     * @return 改名卡类型
     */
    @Override
    public RenameCardType getRenameCardType() {

        return type;
    }

    /**
     * 获取 Bukkit 的物品栈对象
     *
     * @return 物品栈对象
     */
    @Override
    public ItemStack getItem() {

        String lore = StringUtil.stringClone(ItemManager.RENAME_CARD_LORE)
                .replace("%method", getRenameCardType().getMethod())
                .replace("%type", getRenameCardType().getName().replace("改名卡", ""));

        ItemStack item = ItemManager.setTagValue(super.getItem(), "CanDestroy", getRenameCardType().getType());
        item = getInstance().getMoonLake().getItemlib().addFlags(item, ItemFlag.HIDE_DESTROYS);

        return getInstance().getMoonLake().getItemlib().setLore(item, lore.split("\n"));
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
