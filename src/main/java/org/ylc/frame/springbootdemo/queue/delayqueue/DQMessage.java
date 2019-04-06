package org.ylc.frame.springbootdemo.queue.delayqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列消息体
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/3/30 16:13
 */
public class DQMessage implements Delayed {

    private int id;

    /**
     * 消息主体
     */
    private String body;

    /**
     * 执行的时间（毫秒）
     */
    private long executeTime;

    public DQMessage(int id, String body, long delayTime) {
        this.id = id;
        this.body = body;
        this.executeTime = System.currentTimeMillis() + delayTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.executeTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        DQMessage msg = (DQMessage) o;
        return Long.compare(this.executeTime, msg.executeTime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }
}
