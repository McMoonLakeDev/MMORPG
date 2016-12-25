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
 
  
package com.minecraft.moonlake.mmorpg.listeners.player;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.item.currency.Currency;
import com.minecraft.moonlake.mmorpg.api.item.currency.CurrencyManager;

/**
 * Created by MoonLake on 2016/07/05.
 */
public class PlayerCurrencyListener implements Listener {

	private final MMORPG main;
	
	public PlayerCurrencyListener(MMORPG main) {
		
		this.main = main;
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlace(BlockPlaceEvent event) {
		
		ItemStack item = event.getItemInHand();
		if(item == null || item.getType() == Material.AIR) return;
		
		Currency currency = CurrencyManager.getCurrency(item);
		
		if(currency != null && currency.isBlock()) {
			
			event.setCancelled(true);
		}
	}
}
