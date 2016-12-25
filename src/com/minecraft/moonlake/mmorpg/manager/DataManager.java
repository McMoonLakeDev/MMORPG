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

import com.minecraft.moonlake.mmorpg.api.data.player.MMORPGInventoryData;
import com.minecraft.moonlake.mmorpg.api.data.player.MMORPGPlayerData;
import com.minecraft.moonlake.mmorpg.api.data.player.MMORPGPlayerMountData;
import com.minecraft.moonlake.mmorpg.api.mount.Mount;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.role.AbstractRole;
import com.minecraft.moonlake.mmorpg.api.skill.Skill;
import com.minecraft.moonlake.mmorpg.api.skill.data.MMORPGPlayerSkillData;
import com.minecraft.moonlake.mmorpg.api.skill.talent.Talent;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by MoonLake on 2016/5/22.
 */
public final class DataManager extends MMORPGManager {
    
    /**
     * 初始化玩家的 MMORPG 数据
     *
     * @param name 玩家名
     */
    public static void initPlayerData(String name) {

        getMain().getData().initData(name);
    }

    /**
     * 初始化玩家的 MMORPG 数据
     *
     * @param name 玩家名
     * @param maxHealth 最大血量
     * @param maxMagic 最大魔法
     * @param maxSoul 最大灵魂
     */
    public static void initPlayerData(String name, double maxHealth, int maxMagic, int maxSoul) {

        getMain().getData().initData(name, maxHealth, maxMagic, maxSoul);
    }

    /**
     * 读取玩家的 MMORPG 数据到玩家对象
     *
     * @param name 玩家名
     * @param mmorpgPlayer 玩家对象
     */
    public static void loadPlayerData(String name, MMORPGPlayer mmorpgPlayer) {

        MMORPGPlayerData data = getMain().getData().getData(name);
        if(data != null) {

            AbstractRole role = data.getRoleType() != null ? data.getRoleType().newInstance() : null;
            if(role != null) {

                mmorpgPlayer.setRole(role);
            }
            mmorpgPlayer.setRPGMaxHealth(data.getMaxHealth());
            mmorpgPlayer.setRPGHealth(data.getHealth());
            mmorpgPlayer.setMaxMagic(data.getMaxMagic());
            mmorpgPlayer.setMagic(data.getMagic());
            mmorpgPlayer.setRPGLevel(data.getLevel());
            mmorpgPlayer.setExp(data.getExp());
            mmorpgPlayer.setMaxSoul(data.getMaxSoul());
            mmorpgPlayer.setSoul(data.getSoul());
        }
    }

    /**
     * 保存玩家的 MMORPG 到云数据
     *
     * @param name 玩家名
     */
    public static void savePlayerData(String name) {

        MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(name);
        if(mmorpgPlayer != null) {

            MMORPGPlayerData data = new MMORPGPlayerData(name);
            data.setRoleType(mmorpgPlayer.getRoleType());
            data.setMaxHealth(mmorpgPlayer.getRPGMaxHealth());
            data.setHealth(mmorpgPlayer.getRPGHealth());
            data.setMaxMagic(mmorpgPlayer.getMaxMagic());
            data.setMagic(mmorpgPlayer.getMagic());
            data.setLevel(mmorpgPlayer.getRPGLevel());
            data.setExp(mmorpgPlayer.getExp());
            data.setMaxSoul(mmorpgPlayer.getMaxSoul());
            data.setSoul(mmorpgPlayer.getSoul());

            getMain().getData().saveData(name, data);
        }
    }

    /**
     * 保存所有玩家的 MMORPG 数据到云
     */
    public static void savePlayersData() {

        Set<MMORPGPlayer> onlinePlayers = AccountManager.getOnlinePlayers();

        savePlayersData(onlinePlayers);
        savePlayersSkillData(onlinePlayers);
        savePlayerInventoryData(onlinePlayers);
        savePlayerRepertoryDatas(onlinePlayers);
        savePlayerMountDatas(onlinePlayers);
    }

