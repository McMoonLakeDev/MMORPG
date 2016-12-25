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
 
  
package com.minecraft.moonlake.mmorpg.hotbar;

import com.minecraft.moonlake.api.itemlib.ItemBuilder;
import com.minecraft.moonlake.mmorpg.api.event.mount.MountSummonByPlayerEvent;
import com.minecraft.moonlake.mmorpg.api.mount.Mount;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MoonLake on 2016/5/22.
 */
public final class HotBarItem {

    private final static Map<HotBarType, HotBarItemData> hotbarDataMap;
    private final static int coolDown;

    static {

        hotbarDataMap = new HashMap<>();
        coolDown = ConfigManager.get("PlayerMount.CoolDown").asInt();
    }

    private HotBarItem() {

    }

    /**
     * 初始化快捷栏物品数据
     */
    public static void initHotBar() {

        for(HotBarType hbt : HotBarType.values()) {

            HotBarItemData hbtData = null;

            if(hbt == HotBarType.PLAYER_SYSTEM) {

                hbtData = new HotBarItemData(hbt);
                hbtData.addInteract(HotBarInteractType.RIGHT_CLICK, new HotBarRunnable() {

                    @Override
                    public void run(MMORPGPlayer mmorpgPlayer) {

                        mmorpgPlayer.getPlayerGUI().openGUI();
                        mmorpgPlayer.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 10f, 1f);
                    }
                });
            }
            else if(hbt == HotBarType.MOUNT_SYSTEM) {

                hbtData = new HotBarItemData(hbt);
                hbtData.addInteract(HotBarInteractType.SHIFT_RIGHT_CLICK, new HotBarRunnable() {

                    @Override
                    public void run(MMORPGPlayer mmorpgPlayer) {

                        mmorpgPlayer.getMount().open();
                        mmorpgPlayer.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 10f, 1f);
                    }
                });
                hbtData.addInteract(HotBarInteractType.RIGHT_CLICK, new HotBarRunnable() {

                    @Override
                    public void run(MMORPGPlayer mmorpgPlayer) {

                        if(mmorpgPlayer.getMount().getMain() != null) {

                            if(mmorpgPlayer.hasItemCooldown(HotBarType.MOUNT_SYSTEM.getMaterial())) {

                                mmorpgPlayer.l18n("player.mount.summon.coolDown");
                                return;
                            }
                            Mount mount = mmorpgPlayer.getMount().getMain();

                            if(mount.isLiving()) {

                                mmorpgPlayer.l18n("player.mount.summon.isLiving");
                                return;
                            }
                            MountSummonByPlayerEvent msbpe = new MountSummonByPlayerEvent(mount, mmorpgPlayer);
                            Bukkit.getServer().getPluginManager().callEvent(msbpe);

                            if(!msbpe.isCancelled()) {

                                mount.onSummon();
                                mount.onRide();

                                mmorpgPlayer.sendItemCooldownPacket(HotBarType.MOUNT_SYSTEM.getMaterial(), coolDown * 20);
                            }
                        }
                    }
                });
            }
            if(hbtData != null) {

                hotbarDataMap.put(hbt, hbtData);
            }
        }
    }

    /**
     * 执行快捷栏物品交互
     *
     * @param type 快捷栏类型
     * @param interactType 交互类型
     * @param mmorpgPlayer 交互的玩家
     */
    public static void runHotBarInteract(HotBarType type, HotBarInteractType interactType, MMORPGPlayer mmorpgPlayer) {

        if(hotbarDataMap.containsKey(type)) {

            HotBarItemData hbtData = hotbarDataMap.get(type);

            if(hbtData != null) {

                hbtData.runInteract(interactType, mmorpgPlayer);
            }
        }
    }

    /**
     * 初始化玩家的快捷栏物品
     *
     * @param player 玩家
     */
    public static void initHotBarItem(Player player) {

        if(!player.hasPlayedBefore()) {
            // 第一次玩服务器
            for(HotBarType hbt : HotBarType.values()) {

                player.getInventory().setItem(hbt.getHotBarSlot(), new ItemBuilder(hbt.getMaterial(), hbt.getData(), hbt.getType()).build());
            }
        }
    }

    /**
     * 检测玩家的快捷栏物品是否正确
     *
     * @param player 玩家
     */
    public static void checkHotBarItem(Player player) {

        for(HotBarType hbt : HotBarType.values()) {

            ItemStack item = player.getInventory().getItem(hbt.getHotBarSlot());
            ItemStack hotBarItem = new ItemBuilder(hbt.getMaterial(), hbt.getData(), hbt.getType()).build();

            if(item == null || item.getType() != hbt.getMaterial() || !ItemManager.compareMeta(item, hotBarItem)) {

                player.getInventory().setItem(hbt.getHotBarSlot(), hotBarItem);
            }
        }
    }

    /**
     * 检测玩家的快捷栏物品是否正确
     *
     * @param player 玩家
     */
    public static void checkHotBarItem(MMORPGPlayer mmorpgPlayer) {

        if(mmorpgPlayer != null) {

            checkHotBarItem(mmorpgPlayer.getBukkitPlayer());
        }
    }

    /**
     * 检测并清除玩家死亡掉落物的快捷栏物品
     *
     * @param drops 掉落物集合
     */
    public static void removeHotBarItemDrops(List<ItemStack> drops) {

        if(drops != null && drops.size() > 0) {

            for(HotBarType hbt : HotBarType.values()) {

                ItemStack hotBarItem = new ItemBuilder(hbt.getMaterial(), hbt.getData(), hbt.getType()).build();

                for(ItemStack drop : drops) {

                    if(ItemManager.compareMeta(hotBarItem, drop)) {

                        drops.remove(drop);
                    }
                }
            }
        }
    }

    public enum HotBarInteractType {

        /**
         * 快捷栏物品交互类型: 左击
         */
        LEFT_CLICK,
        /**
         * 快捷栏物品交互类型: 右击
         */
        RIGHT_CLICK,
        /**
         * 快捷栏物品交互类型: 潜行左击
         */
        SHIFT_LEFT_CLICK,
        /**
         * 快捷栏物品交互类型: 潜行右击
         */
        SHIFT_RIGHT_CLICK,
        ;
    }

    public enum HotBarType {

        PLAYER_SYSTEM("玩家系统", 4, Material.SKULL_ITEM, 3),
        MOUNT_SYSTEM("坐骑系统", 5, Material.SADDLE, 0),
        PET_SYSTEM("宠物系统", 6, Material.MONSTER_EGG, 0),
        QUEST_SYSTEM("任务系统", 7, Material.BOOK_AND_QUILL, 0),
        SOUL_SYSTEM("灵魂系统", 8, Material.NETHER_STAR, 0),;

        private String type;
        private int hotBarSlot;
        private Material material;
        private int data;

        HotBarType(String type, int hotBarSlot, Material material, int data) {

            this.type = type;
            this.hotBarSlot = hotBarSlot;
            this.material = material;
            this.data = data;
        }

        public static HotBarType fromSlot(int slot) {

            switch (slot) {

                case 4:
                    return PLAYER_SYSTEM;
                case 5:
                    return MOUNT_SYSTEM;
                case 6:
                    return PET_SYSTEM;
                case 7:
                    return QUEST_SYSTEM;
                case 8:
                    return SOUL_SYSTEM;
                default:
                    return null;
            }
        }

        public String getType() {

            return type;
        }

        public int getHotBarSlot() {

            return hotBarSlot;
        }

        public Material getMaterial() {

            return material;
        }

        public int getData() {

            return data;
        }
    }

    /**
     * 关闭对象类并释放内存占用
     */
    public static void close() {

        hotbarDataMap.clear();
    }
}
