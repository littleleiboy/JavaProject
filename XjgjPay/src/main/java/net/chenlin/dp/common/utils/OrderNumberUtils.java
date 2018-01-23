package net.chenlin.dp.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 订单流水号生成
 */
public class OrderNumberUtils {

    /**
     * 产生不重复的流水号(当前毫秒数+4位随机数)
     *
     * @return
     */
    public static String generateInMillis() {
        Long millis = Calendar.getInstance().getTimeInMillis();
        int four = (int) (Math.random() * 1000 + 1000);
        return String.valueOf(millis) + four;
    }

    /**
     * 产生不重复的流水号(当前精确到毫秒的时间15位数字+4位随机数)
     *
     * @return
     */
    public static String generateInTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmssSSS");
        String formatStr = formatter.format(new Date());
        int four = (int) (Math.random() * 1000 + 1000);
        return formatStr + String.valueOf(four);
    }

}
