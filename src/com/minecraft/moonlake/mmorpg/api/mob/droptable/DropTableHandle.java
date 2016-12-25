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
 
  
package com.minecraft.moonlake.mmorpg.api.mob.droptable;

import com.minecraft.moonlake.mmorpg.api.config.yaml.YamlConfig;
import com.minecraft.moonlake.mmorpg.api.config.yaml.YamlManager;
import com.minecraft.moonlake.mmorpg.api.item.currency.CurrencyType;
import com.minecraft.moonlake.mmorpg.api.item.expendable.potion.PotionType;
import com.minecraft.moonlake.mmorpg.manager.ItemManager;
import com.minecraft.moonlake.mmorpg.manager.MMORPGManager;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

/**
 * Created by MoonLake on 2016/8/3.
 */
public final class DropTableHandle extends MMORPGManager {

    private final static String DROP_TABLE_FOLDER = "/droptables";
    private final static Set<String> RPG_ITEM_PREFIX_SET = new HashSet<>();

    static {

        RPG_ITEM_PREFIX_SET.add("_money");
        RPG_ITEM_PREFIX_SET.add("_potion");

        File folder = new File(getMain().getDataFolder(), DROP_TABLE_FOLDER);

        if(!folder.exists()) {

            folder.mkdir();
        }
    }

    /**
     * 删除指定名称的掉落表
     *
     * @param name 掉落表名
     * @return true 则删除成功 else 没有删除成功
     */
    public static boolean delete(String name) {

        File file = has(name);

        if(file == null) {

            throw new RuntimeException("The delete drop table is not exists.");
        }
        return file.delete();
    }

    /**
     * 将指定掉落表对象进行保存
     *
     * @param dropTable 掉落表对象
     * @param force 是否强制保存
     * @throws RuntimeException 如果非强制保存并且表已经存在则抛出异常
     */
    public static void save(DropTable dropTable, boolean force) {

        if(dropTable == null) {

            throw new IllegalArgumentException("The drop table is null.");
        }
        File file = has(dropTable.getName());

        if(file != null && !force) {

            throw new RuntimeException("The drop table is already exists.");
        }
        if(file != null && force) {

            delete(dropTable.getName());
        }
        YamlConfig yamlConfig = YamlManager.loadYamlConfig(file, true, true);
        Iterator<Map.Entry<ItemStack, DropOption>> iterator = dropTable.getDropMap().entrySet().iterator();
        List<String> drops = new ArrayList<>();

        while (iterator.hasNext()) {

            Map.Entry<ItemStack, DropOption> entry = iterator.next();
            ItemStack dropItem = entry.getKey();

            if(!ItemManager.isAir(dropItem)) {

                drops.add(dropItem.getType().name() + ":" + dropItem.getDurability() + " " + entry.getValue().toString());
            }
        }
        yamlConfig.setString("DropTable.Name", dropTable.getName());
        yamlConfig.setStringList("DropTable.Drops", drops);
        yamlConfig.saveData();
    }

    /**
     * 将指定掉落表进行读取
     *
     * @param name 掉落表名
     * @return 掉落表对象 异常或没有则返回 null
     */
    public static DropTable load(String name) {

        File file = has(name);

        if(file == null) {

            return null;
        }
        YamlConfig yamlConfig = YamlManager.loadYamlConfig(file, true);

        return loadFile(yamlConfig, "DropTable.Drops");
    }

    /**
     * 获取指定名称的掉落表是否存在
     *
     * @param name 掉落表名
     * @return 存在则返回 File 对象 否则返回 null
     */
    public static File has(String name) {

        File file = new File(getMain().getDataFolder(), DROP_TABLE_FOLDER + "/" + name + ".yml");

        if(!file.exists()) {

            return null;
        }
        return file;
    }

