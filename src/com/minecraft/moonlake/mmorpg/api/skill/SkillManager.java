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
 
  
package com.minecraft.moonlake.mmorpg.api.skill;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.skill.passive.Passive;
import com.minecraft.moonlake.mmorpg.api.skill.reqatt.SkillReqAtt;
import com.minecraft.moonlake.mmorpg.api.skill.reqatt.type.SkillReqAttDepend;
import com.minecraft.moonlake.mmorpg.api.skill.reqatt.type.SkillReqAttLevel;
import com.minecraft.moonlake.mmorpg.api.skill.talent.Talent;
import com.minecraft.moonlake.mmorpg.api.skill.target.SkillTarget;
import com.minecraft.moonlake.mmorpg.api.skill.target.SkillTargetBlock;
import com.minecraft.moonlake.mmorpg.api.skill.target.SkillTargets;
import com.minecraft.moonlake.mmorpg.api.skill.ultimate.Ultimate;
import com.minecraft.moonlake.mmorpg.manager.MMORPGManager;
import com.minecraft.moonlake.mmorpg.target.TargetHelper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

import java.util.List;
import java.util.Set;

/**
 * Created by MoonLake on 2016/6/1.
 */
public final class SkillManager extends MMORPGManager {

    /**
     * 将技能数据集合转换到字符串技能数据
     *
     * @param skillList 技能集合
     * @return 字符串技能数据 没有则返回 "none"
     */
    public static String toSkill(List<Skill> skillList) {

        return skillList != null && skillList.size() > 0 ? toSkill(skillList.toArray(new Skill[skillList.size()])) : "none";
    }

    /**
     * 将技能数据数组转换到字符串技能数据
     *
     * @param skills 技能数组
     * @return 字符串技能数据 没有则返回 "none"
     */
    public static String toSkill(Skill[] skills) {

        if(skills != null && skills.length > 0) {

            String data = "";

            for(Skill skill : skills) {

                data += skill.getName() + ":" + skill.getLevel() + ",";
            }
            return data.substring(0, data.lastIndexOf(","));
        }
        return "none";
    }

    /**
     * 将天赋数据集合转换到字符串天赋数据
     *
     * @param talentList 天赋集合
     * @return 字符串天赋数据 没有则返回 "none"
     */
    public static String toTalent(List<Talent> talentList) {

        return talentList != null && talentList.size() > 0 ? toTalent(talentList.toArray(new Talent[talentList.size()])) : "none";
    }

    /**
     * 将天赋数据数组转换到字符串天赋数据
     *
     * @param talents 天赋数组
     * @return 字符串天赋数据 没有则返回 "none"
     */
    public static String toTalent(Talent[] talents) {

        if(talents != null && talents.length > 0) {

            String data = "";

            for(Talent talent : talents) {

                data += talent.getName() + ":" + talent.getLevel() + ",";
            }
            return data.substring(0, data.lastIndexOf(","));
        }
        return "none";
    }

    /**
     * 将字符串技能数据转换到技能对象
     *
     * @param skillData 字符串技能数据
     * @return 技能数组 没有则返回 null
     */
    public static Skill[] fromSkill(String skillData) {

        if(skillData != null && (skillData.contains(":") || skillData.contains(","))) {

            String[] datas = skillData.replaceAll(" ", "").split(",");
            Skill[] skills = new Skill[datas.length];

            for(int i = 0; i < skills.length; i++) {

                String[] subDatas = datas[i].split(":");
                Skill skill = Skill.fromName.a(subDatas[0]);

                if(skill != null) {

                    skills[i] = skill;
                    skills[i].setLevel(Integer.parseInt(subDatas[1]));
                }
            }
            return skills;
        }
        return null;
    }

    /**
     * 将字符串天赋数据转换到天赋对象
     *
     * @param talentData 字符串天赋数据
     * @return 天赋数组 没有则返回 null
     */
    public static Talent[] fromTalent(String talentData) {

        if(talentData != null && (talentData.contains(":") || talentData.contains(","))) {

            String[] datas = talentData.replaceAll(" ", "").split(",");
            Talent[] talents = new Talent[datas.length];

            for (int i = 0; i < talents.length; i++) {

                String[] subDatas = datas[i].split(":");
                Talent talent = Talent.fromName.a(subDatas[0]);

                if(talent != null) {

                    talents[i] = talent;
                    talents[i].setLevel(Integer.parseInt(subDatas[1]));
                }
            }
            return talents;
        }
        return null;
    }

    /**
     * 将被动技能转换到字符串被动技能数据
     *
     * @param passive 被动技能对象
     * @return 字符串被动技能数据 没有则返回 "none"
     */
    public static String toPassive(Passive passive) {

        return passive != null ? (passive.getName() + ":" + passive.getLevel()) : "none";
    }

