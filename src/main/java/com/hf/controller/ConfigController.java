package com.hf.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ConfigController
 * @author: xiehongfei
 * @description:
 **/
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @RequestMapping("/get")
    @SentinelResource(value = "get", blockHandler = "deal_testHotKey", fallback = "test1")
    public String get(Integer a) {
        if (a == 2) {
            throw new RuntimeException("1");
        }
        return "1";
    }

    public String deal_testHotKey(BlockException e) {
        return "------deal_testHotKey,o(╥﹏╥)o" + e.getLocalizedMessage();  //sentinel系统默认的提示：Blocked by Sentinel (flow limiting)
    }

    public String test1() {
        return "------deal_testHotKey,o(╥﹏╥)o";  //sentinel系统默认的提示：fallback by Sentinel (flow limiting)
    }
}
