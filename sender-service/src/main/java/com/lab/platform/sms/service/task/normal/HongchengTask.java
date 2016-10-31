package com.lab.platform.sms.service.task.normal;

import com.alibaba.fastjson.JSON;
import com.lab.platform.sms.service.task.AbstractSmsTask;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.TargetAuthenticationStrategy;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pudongxu on 16/8/27.
 */
public class HongchengTask extends AbstractSmsTask {

    private static final String SI_ID = "10659057101608";
    private static final String SECRET_KEY = "hzask!@#7373";
    private static final String REST_SERVICE_URL = "http://115.239.136.21/zjisag/httpservices/capService";

    @Override
    public HttpEntity generateEntity() {

        Map<String, Object> param = new HashMap<>();
        String timeStamp = getTimeStamp();
        String transactionID = timeStamp;
        String streamingNo = SI_ID + transactionID;
        String authenticator = this.encoderByMd5(timeStamp + transactionID + streamingNo + SECRET_KEY);

        param.put("StreamingNo", streamingNo);
        param.put("TimeStamp", timeStamp);
        param.put("TransactionID", transactionID);
        param.put("Authenticator", authenticator);
        param.put("SessionToken", null);
        param.put("SIID", SI_ID);


        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("paramID", "DestNumber");
        map.put("paramName", null);
        map.put("paramValue", mobile);
        list.add(map);

        map = new HashMap<>();
        map.put("paramID", "ExpandNumber");
        map.put("paramName", null);
        map.put("paramValue", "01");
        list.add(map);

        map = new HashMap<>();
        map.put("paramID", "Message");
        map.put("paramName", null);
        map.put("paramValue", msg);
        list.add(map);

        param.put("ParamItems", list);


        return new StringEntity(JSON.toJSONString(param), "UTF-8");
    }


    private String getTimeStamp() {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return format.format(System.currentTimeMillis());
    }

    private String encoderByMd5(String str) {
        try {
            // 确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            // 加密后的字符串
            return base64en.encode(md5.digest(str.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("加密错误");
            return null;
        }
    }
}
