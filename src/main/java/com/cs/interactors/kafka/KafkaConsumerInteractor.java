package com.cs.interactors.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerInteractor {
  
  @KafkaListener(topics = "${kafka.topic}", containerFactory = "kafkaListenerContainerFactory")
  public String recieveMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment)
  {
    System.out.println("*********** recieveMessage() ---> " + data.value() + " ===> " + acknowledgment.getClass());
    acknowledgment.acknowledge();
    return data.key();
  }
  
}
