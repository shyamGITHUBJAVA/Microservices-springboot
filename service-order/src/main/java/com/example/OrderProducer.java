package com.example;



import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "order_topic";  // Name of the Kafka topic

    public void sendOrderToInventory(String orderJson) {
        kafkaTemplate.send(TOPIC, orderJson);
    }
}
