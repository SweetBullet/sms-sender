package com.lab.platform.sms.service.common;

import lombok.Getter;

/**
 * Created by pudongxu on 16/8/27.
 */
public enum Channel {
    hongcheng(100);

    @Getter
    private int percent;

    Channel(int percent) {
        this.percent=percent;
    }
}
