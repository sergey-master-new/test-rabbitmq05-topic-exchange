package com.example.testrabbitmq05topicexchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@EnableRabbit
@Component
public class RabbitMqListener {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private Random random = new Random();

    @RabbitListener(queues = "query-example-5-1")
    public void worker1(String message) throws InterruptedException {
        log.info("worker 1 : " + message);
    }

    @RabbitListener(queues = "query-example-5-2")
    public void worker2(String message) throws InterruptedException {
        log.info("worker 2 : " + message);
    }
}