    /**
     * 保存所有玩家的 MMORPG 数据到云
     *
     * @param mmorpgPlayers 玩家集合
     */
    public static void savePlayersData(Set<MMORPGPlayer> mmorpgPlayers) {

        MMORPGPlayerData[] datas = new MMORPGPlayerData[mmorpgPlayers.size()];
        int index = 0;

        for(MMORPGPlayer mmorpgPlayer : mmorpgPlayers) {

            datas[index] = new MMORPGPlayerData(mmorpgPlayer.getName());
            datas[index].setRoleType(mmorpgPlayer.getRoleType());
            datas[index].setMaxHealth(mmorpgPlayer.getRPGMaxHealth());
            datas[index].setHealth(mmorpgPlayer.getRPGHealth());
            datas[index].setMaxMagic(mmorpgPlayer.getMaxMagic());
            datas[index].setMagic(mmorpgPlayer.getMagic());
            datas[index].setLevel(mmorpgPlayer.getRPGLevel());
            datas[index].setExp(mmorpgPlayer.getExp());
            datas[index].setMaxSoul(mmorpgPlayer.getMaxSoul());
            datas[index].setSoul(mmorpgPlayer.getSoul());
            index++;
        }
        getMain().getData().saveDatas(datas);
    }

    /**
     * 初始化玩家的 MMORPG 数据和默认数据
     *
     * @param name 玩家名
     */
    public static void initPlayerDataAndDefaultData(String name) {

        double maxHealth = ConfigManager.get("PlayerDefaultData.MaxHealth").asDouble();
        int maxMagic = ConfigManager.get("PlayerDefaultData.MaxMagic").asInt();
        int maxSoul = ConfigManager.get("PlayerDefaultData.MaxSoul").asInt();

        initPlayerData(name, maxHealth, maxMagic, maxSoul);
    }

    /**
     * 保存所有玩家的 MMORPG 背包数据到云
     *
     * @param mmorpgPlayers 玩家集合
     */
    public static void savePlayerInventoryData(Set<MMORPGPlayer> mmorpgPlayers) {

        MMORPGInventoryData[] inventoryDatas = new MMORPGInventoryData[mmorpgPlayers.size()];
        int index = 0;

        for(MMORPGPlayer mmorpgPlayer : mmorpgPlayers) {

            inventoryDatas[index] = new MMORPGInventoryData(mmorpgPlayer.getName());
            inventoryDatas[index].setDataMap(PlayerManager.serializeInventory(mmorpgPlayer));
            index++;
        }
        getMain().getData().saveInventoryDatas(inventoryDatas);
    }

    /**
     * 保存玩家的 MMORPG 背包数据到云
     *
     * @param name 玩家名
     */
    public static void savePlayerInventoryData(String name) {

        MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(name);
        if(mmorpgPlayer != null) {

            getMain().getData().saveInventoryData(name, PlayerManager.serializeInventory(mmorpgPlayer));
            mmorpgPlayer.getInventory().clear();
        }
    }

    /**
     * 读取玩家的 MMORPG 背包数据
     *
     * @param name 玩家名
     * @return 序列化数据集合 异常或没有返回空集合
     */
    public static Map<String, String> loadPlayerInventoryData(String name) {

        return getMain().getData().loadInventoryData(name);
    }

    /**
     * 保存玩家的 MMORPG 个人仓库数据
     *
     * @param name 玩家名
     */
    public static void savePlayerRepertoryData(String name) {

        MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(name);
        if(mmorpgPlayer != null) {

            getMain().getData().saveRepertoryData(mmorpgPlayer.getName(), PlayerManager.serializeRepertory(mmorpgPlayer));
        }
    }

