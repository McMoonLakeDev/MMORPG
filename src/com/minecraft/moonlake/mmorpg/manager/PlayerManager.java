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
 
  
package com.minecraft.moonlake.mmorpg.manager;

import com.minecraft.moonlake.mmorpg.api.gui.GUIManager;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.handle.EnumInventoryItemSlot;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/5/26.
 */
public final class PlayerManager extends com.minecraft.moonlake.manager.PlayerManager {

    private final static boolean PLAYER_LIST_ENABLE;

    static {

        PLAYER_LIST_ENABLE = ConfigManager.get("PlayerList.Enable").asBoolean();
    }

    /**
     * 初始化 MMORPG 玩家的对象状态
     *
     * @param mmorpgPlayer 玩家
     */
    public static void initMMORPGPlayerState(MMORPGPlayer mmorpgPlayer) {

        if(!mmorpgPlayer.hasBeforePlayed()) {

            mmorpgPlayer.setHealth(mmorpgPlayer.getMaxHealth());
            mmorpgPlayer.setMagic(mmorpgPlayer.getMaxMagic());
            mmorpgPlayer.setSoul(mmorpgPlayer.getMaxSoul());
        }
        else {

            mmorpgPlayer.getRealHealth().update();
            mmorpgPlayer.getRealMagic().update();
        }
    }

    public static void updatePlayerListName(MMORPGPlayer mmorpgPlayer) {

        if(PLAYER_LIST_ENABLE && mmorpgPlayer != null) {

            String format = "";

            if(mmorpgPlayer.getRoleType() != null) {

                format = ConfigManager.get("PlayerList.Format").asString()
                        .replace("%role", mmorpgPlayer.getRoleType().getPrefix())
                        .replace("%player", mmorpgPlayer.getName());
            }
            else {

                format = ConfigManager.get("PlayerList.NotRoleFormat").asString()
                        .replace("%player", mmorpgPlayer.getName());
            }
            mmorpgPlayer.getBukkitPlayer().setPlayerListName(StringUtil.color(format));
        }
    }

    /**
     * 检测玩家的饱食度等级并重置
     *
     * @param player 玩家
     */
    public static void checkFoodLevel(Player player) {

        if(player == null) return;

        if(player.getFoodLevel() < 20) {

            player.setFoodLevel(20);
        }
    }

    /**
     * 获取玩家的个人仓库空间大小
     *
     * @param mmorpgPlayer 玩家
     * @return 空间大小
     */
    public static int getRepertorySize(MMORPGPlayer mmorpgPlayer) {

        if(mmorpgPlayer.isOp() || mmorpgPlayer.hasPermission("moonlake.mmorpg.repertory")) {

            return GUIManager.sizeCheck(ConfigManager.get("PlayerRepertory.Permission").asInt());
        }
        return GUIManager.sizeCheck(ConfigManager.get("PlayerRepertory.Default").asInt());
    }

    /**
     * 将玩家的背包物品数据序列化为集合字符串数据
     *
     * @param mmorpgPlayer 玩家
     * @return 集合字符串数据 没有则返回 null
     */
    public static Map<String, String> serializeInventory(MMORPGPlayer mmorpgPlayer) {

        if(mmorpgPlayer != null) {

            PlayerInventory playerInventory = mmorpgPlayer.getInventory();
            Map<String, String> dataMap = new HashMap<>();

            if(!ItemManager.isAir(playerInventory.getHelmet())) {
                // head
                dataMap.put(EnumInventoryItemSlot.HEAD.getValue() + ":0", ItemManager.serialize(playerInventory.getHelmet()));
            }
            if(!ItemManager.isAir(playerInventory.getChestplate())) {
                // chest
                dataMap.put(EnumInventoryItemSlot.CHEST.getValue() + ":0", ItemManager.serialize(playerInventory.getChestplate()));
            }
            if(!ItemManager.isAir(playerInventory.getLeggings())) {
                // legging
                dataMap.put(EnumInventoryItemSlot.LEGS.getValue() + ":0", ItemManager.serialize(playerInventory.getLeggings()));
            }
            if(!ItemManager.isAir(playerInventory.getBoots())) {
                // boot
                dataMap.put(EnumInventoryItemSlot.FEET.getValue() + ":0", ItemManager.serialize(playerInventory.getBoots()));
            }
            ItemStack[] extraContents = playerInventory.getExtraContents();
            ItemStack[] storageContents = playerInventory.getStorageContents();

            if(extraContents != null && extraContents.length > 0) {
                // extra
                for(int i = 0; i < extraContents.length; i++) {

                    if(!ItemManager.isAir(extraContents[i])) {

                        dataMap.put(EnumInventoryItemSlot.EXTRA.getValue() + ":" + i, ItemManager.serialize(extraContents[i]));
                    }
                }
            }
            if(storageContents != null && storageContents.length > 0) {
                // storage need slot
                // data format: slot:data
                for(int i = 0; i < storageContents.length; i++) {

                    if(!ItemManager.isAir(storageContents[i])) {

                        dataMap.put(EnumInventoryItemSlot.CONTENT.getValue() + ":" + i, playerInventory.first(storageContents[i]) + ":" + ItemManager.serialize(storageContents[i]));
                    }
                }
            }
            return dataMap;
        }
        return null;
    }

