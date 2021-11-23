package com.hf.nacosConfig;

import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.spring.util.parse.DefaultPropertiesConfigParse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Service
public class PrintLogger {
    private static Logger logger = LoggerFactory.getLogger(PrintLogger.class);

    private static final String LOGGER_TAG = "logging.level.";

    @Autowired
    private LoggingSystem loggingSystem;

    @NacosConfigListener(dataId = "${nacos.example.listener.data-id}", timeout = 5000)
    public void onChange(String newLog) throws Exception {
        Properties properties = new DefaultPropertiesConfigParse().parse(newLog);
        for (Object t : properties.keySet()) {
            String key = String.valueOf(t);
            if (key.startsWith(LOGGER_TAG)) {
                String strLevel = (String) properties.getOrDefault(key, "info");
                LogLevel level = LogLevel.valueOf(strLevel.toUpperCase());
                loggingSystem.setLogLevel(key.replace(LOGGER_TAG, ""), level);
                logger.info("{}:{}", key, strLevel);
            }
        }
    }

    @PostConstruct
    public void printLogger() throws Exception {
        Executors.newSingleThreadExecutor().submit(() -> {
            while (true) {
                TimeUnit.SECONDS.sleep(5);
                logger.info("我是info级别日志");
                logger.error("我是error级别日志");
                logger.warn("我是warn级别日志");
                logger.debug("我是debug级别日志");
            }
        });
    }
}
