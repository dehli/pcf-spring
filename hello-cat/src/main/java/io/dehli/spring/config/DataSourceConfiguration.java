package io.dehli.spring.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration extends AbstractCloudConfig {

    public static final String EXCHANGE_NAME = "messages";
    public static final String QUEUE_GENERIC_NAME = "messages";
    public static final String ROUTING_KEY = "messages";

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue appQueueGeneric() {
        Queue q = new Queue(QUEUE_GENERIC_NAME);
        rabbitAdmin().declareQueue(q);
        return q;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory().rabbitConnectionFactory("hello-cat-rabbitmq"));
    }

    @Bean
    public Binding declareBindingGeneric() {
        return BindingBuilder.bind(appQueueGeneric()).to(appExchange()).with(ROUTING_KEY);
    }

    @Bean
    public DataSource dataSource() {
        return connectionFactory().dataSource("hello-cat-mysql");
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return connectionFactory().redisConnectionFactory("hello-cat-redis");
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory().rabbitConnectionFactory("hello-cat-rabbitmq"));
    }
}
