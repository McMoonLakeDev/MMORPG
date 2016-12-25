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
 
  
package com.minecraft.moonlake.mmorpg.api.pet;

import com.minecraft.moonlake.mmorpg.api.pet.excel.*;
import org.bukkit.entity.EntityType;

/**
 * Created by MoonLake on 2016/5/21.
 */
public enum PetType {

    COW("Cow", "Pet Cow", EntityType.COW, true, PetCow.class),
    PIG("Pig", "Pet Pig", EntityType.PIG, true, PetPig.class),
    DOG("Dog", "Pet Dog", EntityType.WOLF, true, PetDog.class),
    CAT("Cat", "Pet Cat", EntityType.OCELOT, true, PetCat.class),
    SHEEP("Sheep", "Pet Sheep", EntityType.SHEEP, true, PetSheep.class),
    SLIME("Slime", "Pet Slime", EntityType.SLIME, true, PetSlime.class),
    RABBIT("Rabbit", "Pet Rabbit", EntityType.RABBIT, false, PetRabbit.class),
    CHICKEN("Chicken", "Pet Chicken", EntityType.CHICKEN, true, PetChicken.class),
    SNOWMAN("Snowman", "Pet Snowman", EntityType.SNOWMAN, false, PetSnowman.class),
    POLAR_BEAR("PolarBear", "Pet PolarBear", EntityType.POLAR_BEAR, true, PetPolarBear.class),
    MAGMA_CUBE("MagmaCube", "Pet MagmaCube", EntityType.MAGMA_CUBE, true, PetMagmaCube.class),
    MUSHROOM_COW("MushroomCow", "Pet MushroomCow", EntityType.MUSHROOM_COW, true, PetMushroomCow.class),
    ;

    private String type;
    private String defName;
    private EntityType entityType;
    private boolean isBaby;
    private Class<? extends AbstractPet> clazz;

    PetType(String type, String defName, EntityType entityType, boolean isBaby, Class<? extends AbstractPet> clazz) {

        this.type = type;
        this.defName = defName;
        this.entityType = entityType;
        this.isBaby = isBaby;
        this.clazz = clazz;
    }

    /**
     * 获取宠物的类型名
     *
     * @return 类型名
     */
    public String getType() {

        return type;
    }

    /**
     * 获取宠物的默认名称
     *
     * @return 默认名称
     */
    public String getDefaultName() {

        return defName;
    }

    /**
     * 获取宠物的实体类型
     *
     * @return 实体类型
     */
    public EntityType getEntityType() {

        return entityType;
    }

    /**
     * 获取宠物是否为 Baby 类型
     *
     * @return Baby
     */
    public boolean isBaby() {

        return isBaby;
    }

    /**
     * 获取抽象宠物类的子类
     *
     * @return 子类
     */
    public Class<? extends AbstractPet> getClazz() {

        return clazz;
    }
}
