package com.fernandez.quart.service;

public interface KafkaProducerService {

    void sendMessageToKafkaTopic(String topic, String message);
}
