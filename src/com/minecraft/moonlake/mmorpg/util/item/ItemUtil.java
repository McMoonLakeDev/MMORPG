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
 
  
package com.minecraft.moonlake.mmorpg.util.item;

import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.item.Item;
import com.minecraft.moonlake.mmorpg.item.attribute.AttributeType;
import com.minecraft.moonlake.mmorpg.item.attribute.ItemAttribute;
import com.minecraft.moonlake.mmorpg.item.base.BaseType;
import com.minecraft.moonlake.mmorpg.item.base.attackdamage.ItemAttackDamage;
import com.minecraft.moonlake.mmorpg.item.base.attackdamage.ItemAttackDamageType;
import com.minecraft.moonlake.mmorpg.item.base.attackdamage.NormalAttackDamage;
import com.minecraft.moonlake.mmorpg.item.base.attackdamage.ScopeAttackDamage;
import com.minecraft.moonlake.mmorpg.item.base.attackspeed.ItemAttackSpeed;
import com.minecraft.moonlake.mmorpg.item.base.attackspeed.ItemAttackSpeedType;
import com.minecraft.moonlake.mmorpg.item.base.defense.ItemDefense;
import com.minecraft.moonlake.mmorpg.item.base.defense.ItemDefenseType;
import com.minecraft.moonlake.mmorpg.item.quality.QualityType;
import com.minecraft.moonlake.mmorpg.item.require.ItemRequire;
import com.minecraft.moonlake.mmorpg.item.require.RequireType;
import com.minecraft.moonlake.mmorpg.item.require.excel.MinLevel;
import com.minecraft.moonlake.mmorpg.item.require.excel.NotDiscard;
import com.minecraft.moonlake.mmorpg.item.require.excel.NotTrade;
import com.minecraft.moonlake.mmorpg.item.require.excel.RoleRequire;
import com.minecraft.moonlake.mmorpg.api.role.RoleType;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoonLake on 2016/5/14.
 */
public final class ItemUtil implements Item {

    private final MMORPG main;

    public ItemUtil(MMORPG main) {

        this.main = main;
    }

    /**
     * 获取主类API实例对象
     *
     * @return MMORPG
     */
    public MMORPG getInstance() {

        return main;
    }

    /**
     * 设置物品栈的特殊需求属性
     *
     * @param item     物品栈
     * @param requires 特殊需求
     */
    @Override
    public void setItemRequire(ItemStack item, ItemRequire... requires) {

        for(ItemRequire require : requires) {

            require.setItemRequire(item);
        }
    }

    /**
     * 给物品栈设置品质类型
     *
     * @param item 物品栈
     * @param type 品质类型
     */
    @Override
    public void setItemQuality(ItemStack item, QualityType type) {

        getInstance().getMoonLake().getItemlib().setName(item, type.getColor() +
                (item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name()));
    }

    /**
     * 获取物品栈的品质类型
     *
     * @param item 物品栈
     * @return 品质类型 没有则返回 null
     */
    @Override
    public QualityType hasQuality(ItemStack item) {

        String name = ItemManager.getItemDisplayName(item);

        if(name != null) {


        }
        return null;
    }

    /**
     * 设置物品栈的攻击伤害
     *
     * @param item   物品栈
     * @param damage 伤害
     */
    @Override
    public void setItemAttackDamage(ItemStack item, ItemAttackDamage damage) {

        damage.setItemAttackDamage(item);
    }

    /**
     * 设置物品栈的防御力
     *
     * @param item    物品栈
     * @param defense 防御
     */
    @Override
    public void setItemDefense(ItemStack item, ItemDefense defense) {

        defense.setItemDefense(item);
    }

    /**
     * 设置物品栈的攻击速度
     *
     * @param item        物品栈
     * @param attackSpeed 攻击速度
     */
    @Override
    public void setItemAttackSpeed(ItemStack item, ItemAttackSpeed attackSpeed) {

        attackSpeed.setItemAttackSpeed(item);
    }

    /**
     * 获取物品栈是否拥有特殊属性
     *
     * @param item 物品栈
     * @return 物品特殊属性集合 没有则返回 null
     */
    @Override
    public List<ItemAttribute> hasAttribute(ItemStack item) {

        if(item != null && item.getType() != Material.AIR) {

            List<String> loreList = getInstance().getMoonLake().getLorelib().getLore(item);

            if(loreList != null && loreList.size() >= 1) {

                List<ItemAttribute> attList = new ArrayList<>();

                for(String lore : loreList) {

                    for(AttributeType attType : AttributeType.values()) {

                        if(!lore.equalsIgnoreCase("") && lore.length() >= 1 && lore.contains(":")) {

                            String temp = lore.substring(0, lore.lastIndexOf(":"));

                            if (temp.equalsIgnoreCase(StringUtil.color(attType.getName()))) {


                            }
                        }
                    }
                }
                return attList.size() >= 1 ? attList : null;
            }
        }
        return null;
    }

