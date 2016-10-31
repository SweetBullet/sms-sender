package com.lab.platform.sms.service.dispatcher;

import com.lab.platform.sms.service.task.AbstractSmsTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by pudongxu on 16/8/27.
 */
public class TaskDispatcher {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TaskFactory taskFactory;
    @Resource
    private  TaskExecutorFactory executorFactory;

    public void sendSms(String mobile,String msg,int retryTimes) {
        TaskExecutor executor = executorFactory.getTaskExecutor(mobile, retryTimes);
        logger.info("第{}次尝试发送短信,选择通道:{}",retryTimes+1,executor.getChannel().name());
        AbstractSmsTask task = taskFactory.createNewSmsTask(executor.getChannel());
        task.setMobile(mobile);
        task.setMsg(msg);
        task.setRetryTimes(retryTimes);
        task.setDispatcher(this);
        executor.submit(task);
    }
}
