package org.ylc.frame.springbootdemo.queue.delayqueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;

/**
 * 消费者消费队列
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/3/30 16:17
 */
public class Consumer implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(Consumer.class);

    private DelayQueue<DQMessage> queue;

    public Consumer(DelayQueue<DQMessage> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                DQMessage take = queue.take();
                logger.info("消息：{},消费者获取信息转交给线程处理", take.getId());
                Executors.newSingleThreadExecutor().execute(new ConsumerThread(take));
                logger.info("消息：{},转交完成，等待下一队列", take.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
