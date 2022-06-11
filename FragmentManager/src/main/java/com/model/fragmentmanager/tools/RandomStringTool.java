package com.model.fragmentmanager.tools;

import java.util.Locale;
import java.util.Random;

/**
 * 随机字符串生成
 *
 * @author SongWenjun
 * @since 2022-06-10
 */
public class RandomStringTool {
    private static final String charArray = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

    /**
     * 字符集的长度
     */
    private static final int charLength = charArray.length();

    public static String randomStr(int length, Type type, boolean isContainNumber) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int randomNumberInRange = getRandomNumberInRange(isContainNumber ? 0 : 9, charLength - 1);
            if (randomNumberInRange >= charLength) {
                randomNumberInRange = charLength - 1;
            }
            sb.append(charArray.charAt(randomNumberInRange));
        }
        String randomStr = sb.toString();
        if (type == Type.LOWER_CASE) {
            return randomStr.toLowerCase(Locale.CHINESE);
        } else if (type == Type.UPPER_CASE) {
            return randomStr.toUpperCase(Locale.CHINESE);
        } else {
            return randomStr;
        }
    }

    public static String randomStr(int length) {
        return randomStr(length, Type.ALL_CASE, true);
    }

    public static String randomStr(int length, boolean isContainNumber) {
        return randomStr(length, Type.ALL_CASE, isContainNumber);
    }

    public static String randomUpperCaseStr(int length) {
        return randomStr(length, Type.UPPER_CASE, false);
    }

    public static String randomLowerCaseStr(int length) {
        return randomStr(length, Type.LOWER_CASE, false);
    }

    public static String randomUpperCaseStr(int length, boolean isContainNumber) {
        return randomStr(length, Type.UPPER_CASE, isContainNumber);
    }

    public static String randomLowerCaseStr(int length, boolean isContainNumber) {
        return randomStr(length, Type.LOWER_CASE, isContainNumber);
    }

    /**
     * 生成随机数
     *
     * @param min 最小值（包含）
     * @param max 最大值（包含）
     * @return 随机数
     */
    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public enum Type {
        UPPER_CASE,
        LOWER_CASE,
        ALL_CASE
    }
}
