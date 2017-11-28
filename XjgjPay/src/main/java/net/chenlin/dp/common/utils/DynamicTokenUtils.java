package net.chenlin.dp.common.utils;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 动态口令
 * @version 1.0
 * @author Andy Wang
 * @date 2017-11-27
 */
public class DynamicTokenUtils {

    /**
     * 加密算法的KEY枚举
     */
    public enum AlgorithmKey {
        MD5("MD5"),
        SHA("SHA");
        private String value;

        AlgorithmKey(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private static final char[] HEX_CODE = "0123456789abcdef".toCharArray();

    /**
     * 默认的秘钥部分(建议和可变动的秘钥结合使用，不建议使用固定不变的秘钥)
     */
    private static final String DEFAULT_KEY = "8b29f7";

    /**
     * 根据UUID生成唯一性的Token
     * @return
     */
    public static String getTokenByUUID() {
        return generateToken(UUID.randomUUID().toString());
    }

    /**
     * 根据精确到分钟的时间生成一个Token字符串
     * @param key
     * @return
     */
    public static String getTokenByTime(String key) {
        key = key + DEFAULT_KEY;
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");

        return generateToken(key + sdf.format(Calendar.getInstance().getTime()));
    }

    /**
     * 根据相差1分钟左右的时间生成一组Token字符串
     * （用于验证时避免因处理时间差造成的问题，建议将快一分钟、时间相同、慢一分钟三种情况下生成的Token均视为合法）
     * @param key
     * @return
     */
    public static List<String> listTokenByTime(String key) {
        key = key + DEFAULT_KEY;
        List<String> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");

        Calendar cal = Calendar.getInstance();
        result.add(generateToken(key + sdf.format(cal.getTime())));

        cal.add(Calendar.MINUTE, -1);
        result.add(generateToken(key + sdf.format(cal.getTime())));

        cal.add(Calendar.MINUTE, 2);
        result.add(generateToken(key + sdf.format(cal.getTime())));

        return result;
    }

    public static String toHexString(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(HEX_CODE[(b >> 4) & 0xF]);
            r.append(HEX_CODE[(b & 0xF)]);
        }
        return r.toString();
    }

    public static String generateToken(String key) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance(AlgorithmKey.MD5.getValue());
            algorithm.update(key.getBytes());
            byte[] msgDigest = algorithm.digest();
            return toHexString(msgDigest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
