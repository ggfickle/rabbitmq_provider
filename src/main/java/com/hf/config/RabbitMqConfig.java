package com.hf.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 在生产者代码创建队列
 */
@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${spring.rabbitmq.username}")
    private String rabbitmqUserName;

    @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword;

    @Value("${spring.rabbitmq.publisher-confirm-type}")
    private CachingConnectionFactory.ConfirmType confirmType;

    @Value("${spring.rabbitmq.publisher-returns}")
    private boolean publisherReturns;

    //配置连接工厂
    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(rabbitmqHost);
        cachingConnectionFactory.setUsername(rabbitmqUserName);
        cachingConnectionFactory.setPassword(rabbitmqPassword);
        cachingConnectionFactory.setPublisherConfirmType(confirmType);
        cachingConnectionFactory.setPublisherReturns(publisherReturns);
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        //需要传入
        RabbitAdmin rabbitAdmin = new RabbitAdmin(cachingConnectionFactory());
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);


        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("ConfirmCallback:     " + "相关数据：" + correlationData);
            System.out.println("ConfirmCallback:     " + "确认情况：" + ack);
            System.out.println("ConfirmCallback:     " + "原因：" + cause);
        });

        rabbitTemplate.setReturnsCallback(returned -> {
            System.out.println("ReturnCallback:     " + "消息：" + returned.getMessage());
            System.out.println("ReturnCallback:     " + "回应码：" + returned.getReplyCode());
            System.out.println("ReturnCallback:     " + "回应信息：" + returned.getReplyText());
            System.out.println("ReturnCallback:     " + "交换机：" + returned.getExchange());
            System.out.println("ReturnCallback:     " + "路由键：" + returned.getRoutingKey());
        });
        return rabbitTemplate;
    }
}
