package com.hf.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * direct exchange(直连型交换机)
 */
@Configuration
public class DirectRabbitConfig {

    /**
     * 创建一个队列
     * @return
     */
    @Bean
    public Queue testDirectQueue() {
        return new Queue("TestDirectQueue", true);
    }

    /**
     * 创建交换机
     * @return
     */
    @Bean
    public DirectExchange testDirectExchange() {
        return new DirectExchange("TestDirectExchange", true, false);
    }

    /**
     * 将队列绑定到交换机上，并且设置一个路由名
     * @return
     */
    @Bean
    public Binding testBindDirect() {
        return BindingBuilder.bind(testDirectQueue()).to(testDirectExchange()).with("TestDirect");
    }

    @Bean
    public DirectExchange lonelyDirectExchange() {
        return new DirectExchange("lonelyDirectExchange");
    }
}
