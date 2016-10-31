package com.lab.platform.sms.service.impl;

import com.lab.platform.sms.api.param.SendSmsParam;
import com.lab.platform.sms.api.service.SendSmsService;
import com.lab.platform.sms.service.dispatcher.TaskDispatcher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by pudongxu on 16/8/27.
 */
@Component
public class sendSmsServiceImpl implements SendSmsService {

    @Resource
    TaskDispatcher dispatcher;
    public boolean sendSms(SendSmsParam param) {
        dispatcher.sendSms(param.getMobile(),param.getMsg(),param.getRetryTimes());
        return true;
    }
}
