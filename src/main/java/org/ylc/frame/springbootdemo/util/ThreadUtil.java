package org.ylc.frame.springbootdemo.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 线程池工具
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/4/16 21:13
 */
public class ThreadUtil {

    private static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("thread-pool-d%").build();

    private static ExecutorService threadPool = new ThreadPoolExecutor(0, 10, 30L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), threadFactory, new ThreadPoolExecutor.AbortPolicy());


    public static void executeAsync(Runnable runnable) throws Exception {

        try {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            });
        } catch (Exception e) {
            throw new Exception("Exception when running task!", e);
        }
    }

    /**
     * 执行异步方法
     *
     * @param runnable 需要执行的方法体
     * @return 执行的方法体
     */
    // public static void excAsync(final Runnable runnable) {
    //     Thread thread = new Thread() {
    //         @Override
    //         public void run() {
    //             runnable.run();
    //         }
    //     };
    //     thread.start();
    // }

}
