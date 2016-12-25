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
 
  
package com.minecraft.moonlake.mmorpg.sql;

import com.minecraft.moonlake.mmorpg.api.MMORPG;
import com.minecraft.moonlake.mmorpg.api.data.player.MMORPGInventoryData;
import com.minecraft.moonlake.mmorpg.api.data.player.MMORPGPlayerData;
import com.minecraft.moonlake.mmorpg.api.data.player.MMORPGPlayerMountData;
import com.minecraft.moonlake.mmorpg.api.event.player.PlayerSkillPointChangeEvent;
import com.minecraft.moonlake.mmorpg.api.mount.Mount;
import com.minecraft.moonlake.mmorpg.api.mount.MountType;
import com.minecraft.moonlake.mmorpg.api.role.RoleType;
import com.minecraft.moonlake.mmorpg.api.skill.Skill;
import com.minecraft.moonlake.mmorpg.api.skill.data.MMORPGPlayerSkillData;
import com.minecraft.moonlake.mmorpg.api.skill.passive.Passive;
import com.minecraft.moonlake.mmorpg.api.skill.talent.Talent;
import com.minecraft.moonlake.mmorpg.api.skill.ultimate.Ultimate;
import com.minecraft.moonlake.mmorpg.api.sql.MMORPGSql;
import com.minecraft.moonlake.mmorpg.manager.AccountManager;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import com.minecraft.moonlake.mmorpg.api.skill.SkillManager;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.*;

/**
 * Created by MoonLake on 2016/5/14.
 */
public class DataSource implements MMORPGSql {

    private final MMORPG main;
    private final String database;
    private final String host;
    private final String port;
    private final String username;
    private final String password;

    private final String playerDataTable = "mmorpg_player";
    private final String playerSkillDataTable = "mmorpg_player_skill";
    private final String playerPetDataTable = "mmorpg_player_pet_%name";
    private final String playerMountDataTable = "mmorpg_player_mount_%name";
    private final String playerInventoryDataTable = "mmorpg_player_inventory_%name";
    private final String playerRepertoryDataTable = "mmorpg_player_repertory_%name";

    private final String url = "jdbc:mysql://{0}:{1}/{2}?characterEncoding=utf-8";

