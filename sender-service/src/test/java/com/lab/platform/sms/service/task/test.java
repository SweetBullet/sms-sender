package com.lab.platform.sms.service.task;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
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
public class test {

    private final String siID="10659057101608";
    private final String secretKey = "hzask!@#7373";
    private final String restServiceUrl="http://115.239.136.21/zjisag/httpservices/capService";

    private static final String SI_ID = "10659057101608";
    private static final String SECRET_KEY = "hzask!@#7373";
    private static final String REST_SERVICE_URL = "http://115.239.136.21/zjisag/httpservices/capService";

    @Test
    public void test() {
        List<String> list = new ArrayList<String>();
        list.add("15267002519");
        sendMessage(list, "nihao");

    }

    public String sendMessage(List<String> num, String msg){
        //HCSender client= HCSender.getInstance();
        DateFormat format= new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //String siID = "10659057101608";
        //String secretKey = "hzask!@#7373";
        long currentTime = System.currentTimeMillis();
        String timeStamp = format.format(currentTime);
        String transactionID = timeStamp;
        String streamingNo = siID + transactionID;
        String authenticator = this.encoderByMd5(timeStamp + transactionID + streamingNo + secretKey);
        String destNumber="";
        for (String number : num) {
            destNumber = number + ",";
        }
        destNumber = destNumber.substring(0, destNumber.length() - 1);
        //System.out.println(destNumber);

        StringBuilder sendData = new StringBuilder();
        sendData.append("{\"StreamingNo\":").append("\""+streamingNo+"\""+",")
                .append("\"TimeStamp\":").append("\""+timeStamp+"\""+",")
                .append("\"TransactionID\":").append("\""+transactionID+"\""+",")
                .append("\"Authenticator\":").append("\""+authenticator+"\""+",")
                .append("\"SessionToken\":").append("\" \"").append(",")
                .append("\"SIID\":").append("\""+siID+"\""+",")
                .append("\"ParamItems\":[{\"paramID\":")
                .append("\""+"DestNumber"+"\""+",")
                .append("\"paramName\":null"+",")
                .append("\"paramValue\":").append("\""+destNumber+"\"").append("},")
                .append("{\"paramID\":").append("\""+"ExpandNumber"+"\""+",").append("\"paramName\":null"+",").append("\"paramValue\":").append("\"01\"").append("},")
                .append("{\"paramID\":").append("\""+"Message"+"\""+",").append("\"paramName\":null"+",").append("\"paramValue\":").append("\""+msg+"\"").append("}]}");
        System.out.println(sendData.toString());
       return null;
    }

    private  String encoderByMd5(String str) {
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


    @Test
    public void jsonTest() {
        Map<String, Object> param = new HashMap<>();
        DateFormat format= new SimpleDateFormat("yyyyMMddHHmmssSSS");
        long currentTime = System.currentTimeMillis();
        String timeStamp = format.format(currentTime);
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
        map.put("paramValue", "123");
        list.add(map);
        param.put("ParamItems", list);


        System.out.println(JSON.toJSONString(param));
    }

}
