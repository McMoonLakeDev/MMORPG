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

import com.minecraft.moonlake.mmorpg.api.config.yaml.YamlConfig;
import com.minecraft.moonlake.mmorpg.api.config.yaml.YamlManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by MoonLake on 2016/07/04.
 */
public abstract class DataConfig extends DataFile implements YamlConfig {

	private YamlConfiguration yaml;

	public DataConfig(String path) {
		
		this(path, true);
	}

	public DataConfig(String path, boolean read) {

		this(path, true, read);
	}

	public DataConfig(String path, boolean create, boolean read) {
		
		this(new File(getMain().getDataFolder(), path), create, read);
	}
	
	public DataConfig(File file) {
		
		this(file, true);
	}
	
	public DataConfig(File file, boolean read) {

		this(file, true, read);
	}

	public DataConfig(File file, boolean create, boolean read) {

		super(file);

		if(getFile().getParentFile() != null && !getFile().getParentFile().exists()) {

			getFile().getParentFile().mkdirs();
		}
		if(create) {

			createFile();
		}
		if(read) {

			readConfig();
		}
	}

	/**
	 * 创建并初始化 Yaml 配置文件的文件对象
	 */
	@Override
	public void createFile() {

		if(!getFile().exists()) {

			try {

				getFile().createNewFile();
			}
			catch(Exception e) {

				getInstance().log("创建数据配置文件 '" + getFile().getName() + "' 时异常: " + e.getMessage());

				if(getMain().isDebug()) {

					e.printStackTrace();
				}
			}
		}
	}

	/**
     * 读取数据 Yaml 配置文件的文件对象
     */
	@Override
    public void readConfig() {
    	
    	this.yaml = YamlManager.loadYamlConfiguration(getFile());
    }
	
    /**
     * 获取数据 Yaml 配置文件的路径
     * 
     * @return 路径
     */
	@Override
    public String getPath() {
    	
    	return super.getPath();
    }
    
    /**
     * 获取数据 Yaml 配置文件的文件对象
     * 
     * @return 文件对象
     */
	@Override
    public File getFile() {
    	
    	return super.getFile();
    }
    
    /**
     * 将数据 Yaml 配置文件指定路径设置整数值
     * 
     * @param path 路径
     * @param value 值
     */
	@Override
    public void setInt(String path, int value) {
    	
    	this.yaml.set(path, value);
    }
    
    /**
     * 将数据 Yaml 配置文件指定路径设置双精度浮点值
     * 
     * @param path 路径
     * @param value 值
     */
	@Override
    public void setDouble(String path, double value) {
    	
    	this.yaml.set(path, value);
    }
    
    /**
     * 将数据 Yaml 配置文件指定路径设置单精度浮点值
     * 
     * @param path 路径
     * @param value 值
     */
	@Override
    public void setFloat(String path, float value) {
    	
    	this.yaml.set(path, value);
    }

	/**
	 * 将数据 Yaml 配置文件指定路径设置布尔值
	 *
	 * @param path  路径
	 * @param value 值
	 */
	@Override
	public void setBoolean(String path, boolean value) {

		this.yaml.set(path, value);
	}

	/**
     * 将数据 Yaml 配置文件指定路径设置字符串值
     * 
     * @param path 路径
     * @param value 值
     */
	@Override
    public void setString(String path, String value) {
    	
    	this.yaml.set(path, value);
    }

	/**
	 * 将数据 Yaml 配置文件指定路径设置字符串列表值
	 *
	 * @param path  路径
	 * @param value 值
	 */
	@Override
	public void setStringList(String path, List<String> value) {

		this.yaml.set(path, value);
	}

	/**
	 * 获取数据 Yaml 配置文件指定路径的值
	 *
	 * @param path 路径
	 * @return 路径的值
	 */
	public Object get(String path) {

		return this.yaml.get(path, null);
	}

	/**
     * 获取数据 Yaml 配置文件指定路径的整数值
     * 
     * @param path 路径
     */
	@Override
    public int getInt(String path) {
    	
    	return this.yaml.getInt(path);
    }
    
    /**
     * 获取数据 Yaml 配置文件指定路径的双精度浮点值
     * 
     * @param path 路径
     */
	@Override
    public double getDouble(String path) {
    	
    	return this.yaml.getDouble(path);
    }
    
    /**
     * 获取数据 Yaml 配置文件指定路径的单精度浮点值
     * 
     * @param path 路径
     */
	@Override
    public float getFloat(String path) {
    	
    	return (float)this.yaml.getDouble(path);
    }

	/**
	 * 获取数据 Yaml 配置文件指定路径的布尔值
	 *
	 * @param path 路径
	 * @return 路径的布尔值
	 */
	@Override
	public boolean getBoolean(String path) {

		return this.yaml.getBoolean(path);
	}

	/**
     * 获取数据 Yaml 配置文件指定路径的字符串值
     * 
     * @param path 路径
     */
	@Override
    public String getString(String path) {
    	
    	return this.yaml.getString(path);
    }

	/**
	 * 获取数据 Yaml 配置文件指定路径的字符串列表值
	 *
	 * @param path 路径
	 * @return 路径的字符串列表值
	 */
	@Override
	public List<String> getStringList(String path) {

		return this.yaml.getStringList(path);
	}

	/**
	 * 获取数据 Yaml 配置文件指定路径的键集合值
	 *
	 * @param path 路径
	 * @return 键的集合值
	 */
	public Set<String> getKeys(String path) {

		return getKeys(path, false);
	}

	/**
	 * 获取数据 Yaml 配置文件指定路径的键集合值
	 *
	 * @param path 路径
	 * @param subSection 是否子节也获取
	 * @return 键的集合值
	 */
	public Set<String> getKeys(String path, boolean subSection) {

		ConfigurationSection section = this.yaml.getConfigurationSection(path);
		return section != null ? section.getKeys(subSection) : new HashSet<>();
	}

	/**
	 * 将数据 Yaml 配置文件的对象进行读取
	 */
	@Override
    public void loadData() {
    	
    }
    
	/**
	 * 将数据 Yaml 配置文件的对象进行保存
	 */
	@Override
	public void saveData() {
		
		try {
			
			this.yaml.save(getFile());
		}
		catch(Exception e) {
			
			getInstance().log("保存数据配置文件 '" + getFile().getName() + "' 时异常: " + e.getMessage());
			
			if(getMain().isDebug()) {
				
				e.printStackTrace();
			}
		}
	}
}
