package org.ylc.frame.springboot.api.biz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.ylc.frame.springboot.api.setting.component.websocket.WebSocketServer;
import org.ylc.frame.springboot.api.setting.event.DemoEvent;
import org.ylc.frame.springboot.api.setting.component.SpringContextUtil;
import org.ylc.frame.springboot.common.base.HttpResult;

import java.util.List;

/**
 * 测试类接口
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/4/9 21:06
 */
@Api(value = "TestController")
@RestController
@RequestMapping("/test")
public class TestController {


    @ApiOperation(value = "事件测试API")
    @GetMapping("/event/{message}")
    @PostMapping
    public HttpResult eventApi(@ApiParam(name = "message", value = "信息内容")
                               @PathVariable String message) {
        SpringContextUtil.publishEvent(new DemoEvent(message));
        return HttpResult.success(message);
    }

    @ApiOperation(value = "wsPushApi")
    @GetMapping("/wsPush")
    public HttpResult wsPushApi(@ApiParam(name = "wsInfo", value = "要推送的内容")
                                @RequestParam(value = "wsInfo") String wsInfo,
                                @ApiParam(name = "ids", value = "推送人员")
                                @RequestParam(value = "ids") List<String> ids) {
        WebSocketServer.batchSendInfo(wsInfo, ids);
        return HttpResult.success(WebSocketServer.getIds());
    }

}
