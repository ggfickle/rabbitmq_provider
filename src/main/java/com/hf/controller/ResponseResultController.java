package com.hf.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.nacos.api.naming.NamingService;
import com.hf.annotation.PrintLog;
import com.hf.annotation.ResponseResult;
import com.hf.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/response")
@Slf4j
@RefreshScope
public class ResponseResultController {

    @ResponseResult
    @GetMapping("/test1")
    public List<String> testResponseResult() {
        log.info("开始查询所有数据...");

        List<String> findAllUser = new ArrayList<>();
        findAllUser.add("木子雷");
        findAllUser.add("公众号");

        log.info("使用 @ResponseResult 自定义注解进行响应的包装，使controller代码更加简介");
        return findAllUser;
    }

    @PrintLog
    @ResponseResult
    @GetMapping("/test2")
    @SentinelResource(value = "test2", blockHandler = "testBlock", fallback = "testFallback")
    public Map<String, Object> test2(Integer number) {
        try {
            log.info("IP1:{}", IpUtils.getInterIP1());
            log.info("IP2:{}", IpUtils.getInterIP2());
            log.info("IPV4:{}", IpUtils.getOutIPV4());
        } catch (Exception e) {
            log.error("获取id异常，{}", e.getLocalizedMessage());
        }
        if (number == 1) {
            throw new RuntimeException("运行时异常");
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("result", "info");
        return result;
    }

    /**
     * 此时ResponseResult注解不生效
     *
     * @param number
     * @param e
     * @return
     */
    @ResponseResult
    public Map<String, Object> testBlock(Integer number, BlockException e) {
        log.error(number + e.getLocalizedMessage());
//        throw new RuntimeException("人数众多");
        Map<String, Object> result = new HashMap<>();
        result.put("result", "当前参与活动的人数太多，请稍后再试");
        return result;
    }

    @ResponseResult
    public Map<String, Object> testFallback(Integer number, Throwable throwable) {
        log.error(number + throwable.getLocalizedMessage());
        HashMap<String, Object> result = new HashMap<>();
        result.put("result", "服务器开小差了，请稍后再试。");
        return result;
    }
}