    /**
     * 获取物品栈是否拥有攻击速度属性
     *
     * @param item 物品栈
     * @return 物品攻击速度属性 没有则返回 null
     */
    @Override
    public ItemAttackSpeed hasAttackSpeed(ItemStack item) {

        if (item != null && item.getType() != Material.AIR) {

            ItemAttackSpeed attackSpeed = null;
            List<String> loreList = getInstance().getMoonLake().getLorelib().getLore(item);

            if (loreList != null && loreList.size() >= 1) {

                for (String lore : loreList) {

                    for(ItemAttackSpeedType attackSpeedType : ItemAttackSpeedType.values()) {

                        if(!lore.equalsIgnoreCase("") && lore.length() >= 1) {

                            if(lore.equalsIgnoreCase(StringUtil.color(attackSpeedType.getName()))) {

                                attackSpeed = attackSpeedType.newInstance();

                                if(attackSpeed != null) {

                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return attackSpeed;
        }
        return null;
    }

    /**
     * 获取物品栈是否拥有攻击伤害属性
     *
     * @param item 物品栈
     * @return 物品攻击伤害属性 没有则返回 null
     */
    @Override
    public ItemAttackDamage hasAttackDamage(ItemStack item) {

        if (item != null && item.getType() != Material.AIR) {

            ItemAttackDamage attackDamage = null;
            List<String> loreList = getInstance().getMoonLake().getLorelib().getLore(item);

            if (loreList != null && loreList.size() >= 1) {

                BaseType baseType = BaseType.ATTACK_DAMAGE;

                for (String lore : loreList) {

                    if(!lore.equalsIgnoreCase("") && lore.length() >= 1 && lore.startsWith(StringUtil.color(baseType.getName()))) {

                        String damageValue = lore.substring(lore.lastIndexOf(":") + 1);
                        damageValue = StringUtil.fColor(damageValue);

                        for(ItemAttackDamageType attackDamageType : ItemAttackDamageType.values()) {

                            if(attackDamageType == ItemAttackDamageType.SCOPE_DAMAGE) {

                                if(damageValue.contains(String.valueOf(attackDamageType.getSuffix()))) {

                                    String[] values = damageValue.split(String.valueOf(attackDamageType.getSuffix()));
                                    ScopeAttackDamage scopeAttackDamage = new ScopeAttackDamage();
                                    scopeAttackDamage.setMin(Integer.parseInt(values[0].replaceAll(" ", "")));
                                    scopeAttackDamage.setMax(Integer.parseInt(values[1].replaceAll(" ", "")));

                                    attackDamage = scopeAttackDamage;
                                }
                            }
                            else {

                                if(damageValue.charAt(damageValue.length() - 1) == attackDamageType.getSuffix()) {

                                    attackDamage = attackDamageType.newInstance();
                                    damageValue = damageValue.replaceAll(" ", "");

                                    if(attackDamage instanceof NormalAttackDamage) {

                                        attackDamage.setDamage(Integer.parseInt(damageValue));
                                    }
                                }
                            }

                            if(attackDamage != null) {

                                break;
                            }
                        }
                    }
                }
            }
            return attackDamage;
        }
        return null;
    }

    /**
     * 获取物品栈是否拥有防御属性
     *
     * @param item 物品栈
     * @return 物品防御属性 没有则返回 null
     */
    @Override
    public ItemDefense hasDefense(ItemStack item) {

        if (item != null && item.getType() != Material.AIR) {

            ItemDefense defense = null;
            List<String> loreList = getInstance().getMoonLake().getLorelib().getLore(item);

            if (loreList != null && loreList.size() >= 1) {

                BaseType baseType = BaseType.DEFENSE;

                for (String lore : loreList) {

                    if(!lore.equalsIgnoreCase("") && lore.length() >= 1 && lore.startsWith(StringUtil.color(baseType.getName()))) {

                        String defenseValue = lore.substring(lore.lastIndexOf(":") + 1).replaceAll("§([a-f0-9rk]?)", "");

                        for(ItemDefenseType defenseType : ItemDefenseType.values()) {

                            if(defenseValue.endsWith(String.valueOf(defenseType.getSuffix()))) {

                                defense = defenseType.newInstance();
                                defenseValue = defenseValue.replaceAll(" ", "");
                                defense.setDefense(Integer.parseInt(defenseValue));
                            }
                            if(defense != null) {

                                break;
                            }
                        }
                    }
                }
            }
            return defense;
        }
        return null;
    }

    /**
     * 获取物品栈是否拥有特殊需求属性
     *
     * @param item 物品栈
     * @return 物品特殊需求属性集合 没有则返回 null
     */
    @Override
    public List<ItemRequire> hasRequire(ItemStack item) {

        if(item != null && item.getType() != Material.AIR) {

            List<String> loreList = getInstance().getMoonLake().getLorelib().getLore(item);

            if (loreList != null && loreList.size() >= 1) {

                List<ItemRequire> reqList = new ArrayList<>();

                for (String lore : loreList) {

                    for(RequireType reqType : RequireType.values()) {

                        if(!lore.equalsIgnoreCase("") && lore.length() >= 1 && lore.contains(":")) {

                            String temp = lore.substring(0, lore.lastIndexOf(":"));

                            if (temp.equalsIgnoreCase(StringUtil.color(reqType.getName()))) {

                                ItemRequire req = reqType.newInstance();
                                String reqValue = lore.substring(lore.lastIndexOf(":") + 1).replaceAll(" ", "");
                                reqValue = StringUtil.fColor(reqValue);

                                if(req instanceof RoleRequire) {

                                    RoleRequire roleRequire = (RoleRequire)req;
                                    roleRequire.setRoleType(RoleType.fromName(reqValue));

                                    reqList.add(roleRequire);
                                }
                                else if(req instanceof MinLevel) {

                                    MinLevel minLevel = (MinLevel)req;
                                    minLevel.setMinLevel(Integer.parseInt(reqValue));

                                    reqList.add(minLevel);
                                }
                                else if(req instanceof NotDiscard) {

                                    NotDiscard notDiscard = (NotDiscard)req;
                                    notDiscard.setFlag(StringUtil.toFlag(reqValue));

                                    reqList.add(notDiscard);
                                }
                                else if(req instanceof NotTrade) {

                                    NotTrade notTrade = (NotTrade)req;
                                    notTrade.setFlag(StringUtil.toFlag(reqValue));

                                    reqList.add(notTrade);
                                }
                                else {

                                    reqList.add(req);
                                }
                            }
                        }
                    }
                }
                return reqList.size() >= 1 ? reqList : null;
            }
        }
        return null;
    }

    /**
     * 将物品栈的数据正确的进行排序
     *
     * @param item 物品栈
     */
    @Override
    public void sort(ItemStack item) {

        List<String> finalLore = new ArrayList<>();

        // 1. ItemStack Attack Speed
        ItemAttackSpeed attackSpeed = hasAttackSpeed(item);
        if(attackSpeed != null) {

            finalLore.add(attackSpeed.getType().getName());
            finalLore.add("");  // new line
        }

        // 2. ItemStack Attack Damage
        ItemAttackDamage attackDamage = hasAttackDamage(item);
        if(attackDamage != null) {

            finalLore.add(attackDamage.getLore());
            finalLore.add("");  // new line
        }

        // 2. ItemStack Defense
        ItemDefense defense = hasDefense(item);
        if(defense != null) {

            finalLore.add(defense.getLore());
            finalLore.add("");  // new line
        }

        // 3. ItemStack Require
        List<ItemRequire> requireList = hasRequire(item);
        if(requireList != null) {

            for(ItemRequire require : requireList) {

                finalLore.add(require.getLore());
            }
            finalLore.add("");  // new line
        }

        // 4. ItemStack Attribute
        List<ItemAttribute> attributeList = hasAttribute(item);
        if(attributeList != null) {

            for(ItemAttribute att : attributeList) {

                finalLore.add(att.getLore());
            }
        }

        if(finalLore.size() >= 1) {

            getInstance().getMoonLake().getLorelib().setLore(item, finalLore);
        }
    }

    /**
     * 创建物品攻击力属性
     *
     * @param type 类型
     * @return 物品攻击力对象
     */
    @Override
    public <T extends ItemAttackDamage> T createItemAttackDamage(ItemAttackDamageType type) {

        return type.newInstance();
    }

    /**
     * 创建物品攻击速度属性
     *
     * @param type 类型
     * @return 物品攻击速度对象
     */
    @Override
    public <T extends ItemAttackSpeed> T createItemAttackSpeed(ItemAttackSpeedType type) {

        return type.newInstance();
    }

    /**
     * 创建物品防御力属性
     *
     * @param type 类型
     * @return 物品防御力对象
     */
    @Override
    public <T extends ItemDefense> T createItemDefense(ItemDefenseType type) {

        return type.newInstance();
    }
}
