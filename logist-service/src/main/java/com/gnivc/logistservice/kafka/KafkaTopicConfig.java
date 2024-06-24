package com.gnivc.logistservice.kafka;

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
    @Value("${spring.kafka.topic.task.name}")
    private String taskTopic;
    @Value("${spring.kafka.topic.cargo.name}")
    private String cargoTopic;
    @Value("${spring.kafka.topic.driver.buffer.name}")
    private String driverBufTopic;
    @Value("${spring.kafka.topic.transport.buffer.name}")
    private String transportBufTopic;
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic taskTopic() {
        return new NewTopic(taskTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic cargoTopic() {
        return new NewTopic(cargoTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic driverBufTopic() {
        return new NewTopic(driverBufTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic transportBufTopic() {
        return new NewTopic(transportBufTopic, 1, (short) 1);
    }
}
