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
 
  
package com.minecraft.moonlake.mmorpg.util.player;

import com.minecraft.moonlake.api.player.AbstractPlayer;
import com.minecraft.moonlake.mmorpg.api.event.player.PlayerObtainXpEvent;
import com.minecraft.moonlake.mmorpg.api.event.player.PlayerUpgradeEvent;
import com.minecraft.moonlake.mmorpg.api.gui.GUIManager;
import com.minecraft.moonlake.mmorpg.api.gui.player.PlayerGUI;
import com.minecraft.moonlake.mmorpg.api.mount.type.MountDonkey;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.player.repertory.Repertory;
import com.minecraft.moonlake.mmorpg.api.role.Role;
import com.minecraft.moonlake.mmorpg.api.role.RoleType;
import com.minecraft.moonlake.mmorpg.api.skill.Skill;
import com.minecraft.moonlake.mmorpg.api.skill.SkillManager;
import com.minecraft.moonlake.mmorpg.api.skill.target.SkillTarget;
import com.minecraft.moonlake.mmorpg.api.skill.target.SkillTargetBlock;
import com.minecraft.moonlake.mmorpg.api.skill.target.SkillTargets;
import com.minecraft.moonlake.mmorpg.api.system.*;
import com.minecraft.moonlake.mmorpg.effect.EffectManager;
import com.minecraft.moonlake.mmorpg.effect.EffectType;
import com.minecraft.moonlake.mmorpg.handle.EnumAddItemStackCallBack;
import com.minecraft.moonlake.mmorpg.manager.DataManager;
import com.minecraft.moonlake.mmorpg.manager.PlayerManager;
import com.minecraft.moonlake.mmorpg.manager.l18n;
import com.minecraft.moonlake.mmorpg.resources.PlayerLevel;
import com.minecraft.moonlake.mmorpg.task.player.PlayerTaskManager;
import com.minecraft.moonlake.mmorpg.util.player.repertory.RepertoryUtil;
import com.minecraft.moonlake.mmorpg.util.system.*;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by MoonLake on 2016/5/16.
 */
public final class PlayerUtil extends AbstractPlayer implements MMORPGPlayer {

    private Role role;                          // 玩家角色
    private RoleType roleType;                  // 玩家角色类型
    private int exp;                          	// 玩家经验值
    private int level;                          // 玩家等级
    private double health;                      // 玩家血量
    private double maxHealth;                   // 玩家血量上限
    private int magic;                          // 玩家魔法
    private int maxMagic;                       // 玩家魔法上限
    private int soul;                           // 玩家灵魂
    private int maxSoul;                        // 玩家灵魂上限

    private Repertory repertory;                // 玩家个人仓库
    private HealthSystem healthSystem;          // 玩家真实血量系统
    private MagicSystem magicSystem;            // 玩家真实魔法系统
    private SkillSystem skillSystem;            // 玩家技能系统
    private MountSystem mountSystem;            // 玩家坐骑系统
    private PetSystem petSystem;                // 玩家宠物系统

    public PlayerUtil(String name) {

        super(name);

        this.role = null;
        this.roleType = null;
        this.repertory = new RepertoryUtil(this);
        this.healthSystem = new HealSystemUtil(this);
        this.magicSystem = new MagicSystemUtil(this);
        this.skillSystem = new SkillSystemUtil(this);
        this.mountSystem = new MountSystemUtil(this);
        this.petSystem = new PetSystemUtil(this);
    }

    /**
     * 获取此玩家的职业类型
     *
     * @return 职业类型
     */
    @Override
    public RoleType getRoleType() {

        return roleType;
    }

    /**
     * 获取此玩家的职业
     *
     * @return 职业
     */
    @Override
    public Role getRole() {

        return role;
    }

    /**
     * 设置此玩家的职业
     *
     * @param role 职业
     */
    @Override
    public void setRole(Role role) {

        this.role = role;
        this.roleType = role.getType();
    }

    /**
     * 给此玩家发送语言文件指定 键 的值
     *
     * @param key 键
     */
    @Override
    public void l18n(String key) {

        send(l18n.$(key));
    }

