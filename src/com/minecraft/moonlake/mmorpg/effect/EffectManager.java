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
 
  
package com.minecraft.moonlake.mmorpg.effect;

import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.manager.MMORPGManager;
import com.minecraft.moonlake.mmorpg.util.StringUtil;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/6/9.
 */
public final class EffectManager extends MMORPGManager {

    private final static Map<String, Effect> effectMap;
    private final static Map<String, String> effectSaltMap;

    static {

        effectMap = new HashMap<>();
        effectSaltMap = new HashMap<>();
    }

    /**
     * 给指定目标添加效果
     *
     * @param entity 实体
     * @param type 效果类型
     * @param time 效果时间
     */
    public static void addEffect(LivingEntity entity, EffectType type, int time) {

        if(entity != null && type != null && time > 0) {

            Effect effect = type.newInstance(time);

            if(effect != null) {

                effect.setTarget(entity);
                effect.start();

                putEffect(entity, effect);
            }
        }
    }

    /**
     * 给指定玩家添加效果
     *
     * @param mmorpgPlayer 玩家
     * @param type 效果类型
     * @param time 效果时间
     */
    public static void addEffect(MMORPGPlayer mmorpgPlayer, EffectType type, int time) {

        if(mmorpgPlayer != null && type != null) {

            addEffect(mmorpgPlayer.getBukkitPlayer(), type, time);
        }
    }

    /**
     * 获取指定实体是否拥有效果
     *
     * @param entity 实体
     * @param type 效果类型
     * @return true 实体拥有此效果 else 没有
     */
    public static boolean hasEffect(LivingEntity entity, EffectType type) {

        if(entity != null && type != null) {

            String realKey = entity.getUniqueId().toString() + ":" + type.getType();
            String key = StringUtil.MD5(realKey);

            if(effectSaltMap.containsKey(key == null ? "" : key)) {

                String salt = effectSaltMap.get(key);
                String saltKey = StringUtil.MD5(key + salt);

                return effectMap.containsKey(saltKey == null ? "" : saltKey);
            }
        }
        return false;
    }

    /**
     * 获取指定玩家是否拥有效果
     *
     * @param mmorpgPlayer 玩家
     * @param type 效果类型
     * @return true 实体拥有效果 else 没有
     */
    public static boolean hasEffect(MMORPGPlayer mmorpgPlayer, EffectType type) {

        return mmorpgPlayer != null && type != null && hasEffect(mmorpgPlayer.getBukkitPlayer(), type);
    }

    /**
     * 清除指定实体的效果
     *
     * @param entity 实体
     * @param type 效果类型
     */
    public static void removeEffect(LivingEntity entity, EffectType type) {

        String realKey = entity.getUniqueId().toString() + ":" + type.getType();
        String key = StringUtil.MD5(realKey);

        if(effectSaltMap.containsKey(key == null ? "" : key)) {

            String salt = effectSaltMap.get(key);
            String saltKey = StringUtil.MD5(key + salt);

            if(effectMap.containsKey(saltKey == null ? "" : saltKey)) {

                Effect effect = effectMap.get(saltKey);

                if(effect != null) {

                    ((AbstractEffect)effect).cancel();

                    effectMap.remove(saltKey);
                    effectSaltMap.remove(key);
                }
            }
        }
    }

    /**
     * 清除指定玩家的效果
     *
     * @param mmorpgPlayer 玩家
     * @param type 效果类型
     */
    public static void removeEffect(MMORPGPlayer mmorpgPlayer, EffectType type) {

        if(mmorpgPlayer != null && type != null) {

            removeEffect(mmorpgPlayer.getBukkitPlayer(), type);
        }
    }

    /**
     * 清除指定实体的所有效果
     *
     * @param entity 实体
     */
    public static void removeEffect(LivingEntity entity) {

        if(entity != null) {

            for(EffectType type : EffectType.values()) {

                removeEffect(entity, type);
            }
        }
    }

    /**
     * 清除指定玩家的所有效果
     *
     * @param mmorpgPlayer 玩家
     */
    public static void removeEffect(MMORPGPlayer mmorpgPlayer) {

        if(mmorpgPlayer != null) {

            removeEffect(mmorpgPlayer.getBukkitPlayer());
        }
    }

    /**
     * 将实体目标的效果加入到 Map 列表
     *
     * @param entity 实体
     * @param effect 实体效果
     */
    public static void putEffect(LivingEntity entity, Effect effect) {

        if(effect != null && entity != null) {

            String salt = StringUtil.getRandomString(6);
            String realKey = entity.getUniqueId().toString() + ":" + effect.getEffectType().getType();

            String key = StringUtil.MD5(realKey);
            String saltKey = StringUtil.MD5(key + salt);

            effectMap.put(saltKey, effect);
            effectSaltMap.put(key, salt);
        }
    }

    /**
     * 关闭 Map 效果并释放占用
     *
     * @param effect 效果对象
     */
    public static void closeEffect(Effect effect) {

        if(effect != null) {

            String realKey = ((AbstractEffect)effect).getEntity().getUniqueId().toString() + ":" + effect.getEffectType().getType();
            String key = StringUtil.MD5(realKey);

            if(effectSaltMap.containsKey(key == null ? "" : key)) {

                String salt = effectSaltMap.get(key);
                String saltKey = StringUtil.MD5(key + salt);

                if(effectMap.containsKey(saltKey == null ? "" : saltKey)) {

                    effectMap.remove(saltKey);
                }
                effectSaltMap.remove(key);
            }
        }
    }

    /**
     * 关闭对象类并释放内存占用
     */
    public static void close() {

        effectMap.clear();
        effectSaltMap.clear();
    }
}