    /**
     * 将指定掉落表文件进行读取
     *
     * @param yamlConfig Yaml 文件对象
     * @param path 路径
     * @return 掉落表对象 异常或没有则返回 null
     */
    @Deprecated
    public static DropTable loadFile(YamlConfig yamlConfig, String path) {

        if(yamlConfig == null || yamlConfig.get(path) == null) {

            return null;
        }
        DropTable dropTable = new DropTable(yamlConfig.getFile().getName());
        List<String> drops = yamlConfig.getStringList(path);

        if(drops != null && drops.size() > 0) {

            for(String drop : drops) {

                if(drop != null && !drop.equals("")) {

                    String[] dropDatas = drop.contains(" ") ? drop.split(" ") : new String[] { drop };
                    boolean hasItemData = dropDatas[0].contains(":");
                    String[] itemDatas = hasItemData ? dropDatas[0].split(":") : new String[] {};
                    boolean hasAmountRange = dropDatas[1].contains("-");
                    String[] amountRangeDatas = hasAmountRange ? dropDatas[1].split("-") : new String[] {};
                    Material material = Material.matchMaterial(hasItemData ? itemDatas[0] : dropDatas[0]);
                    int data = 0, minAmount = 1, maxAmount = 1;
                    double chance = 1d;
                    boolean isRPG = false;
                    ItemStack dropItem = null;

                    if(material != null) {

                        data = hasItemData && StringUtil.isInteger(itemDatas[1]) ? Integer.parseInt(itemDatas[1]) : 0;
                        minAmount = dropDatas.length > 1 && hasAmountRange ?
                                StringUtil.isInteger(amountRangeDatas[0]) ? Integer.parseInt(amountRangeDatas[0]) : 1 :
                                StringUtil.isInteger(dropDatas[1]) ? Integer.parseInt(dropDatas[1]) : 1;
                        maxAmount = dropDatas.length > 1 && hasAmountRange ?
                                StringUtil.isInteger(amountRangeDatas[1]) ? Integer.parseInt(amountRangeDatas[1]) : 1 :
                                StringUtil.isInteger(dropDatas[1]) ? Integer.parseInt(dropDatas[1]) : 1;
                        chance = dropDatas.length > 2 && StringUtil.isDouble(dropDatas[2]) ? Double.parseDouble(dropDatas[2]) : 1d;
                    }
                    else if(hasItemData ? RPG_ITEM_PREFIX_SET.contains(itemDatas[0]) : RPG_ITEM_PREFIX_SET.contains(dropDatas[0])) {

                        String rpgType = hasItemData ? itemDatas[0] : dropDatas[0];
                        String rpgData = hasItemData ? itemDatas[1] : null;

                        if(rpgData == null) {

                            getMain().log("读取掉落表时发现 RPG 物品 " + rpgType + " 但未知数据!!!");
                            continue;
                        }
                        if(rpgType.equalsIgnoreCase("_money")) {
                            // drop money
                            if(!rpgData.startsWith("gold")) {

                                rpgData = "gold" + rpgData;
                            }
                            CurrencyType currencyType = CurrencyType.fromType(rpgData);

                            if(currencyType == null) {

                                getMain().log("读取掉落表时 RPG 物品钱币类型时未知数据: " + rpgData);
                                continue;
                            }
                            dropItem = currencyType.newInstance(1).getItem();
                        }
                        else if(rpgType.equalsIgnoreCase("_potion")) {
                            // drop potion
                            int reqLevel = 0, value = 1;

                            if(rpgData.contains("[") && rpgData.contains("]")) {

                                String[] attDatas = rpgData.substring(rpgData.indexOf("[") + 1, rpgData.indexOf("]")).split("@");
                                reqLevel = StringUtil.isInteger(attDatas[0]) ? Integer.parseInt(attDatas[0]) : 0;
                                value = StringUtil.isInteger(attDatas[1]) ? Integer.parseInt(attDatas[1]) : 1;
                            }
                            String potion = rpgData.contains("[") ? rpgData.substring(0, rpgData.indexOf("[")) : "null";
                            PotionType potionType = PotionType.fromType(potion);

                            if(potionType == null) {

                                getMain().log("读取掉落表时 RPG 物品药水类型时未知数据: " + potion);
                                continue;
                            }
                            dropItem = potionType.newInstance(1, reqLevel, value).getItem();
                        }
                        isRPG = true;

                        minAmount = dropDatas.length > 1 && hasAmountRange ?
                                StringUtil.isInteger(amountRangeDatas[0]) ? Integer.parseInt(amountRangeDatas[0]) : 1 :
                                StringUtil.isInteger(dropDatas[1]) ? Integer.parseInt(dropDatas[1]) : 1;
                        maxAmount = dropDatas.length > 1 && hasAmountRange ?
                                StringUtil.isInteger(amountRangeDatas[1]) ? Integer.parseInt(amountRangeDatas[1]) : 1 :
                                StringUtil.isInteger(dropDatas[1]) ? Integer.parseInt(dropDatas[1]) : 1;
                        chance = dropDatas.length > 2 && StringUtil.isDouble(dropDatas[2]) ? Double.parseDouble(dropDatas[2]) : 1d;
                    }
                    if(!isRPG) {

                        dropItem = ItemManager.getLibrary().create(material, data, 1);
                    }
                    DropOption dropOption = new DropOption(minAmount, maxAmount, chance);

                    if(dropItem == null) {

                        continue;
                    }
                    dropTable.addDrop(dropItem, dropOption);
                }
            }
        }
        return dropTable;
    }
}
