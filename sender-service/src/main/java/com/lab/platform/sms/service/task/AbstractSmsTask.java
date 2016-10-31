package com.lab.platform.sms.service.task;


import com.lab.platform.sms.api.param.SendSmsParam;
import com.lab.platform.sms.service.dispatcher.TaskDispatcher;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Created by pudongxu on 16/8/27.
 */
public abstract class AbstractSmsTask implements Callable<Boolean> {

    @Setter
    private String userId;
    @Setter
    private String password;
    @Setter
    protected String msg;
    @Setter
    protected String mobile;
    @Setter
    protected   int retryTimes;
    public Logger logger = LoggerFactory.getLogger(AbstractSmsTask.class);

    @Setter
    private HttpClient client;
    @Setter
    private HttpPost post;
    @Setter
    private TaskDispatcher dispatcher;



    private boolean sendSms() {

        long time=System.currentTimeMillis();
        boolean success=false;
        try {
            HttpEntity entity = generateEntity();
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            logger.debug("短信方耗时:{}",System.currentTimeMillis()-time);

        } catch (Exception e) {

        }finally {

        }
        return true;
    }


    public abstract HttpEntity generateEntity();

    @Override
    public Boolean call() throws Exception {
        return sendSms();
    }

     public void retry() {
         try {
             dispatcher.sendSms(mobile,msg,retryTimes+1);
         } catch (Exception e) {
             logger.info("重试请求失败：", e);
         }
     }
}
