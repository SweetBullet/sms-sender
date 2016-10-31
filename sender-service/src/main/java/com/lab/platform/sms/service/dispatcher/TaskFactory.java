package com.lab.platform.sms.service.dispatcher;

import com.lab.platform.sms.service.common.Channel;
import com.lab.platform.sms.service.common.ChannelConfig;
import com.lab.platform.sms.service.task.AbstractSmsTask;
import lombok.Setter;
import org.apache.commons.codec.Charsets;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by pudongxu on 16/8/27.
 */
public class TaskFactory {

    @Setter
    private Map<String,ChannelConfig> configMap;

    private ChannelConfig config;

    private HttpClient client;

    private static final String REFLECT_PATH = "com.lab.platform.sms.service.task.normal.";
    public Logger logger = LoggerFactory.getLogger(getClass());


    public AbstractSmsTask createNewSmsTask(Channel channel) {
        AbstractSmsTask newTask=null;
        config = configMap.get(channel.name());

        String className = channel.name().substring(0, 1).toUpperCase()
                + channel.name().substring(1) + "Task";
        try {
            Class clazz = Class.forName(REFLECT_PATH+className);
            newTask=(AbstractSmsTask)clazz.newInstance();
        } catch (Exception e) {
            logger.error("there is no {}Task class",channel.name());
        }

        newTask.setClient(client);
        newTask.setUserId(config.getUserId());
        newTask.setPassword(config.getPassword());
        newTask.setPost(parseUrl2HttpPost(config.getUrl()));
        return newTask;
    }


    private HttpPost parseUrl2HttpPost(String url) {
        int protocolEnd = url.indexOf("://");
        String protocol = url.substring(0, protocolEnd);
        String remainUrl = url.substring(protocolEnd + 3);
        int hostEnd = remainUrl.indexOf("/");
        if (hostEnd == -1) {
            hostEnd = remainUrl.length() - 1;
        }
        String host = remainUrl.substring(0, hostEnd);
        HttpPost post;
        post = new HttpPost(url);
        post.setHeader("Host", host);
        if ("https".equalsIgnoreCase(protocol)) {
            post.setHeader("Scheme", "https");
        } else {
            post.setHeader("Scheme", "http");
        }
        return post;
    }

    @PostConstruct
    public void init() {
        ConnectionConfig connConfig = ConnectionConfig.custom().setCharset(Charsets.UTF_8).build();
        RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(500)
                .setSocketTimeout(3000).build();
        client = HttpClients.custom().setMaxConnPerRoute(64).setMaxConnTotal(512)
                .setDefaultConnectionConfig(connConfig).setDefaultRequestConfig(defaultRequestConfig).build();

    }
}
