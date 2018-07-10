package org.sword.wechat4j.util;

import java.util.Random;

public class RandomNumberGenerator {
    private static final int defaultLength = 28;
    private static final String NUMBERS = "0123456789";

    /**
     * 获取一定长度的随机数字字符串
     *
     * @param length 指定数字长度
     * @return 一定长度的数字字符串
     */
    public static String generate(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(NUMBERS.length());
            sb.append(NUMBERS.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取默认长度的随机数字字符串
     *
     * @return 默认长度(28位)的数字字符串
     */
    public static String generate() {
        return generate(defaultLength);
    }

}