    /**
     * 给此玩家发送语言文件指定 键 的值
     *
     * @param key  键
     * @param args 参数
     */
    @Override
    public void l18n(String key, Object... args) {

        send(l18n.$(key, args));
    }

    /**
     * 获取此玩家的当前 RPG 血量
     *
     * @return 血量
     */
    @Override
    public double getRPGHealth() {

        return health;
    }

    /**
     * 获取此玩家的 RPG 最大生命
     *
     * @return 最大血量
     */
    @Override
    public double getRPGMaxHealth() {

        return maxHealth;
    }

    /**
     * 给予此玩家指定数量的 RPG 血量
     *
     * @param amount 数量
     */
    @Override
    public void giveRPGHealth(double amount) {

        this.health += amount;

        if(this.health > this.maxHealth) {

            this.health = this.maxHealth;
        }
        updateHeart();
        getRealHealth().update();
    }

    /**
     * 减少此玩家指定数量的 RPG 血量
     *
     * @param amount 数量
     */
    @Override
    public void takeRPGHealth(double amount) {

        this.health -= amount;

        if(this.health < 0) {

            this.health = 0;
            die();
        }
        updateHeart();
        getRealHealth().update();
    }

    /**
     * 获取此玩家的当前经验
     *
     * @return 经验
     */
    @Override
    public int getExp() {

        return exp;
    }

    /**
     * 获取此玩家的当前 RPG 等级
     *
     * @return 等级
     */
    @Override
    public int getRPGLevel() {

        return level;
    }

    /**
     * 获取此玩家的当前魔法
     *
     * @return 魔法
     */
    @Override
    public int getMagic() {

        return magic;
    }

    /**
     * 获取此玩家的最大魔法
     *
     * @return 最大魔法
     */
    @Override
    public int getMaxMagic() {

        return maxMagic;
    }

    /**
     * 给予此玩家指定数量的魔法
     *
     * @param amount 数量
     */
    @Override
    public void giveMagic(int amount) {

        this.magic += amount;

        if(this.magic > this.maxMagic) {

            this.magic = this.maxMagic;
        }
        getRealMagic().update();
    }

    /**
     * 减少此玩家指定数量的魔法
     *
     * @param amount 数量
     */
    @Override
    public void takeMagic(int amount) {

        this.magic -= amount;

        if(this.magic < 0) {

            this.magic = 0;
        }
        getRealMagic().update();
    }

    /**
     * 获取此玩家的当前灵魂
     *
     * @return 灵魂
     */
    @Override
    public int getSoul() {

        return soul;
    }

    /**
     * 获取此玩家的最大灵魂
     *
     * @return 最大灵魂
     */
    @Override
    public int getMaxSoul() {

        return maxSoul;
    }

    /**
     * 设置此玩家的当前 RPG 血量
     *
     * @param health 血量
     */
    @Override
    public void setRPGHealth(double health) {

        this.health = health;

        getRealHealth().update();
    }

    /**
     * 设置此玩家的最大 RPG 血量
     *
     * @param maxHealth 最大血量
     */
    @Override
    public void setRPGMaxHealth(double maxHealth) {

        this.maxHealth = maxHealth;

        getRealHealth().update();
    }

    /**
     * 设置此玩家的当前魔法
     *
     * @param magic 魔法
     */
    @Override
    public void setMagic(int magic) {

        this.magic = magic;

        getRealMagic().update();
    }

    /**
     * 设置此玩家的最大魔法
     *
     * @param maxMagic 最大魔法
     */
    @Override
    public void setMaxMagic(int maxMagic) {

        this.maxMagic = maxMagic;

        getRealMagic().update();
    }

    /**
     * 设置此玩家的当前灵魂
     *
     * @param soul 灵魂
     */
    @Override
    public void setSoul(int soul) {

        this.soul = soul;
    }

    /**
     * 设置此玩家的最大灵魂
     *
     * @param maxSoul 最大灵魂
     */
    @Override
    public void setMaxSoul(int maxSoul) {

        this.maxSoul = maxSoul;
    }

