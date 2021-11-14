package com.hf.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hf.pojo.UserVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sendMessage")
public class SendMessageController {

    @Autowired
    @Qualifier("createRabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    /**
     * 使用消息队列发送消息
     * @return
     */
    @PostMapping("/send1")
    public String sendMessage() {
        UserVO userVO = new UserVO();
        userVO.setName("zhangsan");
        userVO.setId(1L);
        userVO.setAge(12);
        String result = JSON.toJSONString(userVO);
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirect", result);
        return HttpStatus.OK.getReasonPhrase();
    }

    @PostMapping("/sendTopicMessage1")
    public String sendTopicMessage1() {
        UserVO userVO = new UserVO();
        userVO.setName("李四");
        userVO.setId(9L);
        userVO.setAge(15);
        String result = JSON.toJSONString(userVO);
        rabbitTemplate.convertAndSend("testTopicExchange", "topic.man", result);
        return "ok";
    }

    @PostMapping("/sendTopicMessage2")
    public String sendTopicMessage2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: woman is all ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("testTopicExchange", "topic.woman", womanMap);
        return "ok";
    }
}
