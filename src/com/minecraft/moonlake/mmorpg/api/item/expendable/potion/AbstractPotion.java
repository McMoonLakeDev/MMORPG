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
 
  
package com.minecraft.moonlake.mmorpg.api.item.expendable.potion;

import com.minecraft.moonlake.mmorpg.api.item.expendable.AbstractExpendable;
import com.minecraft.moonlake.mmorpg.api.item.expendable.ExpendableType;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import com.minecraft.moonlake.type.potion.PotionEnum;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/14.
 */
public abstract class AbstractPotion extends AbstractExpendable implements Potion {

    private final PotionType type;
    private int reqLevel;
    private int value;

    public AbstractPotion(PotionType type) {

        this(type, 1);
    }

    public AbstractPotion(PotionType type, int amount) {

        this(type, amount, 0, 1);
    }

    public AbstractPotion(PotionType type, int amount, int reqLevel, int value) {

        super(ExpendableType.POTION, amount, type.getName());

        this.type = type;
        this.reqLevel = reqLevel;
        this.value = value;
    }

    /**
     * 获取消耗品药水的类型
     *
     * @return 药水类型
     */
    public PotionType getPotionType() {

        return type;
    }

    /**
     * 复制 Potion 物品栈对象
     *
     * @return 复制的对象
     */
    public Potion clone() {

        return type.newInstance(getAmount());
    }

    /**
     * 获取 Bukkit 的物品栈对象
     *
     * @return 物品栈对象
     */
    public ItemStack getItem() {

        String lore = StringUtil.stringClone(PotionManager.POTION_LORE)
                .replace("%reqLevel", String.valueOf(reqLevel))
                .replace("%value", String.valueOf(value))
                .replace("%type", type.getValueType().getName());

        ItemStack potion = getInstance().getMoonLake().getItemlib().createCustomPotion(PotionEnum.POTION, getAmount(), type.getName(), type.getId(), reqLevel, value);
        potion = getInstance().getMoonLake().getLorelib().setLore(potion, lore.split("\n"));
        potion = ItemManager.setTagValue(potion, "Potion", type.getPotion());

        return getInstance().getMoonLake().getItemlib().addFlags(potion, ItemFlag.HIDE_POTION_EFFECTS);
    }

    /**
     * 获取药水的使用等级
     *
     * @return 使用等级
     */
    public int getReqLevel() {

        return reqLevel;
    }

    /**
     * 设置药水的使用等级
     *
     * @param reqLevel 使用等级
     */
    public void setReqLevel(int reqLevel) {

        this.reqLevel = reqLevel;
    }

    /**
     * 获取药水恢复的值
     *
     * @return 恢复的值
     */
    public int getValue() {

        return value;
    }

    /**
     * 设置药水恢复的值
     *
     * @param value 恢复的值
     */
    public void setValue(int value) {

        this.value = value;
    }

    /**
     * 将药水使用给指定玩家
     *
     * @param mmorpgPlayer 玩家
     */
    public void useItem(MMORPGPlayer mmorpgPlayer) {

        ItemStack obj = getItem().clone();
        obj.setAmount(1);

        mmorpgPlayer.removeItemStack(obj);

        super.useItem(mmorpgPlayer);
    }
}
