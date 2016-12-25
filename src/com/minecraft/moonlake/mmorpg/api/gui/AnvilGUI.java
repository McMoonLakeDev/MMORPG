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

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.AccountManager;
import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/6/3.
 */
public class AnvilGUI {

    private MMORPGPlayer owner;
    private Inventory inv;
    private Listener listener;
    private AnvilClickEventHandler clickEventHandler;
    private AnvilCloseEventHandler closeEventHandler;
    private Map<AnvilSlot, ItemStack> slotItemMap;


    public AnvilGUI(final MMORPGPlayer owner, final AnvilClickEventHandler clickEventHandler, final AnvilCloseEventHandler closeEventHandler) {

        this.owner = owner;
        this.clickEventHandler = clickEventHandler;
        this.closeEventHandler = closeEventHandler;
        this.slotItemMap = new HashMap<>();

        this.listener = new Listener() {

            @EventHandler
            public void onClick(InventoryClickEvent event) {

                if(event.getClickedInventory() == null) return;
                if(!event.getClickedInventory().equals(inv)) return;
                if(!(event.getWhoClicked() instanceof Player)) return;

                MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer((Player)event.getWhoClicked());
                if(mmorpgPlayer == null || mmorpgPlayer.getRole() == null) return;

                event.setCancelled(true);

                AnvilClickEvent anvilClickEvent = new AnvilClickEvent(mmorpgPlayer, AnvilSlot.fromSlot(event.getRawSlot()), event.getCurrentItem());
                clickEventHandler.onAnvilClick(anvilClickEvent);

                if(anvilClickEvent.isWillClose()) {

                    event.getWhoClicked().closeInventory();
                }
                if(anvilClickEvent.isWillDestroy()) {

                    destroy();
                }
            }

            @EventHandler
            public void onMove(InventoryClickEvent event) {

                if(event.getClickedInventory() == null) return;
                if(event.getClickedInventory().getType() != InventoryType.PLAYER) return;

                if(!(event.getWhoClicked() instanceof Player)) return;

                Player player = (Player)event.getWhoClicked();
                boolean result = player.getOpenInventory() != null && player.getOpenInventory().getTopInventory() != null && player.getOpenInventory().getTopInventory().equals(inv);

                if(result) {

                    event.setCancelled(true);
                    player.updateInventory();
                }
            }

            @EventHandler
            public void onClose(InventoryCloseEvent event) {

                if(event.getInventory() == null) return;
                if(!(event.getPlayer() instanceof Player)) return;
                if(!event.getInventory().equals(inv)) return;

                AnvilCloseEvent anvilCloseEvent = new AnvilCloseEvent(getOwner());
                closeEventHandler.onAnvilClose(anvilCloseEvent);

                inv.clear();
                destroy();
            }
        };
        Bukkit.getServer().getPluginManager().registerEvents(listener, MMORPGPlugin.getInstances().getMain());
    }

    /**
     * 获取铁砧 GUI 的拥有者
     *
     * @return 拥有者
     */
    public MMORPGPlayer getOwner() {

        return owner;
    }

    /**
     * 设置铁砧 GUI 的指定索引的物品栈
     *
     * @param slot 铁砧索引
     * @param item 物品栈
     */
    public void setSlotItem(AnvilSlot slot, ItemStack item) {

        this.slotItemMap.put(slot, item);
    }

    /**
     * 将铁砧 GUI 的拥有者打开 GUI 面板
     */
    public void open() {

        try {

            Class<?> CraftPlayer = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            Class<?> EntityPlayer = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            Class<?> AnvilContainer = com.minecraft.moonlake.mmorpg.nms.AnvilContainer.class;
            Class<?> CraftInventoryView = Reflect.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftInventoryView");

            Method getHandle = Reflect.getMethod(CraftPlayer, "getHandle");
            Object NMSPlayer = getHandle.invoke(owner.getBukkitPlayer());

            Object anvilInstance = Reflect.instantiateObject(AnvilContainer, NMSPlayer);
            Method getBukkitView = Reflect.getMethod(AnvilContainer, "getBukkitView");

            Method getTopInventory = Reflect.getMethod(CraftInventoryView, "getTopInventory");
            inv = (Inventory) getTopInventory.invoke(getBukkitView.invoke(anvilInstance));

            for (AnvilSlot slot : slotItemMap.keySet()) {

                inv.setItem(slot.getSlot(), slotItemMap.get(slot));
            }
            Method nextContainerCounter = Reflect.getMethod(EntityPlayer, "nextContainerCounter");
            int id = (Integer) nextContainerCounter.invoke(NMSPlayer);

            Class<?> Packet = Reflect.PackageType.MINECRAFT_SERVER.getClass("Packet");
            Class<?> ChatMessage = Reflect.PackageType.MINECRAFT_SERVER.getClass("ChatMessage");
            Class<?> PlayerConnection = Reflect.PackageType.MINECRAFT_SERVER.getClass("PlayerConnection");
            Class<?> PacketPlayOutOpenWindow = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutOpenWindow");

            Field playerConnection = Reflect.getField(EntityPlayer, true, "playerConnection");
            Method sendPacket = Reflect.getMethod(PlayerConnection, "sendPacket", Packet);

            Object cmInstance = Reflect.instantiateObject(ChatMessage, "Repaireing", new Object[]{});
            Object ppoowInstance = Reflect.instantiateObject(PacketPlayOutOpenWindow, id, "minecraft:anvil", cmInstance, 0);
            sendPacket.invoke(playerConnection.get(NMSPlayer), ppoowInstance);

            Class<?> EntityHuman = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityHuman");
            Field activeContainer = Reflect.getField(EntityHuman, true, "activeContainer");
            activeContainer.set(NMSPlayer, anvilInstance);

            Class<?> Container = Reflect.PackageType.MINECRAFT_SERVER.getClass("Container");
            Field windowId = Reflect.getField(Container, true, "windowId");
            windowId.set(activeContainer.get(NMSPlayer), id);

            Class<?> ICrafting = Reflect.PackageType.MINECRAFT_SERVER.getClass("ICrafting");
            Method addSlotListener = Reflect.getMethod(AnvilContainer, "addSlotListener", ICrafting);
            addSlotListener.invoke(activeContainer.get(NMSPlayer), NMSPlayer);

        } catch (Exception e) {

            MMORPGPlugin.getInstances().log("给名为 '" + owner.getName() + "' 打开铁砧 GUI 时异常: " + e.getMessage());
        }
        /*
        EntityPlayer entityPlayer = ((CraftPlayer) owner.getBukkitPlayer()).getHandle();

        AnvilContainer anvil = new AnvilContainer(entityPlayer);
        inv = anvil.getBukkitView().getTopInventory();

        for (AnvilSlot slot : slotItemMap.keySet()) {

            inv.setItem(slot.getSlot(), slotItemMap.get(slot));
        }
        int id = entityPlayer.nextContainerCounter();

        entityPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(id, "minecraft:anvil", new ChatMessage("Repairing"), 0));
        entityPlayer.activeContainer = anvil;
        entityPlayer.activeContainer.windowId = id;
        entityPlayer.activeContainer.addSlotListener(entityPlayer);
        */
    }

