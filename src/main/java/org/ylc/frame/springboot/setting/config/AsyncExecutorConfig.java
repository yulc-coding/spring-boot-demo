package org.ylc.frame.springboot.setting.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * 配置异步执行
 * 注解 @EnableAsync 启用Spring的异步方法执行功能
 * 在需要异步的方法加上@Async注解可以实现异步
 * <p>
 * 注意：
 * 1. 异步方法不能是private 修饰的
 * 2. 同一个类中调用另一个@Async注解 的类不会异步执行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/3/30 11:15
 */
@Configuration
@EnableAsync
public class AsyncExecutorConfig implements AsyncConfigurer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 自定义线程池，若不重写会使用默认的线程池
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(2);
        // 最大线程数
        taskExecutor.setMaxPoolSize(10);
        // 队列大小
        taskExecutor.setQueueCapacity(15);
        // 线程名的前缀
        taskExecutor.setThreadNamePrefix("async-thread-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    /**
     * 捕捉IllegalArgumentException异常
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new MyAsyncExceptionHandler();
    }

    class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
            log.info("TASK Exception message - " + throwable.getMessage());
            log.info("Method name - " + method.getName());
            for (Object param : objects) {
                log.info("Parameter value - " + param);
            }
        }
    }
}
