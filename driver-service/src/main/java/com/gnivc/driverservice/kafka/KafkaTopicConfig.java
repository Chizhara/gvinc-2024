package com.gnivc.driverservice.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    @Value("${spring.kafka.topic.route.event.name}")
    private String routeEventTopic;
    @Value("${spring.kafka.topic.task.buffer.name}")
    private String taskBufTopic;
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic routeEventTopic() {
        return new NewTopic(routeEventTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic taskBufTopic() {
        return new NewTopic(taskBufTopic, 1, (short) 1);
    }
}
