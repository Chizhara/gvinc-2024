package com.gnivc.portalservice;

import com.gnivc.portalservice.model.company.Company;
import com.gnivc.portalservice.model.company.dto.CompanyInfo;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

public class testtt {
    public static void main(String[] args) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(configProps));

        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setKpp(123123L);


        kafkaTemplate.send("topic", companyInfo);
        while (true) {}
    }
}
