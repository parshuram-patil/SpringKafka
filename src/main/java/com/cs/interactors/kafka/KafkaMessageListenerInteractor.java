package com.cs.interactors.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

public class KafkaMessageListenerInteractor implements AcknowledgingMessageListener<String, String> {

  @Override
  public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment)
  {
    System.out.println("*********** onMessage() ---> " + data.value() + " ===> " + acknowledgment.getClass());
    acknowledgment.acknowledge();
  }
  
}
