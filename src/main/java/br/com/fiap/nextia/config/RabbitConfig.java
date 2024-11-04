package br.com.fiap.nextia.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfig {

    public static final String TASK_QUEUE = "task_queue";
    public static final String EXCHANGE = null;

    @Bean
    public Queue taskQueue() {
        return new Queue(TASK_QUEUE, true);
    }
}
