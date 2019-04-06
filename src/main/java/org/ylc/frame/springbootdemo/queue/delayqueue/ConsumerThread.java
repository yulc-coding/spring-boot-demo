package org.ylc.frame.springbootdemo.queue.delayqueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 延迟队列消费
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/3/30 16:21
 */
public class ConsumerThread implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(ConsumerThread.class);

    private DQMessage dqMessage;

    public ConsumerThread(DQMessage dqMessage) {
        this.dqMessage = dqMessage;
    }

    @Override
    public void run() {
        logger.info("消息：{},准备处理，休息10秒", dqMessage.getId());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("处理消息：{}，执行时间：{}，内容：{}", dqMessage.getId(), dqMessage.getExecuteTime(), dqMessage.getBody());
    }
}