    /**
     * 将字符串被动技能数据转换到被动技能对象
     *
     * @param passiveData 字符串被动技能数据
     * @return 被动技能对象 没有则返回 null
     */
    public static Passive fromPassive(String passiveData) {

        if(passiveData != null && passiveData.contains(":")) {

            String[] datas = passiveData.replaceAll(" ", "").split(":");
            Passive passive = Passive.fromName.a(datas[0]);

            if(passive != null) {

                passive.setLevel(Integer.parseInt(datas[1]));
            }
            return passive;
        }
        return null;
    }

    /**
     * 将技能大招转换到字符串技能大招数据
     *
     * @param ultimate 技能大招对象
     * @return 字符串技能大招数据 没有则返回 "none"
     */
    public static String toUltimate(Ultimate ultimate) {

        return ultimate != null ? (ultimate.getName() + ":" + ultimate.getLevel()) : "none";
    }

    /**
     * 将字符串技能大招数据转换到技能大招对象
     *
     * @param ultimateData 字符串技能大招数据
     * @return 技能大招对象 没有则返回 null
     */
    public static Ultimate fromUltimate(String ultimateData) {

        if(ultimateData != null && ultimateData.contains(":")) {

            String[] datas = ultimateData.replaceAll(" ", "").split(":");
            Ultimate ultimate = Ultimate.fromName.a(datas[0]);

            if(ultimate != null) {

                ultimate.setLevel(Integer.parseInt(datas[1]));
            }
            return ultimate;
        }
        return null;
    }

    /**
     * 获取玩家的指定技能从技能名
     *
     * @param mmorpgPlayer 玩家
     * @param skillName 技能名
     * @return 玩家技能对象 没有则返回 null
     */
    public static Skill getPlayerSkillFromName(MMORPGPlayer mmorpgPlayer, String skillName) {

        Skill targetSkill = null;

        if(mmorpgPlayer != null && mmorpgPlayer.getRole() != null && skillName != null) {

            Set<Skill> skillSet = mmorpgPlayer.getSkill().getSkillList();

            for(Skill skill : skillSet) {

                if(compare(skill, skillName)) {

                    targetSkill = skill;
                    break;
                }
            }
        }
        return targetSkill;
    }

    /**
     * 比较两个技能对象的技能名是否符合
     *
     * @param source 源技能
     * @param target 目标技能
     * @return true 技能名符合 else 不符合
     */
    public static boolean compare(Skill source, Skill target) {

        return source != null && target != null && compare(source, target.getName());
    }

    /**
     * 比较源技能是否符合目标技能名
     *
     * @param source 源技能
     * @param targetName 目标技能名
     * @return true 技能名符合 else 不符合
     */
    public static boolean compare(Skill source, String targetName) {

        return source != null && targetName != null && source.getName().equals(targetName);
    }

    /**
     * 将此玩家强制释放目标技能
     *
     * @param mmorpgPlayer 玩家
     * @param skillTarget 目标技能
     */
    public static void cast(MMORPGPlayer mmorpgPlayer, SkillTarget skillTarget) {

        LivingEntity target = TargetHelper.getLivingTarget(mmorpgPlayer, skillTarget.getRange());

        if(target != null) {

            skillTarget.cast(mmorpgPlayer, target);
        }
    }

    /**
     * 将此玩家强制释放目标技能
     *
     * @param mmorpgPlayer 玩家
     * @param skillTargets 目标技能
     */
    public static void cast(MMORPGPlayer mmorpgPlayer, SkillTargets skillTargets) {

        List<LivingEntity> targets = TargetHelper.getLivingTargets(mmorpgPlayer, skillTargets.getRange());

        if(targets != null && targets.size() > 0) {

            skillTargets.cast(mmorpgPlayer, targets);
        }
    }

    /**
     * 将此玩家强制释放目标方块技能
     *
     * @param mmorpgPlayer 玩家
     * @param skillTargetBlock 目标方块技能
     */
    public static void cast(MMORPGPlayer mmorpgPlayer, SkillTargetBlock skillTargetBlock) {

        Block block = mmorpgPlayer.getTargetBlock((int)skillTargetBlock.getRange());

        if(block != null && block.getType() != Material.AIR) {

            skillTargetBlock.cast(mmorpgPlayer, block);
        }
    }

    /**
     * 给玩家发送技能 GUI 中技能需求属性未通过的消息
     *
     * @param skillReqAtt 技能需求属性对象
     */
    public static void onSkillReqAttGUINotHaveMessage(MMORPGPlayer mmorpgPlayer, SkillReqAtt skillReqAtt) {

        switch (skillReqAtt.getType()) {

            case LEVEL:

                mmorpgPlayer.l18n("player.skillGUI.skill.reqAtt.check.notHave.level", ((SkillReqAttLevel)skillReqAtt).getLevel());
                break;

            case DEPEND:

                mmorpgPlayer.l18n("player.skillGUI.skill.reqAtt.check.notHave.depend", ((SkillReqAttDepend)skillReqAtt).getDepend().getName(), ((SkillReqAttDepend)skillReqAtt).getDepend().getLevel());
                break;

            default:
                break;
        }
    }
}
