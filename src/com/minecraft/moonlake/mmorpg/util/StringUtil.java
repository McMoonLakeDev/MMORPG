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
 
  
package com.minecraft.moonlake.mmorpg.util;

import com.minecraft.moonlake.encrypt.md5.MD5;
import com.minecraft.moonlake.encrypt.md5.MD5Encrypt;
import com.minecraft.moonlake.mmorpg.manager.RandomManager;

import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MoonLake on 2016/5/14.
 */
public final class StringUtil extends com.minecraft.moonlake.util.StringUtil {

    private final static char[] STRING_CHAR;

    static {

        STRING_CHAR = new char[] {

                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
        };
    }

    /**
     * 将字符串进行格式化处理
     *
     * @param key 键
     * @param values 值
     * @return 格式化字符串
     */
    public static String format(String key, Object... values) {

        return MessageFormat.format(key, values);
    }

    /**
     * 获取布尔型值的真假字符串
     *
     * @param flag boolean
     * @return boolean string
     */
    public static String yesOrNo(boolean flag) {

        return flag ? "&a✔" : "&c✘";
    }

    /**
     * 将布尔型值的真假字符串转换为布尔型
     *
     * @param yesOrNo boolean string
     * @return boolean
     */
    public static boolean toFlag(String yesOrNo) {

        if(yesOrNo.equalsIgnoreCase("✔")) {

            return true;
        }
        else if(yesOrNo.equalsIgnoreCase("✘")) {

            return false;
        }
        return false;
    }

    /**
     * 将指定数组对象转换到列表集合对象
     *
     * @param array 数组
     * @param <T> 数组
     * @return 列表集合
     */
    public static <T> List<T> fromArray(T... array) {

        return array != null ? Arrays.asList(array) : null;
    }

    /**
     * 获取复制的字符串对象
     *
     * @param source 源字符串对象
     * @return 复制后的对象
     */
    public static String stringClone(String source) {

        String newValue = source == null ? "" : source;

        return newValue;
    }

    /**
     * 获取字符串列表的拷贝
     *
     * @param stringList 字符串列表
     * @param splitChar 分割字符
     * @return 字符串
     */
    public static String stringCopy(List<String> stringList, char splitChar) {

        String value = "";

        for(String string : stringList) {

            value += string + splitChar;
        }
        return value.substring(0, value.lastIndexOf(splitChar));
    }

    /**
     * 将指定字符串源进行 MD5 加密处理
     *
     * @param value 字符串源
     * @return MD5 的加密源 异常返回空
     */
    public static String MD5(String value) {

        return MD5(value, (byte)1, false);
    }

    /**
     * 将指定字符串源进行 MD5 加密处理
     *
     * @param value 字符串源
     * @param bit 加密源位数 (0 = 16 bit, 1 = 32 bit)
     * @param upCase 是否大写
     * @return  MD5 的加密源 异常返回空
     */
    public static String MD5(String value, byte bit, boolean upCase) {

        MD5Encrypt md5 = new MD5Encrypt(value);
        MD5 md5Data = md5.encrypt();

        if(md5Data != null) {

            if(bit == (byte)0) {

                if(upCase) {

                    return md5Data.to16BitUpperCase();
                }
                return md5Data.to16Bit();
            }
            else if(bit == (byte)1) {

                if(upCase) {

                    return md5Data.to32BitUpperCase();
                }
                return md5Data.to32Bit();
            }
            return md5Data.to32Bit();
        }
        return "";
    }

    /**
     * 获取随机长度的字符串
     *
     * @param length 长度
     * @return 随机字符串
     */
    public static String getRandomString(int length) {

        char[] valueChar = new char[length];

        for(int i = 0; i < valueChar.length; i++) {

            valueChar[i] = STRING_CHAR[RandomManager.getRandom().nextInt(STRING_CHAR.length)];
        }
        return new String(valueChar);
    }
    
    /**
     * 获取指定文件对象的文件名无后缀
     * 
     * @param file 文件对象
     * @return 文件名无后缀 没有则返回 null
     */
    public static String getFileNameNoSuffix(File file) {
    	
    	if(file == null || !file.exists()) return null;
    	
    	String name = file.getName();
    	
    	return name.substring(0, name.lastIndexOf("."));
    }

    /**
     * 获取指定字符串是否为整数型
     *
     * @param string 字符串
     * @return true 则是整数型 else 不是
     */
    public static boolean isInteger(String string) {

        boolean result = false;

        try {

            Integer.parseInt(string);
            result = true;
        }
        catch (Exception e) { }

        return result;
    }

    /**
     * 获取指定字符串是否为整数型比值 (15:22)
     *
     * @param string 字符串
     * @return true 则是整数型比值 else 不是
     */
    public static boolean isIntegerRatio(String string) {

        boolean result = false;

        try {

            if(string == null || !string.contains(":")) {

                return false;
            }
            Integer.parseInt(string.split(":")[0]);
            Integer.parseInt(string.split(":")[1]);
            result = true;
        }
        catch (Exception e) { }

        return result;
    }

    /**
     * 获取指定字符串是否为双精度浮点数型
     *
     * @param string 字符串
     * @return true 则是双精度浮点数型 else 不是
     */
    public static boolean isDouble(String string) {

        boolean result = false;

        try {

            Double.parseDouble(string);
            result = true;
        }
        catch (Exception e) { }

        return result;
    }
}
