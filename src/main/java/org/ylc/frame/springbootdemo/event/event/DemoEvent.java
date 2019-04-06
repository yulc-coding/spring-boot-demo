package org.ylc.frame.springbootdemo.event.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;


/**
 * 事件demo
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/3/30 16:06
 */
public class DemoEvent extends ApplicationEvent {

    private static final Logger logger = LoggerFactory.getLogger(DemoEvent.class);

    /**
     * 接受信息
     */
    private String message;

    public DemoEvent(String message) {
        super(message);
        this.message = message;
        logger.info("add event success! message: {}", message);
    }

    public String getMessage() {
        return message;
    }

}
