package com.gnivc.portalservice.config;

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
    @Value("${spring.kafka.topic.driver.name}")
    private String driverTopic;
    @Value("${spring.kafka.topic.transport.name}")
    private String transportTopic;
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic driverTopic() {
        return new NewTopic(driverTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic transportBufTopic() {
        return new NewTopic(transportTopic, 1, (short) 1);
    }
}
