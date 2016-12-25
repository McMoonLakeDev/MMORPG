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
 
  
package com.minecraft.moonlake.mmorpg.resources;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/7/3.
 */
public final class PlayerLevel {

	private final static Map<Integer, Integer> LEVELMAP;
	
	/**
	 * 月色之湖 MMORPG 玩家最大等级
	 */
	public final static int MAXLEVEL = 50;
	
	static {
		
		LEVELMAP = new HashMap<>();
		
		// add 1 - 100 level
		for(int i = 1; i < 101; i++) {
			
			LEVELMAP.put(i, getLevelNeedExp(i));
		}
	}
	
	/**
	 * 获取指定等级的需求经验值
	 * 
	 * @param level 等级
	 * @return 等级的经验值
	 */
    public static int getLevelNeedExp(int level) {

        if(LEVELMAP.containsKey(level)) {
        	
        	return LEVELMAP.get(level);
        }
        return 20 * (level * level * level * 5 + level) - 80;
    }
    
    /**
     * 获取指定玩家的下一级需求经验
     * 
     * @param mmorpgPlayer 玩家
     * @return 下一级需求经验
     */
    public static int getNextLevelNeedExp(MMORPGPlayer mmorpgPlayer) {
    	
    	int nextExp = getLevelNeedExp(mmorpgPlayer.getLevel() + 1);
    	
    	return nextExp - mmorpgPlayer.getExp();
    }
    
    /**
     * 获取指定玩家的经验条进度
     * 
     * @param mmorpgPlayer 玩家
     * @return 经验条进度
     */
    public static double getPlayerExpProgress(MMORPGPlayer mmorpgPlayer) {
    	
    	if(mmorpgPlayer.getExp() > 0) {
    		
    		int upExp = getLevelNeedExp(mmorpgPlayer.getLevel() - 1);
        	double progress = ((double)(mmorpgPlayer.getExp() - upExp) / (double)(getLevelNeedExp(mmorpgPlayer.getLevel() + 1) - upExp));
        	
        	return StringUtil.rounding(progress, 5);
    	}
    	return 0d;
    }
    
    /**
     * 关闭对象类并释放内存占用
     */
    public static void close() {
    	
    	LEVELMAP.clear();
    }
}
