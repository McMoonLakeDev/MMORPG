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

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.adapter.AdapterManager;
import com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity;
import com.minecraft.moonlake.mmorpg.api.entity.ai.PetFollower;
import com.minecraft.moonlake.mmorpg.api.event.pet.PetRemoveEvent;
import com.minecraft.moonlake.mmorpg.api.event.player.PlayerEquipPetEvent;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.exception.MMORPGIllegalArgumentException;
import com.minecraft.moonlake.mmorpg.manager.EntityManager;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import com.minecraft.moonlake.mmorpg.util.pet.PetFollowerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Ageable;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MoonLake on 2016/5/21.
 */
public abstract class AbstractPet implements Pet {

    private PetType type;
    private String name;
    private Entity entity;
    private MMORPGPlayer owner;
    private PetFollower petFollower;
    private BukkitRunnable updateTask;
    private ExecutorService pathUpdate;

    public AbstractPet(PetType type) {

        this(type, type.getDefaultName());
    }

    public AbstractPet(PetType type, String name) {

        this.name = name;
        this.type = type;
        this.entity = null;
        this.updateTask = null;
        this.petFollower = null;
        this.pathUpdate = Executors.newSingleThreadExecutor();
    }

    /**
     * 将宠物装备到主人身边 (生成并跟随
     *
     */
    @Override
    public void equip() {

        if(owner == null) {

            throw new MMORPGIllegalArgumentException("宠物的主人对象不能为 null 值.");
        }
        PlayerEquipPetEvent pepe = new PlayerEquipPetEvent(owner, this);
        Bukkit.getServer().getPluginManager().callEvent(pepe);

        if(!pepe.isCancelled()) {

            if(petFollower == null) {

                petFollower = new PetFollowerUtil(this, owner);
            }
            entity = AdapterManager.adap(owner.getWorld().spawnEntity(owner.getLocation(), getType().getEntityType()));

            init();

            EntityManager.removePathFinders(entity);
            EntityManager.setSilent(entity, true);
            EntityManager.putPetList(this);

            updateTask = new BukkitRunnable() {

                @Override
                public void run() {

                    if(owner != null && entity != null) {

                        update();
                        pathUpdate.execute(petFollower.getTask());
                    }
                }
            };
            updateTask.runTaskTimer((MMORPGPlugin)MMORPGPlugin.getInstances(), 10L, 3L);
        }
    }

    /**
     * 将宠物清除并释放占用资源
     */
    @Override
    public void clear() {

        PetRemoveEvent pre = new PetRemoveEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(pre);

        EntityManager.delPetFromList(entity.getUniqueId());

        if(updateTask != null) {

            updateTask.cancel();
            updateTask = null;
        }
        if(pathUpdate != null) {

            pathUpdate.shutdown();
            pathUpdate = null;
        }
        if(entity != null && !entity.isDead()) {

            entity.remove();
            entity = null;
        }
    }

    protected void init() {

        initPet();
    }

    /**
     * 获取此宠物的实体对象
     *
     * @return 实体对象
     */
    @Override
    public Entity getEntity() {

        return entity;
    }

    /**
     * 获取此宠物的宠物类型
     *
     * @return 宠物类型
     */
    @Override
    public PetType getType() {

        return type;
    }

    /**
     * 获取此宠物的显示名称
     *
     * @return 显示名
     */
    @Override
    public String getDisplayName() {

        return name;
    }

    /**
     * 设置此宠物的显示名称
     *
     * @param name 显示名
     */
    @Override
    public void setDisplayName(String name) {

        this.name = name;

        if(entity != null && !entity.isDead()) {

            entity.setCustomName(StringUtil.color(name));
        }
    }

    /**
     * 设置此宠物的拥有者
     *
     * @param player 玩家
     */
    @Override
    public void setOwner(MMORPGPlayer player) {

        this.owner = player;
    }

    /**
     *
     * 获取此宠物的拥有者
     *
     * @return 玩家 没有则返回 null
     */
    @Override
    public MMORPGPlayer getOwner() {

        return owner;
    }

    /**
     * 初始化此宠物的状态
     */
    private void initPet() {

        if(entity == null) return;

        if(entity instanceof Ageable) {

            if(type.isBaby()) {

                ((Ageable)entity).setBaby();
            }
            else {

                ((Ageable)entity).setAdult();
            }
            ((Ageable)entity).setAgeLock(true);
        }
        entity.setCustomNameVisible(true);
        entity.setRemoveWhenFarAway(false);
        entity.setCustomName(StringUtil.color(getDisplayName()));
        entity.setMetadata("petOwner", new FixedMetadataValue((MMORPGPlugin)MMORPGPlugin.getInstances(), owner.getName()));
    }

    /**
     * 更新此宠物的状态
     */
    public abstract void update();
}