    public DataSource(MMORPG main) {

        this.main = main;

        database  = ConfigManager.get("MySQL.Database").asString();
        host      = ConfigManager.get("MySQL.Host").asString();
        port      = ConfigManager.get("MySQL.Port").asString();
        username  = ConfigManager.get("MySQL.Username").asString();
        password  = ConfigManager.get("MySQL.Password").asString();

        Connection con = null;
        Statement st = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            main.log("初始化数据库驱动完毕.");

            con = getConnection("mysql");
            st = con.createStatement();
            st.executeUpdate("create database if not exists " + database);
            con = getConnection(database);

            // player data table create
            st = con.createStatement();
            st.executeUpdate("create table if not exists " + playerDataTable + " (" +

                    "id"           +   " integer not null auto_increment,"       +
                    "name"         +   " varchar(20) not null unique,"           +
                    "role"         +   " varchar(255) not null default 'none',"  +
                    "exp"          +   " integer not null default '0',"          +
                    "level"        +   " integer not null default '0',"          +
                    "health"       +   " double not null default '0.0',"         +
                    "maxHealth"    +   " double not null default '0.0',"         +
                    "magic"        +   " integer not null default '0',"          +
                    "maxMagic"     +   " integer not null default '0',"          +
                    "soul"         +   " integer not null default '0',"          +
                    "maxSoul"      +   " integer not null default '0',"          +

                    "primary key (id));"
            );
            // player skill data table create
            st = con.createStatement();
            st.executeUpdate("create table if not exists " + playerSkillDataTable + " (" +

                    "id"           +   " integer not null auto_increment,"       +
                    "name"         +   " varchar(20) not null unique,"           +
                    "point"        +   " integer not null default '0',"          +
                    "passive"      +   " varchar(255) not null default 'none',"  +
                    "skills"       +   " varchar(255) not null default 'none',"  +
                    "ultimate"     +   " varchar(255) not null default 'none',"  +
                    "talents"      +   " varchar(255) not null default 'none',"  +

                    "primary key (id));"
            );
        }
        catch (ClassNotFoundException e) {
            // 初始化驱动异常
            main.log("初始化数据库驱动时异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (SQLException e) {
            // 数据库异常
            main.log("连接错误,数据库信息异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {
            // 其他异常
            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {
            // 关闭
            close(st);
            close(con);
        }
    }

    /**
     * 初始化指定玩家的 MMORPG 数据
     *
     * @param name 玩家名
     * @return 是否成功
     */
    @Override
    public synchronized boolean initData(String name) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            con = getConnection(database);
            pst = con.prepareStatement("select * from " + playerDataTable + " where name=?;");
            pst.setString(1, name);
            rs = pst.executeQuery();

            if(!rs.next()) {
                // not exists player to init
                pst = con.prepareStatement("insert into " + playerDataTable + " (name) values (?);");
                pst.setString(1, name);
                pst.executeUpdate();
            }
            return true;
        }
        catch (SQLException e) {

            main.log("初始化数据名为 '" + name + "' 时发生异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(rs);
            close(pst);
            close(con);
        }
        return false;
    }

    /**
     * 初始化指定玩家的 MMORPG 数据
     *
     * @param name      玩家名
     * @param maxHealth 最大血量
     * @param maxMagic  最大魔法
     * @param maxSoul   最大灵魂
     * @return 是否成功
     */
    @Override
    public boolean initData(String name, double maxHealth, int maxMagic, int maxSoul) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            con = getConnection(database);
            pst = con.prepareStatement("select * from " + playerDataTable + " where name=?;");
            pst.setString(1, name);
            rs = pst.executeQuery();

            if(!rs.next()) {
                // not exists player to init
                pst = con.prepareStatement("insert into " + playerDataTable + " (name,maxHealth,maxMagic,maxSoul) values (?,?,?,?);");
                pst.setString(1, name);
                pst.setDouble(2, maxHealth);
                pst.setInt(3, maxMagic);
                pst.setInt(4, maxSoul);
                pst.executeUpdate();
            }
            return true;
        }
        catch (SQLException e) {

            main.log("初始化数据名为 '" + name + "' 时发生异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(rs);
            close(pst);
            close(con);
        }
        return false;
    }

    /**
     * 获取指定玩家的 MMORPG 数据
     *
     * @param name 玩家名
     * @return 存在则返回数据 否则返回 null
     */
    @Override
    public synchronized MMORPGPlayerData getData(String name) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            con = getConnection(database);
            pst = con.prepareStatement("select * from " + playerDataTable + " where name=?;");
            pst.setString(1, name);
            rs = pst.executeQuery();

            MMORPGPlayerData data = null;

