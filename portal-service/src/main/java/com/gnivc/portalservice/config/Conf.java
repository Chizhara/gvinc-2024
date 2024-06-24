package com.gnivc.portalservice.config;

import com.gnivc.commonexception.StarterConfig;
import com.gnivc.kafka.KafkaConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({KafkaConfig.class, StarterConfig.class})
public class Conf {
}
