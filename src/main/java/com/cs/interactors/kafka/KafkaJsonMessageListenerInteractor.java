package com.cs.interactors.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

import com.cs.model.kafka.KafkaMessageModel;

public class KafkaJsonMessageListenerInteractor implements AcknowledgingMessageListener<String, KafkaMessageModel> {

  @Override
  public void onMessage(ConsumerRecord<String, KafkaMessageModel> data, Acknowledgment acknowledgment)
  {
    System.out.println("*********** Json onMessage() ---> " + data.value().getKey() + "::" + data.value().getValue() + " ===> " + acknowledgment.getClass());
    acknowledgment.acknowledge();
  }
  
}