    /**
     * 破坏对象并释放占用资源
     */
    public void destroy() {

        this.owner = null;
        this.clickEventHandler = null;
        this.slotItemMap = null;

        HandlerList.unregisterAll(listener);

        this.listener = null;
    }

    /**
     * 铁砧 GUI 点击事件
     */
    public class AnvilClickEvent {

        private MMORPGPlayer clicked;
        private AnvilSlot slot;
        private ItemStack item;
        private boolean close;
        private boolean destroy;

        public AnvilClickEvent(MMORPGPlayer mmorpgPlayer, AnvilSlot slot, ItemStack currentItem) {

            this.clicked = mmorpgPlayer;
            this.slot = slot;
            this.item = currentItem;
            this.close = true;
            this.destroy = true;
        }

        /**
         * 获取点击的铁砧 GUI 索引
         *
         * @return 索引
         */
        public AnvilSlot getSlot() {

            return slot;
        }

        /**
         * 获取点击的铁砧 GUI 的物品
         *
         * @return 点击的物品
         */
        public ItemStack getClickItem() {

            return item;
        }

        /**
         * 获取点击的铁砧 GUI 的点击者
         *
         * @return 点击者
         */
        public MMORPGPlayer getClicked() {

            return clicked;
        }

        /**
         * 设置点击的铁砧 GUI 是否将要关闭
         *
         * @param close 是否将要关闭
         */
        public void setWillClose(boolean close) {

            this.close = close;
        }

        /**
         * 设置点击的铁砧 GUI 是否将要破坏
         *
         * @param destroy 是否将要破坏
         */
        public void setWillDestroy(boolean destroy) {

            this.destroy = destroy;
        }

        /**
         * 获取点击的铁砧 GUI 是否将要关闭
         *
         * @return 是否将要关闭
         */
        public boolean isWillClose() {

            return close;
        }

        /**
         * 获取点击的铁砧 GUI 是否将要破坏
         *
         * @return 是否将要破坏
         */
        public boolean isWillDestroy() {

            return destroy;
        }
    }

    /**
     * 铁砧 GUI 关闭事件
     */
    public class AnvilCloseEvent {

        private MMORPGPlayer owner;

        public AnvilCloseEvent(MMORPGPlayer mmorpgPlayer) {

            this.owner = mmorpgPlayer;
        }

        /**
         * 获取铁砧 GUI 的拥有者
         *
         * @return 拥有者
         */
        public MMORPGPlayer getOwner() {

            return owner;
        }
    }

    public enum AnvilSlot {

        /**
         * 铁砧 GUI 左输入口
         */
        INPUT_LEFT(0),
        /**
         * 铁砧 GUI 右输入口
         */
        INPUT_RIGHT(1),
        /**
         * 铁砧 GUI 输出口
         */
        OUTPUT(2),;

        private int slot;

        AnvilSlot(int slot) {

            this.slot = slot;
        }

        /**
         * 获取铁砧 GUI 的索引
         *
         * @return 索引
         */
        public int getSlot() {

            return slot;
        }

        /**
         * 将索引值转换到铁砧索引对象
         *
         * @param slot 索引
         * @return 铁砧索引对象
         */
        public static AnvilSlot fromSlot(int slot) {

            switch (slot) {

                case 0:
                    return INPUT_LEFT;
                case 1:
                    return INPUT_RIGHT;
                case 2:
                    return OUTPUT;
                default:
                    return null;
            }
        }
    }

    public interface AnvilClickEventHandler {

        /**
         * 铁砧 GUI 点击事件
         *
         * @param event 事件对象
         */
        void onAnvilClick(AnvilClickEvent event);
    }

    public interface AnvilCloseEventHandler {

        /**
         * 铁砧 GUI 关闭事件
         *
         * @param event 事件对象
         */
        void onAnvilClose(AnvilCloseEvent event);
    }
}
