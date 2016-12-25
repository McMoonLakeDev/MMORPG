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
 
  
package com.minecraft.moonlake.mmorpg.api.pet;

import com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;

/**
 * Created by MoonLake on 2016/5/21.
 */
public interface Pet {

    /**
     * 设置此宠物的拥有者
     *
     * @param player 玩家
     */
    void setOwner(MMORPGPlayer player);

    /**
     *
     * 获取此宠物的拥有者
     *
     * @return 玩家 没有则返回 null
     */
    MMORPGPlayer getOwner();

    /**
     * 获取此宠物的实体对象
     *
     * @return 实体对象
     */
    Entity getEntity();

    /**
     * 获取此宠物的宠物类型
     *
     * @return 宠物类型
     */
    PetType getType();

    /**
     * 获取此宠物的显示名称
     *
     * @return 显示名
     */
    String getDisplayName();

    /**
     * 设置此宠物的显示名称
     *
     * @param name 显示名
     */
    void setDisplayName(String name);

    /**
     * 将宠物装备到主人身边 (生成并跟随
     *
     */
    void equip();

    /**
     * 将宠物清除并释放占用资源
     */
    void clear();
}
