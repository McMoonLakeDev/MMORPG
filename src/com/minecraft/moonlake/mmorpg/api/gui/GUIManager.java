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
 
  
package com.minecraft.moonlake.mmorpg.api.gui;

import com.minecraft.moonlake.mmorpg.api.gui.player.MountGUI;
import com.minecraft.moonlake.mmorpg.api.gui.player.PlayerGUI;
import com.minecraft.moonlake.mmorpg.api.gui.player.SkillGUI;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import com.minecraft.moonlake.mmorpg.manager.MMORPGManager;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/5/31.
 */
public final class GUIManager extends MMORPGManager {

    private final static Map<String, PlayerGUI> playerGUIMap;
    private final static Map<String, SkillGUI> skillGUIMap;
    private final static Map<String, MountGUI> mountGUIMap;

    static {

        playerGUIMap = new HashMap<>();
        skillGUIMap = new HashMap<>();
        mountGUIMap = new HashMap<>();
    }

    /**
     * 给 MMORPG 玩家创建基础 GUI 系统
     *
     * @param mmorpgPlayer 玩家
     */
    public static void createBaseGUI(MMORPGPlayer mmorpgPlayer) {

        // player gui
        int playerGUISize = 3;
        String playerGUITitle = "玩家 GUI 面板";
        playerGUIMap.put(mmorpgPlayer.getName(), new PlayerGUI(mmorpgPlayer, playerGUITitle, playerGUISize));

        // skill gui
        int skillGUISize = sizeCheck(ConfigManager.get("SkillGUI.Size").asInt());
        String skillGUITitle = StringUtil.color(ConfigManager.get("SkillGUI.Title").asString());
        skillGUITitle = skillGUITitle.replace("%role", mmorpgPlayer.getRoleType().getName()).replace("%player", mmorpgPlayer.getName());
        skillGUIMap.put(mmorpgPlayer.getName(), new SkillGUI(mmorpgPlayer, skillGUITitle, skillGUISize));

        // mount gui
        String mountGUITitle = StringUtil.color(ConfigManager.get("PlayerMount.Title").asString());
        mountGUITitle = mountGUITitle.replace("%player", mmorpgPlayer.getName());
        mountGUIMap.put(mmorpgPlayer.getName(), new MountGUI(mmorpgPlayer, mountGUITitle, 3));

        // load gui data
        loadGUIDataUtil(mmorpgPlayer);
    }

    private static void loadGUIDataUtil(MMORPGPlayer mmorpgPlayer) {

        // skill gui init
        getSkillGUI(mmorpgPlayer).readSkillData();
        getSkillGUI(mmorpgPlayer).register();

        // mount gui init
        getMountGUI(mmorpgPlayer).readMountData(mmorpgPlayer.getMount().getMountList());
        getMountGUI(mmorpgPlayer).setDefaultToMain();
        getMountGUI(mmorpgPlayer).register();

        // player gui init
        getPlayerGUI(mmorpgPlayer).readPlayerData();
        getPlayerGUI(mmorpgPlayer).register();
    }

    /**
     * 获取 MMORPG 玩家的 GUI 对象
     *
     * @param mmorpgPlayer 玩家
     * @return GUI 对象 没有则返回 null
     */
    public static PlayerGUI getPlayerGUI(MMORPGPlayer mmorpgPlayer) {

        return mmorpgPlayer != null ? playerGUIMap.get(mmorpgPlayer.getName()) : null;
    }

    /**
     * 获取 MMORPG 玩家的技能 GUI 对象
     *
     * @param mmorpgPlayer 玩家
     * @return GUI 对象 没有则返回 null
     */
    public static SkillGUI getSkillGUI(MMORPGPlayer mmorpgPlayer) {

        return mmorpgPlayer != null ? skillGUIMap.get(mmorpgPlayer.getName()) : null;
    }

    /**
     * 获取 MMORPG 玩家的坐骑 GUI 对象
     *
     * @param mmorpgPlayer 玩家
     * @return GUI 对象 没有则返回 null
     */
    public static MountGUI getMountGUI(MMORPGPlayer mmorpgPlayer) {

        return mmorpgPlayer != null ? mountGUIMap.get(mmorpgPlayer.getName()) : null;
    }

