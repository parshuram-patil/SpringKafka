package com.cs.interactors.kafka;

import com.cs.model.kafka.KafkaMessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerInteractor {

    @Autowired
    KafkaTemplate<String, KafkaMessageModel> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    public String sendMessage(KafkaMessageModel messageModel) {
        String messageInfo;

        try {
            kafkaTemplate.send(topic, messageModel.getKey(), messageModel).get();
            messageInfo = "Message Sent";
        } catch (Exception e) {
            messageInfo = "Cannot Send Message";
            e.printStackTrace();
        }

        System.out.println(messageInfo + " - " + messageModel.getKey());

        return messageInfo;
    }

}
