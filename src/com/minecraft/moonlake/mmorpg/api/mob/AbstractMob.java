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
 
  
package com.minecraft.moonlake.mmorpg.api.mob;

import com.minecraft.moonlake.mmorpg.api.adapter.entity.AbstractEntity;
import com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity;
import com.minecraft.moonlake.mmorpg.api.adapter.entity.equipment.EquipmentType;
import com.minecraft.moonlake.mmorpg.api.mob.droptable.DropOption;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/6/28.
 */
public abstract class AbstractMob extends AbstractEntity implements Mob {

    private final MobType type;
    private final String DISPLAYNAME_FORMAT = "[Lv.{0}] {1}";

    private int mobLevel;
    private int dropExp;
    private String displayName;
    private Map<ItemStack, DropOption> dropItemMap;
    private Map<EquipmentType, ItemStack> equipItemMap;

    public AbstractMob(MobType type, org.bukkit.entity.Entity entity) {

        super(entity);

        this.type = type;
        this.mobLevel = 0;
        this.dropExp = 0;
        this.dropItemMap = new HashMap<>();
        this.equipItemMap = new HashMap<>();

        this.setCustomName(type.getName());
        this.setCustomNameVisible(true);
    }

    /**
     * 获取此怪物的类型
     *
     * @return 类型
     */
    @Override
    public MobType getType() {

        return type;
    }

    /**
     * 设置此怪物的等级
     *
     * @param mobLevel 等级
     */
    @Override
    public void setMobLevel(int mobLevel) {

        this.mobLevel = mobLevel;
    }

    /**
     * 获取此怪物的等级
     *
     * @return 等级
     */
    @Override
    public int getMobLevel() {

        return mobLevel;
    }

    /**
     * 设置此怪物死亡的掉落经验
     *
     * @param dropExp 经验
     */
    @Override
    public void setDropExp(int dropExp) {

        this.dropExp = dropExp;
    }

    /**
     * 获取此怪物死亡的掉落经验
     *
     * @return 经验
     */
    @Override
    public int getDropExp() {

        return dropExp;
    }

    /**
     * 获取此怪物的显示名称
     *
     * @return 显示名称
     */
    public String getDisplayName() {

        return displayName;
    }

    /**
     * 设置此怪物死亡的掉落物品
     *
     * @param items 物品集合
     */
    @Override
    public void setDropItems(Map<ItemStack, DropOption> items) {

        dropItemMap = items != null ? items : new HashMap<>();
    }

    /**
     * 获取此怪物死亡的掉落物品集合
     *
     * @return 物品集合
     */
    @Override
    public Map<ItemStack, DropOption> getDropItems() {

        return dropItemMap;
    }

    /**
     * 清除此怪物死亡的掉落物品集合
     */
    @Override
    public void clearDropItems() {

        dropItemMap.clear();
    }

    /**
     * 将此怪物死亡的掉落物品集合添加物品
     *
     * @param item 物品
     * @param option 配置
     */
    @Override
    public void addDropItems(ItemStack item, DropOption option) {

        dropItemMap.put(item, option);
    }

    /**
     * 设置此怪物指定装备栏的物品
     *
     * @param type 装备栏
     * @param item 物品
     */
    @Override
    public void setEquipmentItem(EquipmentType type, ItemStack item) {

        setEquipmentItem(type, item, 0f);
    }

    /**
     * 设置此怪物指定装备栏的物品
     *
     * @param type 装备栏
     * @param item 物品
     * @param dropChange 装备掉落几率
     */
    @Override
    public void setEquipmentItem(EquipmentType type, ItemStack item, float dropChange) {

        if(equipItemMap.containsKey(type)) {

            equipItemMap.remove(type);
        }
        equipItemMap.put(type, item);

        super.setEquipmentItem(type, item, dropChange);
    }

    /**
     * 获取此实体指定装备栏的物品
     *
     * @param type 装备栏
     * @return 物品
     */
    @Override
    public ItemStack getEquipmentItem(EquipmentType type) {

        if(equipItemMap.containsKey(type)) {

            return equipItemMap.get(type);
        }
        return super.getEquipmentItem(type);
    }

    /**
     * 清除此指定所有装备栏的物品
     */
    @Override
    public void clearEquipmentItems() {

        equipItemMap.clear();

        super.clearEquipmentItems();
    }

    @Override
    public boolean equals(Object obj) {

        if(obj != null) {

            if(obj instanceof Mob) {

                Mob other = (Mob)obj;

                if(!other.getUniqueId().equals(other.getUniqueId())) {

                    return false;
                }
                if(!other.getName().equals(other.getName())) {

                    return false;
                }
                if(!other.getType().equals(other.getType())) {

                    return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(Entity o) {

        if(o instanceof Mob) {

            return getType().getName().compareTo(((Mob)o).getType().getName());
        }
        return super.compareTo(o);
    }
    
    /**
     * 设置此实体的自定义名称
     *
     * @param customName 名称
     */
    @Override
    public void setCustomName(String customName) {

        this.displayName = customName;

        super.setCustomName(StringUtil.format(DISPLAYNAME_FORMAT, mobLevel, displayName));
    }

    /**
     * 将此怪物进行清除
     *
     * @return 是否死亡
     */
    @Override
    public boolean remove() {

        return super.remove();
    }
}