    /**
     * 关闭并释放指定玩家的 GUI 对象
     *
     * @param name 玩家名
     */
    public static void closeGUI(String name) {

        if(playerGUIMap.containsKey(name)) {

            playerGUIMap.remove(name).unregister();
        }
        if(skillGUIMap.containsKey(name)) {

            skillGUIMap.remove(name).unregister();
        }
        if(mountGUIMap.containsKey(name)) {

            mountGUIMap.remove(name).unregister();
        }
    }

    /**
     * 检测指定物品栏大小值是否符合
     *
     * @param size 大小值 (1 - 6) | (9、18、27、36、45、54)
     * @return 检测完毕的大小值 不符合则返回 9
     */
    public static int sizeCheck(int size) {

        return (size > 6 && size <= 54 && size % 9 == 0) ? size : (size >= 1 && size <= 6) ? size * 9 : 9;
    }

    /**
     * 将二维坐标转换到 GUI 所需的索引值
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @return 索引值
     */
    public static int getSlot(int x, int y) {

        return (y * 9) - (9 - x) - 1;
    }

    /**
     * 设置物品从二维坐标数组到 GUI 物品栏
     *
     * @param gui GUI 对象
     * @param arrayPoint 二维坐标数组
     * @param item 物品栈
     */
    public static void setItemFromArrayPoint(Inventory gui, int[][] arrayPoint, ItemStack item) {

        if(gui == null || arrayPoint.length == 0 || item == null || item.getType() == Material.AIR) return;

        for(int i = 0; i < arrayPoint.length; i++) {

            gui.setItem(getSlot(arrayPoint[i][1], arrayPoint[i][0]), item);
        }
    }

    /**
     * 设置物品从二维坐标数组到 GUI 物品栏
     *
     * @param gui GUI 对象
     * @param arrayPoint 二维坐标数组
     * @param items 物品栈
     */
    public static void setItemsFromArrayPoint(Inventory gui, int[][] arrayPoint, ItemStack... items) {

        if(gui == null || arrayPoint.length == 0 || items == null || items.length != arrayPoint.length) return;

        for(int i = 0; i < items.length; i++) {

            gui.setItem(getSlot(arrayPoint[i][1], arrayPoint[i][0]), items[i]);
        }
    }

    /**
     * 获取 GUI 物品栏从二维坐标数组
     *
     * @param gui GUI 对象
     * @param arrayPoint 二维坐标数组
     * @return 对应的物品数组 没有则为 null
     */
    public static ItemStack[] getItemsFromArrayPoint(Inventory gui, int[][] arrayPoint) {

        if(gui != null && arrayPoint.length > 0) {

            ItemStack[] temp = new ItemStack[arrayPoint.length];

            for(int i = 0; i < temp.length; i++) {

                temp[i] = gui.getItem(getSlot(arrayPoint[i][1], arrayPoint[i][0]));
            }
            return temp;
        }
        return null;
    }

    /**
     * 设置物品从二维坐标到 GUI 物品栏
     *
     * @param gui GUI 对象
     * @param x X 坐标
     * @param y Y 坐标
     * @param item 物品栈
     */
    public static void setItemFromPoint(Inventory gui, int x, int y, ItemStack item) {

        setItemFromSlot(gui, getSlot(x, y), item);
    }

    /**
     * 设置物品从二维坐标对象到 GUI 物品栏
     *
     * @param gui GUI 对象
     * @param arrayPoint 二维坐标对象
     * @param item 物品栈
     */
    public static void setItemFromPoint(Inventory gui, ArrayPoint arrayPoint, ItemStack item) {

        if(arrayPoint == null) return;

        setItemFromSlot(gui, arrayPoint.getSlot(), item);
    }

    /**
     * 设置物品从索引到 GUI 物品栏
     *
     * @param gui GUI 对象
     * @param slot 索引
     * @param item 物品栈
     */
    public static void setItemFromSlot(Inventory gui, int slot, ItemStack item) {

        if(gui != null && item != null && item.getType() != Material.AIR) {

            gui.setItem(slot, item);
        }
    }

