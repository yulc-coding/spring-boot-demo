package org.ylc.frame.springbootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ylc.frame.springbootdemo.queue.delayqueue.Consumer;
import org.ylc.frame.springbootdemo.queue.delayqueue.DQMessage;

import java.util.concurrent.DelayQueue;

/**
 * 延迟队列配置
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/3/30 16:12
 */
@Configuration
public class DelayQueueConfig {

    @Bean
    public DelayQueue<DQMessage> initQueue() {
        DelayQueue<DQMessage> delayQueue = new DelayQueue<>();
        new Thread(new Consumer(delayQueue)).start();
        return delayQueue;
    }
}
