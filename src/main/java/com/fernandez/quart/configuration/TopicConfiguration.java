package com.fernandez.quart.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfiguration {

    @Value("${myapp.topic1.name}")
    private String topic1Name;

    @Value("${myapp.topic1.partitions}")
    private int topic1Partitions;

    @Value("${myapp.topic1.replicas}")
    private int topic1Replicas;

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name(topic1Name)
                .partitions(topic1Partitions)
                .replicas(topic1Replicas)
                .build();
    }
}
