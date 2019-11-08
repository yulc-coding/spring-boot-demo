package org.ylc.frame.springboot.biz.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 定时任务相关
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/11/8 20:43
 */
@Component
public class MySchedule {

    private static final Logger logger = LoggerFactory.getLogger(MySchedule.class);

    /**
     * 周期性执行
     * 注意：如果方法执行时间超过的任务调度的频率，调度会在下个周期执行
     * ex: 每10秒执行一次，0秒第一次执行，调度频率为10秒，如果防范执行时间小于10秒，下次任务在第10秒执行，
     * 如果方法执行了12秒，那么下一次在20秒的时候执行
     * Cron 表达式 生成器： http://qqe2.com/cron
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void cyclicalTask() {
        logger.info("This is a cyclical task begin => {}", System.currentTimeMillis());
        try {
            Thread.sleep(5000);
            // Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("This is a cyclical task end => {}", System.currentTimeMillis());
    }

    /**
     * 固定间隔任务
     * 每次任务执行完后固定间隔时间执行
     * 下一次的任务执行时间，是从方法最后一次任务执行结束时间开始计算。并以此规则开始周期性的执行任务。
     * ex: 每隔10秒执行一次，0秒第一次执行，方法执行了5秒，下次执行为第15秒
     */
    @Scheduled(fixedDelay = 1000 * 10)
    public void fixedDelayTask() {
        logger.info("This is a fixed delay task begin => {}", System.currentTimeMillis());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("This is a fixed delay task end => {}", System.currentTimeMillis());
    }

    /**
     * 固定频率任务，每10秒执行一次
     * 注意：当方法的执行时间超过任务调度频率时，调度器会在当前方法执行完成后立即执行下次任务。
     * ex: 每隔10秒执行一次，0秒第一次执行，方法执行小于10秒，则下次第10秒开始执行，
     * 如果方法执行了12秒，那么下一次执行方法的时间是第12秒。
     */
    @Scheduled(fixedRate = 1000 * 10)
    public void fixedRateTask() {
        logger.info("This is a fixed rate task begin => {}", System.currentTimeMillis());
        try {
            Thread.sleep(5000);
            // Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("This is a fixed rate task end => {}", System.currentTimeMillis());
    }


}
