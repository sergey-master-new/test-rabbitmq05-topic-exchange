package com.example.testrabbitmq05topicexchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfiguration {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.rabbitmq.host}")
    private String hostname;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.username}")
    private String userName;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    //    сonnectionFactory — для соединения с RabbitMQ
    @Bean
    public ConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(hostname);
        connectionFactory.setPassword(password);
        connectionFactory.setUsername(userName);
        connectionFactory.setVirtualHost(virtualHost);

        return connectionFactory;
    }

    //    rabbitAdmin — для регистрации/отмены регистрации очередей и т.п.;
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    //    rabbitTemplate — для отправки сообщений (producer);
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange("exchange-example-5");
        return rabbitTemplate;
    }

    @Bean
    public Queue myQueue1() {
        return new Queue("query-example-5-1");
    }

    @Bean
    public Queue myQueue2() {
        return new Queue("query-example-5-2");
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("exchange-example-5");
    }

    @Bean
    public Binding binding1(){
        return BindingBuilder.bind(myQueue1()).to(topicExchange()).with("*.orange.*.*");
    }

    @Bean
    public Binding binding1test(){
        return BindingBuilder.bind(myQueue2()).to(topicExchange()).with("*.*.rabbit.*");
    }

    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(myQueue2()).to(topicExchange()).with("*.*.rabbit");
    }

    @Bean
    public Binding binding3(){
        return BindingBuilder.bind(myQueue2()).to(topicExchange()).with("lazy.#");
    }
}
