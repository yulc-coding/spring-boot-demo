package org.ylc.frame.springboot.biz.sys.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ylc.frame.springboot.component.event.DemoEvent;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 样例
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-01-02
 */
@RestController
@RequestMapping("/example")
public class ExampleController {

    private final ApplicationEventPublisher applicationEventPublisher;

    public ExampleController(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @GetMapping("/event")
    public String event(String message) {
        applicationEventPublisher.publishEvent(new DemoEvent(message));
        return "success";
    }


}
