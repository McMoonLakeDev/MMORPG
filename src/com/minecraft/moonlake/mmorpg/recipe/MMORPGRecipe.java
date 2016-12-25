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
 
  
package com.minecraft.moonlake.mmorpg.recipe;

import com.minecraft.moonlake.mmorpg.api.item.currency.type.GoldBlock;
import com.minecraft.moonlake.mmorpg.api.item.currency.type.GoldBroken;
import com.minecraft.moonlake.mmorpg.api.item.currency.type.GoldCoin;
import com.minecraft.moonlake.mmorpg.api.item.currency.type.GoldIngot;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

/**
 * Created by MoonLake on 2016/6/16.
 */
public final class MMORPGRecipe {

    private final static ShapedRecipe GOLD_COIN_RECIPE;
    private final static ShapedRecipe GOLD_INGOT_RECIPE;
    private final static ShapedRecipe GOLD_BLOCK_RECIPE;
    private final static ShapelessRecipe GOLD_INGOT_RECIPES;
    private final static ShapelessRecipe GOLD_COIN_RECIPES;
    private final static ShapelessRecipe GOLD_BREAK_RECIPES;

    static {

        GOLD_COIN_RECIPE = new ShapedRecipe(new GoldCoin().getItem());
        GOLD_COIN_RECIPE.shape("###", "###", "###" );
        GOLD_COIN_RECIPE.setIngredient('#', new GoldBroken().getMaterial());

        GOLD_INGOT_RECIPE = new ShapedRecipe(new GoldIngot().getItem());
        GOLD_INGOT_RECIPE.shape("###", "###", "###");
        GOLD_INGOT_RECIPE.setIngredient('#', new GoldCoin().getMaterial());

        GOLD_BLOCK_RECIPE = new ShapedRecipe(new GoldBlock().getItem());
        GOLD_BLOCK_RECIPE.shape("###", "###", "###" );
        GOLD_BLOCK_RECIPE.setIngredient('#', new GoldIngot().getMaterial());

        GOLD_INGOT_RECIPES = new ShapelessRecipe(new GoldIngot(9).getItem());
        GOLD_INGOT_RECIPES.addIngredient(1, new GoldBlock().getMaterial());

        GOLD_COIN_RECIPES = new ShapelessRecipe(new GoldCoin(9).getItem());
        GOLD_COIN_RECIPES.addIngredient(1, new GoldIngot().getMaterial());

        GOLD_BREAK_RECIPES = new ShapelessRecipe(new GoldBroken(9).getItem());
        GOLD_BREAK_RECIPES.addIngredient(1, new GoldCoin().getMaterial());
    }

    /**
     * 添加默认 MMORPG 的合成配置
     */
    public static void addDefaultRecipe() {

        Bukkit.getServer().clearRecipes();

        Bukkit.getServer().addRecipe(GOLD_COIN_RECIPE);
        Bukkit.getServer().addRecipe(GOLD_INGOT_RECIPE);
        Bukkit.getServer().addRecipe(GOLD_BLOCK_RECIPE);

        Bukkit.getServer().addRecipe(GOLD_INGOT_RECIPES);
        Bukkit.getServer().addRecipe(GOLD_COIN_RECIPES);
        Bukkit.getServer().addRecipe(GOLD_BREAK_RECIPES);
    }
}
