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
 
  
package com.minecraft.moonlake.mmorpg.api.mount;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.adapter.AdapterManager;
import com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity;
import com.minecraft.moonlake.mmorpg.api.event.mount.MountSummonEvent;
import com.minecraft.moonlake.mmorpg.api.event.player.mount.PlayerEjectMountEvent;
import com.minecraft.moonlake.mmorpg.api.event.player.mount.PlayerRideMountEvent;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Created by MoonLake on 2016/6/19.
 */
public abstract class AbstractMount implements Mount {

    private final MountType type;
    private MMORPGPlayer owner;
    private String displayName;
    private Entity mount;

    public AbstractMount(MountType type) {

        this(type, type.getName());
    }

    public AbstractMount(MountType type, String displayName) {

        this(type, displayName, null);
    }

    public AbstractMount(MountType type, MMORPGPlayer owner) {

        this(type, type.getName(), owner);
    }

    public AbstractMount(MountType type, String displayName, MMORPGPlayer owner) {

        this.type = type;
        this.mount = null;
        this.owner = owner;
        this.displayName = displayName;
    }

    /**
     * 获取坐骑的类型
     *
     * @return 类型
     */
    @Override
    public MountType getType() {

        return type;
    }

    /**
     * 获取此坐骑的拥有者
     *
     * @return 拥有者
     */
    @Override
    public MMORPGPlayer getOwner() {

        return owner;
    }

    /**
     * 设置此坐骑的拥有者
     *
     * @param mmorpgPlayer 玩家
     */
    @Override
    public void setOwner(MMORPGPlayer mmorpgPlayer) {

        this.owner = mmorpgPlayer;
    }

    /**
     * 获取此坐骑的显示名
     *
     * @return 显示名
     */
    public String getDisplayName() {

        return displayName;
    }

    /**
     * 设置此坐骑的显示名
     *
     * @param displayName 显示名
     */
    public void setDisplayName(String displayName) {

        this.displayName = displayName;

        if(isLiving()) {

            getMount().setCustomName(Util.color(displayName));
        }
    }

    /**
     * 获取此坐骑的实体对象
     *
     * @return 实体对象
     */
    public Entity getMount() {

        return mount;
    }

    /**
     * 将坐骑召唤到拥有者位置
     */
    @Override
    public void onSummon() {

        MountSummonEvent mse = new MountSummonEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(mse);

        if(!mse.isCancelled()) {

            if(isLiving()) {

                teleport();
                return;
            }
            mount = AdapterManager.adap(getOwner().getWorld().spawn(getOwner().getLocation(), type.getEntityClazz()));
            mount.setCustomNameVisible(true);
            mount.setRemoveWhenFarAway(false);
            mount.setCustomName(Util.color(displayName));
            mount.setMetadata("mount", new FixedMetadataValue((MMORPGPlugin)MMORPGPlugin.getInstances(), getOwner().getName()));

            if(mount.isLiving()) {

                mount.setAttributeMovementSpeed(0.3d);
                mount.setAttributeMaxHealth(20d, true);
            }
            if(mount.isTameable()) {

                mount.asTameable().setTamed(true);
                mount.asTameable().setOwner(getOwner().getBukkitPlayer());
            }
            if(mount.isAgeable()) {

                mount.asAgeable().setAgeLock(true);
                mount.asAgeable().setAdult();
                mount.asAgeable().setBreed(false);
            }
        }
    }

    /**
     * 将此坐骑清除
     */
    @Override
    public void remove() {

        if(isLiving()) {

            mount.remove();
            mount = null;
        }
    }

    /**
     * 将此坐骑传送到拥有者指定位置
     */
    @Override
    public void teleport() {

        if(isLiving()) {

            getMount().teleport(getOwner().getLocation());
        }
    }

    /**
     * 获取此坐骑是否活着
     *
     * @return 是否活着
     */
    @Override
    public boolean isLiving() {

        return mount != null && !mount.isDead();
    }

    /**
     * 将此拥有者骑乘此坐骑
     */
    @Override
    public void onRide() {

        PlayerRideMountEvent prme = new PlayerRideMountEvent(getOwner(), this);
        Bukkit.getServer().getPluginManager().callEvent(prme);

        if(!prme.isCancelled()) {

            if(isLiving()) {

                teleport();
                getMount().setPassenger(getOwner());
            }
        }
    }

    /**
     * 将此坐骑的骑乘者离开
     */
    @Override
    public void onEject() {

        PlayerEjectMountEvent peme = new PlayerEjectMountEvent(getOwner(), this);
        Bukkit.getServer().getPluginManager().callEvent(peme);

        if(!peme.isCancelled()) {

            if(isLiving()) {

                if(getMount().getPassenger().isPlayer()) {

                    if(getMount().getPassenger() != null && getMount().getPassenger().getName().equals(getOwner().getName())) {

                        getOwner().onEject();
                    }
                }
            }
        }
    }
}
