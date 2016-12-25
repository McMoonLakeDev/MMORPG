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

import com.minecraft.moonlake.api.itemlib.potion.CustomPotionEffect;
import com.minecraft.moonlake.mmorpg.api.adapter.effect.BukkitPotionEffect;
import com.minecraft.moonlake.mmorpg.api.item.expendable.ExpendableType;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

/**
 * Created by MoonLake on 2016/6/14.
 */
public final class PotionManager extends ItemManager {

    /**
     * 获取指定物品栈是否是药水消耗品
     *
     * @param item 物品栈
     * @return true 是药水消耗品 else 不是
     */
    public static boolean isPotion(ItemStack item) {

        if(item != null && item.getType() == ExpendableType.POTION.getMaterial()) {

            return ItemManager.hasTagKey(item, "Potion");
        }
        return false;
    }

    /**
     * 获取指定物品栈的药水消耗品对象
     *
     * @param item 物品栈
     * @return 药水消耗品对象 异常或没有则返回 null
     */
    public static Potion getPotion(ItemStack item) {

        if(isPotion(item)) {

            Set<CustomPotionEffect> potionEffects = ItemManager.getLibrary().getCustomPoionEffectList(item);

            if(potionEffects != null && !potionEffects.isEmpty()) {

                CustomPotionEffect potionEffect = potionEffects.toArray(new CustomPotionEffect[] { })[0];

                if(potionEffect != null) {

                    PotionType potionType = BukkitPotionEffect.getPotionTypeFromBukkitPotionEffectTypeId(potionEffect.getId());

                    if(potionType != null) {

                        Potion potion = potionType.newInstance(item.getAmount());
                        potion.setReqLevel(potionEffect.getAmplifier());
                        potion.setValue(potionEffect.getDuration());

                        return potion;
                    }
                }
            }
        }
        return null;
    }
}
