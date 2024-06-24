package com.gnivc.kafka;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@ComponentScan(basePackages = {"com.gnivc.kafka.consumer", "com.gnivc.kafka.producer"})
public class KafkaConfig {
}
