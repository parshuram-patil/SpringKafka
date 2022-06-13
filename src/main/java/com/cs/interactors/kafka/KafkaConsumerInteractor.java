package com.cs.interactors.kafka;

import com.cs.model.kafka.KafkaMessageModel;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerInteractor {

    @KafkaListener(topics = "${kafka.topic}", containerFactory = "kafkaListenerContainerFactory")
    public String receiveMessage(ConsumerRecord<String, KafkaMessageModel> data, Acknowledgment acknowledgment) {
        System.out.println("Received Message - " + data.key());
        acknowledgment.acknowledge();
        return null;
    }

}
