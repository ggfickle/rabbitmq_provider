package com.hf.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * sentinel Controller
 */

@Slf4j
@RestController
@RequestMapping("/sentinel")
public class SentinelController {

    @GetMapping("/test1")
    @SentinelResource(value = "test1", blockHandler = "blockTest1", fallback = "fallback1")
    public String test1(Integer a) {
        if (a == 1) {
            throw new NullPointerException();
        }
        return "1";
    }

    public String blockTest1(Integer a, BlockException e) {
        return a + "出错了" + e.getLocalizedMessage();
    }

    public String fallback1(Integer a) {
        return "fallbalc1" + a;
    }
}
