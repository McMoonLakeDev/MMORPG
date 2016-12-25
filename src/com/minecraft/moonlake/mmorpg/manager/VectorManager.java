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

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Created by MoonLake on 2016/5/27.
 */
public final class VectorManager extends com.minecraft.moonlake.manager.VectorManager {

    /**
     * 将 WorldEdit 的 Vector 对象转换为 XYZ 坐标
     *
     * @param oswVector Vector 对象
     * @param isDouble 是否 Double 坐标
     * @return 坐标数据 ("0,0,0")
     */
    public static String toXYZ(com.sk89q.worldedit.Vector oswVector, boolean isDouble) {

        String data = "";

        if(isDouble) {

            data = oswVector.getX() + "," + oswVector.getY() + "," + oswVector.getZ();
        }
        else {

            data = oswVector.getBlockX() + "," + oswVector.getBlockY() + "," + oswVector.getBlockZ();
        }
        return data;
    }

    /**
     * 将 XYZ 坐标数据转换到 WorldEdit 的 Vector 对象
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @return Vector 对象
     */
    public static com.sk89q.worldedit.Vector fromXYZ(double x, double y, double z) {

        return new com.sk89q.worldedit.Vector(x, y, z);
    }

    /**
     * 将 XYZ 字符串坐标数据转换到  WorldEdit 的 Vector 对象
     *
     * @param pointData 字符串坐标数据 ("0,0,0")
     * @return Vector 对象 序列化失败则返回 null
     */
    public static com.sk89q.worldedit.Vector getWVfromXYZ(String pointData) {

        if(!pointData.contains(",")) return null;
        String[] datas = pointData.replaceAll(" ", "").split(",");

        com.sk89q.worldedit.Vector vector = null;

        try {

            vector = new com.sk89q.worldedit.Vector(

                    Double.parseDouble(datas[0]),
                    Double.parseDouble(datas[1]),
                    Double.parseDouble(datas[2])
            );
        }
        catch (Exception e) { }

        return vector;
    }

    /**
     * 在指定位置击退玩家
     *
     * @param mmorpgPlayer 玩家
     * @param source 源位置
     * @param power 力度
     */
    public static void knockBackVector(MMORPGPlayer mmorpgPlayer, Location source, double power) {
        // vector knock Back player
        double x = source.getX() - source.getX();
        double y = source.getY() - source.getY();
        double z = source.getZ() - source.getZ();

        Vector vector = new Vector(x, y, z).normalize().multiply(power);
        mmorpgPlayer.setVelocity(vector);
    }
}