    /**
     * 保存所有玩家的 MMORPG 个人仓库数据
     *
     * @param mmorpgPlayers 玩家集合
     */
    public static void savePlayerRepertoryDatas(Set<MMORPGPlayer> mmorpgPlayers) {

        MMORPGInventoryData[] repertoryDatas = new MMORPGInventoryData[mmorpgPlayers.size()];
        int index = 0;

        for(MMORPGPlayer mmorpgPlayer : mmorpgPlayers) {

            repertoryDatas[index] = new MMORPGInventoryData(mmorpgPlayer.getName());
            repertoryDatas[index].setDataMap(PlayerManager.serializeRepertory(mmorpgPlayer));
            index++;
        }
        getMain().getData().saveRepertoryDatas(repertoryDatas);
    }

    /**
     * 读取指定 MMORPG 玩家的个人仓库数据
     *
     * @param name 玩家名
     */
    public static Map<Integer, String> loadPlayerRepertoryData(String name) {

        return getMain().getData().loadRepertoryData(name);
    }

    /**
     * 初始化指定 MMORPG 玩家的坐骑数据
     *
     * @param name 玩家名
     * @param defaultMounts 默认坐骑
     */
    public static void initPlayerMountData(String name, Mount... defaultMounts) {

        getMain().getData().initPlayerMountData(name, defaultMounts);
    }

    /**
     * 读取指定 MMORPG 玩家的坐骑数据
     *
     * @param name 玩家名
     * @return 坐骑集合 异常或没有则返回空集合
     */
    public static List<Mount> loadPlayerMountData(String name) {

        return getMain().getData().loadPlayerMountData(name);
    }

    /**
     * 保存指定 MMORPG 玩家的坐骑数据
     *
     * @param name 玩家名
     */
    public static void savePlayerMountData(String name) {

        MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(name);
        if(mmorpgPlayer != null) {

            MMORPGPlayerMountData mountData = new MMORPGPlayerMountData(name);
            mountData.setMounts(mmorpgPlayer.getMount().getMountList());

            getMain().getData().savePlayerMountData(name, mountData);
        }
    }

    /**
     * 保存所有 MMORPG 玩家的坐骑数据
     *
     * @param mmorpgPlayers 玩家集合
     */
    public static void savePlayerMountDatas(Set<MMORPGPlayer> mmorpgPlayers) {

        MMORPGPlayerMountData[] mountDatas = new MMORPGPlayerMountData[mmorpgPlayers.size()];
        int index = 0;

        for(MMORPGPlayer mmorpgPlayer : mmorpgPlayers) {

            mountDatas[index] = new MMORPGPlayerMountData(mmorpgPlayer.getName());
            mountDatas[index].setMounts(mmorpgPlayer.getMount().getMountList());
            index++;
        }
        getMain().getData().savePlayerMountDatas(mountDatas);
    }

    /**
     * 初始化玩家的 MMORPG 技能数据
     *
     * @param name 玩家名
     */
    public static void initPlayerSkillData(String name) {

        getMain().getData().initPlayerSkillData(name);
    }

    /**
     * 保存所有玩家的 MMORPG 技能数据到云
     *
     * @param mmorpgPlayers 玩家集合
     */
    public static void savePlayersSkillData(Set<MMORPGPlayer> mmorpgPlayers) {

        MMORPGPlayerSkillData[] skillDatas = new MMORPGPlayerSkillData[mmorpgPlayers.size()];
        int index = 0;

        for(MMORPGPlayer mmorpgPlayer : mmorpgPlayers) {

            skillDatas[index] = new MMORPGPlayerSkillData(mmorpgPlayer.getName());
            skillDatas[index].setSkillPoint(mmorpgPlayer.getSkill().getSkillPoint());
            skillDatas[index].setPassive(mmorpgPlayer.getSkill().getPassive());
            skillDatas[index].setSkills(mmorpgPlayer.getSkill().getSkillList());
            skillDatas[index].setUltimate(mmorpgPlayer.getSkill().getUltimate());
            skillDatas[index].setTalents(mmorpgPlayer.getSkill().getTalentList());
            index++;
        }
        getMain().getData().savePlayerSkillDatas(skillDatas);
    }

