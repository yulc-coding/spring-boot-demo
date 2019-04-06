package org.ylc.frame.springbootdemo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间相关工具
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/3/30 13:27
 */
public class DateUtils {

    /**
     * 获取今日日期 yyyy-MM-dd
     */
    public static String getCurDate() {
        LocalDate today = LocalDate.now();
        return today.toString();
    }

    /**
     * 获取当前时间 yyyy-MM-dd HH:mm:ss
     */
    public static String getCurDateTime() {
        LocalDateTime curDateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return curDateTime.format(dtf);

    }


    public static void main(String[] args) {
        System.out.println(getCurDateTime());

    }
}
