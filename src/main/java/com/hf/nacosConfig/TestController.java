package com.hf.nacosConfig;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @NacosValue(value = "${people.enable:bbbbb}", autoRefreshed = true)
    private String enable;

    @Value("${people.enable:}")
    private String springEnable;

    @Autowired
    private Apple apple;

    @Autowired
    private TestConfiguration configuration;

    @Scheduled(cron = "0/10 * * * * *")
    public void print() {
        System.out.println(configuration.getCount());
    }

    @RequestMapping("/testGet")
    @ResponseBody
    public String testGet() {
        return enable + "-" + springEnable;
    }

    @GetMapping("/apple")
    public String getApplr() {
        return apple.toString();
    }

}
