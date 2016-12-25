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
 
  
package com.minecraft.moonlake.mmorpg.util.skill.combo;

import com.minecraft.moonlake.mmorpg.api.event.player.PlayerComboFinishEvent;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.skill.Skill;
import com.minecraft.moonlake.mmorpg.api.skill.combo.SkillCombo;
import com.minecraft.moonlake.mmorpg.api.skill.combo.SkillComboType;
import com.minecraft.moonlake.mmorpg.manager.ConfigManager;
import com.minecraft.moonlake.mmorpg.manager.SkillComboManager;
import com.minecraft.moonlake.mmorpg.manager.l18n;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/5/16.
 */
public final class SkillComboUtil implements SkillCombo {

    private int comboIndex;
    private long comboTime;
    private final MMORPGPlayer mmorpgPlayer;
    private final SkillComboType[] combos;
    private final Map<Integer, Skill> skillMap;
    private final long interval;

    public SkillComboUtil(MMORPGPlayer mmorpgPlayer, int comboAmount) {

        this.comboIndex = 0;
        this.comboTime = 0L;
        this.mmorpgPlayer = mmorpgPlayer;
        this.skillMap = new HashMap<>();
        this.combos = new SkillComboType[comboAmount + 1];
        this.interval = ConfigManager.get("SkillCombo.ComboTime").asLong();
    }

    /**
     * 应用下次组合类型
     *
     * @param comboType 组合类型
     */
    public void applyClick(SkillComboType comboType) {

        checkExpired();

        combos[comboIndex++] = comboType;
        comboTime = System.currentTimeMillis();

        if(mmorpgPlayer.getMount().getMain() != null && mmorpgPlayer.getMount().getMain().isLiving()) {
            // if player mount is living then eject
            mmorpgPlayer.getMount().getMain().onEject();
        }
        mmorpgPlayer.sendMainChatPacket(getCurrentComboString());
        mmorpgPlayer.playSound(Sound.UI_BUTTON_CLICK, 10f, 1f);

        int id = SkillComboManager.convertCombo(combos, comboIndex);
        if(comboIndex == combos.length && skillMap.containsKey(id)) {

            Skill skill = skillMap.get(id);

            if(skill != null && skill.getLevel() > 0) {

                PlayerComboFinishEvent pcfe = new PlayerComboFinishEvent(mmorpgPlayer, id, skill);
                Bukkit.getServer().getPluginManager().callEvent(pcfe);

                if(skillMap.containsKey(id) && !pcfe.isCancelled()) {

                    if(mmorpgPlayer.getMagic() < skill.getMagic()) {

                        mmorpgPlayer.sendMainChatPacket(l18n.$("player.skill.cast.notHave.magic", getCurrentComboString(), d$n(skill)));
                        mmorpgPlayer.playSound(Sound.BLOCK_ANVIL_DESTROY, 10f, 1f);
                        return;
                    }
                    mmorpgPlayer.sendMainChatPacket(l18n.$("player.skill.cast.success", getCurrentComboString(), d$n(skill), skill.getMagic()));
                    mmorpgPlayer.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 10f, 1f);

                    mmorpgPlayer.takeMagic(skill.getMagic());
                    mmorpgPlayer.cast(skill);

                    // combo finish to break combo
                    clearCombo();
                }
            }
        }
    }

    private String d$n(Skill skill) {

        return skill.getDisplayName() != null ? skill.getDisplayName() : skill.getName();
    }

    /**
     * 清除全部组合
     */
    public void clearCombo() {

        comboIndex = 0;
    }

    /**
     * 获取当前组合的 ID
     *
     * @return 组合 ID
     */
    public int getCurrentComboId() {

        return SkillComboManager.convertCombo(combos, comboIndex);
    }

    /**
     * 获取当前组合的字符串
     *
     * @return 组合字符串
     */
    public String getCurrentComboString() {

        if (this.comboIndex == 0) {

            return "";
        }
        checkExpired();

        ArrayList<SkillComboType> active = new ArrayList<>(this.comboIndex);

        for (int i = 0; i < this.comboIndex; i++) {

            active.add(combos[i]);
        }
        return SkillComboManager.getComboString(active);
    }

    /**
     * 给玩家添加技能
     *
     * @param skill 技能
     */
    @Override
    public void addSkill(Skill skill) {

        if(skill == null || !skill.hasCombo()) return;

        setSkill(skill, skill.getCombo());
    }

    /**
     * 给玩家删除技能
     *
     * @param skill 技能
     */
    @Override
    public void delSkill(Skill skill) {

        if(skill == null || !skill.hasCombo()) return;

        if(skillMap.containsKey(skill.getCombo())) {

            skillMap.remove(skill.getCombo());
        }
    }

    /**
     * 设置玩家的技能组合 ID
     *
     * @param skill 技能
     * @param combo 组合 ID
     */
    @Override
    public void setSkill(Skill skill, int combo) {

        if(skill == null || !skill.hasCombo()) return;

        delSkill(skill);
        skillMap.put(combo, skill);
    }

    /**
     * 设置玩家的技能组合类型
     *
     * @param skill  技能
     * @param combos 组合类型
     */
    @Override
    public void setSkill(Skill skill, SkillComboType[] combos) {

        setSkill(skill, SkillComboManager.convertCombo(combos));
    }

    /**
     * 清除技能
     */
    @Override
    public void clearSkill() {

        skillMap.clear();
    }

    /**
     * 获取当前组合的索引
     *
     * @return 已经组合的索引
     */
    @Override
    public int getComboIndex() {

        return comboIndex;
    }

    /**
     * 检测组合是否应该破坏
     */
    private void checkExpired() {

        if(comboIndex == combos.length || System.currentTimeMillis() - comboTime > interval) {

            clearCombo();
        }
    }
}
