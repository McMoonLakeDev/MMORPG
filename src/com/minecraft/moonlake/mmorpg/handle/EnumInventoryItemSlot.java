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
 
  
package com.minecraft.moonlake.mmorpg.handle;

/**
 * Created by MoonLake on 2016/6/16.
 */
public enum EnumInventoryItemSlot {

    HEAD("Head", "head"),
    CHEST("Chest", "chest"),
    LEGS("Legs", "legs"),
    FEET("Feet", "feet"),
    EXTRA("Extra", "extra"),
    CONTENT("Content", "content"),;

    private String type;
    private String value;

    EnumInventoryItemSlot(String type, String value) {

        this.type = type;
        this.value = value;
    }

    public String getType() {

        return type;
    }

    public String getValue() {

        return value;
    }

    public static EnumInventoryItemSlot fromType(String type) {

        switch (type.toLowerCase()) {

            case "head":
                return HEAD;
            case "chest":
                return CHEST;
            case "legs":
                return LEGS;
            case "feet":
                return FEET;
            case "extra":
                return EXTRA;
            case "content":
                return CONTENT;
            default:
                return null;
        }
    }
}
