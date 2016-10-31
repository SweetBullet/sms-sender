package com.lab.platform.sms.service.dispatcher;

import com.lab.platform.sms.service.common.Channel;
import lombok.Getter;

import java.util.concurrent.*;

/**
 * Created by pudongxu on 16/8/27.
 */
public class TaskExecutor {

    private ExecutorService executor;
    @Getter
    private Channel channel;
    @Getter
    private int percent;

    public TaskExecutor(Channel channel,int percent) {
        this.channel=channel;
        this.executor = Executors.newFixedThreadPool(25);
        this.percent=percent;
    }

    public boolean equals(TaskExecutor other) {
        return this.channel==other.getChannel();
    }

    public <T> Future<T> submit(Callable<T> task) {
        return executor.submit(task);
    }

}
