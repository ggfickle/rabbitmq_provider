package com.hf.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitConfig {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    /**
     * topic交换机
     * @return
     */
    @Bean
    public TopicExchange testTopicExchange() {
        return new TopicExchange("testTopicExchange",true, false);
    }

    @Bean
    public Queue testTopicQuqueMan() {
        Queue queue = new Queue("topic.man", true);
        //显式声明邮件队列
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public Queue testTopicQuqueWoman() {
        Queue queue = new Queue("topic.woman", true);
        //显式声明邮件队列
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    /**
     * 绑定队列到交换机
     * @return
     */
    @Bean
    public Binding bindingBuilderMan() {
        return  BindingBuilder.bind(testTopicQuqueMan()).to(testTopicExchange()).with("topic.man");
    }

    /**
     * 绑定队列到交换机
     * @return
     */
    @Bean
    public Binding bindingBuilderTopic() {
        return  BindingBuilder.bind(testTopicQuqueWoman()).to(testTopicExchange()).with("topic.#");
    }
}
