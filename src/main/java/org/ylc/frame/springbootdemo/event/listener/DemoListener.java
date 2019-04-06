package org.ylc.frame.springbootdemo.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.ylc.frame.springbootdemo.event.event.DemoEvent;

/**
 * 监听demo
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/3/30 16:07
 */
public class DemoListener implements ApplicationListener<DemoEvent> {

    private static final Logger logger = LoggerFactory.getLogger(DemoListener.class);

    /**
     * 执行事件，@Async 支持异步
     *
     * @param demoEvent 监听事件
     */
    @Async
    @Override
    public void onApplicationEvent(DemoEvent demoEvent) {
        logger.info("listener get event, sleep 2 second...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("event message is : {}", demoEvent.getMessage());
    }
}
