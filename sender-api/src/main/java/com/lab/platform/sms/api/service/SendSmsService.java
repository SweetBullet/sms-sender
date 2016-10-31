package com.lab.platform.sms.api.service;

import com.lab.platform.sms.api.param.SendSmsParam;

/**
 * Created by pudongxu on 16/8/27.
 */
public interface SendSmsService {
    boolean sendSms(SendSmsParam param);
}
