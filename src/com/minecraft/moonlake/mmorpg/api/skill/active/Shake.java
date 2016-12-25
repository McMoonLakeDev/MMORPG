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
 
  
package com.minecraft.moonlake.mmorpg.api.skill.active;

import com.minecraft.moonlake.manager.BlockManager;
import com.minecraft.moonlake.mmorpg.api.adapter.entity.Entity;
import com.minecraft.moonlake.mmorpg.api.player.MMORPGPlayer;
import com.minecraft.moonlake.mmorpg.api.skill.AbstractSkill;
import com.minecraft.moonlake.mmorpg.api.skill.combo.SkillComboType;
import com.minecraft.moonlake.mmorpg.api.skill.reqatt.type.SkillReqAttDepend;
import com.minecraft.moonlake.mmorpg.manager.EntityManager;
import com.minecraft.moonlake.mmorpg.task.MMORPGRunnable;
import com.minecraft.moonlake.mmorpg.task.TaskManager;
import com.minecraft.moonlake.particle.ParticleEffect;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

/**
 * Created by MoonLake on 2016/6/6.
 */
public class Shake extends AbstractSkill {

    public Shake() {

        super("Shake");

        setMagic(25);
        setDisplayName("山崩地裂");
        setDescription("人物跃进并跳起，对落点 5 范围内怪物造成( 100 + X% )伤害");
        setCombo(new SkillComboType[] { SkillComboType.RIGHT, SkillComboType.LEFT, SkillComboType.RIGHT });
        addReqAtt(new SkillReqAttDepend(new Cleave(), 10));
    }

    /**
     * 释放此技能
     *
     * @param owner 释放者
     */
    @Override
    public void cast(MMORPGPlayer owner) {

        Vector vector = owner.getLocation().getDirection().multiply(1.2d).setY(0.6d);
        owner.setNoDamageTicks(100);
        owner.setVelocity(vector);

        TaskManager.runTimer(new MMORPGRunnable() {

            int living = 0;
            boolean done = false;
            MMORPGRunnable shake = null;

            @Override
            public void run() {

                if(living >= 12 * 20) {

                    done = true;
                }
                if(done) {

                    if(shake != null) {

                        shake.cancel();
                    }
                    cancel();
                }
                ParticleEffect.FLAME.display(owner.getLocation(), 32, 0.4f, 0.16f, 0.4f, 0f, 3);

                if(owner.isOnGround()) {

                    if(shake == null) {

                        owner.setVelocity(new Vector(0d, 1.2d, 0d));
                        ParticleEffect.FLAME.display(owner.getLocation(), 32, 2f, 0.6f, 2f, 0f, 100);

                        shake = new MMORPGRunnable() {

                            int count = 0;
                            boolean state = false;

                            @Override
                            public void run() {

                                if(owner.isOnGround() && !state) {

                                    state = true;
                                    owner.getWorld().playSound(owner.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10f, 1f);
                                    ParticleEffect.EXPLOSION_LARGE.display(owner.getLocation().add(0d, 0.8d, 0d), 32, 2.5f, 0.5f, 2.5f, 0f, 20);
                                }
                                if(state) {

                                    if(count >= 5) {

                                        done = true;
                                        return;
                                    }
                                    for(Block block : BlockManager.getFallingBlocksInRadius(owner.getLocation().clone().add(0d, -1d, 0d), count)) {

                                        FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation().clone().add(0d, 1.1d, 0d), block.getType(), block.getData());
                                        fallingBlock.setVelocity(new Vector(0f, 0.3f, 0f));
                                        fallingBlock.setDropItem(false);
                                        fallingBlock.setHurtEntities(false);

                                        getInstance().getFallingBlockListener().getFallingBlockList().add(fallingBlock);

                                        for(Entity entity : EntityManager.getEntityInRadius(fallingBlock.getLocation(), 4.5d, owner)) {

                                            entity.damage(10, owner);
                                            entity.setVector(new Vector(0d, 0.6d, 0d));
                                        }
                                    }
                                    count++;
                                }
                            }
                        };
                        shake.runTaskTimer(getInstance().getMain(), 10L, 1L);
                    }
                }
                living++;
            }
        }, 10L, 1L);
    }
}