    /**
     * 加载玩家的 MMORPG 技能数据到玩家对象
     *
     * @param name 玩家名
     * @param mmorpgPlayer 玩家对象
     */
    public static void loadPlayerSkillData(String name, MMORPGPlayer mmorpgPlayer) {

        MMORPGPlayerSkillData skillData = getMain().getData().loadPlayerSkillData(name);

        if(skillData != null) {

            if((skillData.getSkills() == null || skillData.getSkills().isEmpty()) && mmorpgPlayer.getRole() != null) {

                skillData.setSkills(Arrays.asList(mmorpgPlayer.getRole().getRoleSkills()));
            }
            if(skillData.getSkills() != null) {

                for(Skill skill : skillData.getSkills()) {

                    mmorpgPlayer.getSkill().addSkill(skill);
                }
            }
            if((skillData.getTalents() == null || skillData.getTalents().isEmpty()) && mmorpgPlayer.getRole() != null) {

                skillData.setTalents(Arrays.asList(mmorpgPlayer.getRole().getRoleTalents()));
            }
            if(skillData.getSkills() != null) {

                for(Talent talent : skillData.getTalents()) {

                    mmorpgPlayer.getSkill().addTalent(talent);
                }
            }
            if(skillData.getPassive() != null) {

                mmorpgPlayer.getSkill().setPassive(skillData.getPassive());
            }
            if(skillData.getPassive() == null && mmorpgPlayer.getRole() != null) {

                mmorpgPlayer.getSkill().setPassive(mmorpgPlayer.getRole().getRolePassive());
            }
            if(skillData.getUltimate() != null) {

                mmorpgPlayer.getSkill().setUltimate(skillData.getUltimate());
            }
            if(skillData.getUltimate() == null && mmorpgPlayer.getRole() != null) {

                mmorpgPlayer.getSkill().setUltimate(mmorpgPlayer.getRole().getRoleUltimate());
            }
        }
    }

    /**
     * 保存玩家的技能以及天赋数据
     *
     * @param name 玩家名
     */
    public static void savePlayerSkillData(String name) {

        MMORPGPlayer mmorpgPlayer = AccountManager.getPlayer(name);
        if(mmorpgPlayer != null) {

            MMORPGPlayerSkillData skillData = new MMORPGPlayerSkillData(mmorpgPlayer.getName());
            skillData.setSkillPoint(mmorpgPlayer.getSkill().getSkillPoint());
            skillData.setPassive(mmorpgPlayer.getSkill().getPassive());
            skillData.setSkills(mmorpgPlayer.getSkill().getSkillList());
            skillData.setUltimate(mmorpgPlayer.getSkill().getUltimate());
            skillData.setTalents(mmorpgPlayer.getSkill().getTalentList());

            getMain().getData().savePlayerSkillData(mmorpgPlayer.getName(), skillData);
        }
    }

    /**
     * 获取玩家的技能点数
     *
     * @param name 玩家名
     * @return 技能点数
     */
    public static int getPlayerSkillPoint(String name) {

        return getMain().getData().getPlayerSkillPoint(name);
    }

    /**
     * 设置玩家的技能点数
     *
     * @param name 玩家名
     * @param point 新的技能点数
     */
    public static void setPlayerSkillPoint(String name, int point) {

        getMain().getData().setPlayerSkillPoint(name, point);
    }

    /**
     * 给予指定玩家数量的技能点
     *
     * @param name 玩家名
     * @param give 给予的数量
     */
    public static void givePlayerSkillPoint(String name, int give) {

        getMain().getData().givePlayerSkillPoint(name, give);
    }

    /**
     * 减少指定玩家数量的技能点
     *
     * @param name 玩家名
     * @param take 减少的数量
     */
    public static void takePlayerSkillPoint(String name, int take) {

        getMain().getData().takePlayerSkillPoint(name, take);
    }
}
