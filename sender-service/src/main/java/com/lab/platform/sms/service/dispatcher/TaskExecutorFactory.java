package com.lab.platform.sms.service.dispatcher;

import com.lab.platform.sms.service.common.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pudongxu on 16/8/27.
 */
public class TaskExecutorFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    private List<TaskExecutor> executors;

    private TaskExecutor getTaskExecutor(String mobile) {
        TaskExecutor executor= null;
        int lastNum = Integer.parseInt(mobile.substring(mobile.length()-2, mobile.length())) + 1;
        int loopSize = 0;
        for (TaskExecutor taskExecutor : executors) {
            loopSize += taskExecutor.getPercent();
            if (lastNum <= loopSize) {
                executor = taskExecutor;
                break;
            }
        }
        return executor;
    }

    public TaskExecutor getTaskExecutor(String mobile,int retryTimes) {

        Collections.sort(executors,(executor1, executor2)->
                Integer.compare(executor2.getPercent(),executor1.getPercent())
        );
        TaskExecutor executorService = getTaskExecutor(mobile);
        if(retryTimes==0) {
            logger.debug("首次尝试发送短信,选择通道:{}",executorService.getChannel().name());
            return executorService;
        }
        //重新选取tTaskExecutor
        List<TaskExecutor> newExecutors = new ArrayList<>();
        newExecutors.add(executorService);
        for (TaskExecutor taskExecutor : executors) {
            if(!taskExecutor.equals(executorService)&&taskExecutor.getPercent()!=0)
                newExecutors.add(taskExecutor);
        }
        int retryIndex = retryTimes % newExecutors.size();
        executorService = newExecutors.get(retryIndex);
        logger.debug("第{}次重发短信,选择通道:{}",retryTimes,executorService.getChannel().name());
        return executorService;

    }

    @PostConstruct
    public void init() {
        executors = new ArrayList<>(Channel.values().length);
        for (Channel channel : Channel.values()) {
            TaskExecutor executor = new TaskExecutor(channel, channel.getPercent());
            executors.add(executor);
        }
    }
}
