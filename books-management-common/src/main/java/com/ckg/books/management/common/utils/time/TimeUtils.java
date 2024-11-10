package com.ckg.books.management.common.utils.time;

/**
 * 时间工具
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
public class TimeUtils {

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    /**
     * 分钟转毫秒
     *
     * @param minute 分钟数
     * @return 毫秒数
     */
    public static long minuteToMillisecond(long minute) {
        return minute * MILLIS_MINUTE;
    }
}
