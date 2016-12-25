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
 
  
package com.minecraft.moonlake.mmorpg.api.mob;

import com.minecraft.moonlake.mmorpg.api.data.DataConfig;
import com.minecraft.moonlake.mmorpg.api.mob.droptable.DropTable;
import com.minecraft.moonlake.mmorpg.api.mob.droptable.DropTableHandle;

/**
 * Created by MoonLake on 2016/07/05.
 */
public class MobConfig extends DataConfig {

	private final MobType mobType;
	private final String name;
	private MobData mobData;
	
	public MobConfig(MobType mobType, String name) {
		
		this(mobType, name, true);
	}

	public MobConfig(MobType mobType, String name, boolean read) {

		this(mobType, name, true, read);
	}

	public MobConfig(MobType mobType, String name, boolean create, boolean read) {

		super("/mobs/" + mobType.getType().toLowerCase() + "/" + name + ".yml", create, read);

		this.name = name;
		this.mobType = mobType;
	}
	
	/**
	 * 获取此怪物的类型
	 * 
	 * @return 类型
	 */
	public MobType getMobType() {
		
		return mobType;
	}

	/**
	 * 设置怪物数据 Yaml 配置文件的怪物数据类型对象
	 *
	 * @param mobData 怪物数据类对象
     */
	public void setMobData(MobData mobData) {

		this.mobData = mobData;
	}

	/**
	 * 获取怪物数据 Yaml 配置文件的怪物数据类对象
	 *
	 * @return 怪物数据类对象 没有则返回 null
     */
	public MobData getMobData() {

		return mobData;
	}

	/**
	 * 将怪物数据 Yaml 配置文件的对象进行读取
	 */
	@Override
    public void loadData() {
    	
    	MobType mobType = MobType.fromType(getString("Mob.Type"));

		if(mobType != null) {

			mobData = new MobData(mobType, name);
			mobData.setHealth(getDouble("Mob.Base.Health"));
			mobData.setMaxHealth(getDouble("Mob.Base.MaxHealth"));
			mobData.setLevel(getInt("Mob.Base.Level"));
			mobData.setDropExp(getInt("Mob.Base.DropExp"));

			if(get("Mob.Base.DropTable") != null) {

				for(String tableName : getKeys("Mob.Base.DropTable")) {

					DropTable dropTable = DropTableHandle.load(tableName);

					if(dropTable == null) {

						getInstance().log("读取自定义怪物的数据时不存在 " + tableName + " 的掉落表.");
						continue;
					}
					mobData.addDropItems(dropTable.getDropMap());
				}
			}
			if(get("Mob.Base.DropItems") != null) {

				DropTable dropTable = DropTableHandle.loadFile(this, "Mob.Base.DropItems");

				if(dropTable != null) {

					mobData.addDropItems(dropTable.getDropMap());
				}
			}
			mobData.setDisplayName(getString("Mob.Options.DisplayName"));
		}
    }
	
	/**
	 * 将怪物数据 Yaml 配置文件的对象进行保存
	 */
	@Override
	public void saveData() {

		if(mobData != null) {

			this.setString("Mob.Name", name);
			this.setString("Mob.Type", mobData.getMobType().getType());
			this.setDouble("Mob.Base.Health", mobData.getHealth());
			this.setDouble("Mob.Base.MaxHealth", mobData.getMaxHealth());
			this.setInt("Mob.Base.Level", mobData.getLevel());
			this.setInt("Mob.Base.DropExp", mobData.getDropExp());
			this.setString("Mob.Options.DisplayName", mobData.getDisplayName());
		}
		super.saveData();
	}
}