    /**
     * 设置物品从二维坐标数组的指定索引到 GUI 物品栏
     *
     * @param gui GUI 对象
     * @param arrayPoint 二维坐标数组
     * @param arrayPointIndex 二维坐标数组指定索引
     * @param item 物品栈
     */
    public static void setItemFromArrayPointIndex(Inventory gui, int[][] arrayPoint, int arrayPointIndex, ItemStack item) {

        if(gui != null && arrayPoint != null && !(arrayPointIndex < arrayPoint.length - 1) && item != null && item.getType() != Material.AIR) {

            gui.setItem(getSlot(arrayPoint[arrayPointIndex][1], arrayPoint[arrayPointIndex][0]), item);
        }
    }

    /**
     * 获取物品从指定索引
     *
     * @param gui GUI 对象
     * @param slot 索引
     */
    public static ItemStack getItemFromSlot(Inventory gui, int slot) {

        return gui.getItem(slot);
    }

    /**
     * 获取点击索引的二维坐标数组索引
     *
     * @param slot 点击的索引
     * @return 二维坐标对象
     */
    public static ArrayPoint getArrayPointIndexFromCurrentSlot(int slot) {

        int x = (slot + 1) % 9;
        int y = (slot + (9 - x) + 1) / 9;

        return new ArrayPoint(x, y);
    }

    /**
     * 获取索引数组从二维坐标数组
     *
     * @param arrayPoint 二维坐标数组
     * @return 索引数组
     */
    public static int[] getSlotsFromArrayPoint(int[][] arrayPoint) {

        int[] slots = new int[arrayPoint.length];

        for(int i = 0; i < slots.length; i++) {

            slots[i] = getSlot(arrayPoint[i][0], arrayPoint[i][1]);
        }
        return slots;
    }

    /**
     * 获取索引从二维坐标数组
     *
     * @param arrayPoint 二维坐标数组
     * @return 索引
     * @throws RuntimeException 如果二维坐标数组长度不为 1 则抛出异常
     */
    public static int getSlotFromArrayPoint(int[][] arrayPoint) {

        if(arrayPoint == null || arrayPoint.length != 1) {

            throw new RuntimeException("The array point is null or length not 1.");
        }
        return getSlot(arrayPoint[0][0], arrayPoint[0][1]);
    }

    /**
     * 获取指定 GUI 对象是否拥有物品栈 (除空气)
     *
     * @param gui GUI 对象
     * @return true 拥有物品栈 else 没有
     */
    public static boolean hasItem(Inventory gui) {

        return hasItem(gui, Material.AIR);
    }

    /**
     * 获取指定 GUI 对象是否拥有物品栈
     *
     * @param gui GUI 对象
     * @param flag 去除的物品栈类型
     * @return true 拥有物品栈 else 没有
     */
    public static boolean hasItem(Inventory gui, Material flag) {

        if(gui != null) {

            ItemStack[] content = gui.getContents();
            boolean result = false;

            if(content != null && content.length > 0) {

                for(ItemStack item : content) {

                    result = item != null && item.getType() != flag;

                    if(result) {

                        break;
                    }
                }
            }
            return result;
        }
        return false;
    }

    /**
     * 创建月色之湖大型角色扮演服务器 GUI 对象
     *
     * @param name 名称
     * @param title 标题
     * @param size 大小
     * @return GUI 对象
     */
    public static MMORPGGUI createGUI(String name, String title, int size) {

        return new MMORPGGUIUtil(name, title, size);
    }

    /**
     * 关闭对象类并释放内存占用
     */
    public static void close() {

        for(PlayerGUI playerGUI : playerGUIMap.values()) {

            playerGUI.unregister();
        }
        for(SkillGUI skillGUI : skillGUIMap.values()) {

            skillGUI.unregister();
        }
        for(MountGUI mountGUI : mountGUIMap.values()) {

            mountGUI.unregister();
        }
        playerGUIMap.clear();
        skillGUIMap.clear();
        mountGUIMap.clear();
    }
}
