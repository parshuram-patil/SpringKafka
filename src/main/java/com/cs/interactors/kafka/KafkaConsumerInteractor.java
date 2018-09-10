package com.cs.interactors.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.cs.proto.compile.klassInstance.KlassInstanceProto.KlassInstance;

@Component
public class KafkaConsumerInteractor {
  
  @KafkaListener(topics = "${kafka.topic}", containerFactory = "kafkaListenerContainerFactory")
  public String recieveMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment)
  { 
    System.out.println("*********** recieveMessage() ---> " + data.value() + " ===> " + acknowledgment.getClass());
    acknowledgment.acknowledge();
    return null;
  }
  
  @KafkaListener(topics = "${kafka.proto.topic}", containerFactory = "kafkaListenerContainerFactoryForKlassInstance")
  public String recieveMessage1(ConsumerRecord<String, KlassInstance> data, Acknowledgment acknowledgment)
  { 
    System.out.println("*********** recieveMessage() ---> " + data.key()/* + " ===> " + data.value()*/);
    acknowledgment.acknowledge();
    return null;
  }
  
}