            if(rs.next()) {

                data = new MMORPGPlayerData(name);
                data.setRoleType(RoleType.fromType(rs.getString("role")));
                data.setExp(rs.getInt("exp"));
                data.setLevel(rs.getInt("level"));
                data.setHealth(rs.getDouble("health"));
                data.setMaxHealth(rs.getDouble("maxHealth"));
                data.setMagic(rs.getInt("magic"));
                data.setMaxMagic(rs.getInt("maxMagic"));
                data.setSoul(rs.getInt("soul"));
                data.setMaxSoul(rs.getInt("maxSoul"));
            }
            return data;
        }
        catch (SQLException e) {

            main.log("获取数据名为 '" + name + "' 时发送异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(rs);
            close(pst);
            close(con);
        }
        return null;
    }

    /**
     * 保存指定玩家的 MMORPG 数据
     *
     * @param name 玩家名
     * @param data 数据对象
     * @return 是否成功
     */
    @Override
    public synchronized boolean saveData(String name, MMORPGPlayerData data) {

        Connection con = null;
        PreparedStatement pst = null;

        try {

            con = getConnection(database);
            pst = con.prepareStatement("update " + playerDataTable + " set " +

                    "role=?,"         +
                    "exp=?,"           +
                    "level=?,"        +
                    "health=?,"       +
                    "maxHealth=?,"    +
                    "magic=?,"        +
                    "maxMagic=?,"     +
                    "soul=?,"         +
                    "maxSoul=?"       +
                    " where name=?;"
            );
            pst.setString(1, data.getRoleType() != null ? data.getRoleType().getType() : "none");
            pst.setInt(2, data.getExp());
            pst.setInt(3, data.getLevel());
            pst.setDouble(4, data.getHealth());
            pst.setDouble(5, data.getMaxHealth());
            pst.setInt(6, data.getMagic());
            pst.setInt(7, data.getMaxMagic());
            pst.setInt(8, data.getSoul());
            pst.setInt(9, data.getMaxSoul());
            pst.setString(10, name);
            pst.executeUpdate();
        }
        catch (SQLException e) {

            main.log("保存数据名为 '" + name + "' 时发生异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(pst);
            close(con);
        }
        return false;
    }

    /**
     * 保存所有 MMORPG 玩家数组数据
     *
     * @param datas 数组数据
     * @return 是否成功
     */
    @Override
    public synchronized boolean saveDatas(MMORPGPlayerData[] datas) {

        Connection con = null;
        PreparedStatement pst = null;

        try {

            con = getConnection(database);
            for(MMORPGPlayerData data : datas) {

                pst = con.prepareStatement("update " + playerDataTable + " set " +

                        "role=?,"         +
                        "exp=?,"          +
                        "level=?,"        +
                        "health=?,"       +
                        "maxHealth=?,"    +
                        "magic=?,"        +
                        "maxMagic=?,"     +
                        "soul=?,"         +
                        "maxSoul=?"       +
                        " where name=?;"
                );
                pst.setString(1, data.getRoleType() != null ? data.getRoleType().getType() : "none");
                pst.setInt(2, data.getExp());
                pst.setInt(3, data.getLevel());
                pst.setDouble(4, data.getHealth());
                pst.setDouble(5, data.getMaxHealth());
                pst.setInt(6, data.getMagic());
                pst.setInt(7, data.getMaxMagic());
                pst.setInt(8, data.getSoul());
                pst.setInt(9, data.getMaxSoul());
                pst.setString(10, data.getName());
                pst.executeUpdate();
            }
        }
        catch (SQLException e) {

            main.log("保存数组玩家数据时发生异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(pst);
            close(con);
        }
        return false;
    }

    /**
     * 保存指定 MMORPG 玩家的背包数据
     *
     * @param name 玩家名
     * @param dataMap 序列化数据集合
     * @return 是否成功
     */
    @Override
    public boolean saveInventoryData(String name, Map<String, String> dataMap) {

        Connection con = null;
        Statement st = null;
        PreparedStatement pst = null;

        try {

            String table = StringUtil.stringClone(playerInventoryDataTable).replace("%name", name);

            con = getConnection(database);
            st = con.createStatement();
            st.executeUpdate("drop table if exists " + table);

            // create table
            st = con.createStatement();
            st.executeUpdate("create table if not exists " + table + "(" +

                    "id"           +   " integer not null auto_increment unique,"  +
                    "type"         +   " varchar(255) not null default 'none',"    +
                    "slot"         +   " varchar(255) not null default '0',"       +
                    "value"        +   " text(65535) not null,"                    +

                    "primary key (id));"
            );
            Set<String> keySet = dataMap.keySet();

            for(String type : keySet) {

                String[] typeDatas = type.split(":");
                pst = con.prepareStatement("insert into " + table + " (type,slot,value) values (?,?,?);");
                pst.setString(1, typeDatas[0]);
                pst.setString(2, typeDatas[1]);
                pst.setString(3, dataMap.get(type));
                pst.executeUpdate();
            }
            return true;
        }
        catch (SQLException e) {

            main.log("保存名为 '" + name + "' 玩家的背包数据时异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(pst);
            close(st);
            close(con);
        }
        return false;
    }

    /**
     * 保存所有 MMORPG 玩家背包数组数据
     *
     * @param inventoryDatas 数组数据
     * @return 是否成功
     */
    @Override
    public boolean saveInventoryDatas(MMORPGInventoryData[] inventoryDatas) {

        Connection con = null;
        Statement st = null;
        PreparedStatement pst = null;

        try {

            for(MMORPGInventoryData data : inventoryDatas) {

                String table = StringUtil.stringClone(playerInventoryDataTable).replace("%name", data.getName());

                con = getConnection(database);
                st = con.createStatement();
                st.executeUpdate("drop table if exists " + table);

                // create table
                st = con.createStatement();
                st.executeUpdate("create table if not exists " + table + "(" +

                        "id"           +   " integer not null auto_increment unique,"  +
                        "type"         +   " varchar(255) not null default 'none',"    +
                        "slot"         +   " varchar(255) not null default '0',"       +
                        "value"        +   " text(65535) not null,"                    +

                        "primary key (id));"
                );
                Set<String> keySet = data.getDataMap().keySet();

                for(String type : keySet) {

                    String[] typeDatas = type.split(":");
                    pst = con.prepareStatement("insert into " + table + " (type,slot,value) values (?,?,?);");
                    pst.setString(1, typeDatas[0]);
                    pst.setString(2, typeDatas[1]);
                    pst.setString(3, data.getDataMap().get(type).toString());
                    pst.executeUpdate();
                }
            }
            return true;
        }
        catch (SQLException e) {

            main.log("保存数组玩家的背包数据时异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(pst);
            close(st);
            close(con);
        }
        return false;
    }

    /**
     * 读取指定 MMORPG 玩家的背包数据
     *
     * @param name 玩家名
     * @return 序列化数据集合 异常或没有返回空集合
     */
    @Override
    public Map<String, String> loadInventoryData(String name) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            Map<String, String> dataMap = new HashMap<>();
            String table = StringUtil.stringClone(playerInventoryDataTable).replace("%name", name);

            con = getConnection(database);
            pst = con.prepareStatement("select * from " + table);
            rs = pst.executeQuery();

            while (rs.next()) {
                // have data
                String type = rs.getString("type") + ":" + rs.getString("slot");
                String value = rs.getString("value");

                dataMap.put(type, value);
            }
            return dataMap;
        }
        catch (SQLException e) {

            main.log("读取名为 '" + name + "' 玩家的背包数据时异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(rs);
            close(pst);
            close(con);
        }
        return new HashMap<>();
    }

    /**
     * 保存指定 MMORPG 玩家的个人仓库数据
     *
     * @param name    玩家名
     * @param dataMap 序列化数据集合
     * @return 是否成功
     */
    @Override
    public boolean saveRepertoryData(String name, Map<Integer, String> dataMap) {

        Connection con = null;
        Statement st = null;
        PreparedStatement pst = null;

        try {

            String table = StringUtil.stringClone(playerRepertoryDataTable).replace("%name", name);

            con = getConnection(database);
            st = con.createStatement();
            st.executeUpdate("drop table if exists " + table);

            // create table
            st = con.createStatement();
            st.executeUpdate("create table if not exists " + table + "(" +

                    "id"           +   " integer not null auto_increment unique,"  +
                    "slot"         +   " integer not null default '0',"            +
                    "value"        +   " text(65535) not null,"                    +

                    "primary key (id));"
            );
            Set<Integer> keySet = dataMap.keySet();

            for(Integer integer : keySet) {

                pst = con.prepareStatement("insert into " + table + " (slot,value) values (?,?);");
                pst.setInt(1, integer);
                pst.setString(2, dataMap.get(integer));
                pst.executeUpdate();
            }
            return true;
        }
        catch (SQLException e) {

            main.log("保存名为 '" + name + "' 玩家的个人仓库数据时异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(pst);
            close(st);
            close(con);
        }
        return false;
    }

    /**
     * 保存所有 MMORPG 玩家个人仓库数组数据
     *
     * @param repertoryDatas 数组数据
     * @return 是否成功
     */
    @Override
    public boolean saveRepertoryDatas(MMORPGInventoryData[] repertoryDatas) {

        Connection con = null;
        Statement st = null;
        PreparedStatement pst = null;

        try {

            for(MMORPGInventoryData repertoryData : repertoryDatas) {

                String table = StringUtil.stringClone(playerRepertoryDataTable).replace("%name", repertoryData.getName());

                con = getConnection(database);
                st = con.createStatement();
                st.executeUpdate("drop table if exists " + table);

                // create table
                st = con.createStatement();
                st.executeUpdate("create table if not exists " + table + "(" +

                        "id"           +   " integer not null auto_increment unique,"  +
                        "slot"         +   " integer not null default '0',"            +
                        "value"        +   " text(65535) not null,"                    +

                        "primary key (id));"
                );
                Set<Integer> keySet = repertoryData.getDataMap().keySet();

                for(Integer integer : keySet) {

                    pst = con.prepareStatement("insert into " + table + " (slot,value) values (?,?);");
                    pst.setInt(1, integer);
                    pst.setString(2, repertoryData.getDataMap().get(integer).toString());
                    pst.executeUpdate();
                }
            }
            return true;
        }
        catch (SQLException e) {

            main.log("保存数组玩家的个人仓库数据时异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(pst);
            close(st);
            close(con);
        }
        return false;
    }

    /**
     * 读取指定 MMORPG 玩家的个人仓库数据
     *
     * @param name 玩家名
     * @return 序列化数据集合 异常或没有则返回空集合
     */
    @Override
    public Map<Integer, String> loadRepertoryData(String name) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            Map<Integer, String> dataMap = new HashMap<>();
            String table = StringUtil.stringClone(playerRepertoryDataTable).replace("%name", name);

            con = getConnection(database);
            pst = con.prepareStatement("select * from " + table);
            rs = pst.executeQuery();

            while (rs.next()) {
                // have data
                dataMap.put(rs.getInt("slot"), rs.getString("value"));
            }
            return dataMap;
        }
        catch (SQLException e) {

            main.log("读取名为 '" + name + "' 玩家的个人仓库数据时异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(rs);
            close(pst);
            close(con);
        }
        return new HashMap<>();
    }

    /**
     * 初始化指定玩家的 MMORPG 坐骑数据
     *
     * @param name          玩家名
     * @param defaultMounts 默认坐骑
     * @return 是否成功
     */
    @Override
    public boolean initPlayerMountData(String name, Mount... defaultMounts) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            String table = StringUtil.stringClone(playerMountDataTable).replace("%name", name);

            con = getConnection(database);
            rs = con.getMetaData().getTables(null, null, table, null);

            if(!rs.next()) {
                // not exists table then init
                pst = con.prepareStatement("create table " + table + " (" +

                        "id"      +   " integer not null auto_increment unique," +
                        "name"    +   " varchar(255) not null default 'none',"   +
                        "type"    +   " varchar(255) not null default 'none',"   +

                        "primary key (id));"
                );
                pst.executeUpdate();

                if(defaultMounts != null && defaultMounts.length > 0) {
                    // insert mount data
                    for(Mount mount : defaultMounts) {

                        pst = con.prepareStatement("insert into " + table + " (name,type) values (?,?);");
                        pst.setString(1, mount.getDisplayName() == null ? "none" : mount.getDisplayName());
                        pst.setString(2, mount.getType().getType());
                        pst.executeUpdate();
                    }
                }
            }
            return true;
        }
        catch (SQLException e) {

            main.log("初始化名为 '" + name + "' 玩家的坐骑数据时异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(rs);
            close(pst);
            close(con);
        }
        return false;
    }

    /**
     * 读取指定玩家的 MMORPG 坐骑数据
     *
     * @param name 玩家名
     * @return 坐骑集合 异常或没有则返回空集合
     */
    @Override
    public List<Mount> loadPlayerMountData(String name) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            List<Mount> mounts = new ArrayList<>();
            String table = StringUtil.stringClone(playerMountDataTable).replace("%name", name);

            con = getConnection(database);
            pst = con.prepareStatement("select * from " + table);
            rs = pst.executeQuery();

            while (rs.next()) {

                MountType mountType = MountType.fromType(rs.getString("type"));
                if(mountType != null) {

                    Mount mount = mountType.newInstance(rs.getString("name"));
                    if(mount != null) {

                        mounts.add(mount);
                    }
                }
            }
            return mounts;
        }
        catch (SQLException e) {

            main.log("读取名为 '" + name + "' 玩家的坐骑数据时异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(rs);
            close(pst);
            close(con);
        }
        return new ArrayList<>();
    }

    /**
     * 保存指定玩家的 MMORPG 坐骑数据
     *
     * @param name      玩家名
     * @param mountData 坐骑数据
     * @return 是否成功
     */
    @Override
    public boolean savePlayerMountData(String name, MMORPGPlayerMountData mountData) {

        Connection con = null;
        Statement st = null;
        PreparedStatement pst = null;

        try {

            String table = StringUtil.stringClone(playerMountDataTable).replace("%name", name);

            con = getConnection(database);
            st = con.createStatement();
            st.executeUpdate("drop table if exists " + table);

            st = con.createStatement();
            st.executeUpdate("create table " + table + " (" +

                    "id"      +   " integer not null auto_increment unique," +
                    "name"    +   " varchar(255) not null default 'none',"   +
                    "type"    +   " varchar(255) not null default 'none',"   +

                    "primary key (id));"
            );
            List<Mount> mounts = mountData.getMounts();

            if(mounts != null && mounts.size() > 0) {

                for(Mount mount : mounts) {

                    if(mount != null) {

                        pst = con.prepareStatement("insert into " + table + " (name,type) values (?,?);");
                        pst.setString(1, mount.getDisplayName() == null ? mount.getType().getName() : mount.getDisplayName());
                        pst.setString(2, mount.getType().getType());
                        pst.executeUpdate();
                    }
                }
            }
            return true;
        }
        catch (SQLException e) {

            main.log("保存名为 '" + name + "' 玩家的坐骑数据时异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(pst);
            close(st);
            close(con);
        }
        return false;
    }

    /**
     * 保存所有 MMORPG 玩家坐骑数组数据
     *
     * @param mountDatas 数组数据
     * @return 是否成功
     */
    @Override
    public boolean savePlayerMountDatas(MMORPGPlayerMountData[] mountDatas) {

        Connection con = null;
        Statement st = null;
        PreparedStatement pst = null;

        try {

            for(MMORPGPlayerMountData mountData : mountDatas) {

                String table = StringUtil.stringClone(playerMountDataTable).replace("%name", mountData.getName());

                con = getConnection(database);
                st = con.createStatement();
                st.executeUpdate("drop table if exists " + table);

                st = con.createStatement();
                st.executeUpdate("create table " + table + " (" +

                        "id"      +   " integer not null auto_increment unique," +
                        "name"    +   " varchar(255) not null default 'none',"   +
                        "type"    +   " varchar(255) not null default 'none',"   +

                        "primary key (id));"
                );
                List<Mount> mounts = mountData.getMounts();

                if(mounts != null && mounts.size() > 0) {

                    for(Mount mount : mounts) {

                        if(mount != null) {

                            pst = con.prepareStatement("insert into " + table + " (name,type) values (?,?);");
                            pst.setString(1, mount.getDisplayName() == null ? mount.getType().getName() : mount.getDisplayName());
                            pst.setString(2, mount.getType().getType());
                            pst.executeUpdate();
                        }
                    }
                }
            }
            return true;
        }
        catch (SQLException e) {

            main.log("保存数组玩家的坐骑数据时异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(pst);
            close(st);
            close(con);
        }
        return false;
    }

    /**
     * 初始化玩家的技能以及天赋数据
     *
     * @param name 玩家名
     * @return 是否成功
     */
    @Override
    public synchronized boolean initPlayerSkillData(String name) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            con = getConnection(database);
            pst = con.prepareStatement("select * from " + playerSkillDataTable + " where name=?;");
            pst.setString(1, name);
            rs = pst.executeQuery();

            if(!rs.next()) {
                // not exists player to init
                pst = con.prepareStatement("insert into " + playerSkillDataTable + " (name) values (?);");
                pst.setString(1, name);
                pst.executeUpdate();
            }
            return true;
        }
        catch (SQLException e) {

            main.log("初始化数据名为 '" + name + "' 时发生异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(rs);
            close(pst);
            close(con);
        }
        return false;
    }

    /**
     * 读取玩家的技能以及天赋数据
     *
     * @param name 玩家名
     * @return 玩家技能数据对象 没有则返回 null
     */
    @Override
    public synchronized MMORPGPlayerSkillData loadPlayerSkillData(String name) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            con = getConnection(database);
            pst = con.prepareStatement("select * from " + playerSkillDataTable + " where name=?;");
            pst.setString(1, name);
            rs = pst.executeQuery();

            MMORPGPlayerSkillData data = null;

            if(rs.next()) {

                data = new MMORPGPlayerSkillData(name);
                data.setSkillPoint(rs.getInt("point"));

                Passive passive = SkillManager.fromPassive(rs.getString("passive"));
                Skill[] skills = SkillManager.fromSkill(rs.getString("skills"));
                Ultimate ultimate = SkillManager.fromUltimate(rs.getString("ultimate"));
                Talent[] talents = SkillManager.fromTalent(rs.getString("talents"));

                if(passive != null) {

                    data.setPassive(passive);
                }
                if(skills != null) {

                    data.setSkills(StringUtil.fromArray(skills));
                }
                if(ultimate != null) {

                    data.setUltimate(ultimate);
                }
                if(talents != null) {

                    data.setTalents(StringUtil.fromArray(talents));
                }
            }
            return data;
        }
        catch (SQLException e) {

            main.log("获取技能数据名为 '" + name + "' 时发生异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(rs);
            close(pst);
            close(con);
        }
        return null;
    }

    /**
     * 保存玩家的技能以及天赋数据
     *
     * @param name      玩家名
     * @param skillData 玩家技能数据对象
     * @return 是否成功
     */
    @Override
    public synchronized boolean savePlayerSkillData(String name, MMORPGPlayerSkillData skillData) {

        Connection con = null;
        PreparedStatement pst = null;

        try {

            con = getConnection(database);
            pst = con.prepareStatement("update " + playerSkillDataTable + " set " +

                    "point=?,"          +
                    "passive=?,"        +
                    "skills=?,"         +
                    "ultimate=?,"       +
                    "talents=?"         +
                    " where name=?;"
            );
            pst.setInt(1, skillData.getSkillPoint());
            pst.setString(2, SkillManager.toPassive(skillData.getPassive()));
            pst.setString(3, SkillManager.toSkill(skillData.getSkills()));
            pst.setString(4, SkillManager.toUltimate(skillData.getUltimate()));
            pst.setString(5, SkillManager.toTalent(skillData.getTalents()));
            pst.setString(6, name);
            pst.executeUpdate();

            return true;
        }
        catch (SQLException e) {

            main.log("保存技能数据名为 '" + name + "' 时发生异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(pst);
            close(con);
        }
        return false;
    }

    /**
     * 保存所有玩家的 MMORPG 技能数据到云
     *
     * @param skillDatas 技能数组集合
     * @return 是否成功
     */
    @Override
    public synchronized boolean savePlayerSkillDatas(MMORPGPlayerSkillData[] skillDatas) {

        Connection con = null;
        PreparedStatement pst = null;

        try {

            con = getConnection(database);

            for(MMORPGPlayerSkillData skillData : skillDatas) {

                pst = con.prepareStatement("update " + playerSkillDataTable + " set " +

                        "point=?,"          +
                        "passive=?,"        +
                        "skills=?,"         +
                        "ultimate=?,"       +
                        "talents=?"         +
                        " where name=?;"
                );
                pst.setInt(1, skillData.getSkillPoint());
                pst.setString(2, SkillManager.toPassive(skillData.getPassive()));
                pst.setString(3, SkillManager.toSkill(skillData.getSkills()));
                pst.setString(4, SkillManager.toUltimate(skillData.getUltimate()));
                pst.setString(5, SkillManager.toTalent(skillData.getTalents()));
                pst.setString(6, skillData.getName());
                pst.executeUpdate();
            }
            return true;
        }
        catch (SQLException e) {

            main.log("保存数组技能数据时发生异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(pst);
            close(con);
        }
        return false;
    }

    /**
     * 获取玩家的技能点数
     *
     * @param name 玩家名
     * @return 技能点数
     */
    @Override
    public synchronized int getPlayerSkillPoint(String name) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            con = getConnection(database);
            pst = con.prepareStatement("select * from " + playerSkillDataTable + " where name=?;");
            pst.setString(1, name);
            rs = pst.executeQuery();

            int point = 0;

            if(rs.next()) {

                point = rs.getInt("point");
            }
            return point;
        }
        catch (SQLException e) {

            main.log("获取技能点数据名为 '" + name + "' 时发生异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(rs);
            close(pst);
            close(con);
        }
        return 0;
    }

    /**
     * 设置玩家的技能点数
     *
     * @param name  玩家名
     * @param point 新的技能点数
     * @return 是否成功
     */
    @Override
    public synchronized boolean setPlayerSkillPoint(String name, int point) {

        Connection con = null;
        PreparedStatement pst = null;

        try {

            con = getConnection(database);
            pst = con.prepareStatement("update " + playerSkillDataTable + " set point=? where name=?;");
            pst.setInt(1, point);
            pst.setString(2, name);
            pst.executeUpdate();

            // skill point change event
            PlayerSkillPointChangeEvent pspce = new PlayerSkillPointChangeEvent(AccountManager.getPlayer(name), point);
            Bukkit.getServer().getPluginManager().callEvent(pspce);

            return true;
        }
        catch (SQLException e) {

            main.log("获取技能点数据名为 '" + name + "' 时发生异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(pst);
            close(con);
        }
        return false;
    }

    /**
     * 给予指定玩家数量的技能点
     *
     * @param name 玩家名
     * @param give 给予的数量
     * @return 是否成功
     */
    @Override
    public synchronized boolean givePlayerSkillPoint(String name, int give) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            con = getConnection(database);
            pst = con.prepareStatement("select * from " + playerSkillDataTable + " where name=?;");
            pst.setString(1, name);
            rs = pst.executeQuery();

            int sql_point = 0;

            if(rs.next()) {

                sql_point = rs.getInt("point");
            }
            pst = con.prepareStatement("update " + playerSkillDataTable + " set point=point+? where name=?;");
            pst.setInt(1, give);
            pst.setString(2, name);
            pst.executeUpdate();

            // skill point change event
            PlayerSkillPointChangeEvent pspce = new PlayerSkillPointChangeEvent(AccountManager.getPlayer(name), sql_point + give);
            Bukkit.getServer().getPluginManager().callEvent(pspce);

            return true;
        }
        catch (SQLException e) {

            main.log("给予技能点数据名为 '" + name + "' 时发生异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(rs);
            close(pst);
            close(con);
        }
        return false;
    }

    /**
     * 减少指定玩家数量的技能点
     *
     * @param name 玩家名
     * @param take 减少的数量
     * @return 是否成功
     */
    @Override
    public synchronized boolean takePlayerSkillPoint(String name, int take) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            con = getConnection(database);
            pst = con.prepareStatement("select * from " + playerSkillDataTable + " where name=?;");
            pst.setString(1, name);
            rs = pst.executeQuery();

            int sql_point = 0;

            if(rs.next()) {

                sql_point = rs.getInt("point");
            }
            pst = con.prepareStatement("update " + playerSkillDataTable + " set point=point-? where name=?;");
            pst.setInt(1, take);
            pst.setString(2, name);
            pst.executeUpdate();

            // skill point change event
            PlayerSkillPointChangeEvent pspce = new PlayerSkillPointChangeEvent(AccountManager.getPlayer(name), sql_point - take);
            Bukkit.getServer().getPluginManager().callEvent(pspce);

            return true;
        }
        catch (SQLException e) {

            main.log("减少技能点数据名为 '" + name + "' 时发生异常: " + e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        catch (Exception e) {

            main.log(e.getMessage());

            if(main.isDebug()) {

                e.printStackTrace();
            }
        }
        finally {

            close(pst);
            close(con);
        }
        return false;
    }

    public final synchronized Connection getConnection(String database) throws Exception {

        Connection con = null;
        String target = StringUtil.format(url, host, port, database);

        try {

            con = DriverManager.getConnection(target, this.username, this.password);
        }
        catch (SQLException e) {
            // 数据库异常
            throw new Exception("连接错误,数据库信息异常: " + e.getMessage());
        }
        return con;
    }

    private void close(Connection con) {
        // 关闭声明
        if(con != null) {
            // 不为空
            try {

                con.close();
            }
            catch (SQLException e) {

                main.log("关闭数据库的 Connection 异常: " + e.getMessage());

                if(main.isDebug()) {

                    e.printStackTrace();
                }
            }
        }
    }

    private void close(Statement st) {
        // 关闭声明
        if(st != null) {
            // 不为空
            try {

                st.close();
            }
            catch (SQLException e) {

                main.log("关闭数据库的 Statement 异常: " + e.getMessage());

                if(main.isDebug()) {

                    e.printStackTrace();
                }
            }
        }
    }

    private void close(ResultSet rs) {
        // 关闭声明
        if(rs != null) {
            // 不为空
            try {

                rs.close();
            }
            catch (SQLException e) {

                main.log("关闭数据库的 ResultSet 异常: " + e.getMessage());

                if(main.isDebug()) {

                    e.printStackTrace();
                }
            }
        }
    }
}
