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
 
  
package com.minecraft.moonlake.mmorpg.item;

import com.minecraft.moonlake.api.itemlib.Itemlib;
import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.MMORPGCore;
import com.minecraft.moonlake.mmorpg.item.attribute.AbstractAttribute;
import com.minecraft.moonlake.mmorpg.item.attribute.ItemAttribute;
import com.minecraft.moonlake.mmorpg.item.base.attackdamage.ItemAttackDamage;
import com.minecraft.moonlake.mmorpg.item.base.attackspeed.ItemAttackSpeed;
import com.minecraft.moonlake.mmorpg.item.base.defense.ItemDefense;
import com.minecraft.moonlake.mmorpg.item.quality.QualityType;
import com.minecraft.moonlake.mmorpg.item.require.AbstractRequire;
import com.minecraft.moonlake.mmorpg.item.require.ItemRequire;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoonLake on 2016/5/15.
 */
public class ItemBase implements MMORPGCore {

    private ItemStack item;
    private QualityType quality;
    private ItemAttackDamage attackDamage;
    private ItemAttackSpeed attackSpeed;
    private ItemDefense defense;
    private List<ItemRequire> requireList;
    private List<ItemAttribute> attributeList;
    private final static MMORPG main;

    // hide item stack the attribute
    private final boolean HIDE_ATTRIBUTE = true;

    static {

        main = MMORPGPlugin.getInstances();
    }

    public ItemBase(int id) {

        this(id, 0, 1, "BaseItem");
    }

    public ItemBase(int id, int data) {

        this(id, data, 1, "BaseItem");
    }

    public ItemBase(int id, int data, int amount) {

        this(id, data, amount, "BaseItem");
    }

    public ItemBase(int id, int data, int amount, String name) {

        this.quality = null;
        this.attackSpeed = null;
        this.attackDamage = null;
        this.requireList = new ArrayList<>();
        this.attributeList = new ArrayList<>();
        this.item = getInstance().getMoonLake().getItemlib().create(id, data, amount, name);
    }

    public ItemBase(Material id) {

        this(id, 0, 1, "BaseItem");
    }

    public ItemBase(Material id, int data) {

        this(id, data, 1, "BaseItem");
    }

    public ItemBase(Material id, int data, int amount) {

        this(id, data, amount, "BaseItem");
    }

    public ItemBase(Material id, int data, int amount, String name) {

        this(id.getId(), data, amount, name);
    }

    @Override
    public MMORPG getInstance() {

        return main;
    }

    public ItemStack getItem() {

        if(getAttackSpeed() != null) {

            getAttackSpeed().setItemAttackSpeed(item);

            // change item stack attack speed
            double count = getAttackSpeed().getType().getCount();
            item = getInstance().getMoonLake().getItemlib().setItemAttackSpeed(item, count, false, Itemlib.AttributeType.Slot.MAIN_HAND);
        }
        if(getAttackDamage() != null) {

            getAttackDamage().setItemAttackDamage(item);

            // change item stack attack damage
            int damage = getAttackDamage().getDamage();
            item = getInstance().getMoonLake().getItemlib().setItemAttackDamage(item, damage, false, Itemlib.AttributeType.Slot.MAIN_HAND);
        }
        if(getDefense() != null) {

            getDefense().setItemDefense(item);

            // change item stack defense
            int count = getDefense().getDefense();
            item = getInstance().getMoonLake().getItemlib().setItemArmorDefense(item, count, false, getDefense().getSlot());
        }
        if(getRequireList().size() >= 1) {

            for(ItemRequire require : getRequireList()) {

                require.setItemRequire(item);
            }
        }
        if(getAttributeList().size() >= 1) {

            for(ItemAttribute attribute : getAttributeList()) {

                getInstance().getMoonLake().getLorelib().addLore(item, attribute.getLore());
            }
        }
        if(getQuality() != null) {

            getInstance().getItem().setItemQuality(item, getQuality());
        }
        getInstance().getItem().sort(item);

        if(HIDE_ATTRIBUTE) {

            item = getInstance().getMoonLake().getItemlib().addFlags(item, ItemFlag.HIDE_ATTRIBUTES);
        }
        return item;
    }

    public <T extends ItemAttackSpeed> void setAttackSpeed(T attackSpeed) {

        this.attackSpeed = attackSpeed;
    }

    public <T extends ItemAttackDamage> void setAttackDamage(T attackDamage) {

        this.attackDamage = attackDamage;
    }

    public <T extends ItemDefense> void setDefense(T defense) {

        this.defense = defense;
    }

    public <T extends ItemAttackDamage> T getAttackDamage() {

        return (T)attackDamage;
    }

    public <T extends ItemAttackSpeed> T getAttackSpeed() {

        return (T)attackSpeed;
    }

    public <T extends ItemDefense> T getDefense() {

        return (T)defense;
    }

    public void setQuality(QualityType quality) {

        this.quality = quality;
    }

    public QualityType getQuality() {

        return quality;
    }

    public void addRequire(AbstractRequire require) {

        this.requireList.add(require);
    }

    public void addAttribute(AbstractAttribute attribute) {

        this.attributeList.add(attribute);
    }

    public List<ItemRequire> getRequireList() {

        return requireList;
    }

    public List<ItemAttribute> getAttributeList() {

        return attributeList;
    }
}
