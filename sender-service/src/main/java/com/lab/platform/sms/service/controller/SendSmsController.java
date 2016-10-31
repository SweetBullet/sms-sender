package com.lab.platform.sms.service.controller;

import com.lab.platform.sms.api.param.SendSmsParam;
import com.lab.platform.sms.api.service.SendSmsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * Created by pudongxu on 16/9/2.
 */

@Controller
@RequestMapping("/lab205")
public class SendSmsController {

//    @Resource
//    private SendSmsService sendSmsService;

//    @RequestMapping(value = "/send",method = RequestMethod.GET)
//    public boolean sendSms(SendSmsParam param) {
//        sendSmsService.sendSms(param);
//        return true;
//    }

    @RequestMapping(value = "/send",method = RequestMethod.GET)
    public void sendSms(@RequestParam("name")String name) {
        System.out.println(name);
    }
}