    /**
     * 将玩家背包对象读取指定的序列化数据
     *
     * @param playerInventory 玩家背包
     * @param dataMap 序列化的数据集合
     */
    public static void readSerializeInventory(PlayerInventory playerInventory, Map<String, String> dataMap) {

        if(playerInventory != null && dataMap != null && dataMap.size() > 0) {

            playerInventory.clear();

            if(dataMap.containsKey(EnumInventoryItemSlot.HEAD.getValue() + ":0")) {
                // head
                ItemStack head = ItemManager.deserialize(dataMap.get(EnumInventoryItemSlot.HEAD.getValue() + ":0"));
                if(!ItemManager.isAir(head)) {

                    playerInventory.setHelmet(head);
                }
            }
            if(dataMap.containsKey(EnumInventoryItemSlot.CHEST.getValue() + ":0")) {
                // chest
                ItemStack chest = ItemManager.deserialize(dataMap.get(EnumInventoryItemSlot.CHEST.getValue() + ":0"));
                if(!ItemManager.isAir(chest)) {

                    playerInventory.setChestplate(chest);
                }
            }
            if(dataMap.containsKey(EnumInventoryItemSlot.LEGS.getValue() + ":0")) {
                // legging
                ItemStack legging = ItemManager.deserialize(dataMap.get(EnumInventoryItemSlot.LEGS.getValue() + ":0"));
                if(!ItemManager.isAir(legging)) {

                    playerInventory.setLeggings(legging);
                }
            }
            if(dataMap.containsKey(EnumInventoryItemSlot.FEET.getValue() + ":0")) {
                // boot
                ItemStack boot = ItemManager.deserialize(dataMap.get(EnumInventoryItemSlot.FEET.getValue() + ":0"));
                if(!ItemManager.isAir(boot)) {

                    playerInventory.setBoots(boot);
                }
            }
            ItemStack[] extraArray = new ItemStack[playerInventory.getExtraContents().length];

            for(int i = 0; i < extraArray.length; i++) {

                if(dataMap.containsKey(EnumInventoryItemSlot.EXTRA.getValue() + ":" + i)) {
                    // extra
                    ItemStack extra = ItemManager.deserialize(dataMap.get(EnumInventoryItemSlot.EXTRA.getValue() + ":" + i));
                    if(!ItemManager.isAir(extra)) {

                        extraArray[i] = extra;
                    }
                }
            }
            playerInventory.setExtraContents(extraArray);

            for(int i = 0; i < playerInventory.getStorageContents().length; i++) {

                if(dataMap.containsKey(EnumInventoryItemSlot.CONTENT.getValue() + ":" + i)) {
                    // storage
                    String[] valueDatas = dataMap.get(EnumInventoryItemSlot.CONTENT.getValue() + ":" + i).split(":");
                    int slot = Integer.parseInt(valueDatas[0]);
                    ItemStack storage = ItemManager.deserialize(valueDatas[1]);

                    if(!ItemManager.isAir(storage)) {

                        playerInventory.setItem(slot, storage);
                    }
                }
            }
        }
    }

    /**
     * 将玩家的个人仓库物品数据序列化为集合字符串数据
     *
     * @param mmorpgPlayer 玩家
     * @return 集合字符串数据 没有则返回 null
     */
    public static Map<Integer, String> serializeRepertory(MMORPGPlayer mmorpgPlayer) {

        if(mmorpgPlayer != null) {

            Inventory reperotyr = mmorpgPlayer.getRepertory().getInventory();
            Map<Integer, String> dataMap = new HashMap<>();

            for(int i = 0; i < reperotyr.getSize(); i++) {

                ItemStack item = reperotyr.getItem(i);

                if(!ItemManager.isAir(item)) {

                    dataMap.put(i, ItemManager.serialize(item));
                }
            }
            return dataMap;
        }
        return null;
    }

    /**
     * 将玩家干草块对象读取指定的序列化数据
     *
     * @param repertory 玩家个人仓库
     * @param dataMap 序列化的数据集合
     */
    public static void readSerializeRepertory(Inventory repertory, Map<Integer, String> dataMap) {

        if(repertory != null && dataMap != null) {

            for(int i = 0; i < repertory.getSize(); i++) {

                if(dataMap.containsKey(i)) {

                    ItemStack item = ItemManager.deserialize(dataMap.get(i));

                    if(!ItemManager.isAir(item)) {

                        repertory.setItem(i, item);
                    }
                }
            }
        }
    }
}