    /**
     * 拿走此玩家指定数量灵魂
     *
     * @param amount 数量
     */
    @Override
    public void takeSoul(int amount) {

        this.soul -= amount;

        if(this.soul < 0) {

            this.soul = 0;
        }
    }

    /**
     * 给予此玩家指定数量灵魂
     *
     * @param amount 数量
     */
    @Override
    public void giveSoul(int amount) {

        this.soul += amount;

        if(this.soul > this.maxSoul) {

            this.soul = this.maxSoul;
        }
    }
    
    /**
     * 设置此玩家的经验值
     * 
     * @param exp 经验值
     */
    public void setExp(int exp) {
    	
    	this.exp = exp;
    	
        if(this.exp >= PlayerLevel.getLevelNeedExp(level + 1)) {
        	
        	PlayerUpgradeEvent pue = new PlayerUpgradeEvent(this);
            Bukkit.getServer().getPluginManager().callEvent(pue);

            setLevel(getLevel() + 1);
        }
    	getBukkitPlayer().setExp((float)PlayerLevel.getPlayerExpProgress(this));
    }

    /**
     * 给予此玩家指定经验
     *
     * @param exp 经验
     */
    @Override
    public void giveExp(int exp) {

        PlayerObtainXpEvent poxe = new PlayerObtainXpEvent(this, exp);
        Bukkit.getServer().getPluginManager().callEvent(poxe);

        if(!poxe.isCancelled()) {

            setExp(this.exp + poxe.getObtainXp());
        }
    }

    /**
     * 设置此玩家的当前 RPG 等级
     *
     * @param level 等级
     */
    @Override
    public void setRPGLevel(int level) {

        this.level = level;

        setLevel(level);
    }

    /**
     * 将此玩家强制受到伤害
     *
     * @param damage 伤害
     */
    @Override
    public void damage(int damage) {

        health -= damage;

        if(health <= 0.0d) {

            die();

            return;
        }
        updateHeart();
        getRealHealth().update();

        // 玩家受伤溅血效果
        getBukkitPlayer().damage(0);
        getWorld().playEffect(getLocation(), Effect.STEP_SOUND, 152);
    }

    /**
     * 将此玩家设置为死亡
     */
    @Override
    public void die() {

        health = 0.0d;
        magic = 0;

        getRealHealth().update();
        getRealMagic().update();

        if(!isDead()) {

            setHealth(0d);
        }
    }

    /**
     * 获取此玩家是否死亡
     *
     * @return true 则死亡 else 没有
     */
    @Override
    public boolean isDead() {

        return getBukkitPlayer().isDead();
    }

    @Override
    public void respawn() {

        health = maxHealth;
        magic = maxMagic;

        updateHeart();
        getRealHealth().update();
        getRealMagic().update();
    }

    /**
     * 更新此玩家的心血量同步到真实血量
     */
    @Override
    public void updateHeart() {

        double real = health / maxHealth;
        double heart = real * getMaxHealth();

        if(heart <= 0.0d) {

            die();

            return;
        }
        setHealth(heart);
    }

    /**
     * 初始化玩家的 MMORPG 数据
     */
    @Deprecated
    @Override
    public void initData() {

        DataManager.initPlayerData(getName());
        DataManager.initPlayerSkillData(getName());

        // init mount data
        DataManager.initPlayerMountData(getName(), new MountDonkey());
    }

    /**
     * 保存玩家的 MMORPG 数据到数据商店
     */
    @Deprecated
    @Override
    public void saveData() {

        DataManager.savePlayerData(getName());
        DataManager.savePlayerSkillData(getName());
        DataManager.savePlayerInventoryData(getName());

        // save repertory data
        getRepertory().saveData();
        // save mount data
        getMount().saveData();
    }

    /**
     * 加载玩家的 MMORPG 数据到服务云端
     */
    @Deprecated
    @Override
    public void loadData() {

        DataManager.loadPlayerData(getName(), this);
        DataManager.loadPlayerSkillData(getName(), this);
        PlayerManager.readSerializeInventory(getInventory(), DataManager.loadPlayerInventoryData(getName()));

        // load repertory data
        getRepertory().loadData();
        // load mount data
        getMount().loadData();
    }

