package com.lab.platform.sms.api.param;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

/**
 * Created by pudongxu on 16/8/27.
 */
@Data
@Builder
public class SendSmsParam {
    private String mobile;
    private String msg;
    private int retryTimes;
}
