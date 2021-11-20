package com.hf.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.hf.annotation.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/response")
@Slf4j
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

    @GetMapping("/test2")
    @SentinelResource(value = "test2", blockHandler = "testBlock", fallback = "testFallback")
    public String test2(Integer number, Integer a) {
        if (number == 1) {
            throw new RuntimeException("运行时异常");
        }
        return "info";
    }

    public String testBlock(Integer number, Integer a, BlockException e){
//        throw new NullPointerException();
        log.error(e.getLocalizedMessage());
        return "当前参与活动的人数太多，请稍后再试";
    }

    public String testFallback(Integer number, Integer a, Throwable throwable) {
        log.error(throwable.getLocalizedMessage());
        return "服务器开小差了，请稍后再试。";
    }
}
