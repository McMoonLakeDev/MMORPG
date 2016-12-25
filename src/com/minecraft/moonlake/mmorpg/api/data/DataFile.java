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
 
  
package com.minecraft.moonlake.mmorpg.api.data;

import com.minecraft.moonlake.mmorpg.MMORPGPlugin;
import com.minecraft.moonlake.mmorpg.api.MMORPG;

import java.io.File;

/**
 * Created by MoonLake on 2016/07/04.
 */
public class DataFile {

	private final static MMORPG MAIN;
	private final String path;
	private File file;
	
	static {
		
		MAIN = MMORPGPlugin.getInstances();
	}
	
	public DataFile(String path) {
		
		this.path = path;
	}
	
	public DataFile(File file) {
		
		this.path = file.getPath();
		this.file = file;
	}
	
    /**
     * 获取月色之湖 MMORPG 主类实例对象
     *
     * @return MMORPG
     */
    public MMORPG getInstance() {
    	
    	return MAIN;
    }
    
    /**
     * 获取月色之湖 MMORPG 主类实例对象
     *
     * @return MMORPG
     */
    protected static MMORPG getMain() {
    	
    	return MAIN;
    }
	
	/**
     * 获取数据文件的路径
     * 
     * @return 路径
     */
    public String getPath() {
    	
    	return path;
    }
	
	/**
     * 获取数据文件的文件对象
     * 
     * @return 文件对象
     */
	public File getFile() {
		
		return file;
	}

	/**
	 * 获取数据文件对象是否存在
	 *
	 * @return true 则存在 else 没有
     */
	public boolean exists() {

		return file.exists();
	}

	/**
	 * 删除数据文件对象的磁盘文件
	 */
	public boolean delete() {
		
		boolean result = false;
		
		try {
			
			result = file.delete();
		}
		catch(Exception e) {
			
			getMain().log("删除数据文件时异常: " + e.getMessage());
			
			if(getMain().isDebug()) {
				
				e.printStackTrace();
			}
		}
		return result;
	}
}