    /**
     * 获取此玩家的真实血量系统对象
     *
     * @return 真实血量
     */
    @Override
    public HealthSystem getRealHealth() {

        return healthSystem;
    }

    /**
     * 获取此玩家的真实魔法系统对象
     *
     * @return 真实魔法
     */
    @Override
    public MagicSystem getRealMagic() {

        return magicSystem;
    }

    /**
     * 获取此玩家的技能系统对象
     *
     * @return 技能系统
     */
    @Override
    public SkillSystem getSkill() {

        return skillSystem;
    }

    /**
     * 获取此玩家的坐骑系统对象
     *
     * @return 坐骑系统
     */
    @Override
    public MountSystem getMount() {

        return mountSystem;
    }

    /**
     * 获取此玩家的宠物系统对象
     *
     * @return 宠物系统
     */
    @Override
    public PetSystem getPet() {

        return petSystem;
    }

    /**
     * 获取此玩家的个人仓库对象
     *
     * @return 个人仓库对象
     */
    @Override
    public Repertory getRepertory() {

        return repertory;
    }

    /**
     * 获取此玩家的 GUI 对象
     *
     * @return GUI 对象
     */
    @Override
    public PlayerGUI getPlayerGUI() {

        return GUIManager.getPlayerGUI(this);
    }

    /**
     * 将此玩家添加效果
     *
     * @param type 效果类型
     * @param time 效果时间
     */
    @Override
    public void addEffect(EffectType type, int time) {

        EffectManager.addEffect(this, type, time);
    }

    /**
     * 获取此玩家是否拥有效果
     *
     * @param type 效果类型
     * @return true 拥有此效果类型 else 没有
     */
    @Override
    public boolean hasEffect(EffectType type) {

        return EffectManager.hasEffect(this, type);
    }

    /**
     * 清除此玩家指定效果
     *
     * @param type 效果类型
     */
    @Override
    public void removeEffect(EffectType type) {

        EffectManager.removeEffect(this, type);
    }

    /**
     * 清除此玩家的所有效果
     */
    @Override
    public void removeEffect() {

        EffectManager.removeEffect(this);
    }

    /**
     * 给玩家背包给予指定物品栈
     *
     * @param callBack 回调类型
     * @param items 物品栈
     */
    @Override
    public void addItemStack(EnumAddItemStackCallBack callBack, ItemStack... items) {

        Map<Integer, ItemStack> map = addItemStack(items);

        if(map != null && !map.isEmpty()) {

            if(callBack == EnumAddItemStackCallBack.DROP_LOCATION) {

                Iterator<ItemStack> iterator = map.values().iterator();

                while (iterator.hasNext()) {

                    getWorld().dropItemNaturally(getLocation(), iterator.next());
                }
            }
        }
    }

    /**
     * 获取此玩家是否拥有效果任务
     *
     * @return true 则有效果任务 else 没有
     */
    @Override
    public boolean hasParticleTask() {

        return PlayerTaskManager.hasTask(this);
    }

    /**
     * 获取此玩家的附近指定半径坐标的实体集合
     *
     * @param x X 半径
     * @param y Y 半径
     * @param z Z 半径
     * @return
     */
    @Override
    public List<Entity> getNearbyEntities(double x, double y, double z) {

        return getBukkitPlayer().getNearbyEntities(x, y, z);
    }

    /**
     * 将此玩家强制释放技能
     *
     * @param name 技能名
     */
    @Override
    public void cast(String name) {

        Skill skill = Skill.fromName.a(name);

        if(skill != null) {

            cast(skill);
        }
    }

    /**
     * 将此玩家强制释放技能
     *
     * @param skill 技能对象
     */
    @Override
    public void cast(Skill skill) {

        if(skill instanceof SkillTarget) {
            // one target skill
            SkillManager.cast(this, (SkillTarget)skill);
        }
        else if(skill instanceof SkillTargets) {
            // many target skill
            SkillManager.cast(this, (SkillTargets)skill);
        }
        else if(skill instanceof SkillTargetBlock) {
            // block target skill
            SkillManager.cast(this, (SkillTargetBlock)skill);
        }
        else {
            // normal skill
            skill.cast(this);
        }
    }
}
